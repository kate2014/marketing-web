package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.domain.mapper.WxTradeCustomerMapper;
import com.zhongmei.yunfu.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 微信小程序购买使用记录 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-17
 */
@Service
public class WxTradeCustomerServiceImpl extends ServiceImpl<WxTradeCustomerMapper, WxTradeCustomerEntity> implements WxTradeCustomerService {

    @Autowired
    CollageMarketingService mCollageMarketingService;
    @Autowired
    CollageCustomerService mCollageCustomerService;
    @Autowired
    CutDownMarketingService mCutDownMarketingService;
    @Autowired
    FlashSalesMarketingService mFlashSalesMarketingService;

    @Override
    public Boolean addWxTradeCustomer(WxTradeCustomerEntity mWxTradeCustomer) throws Exception {

        Boolean isSuccess = insert(mWxTradeCustomer);
        return isSuccess;
    }

    @Override
    public Boolean modfiyWxTradeCustomer(WxTradeCustomerEntity mWxTradeCustomer) throws Exception {
        EntityWrapper<WxTradeCustomerEntity> eWrapper = new EntityWrapper<>(new WxTradeCustomerEntity());
        eWrapper.eq("id", mWxTradeCustomer.getId());
        Boolean isSuccess = update(mWxTradeCustomer, eWrapper);
        return isSuccess;
    }

    @Override
    public Boolean modfiyEnabledFlag(WxTradeCustomerEntity mWxTradeCustomer) throws Exception {
        EntityWrapper<WxTradeCustomerEntity> eWrapper = new EntityWrapper<>(new WxTradeCustomerEntity());
        eWrapper.eq("trade_id", mWxTradeCustomer.getTradeId());
        Boolean isSuccess = update(mWxTradeCustomer, eWrapper);

        return isSuccess;
    }

    @Override
    public WxTradeCustomerEntity queryDetailById(Long id) {
        WxTradeCustomerEntity mWxTradeCustomer = selectById(id);
        return mWxTradeCustomer;
    }

    @Override
    public WxTradeCustomerEntity queryByTradeId(Long tradeId) throws Exception{
        EntityWrapper<WxTradeCustomerEntity> eWrapper = new EntityWrapper<>(new WxTradeCustomerEntity());
        eWrapper.eq("trade_id", tradeId);
        eWrapper.setSqlSelect("id,trade_id,marketing_id,relate_id,customer_id,type,status,shop_identy,brand_identy");
        WxTradeCustomerEntity mWxTradeCustomer = selectOne(eWrapper);
        return mWxTradeCustomer;
    }

    @Override
    public List<WxTradeCustomerEntity> queryListDataByCustomerId(WxTradeCustomerEntity mWxTradeCustomer) throws Exception{
        EntityWrapper<WxTradeCustomerEntity> eWrapper = new EntityWrapper<>(new WxTradeCustomerEntity());
        eWrapper.eq("brand_identy", mWxTradeCustomer.getBrandIdenty());
        eWrapper.eq("shop_identy", mWxTradeCustomer.getShopIdenty());
        eWrapper.eq("customer_id", mWxTradeCustomer.getCustomerId());
        eWrapper.eq("status", 1);
        eWrapper.eq("enabled_flag", 1);
        eWrapper.eq("status_flag", 1);
        eWrapper.orderBy("server_create_time",false);
        List<WxTradeCustomerEntity> lsitData = selectList(eWrapper);

        return lsitData;
    }

    //验证活动是否可以参与
    @Override
    public String checkMarketingVaild(TradeModel mTradeModel)throws Exception{

        //判断会员是否参与过该活动，目前限制一活动同一人只能参与一次
        int count = queryJoinCountByCustomer(mTradeModel);
        if(count>0){
            return "您已参与过该活动，不允许多次参与，请在-我的-订单中心查看";
        }

        //拼团
        if(mTradeModel.getType() == 1){
            //获取团购活动信息
            CollageMarketingEntity mCollageMarketingEntity = mCollageMarketingService.queryCollageById(mTradeModel.getMarketingId());

            if(mCollageMarketingEntity.getEnabledFlag() == 2){
                return "活动已停止参与";
            }

            //判断是开团还是参团, 为null则表示开团
            if(mTradeModel.getRelationId() == null){

                //判断活动有效期
                if(mCollageMarketingEntity.getEndTime().getTime() < new Date().getTime()){
                    return "活动已结束";
                }

                if(mCollageMarketingEntity.getBeginTime().getTime() > new Date().getTime()){
                    return "活动还未开始";
                }

                //判断开团数是否大于活动开团上线
                if(mCollageMarketingEntity.getMaxOpenGroup() <= mCollageMarketingEntity.getOpenGroupCount()){
                    return "该拼团活动已达开团上线";
                }
            }else{
                //判断参团数是否已到上线
                CollageCustomerEntity mCollageCustomerEntity = mCollageCustomerService.queryCollage(mTradeModel.getRelationId());
                int joinCount = mCollageCustomerEntity.getJoinCount();
                int peopleCount = mCollageMarketingEntity.getCollagePeopleCount();

                if(mCollageCustomerEntity.getState() == 2){
                    return "该团已成团，如需购买可发起新团";
                }else if(mCollageCustomerEntity.getState() == 3){
                    return "该团已拼团失败";
                }
                if(joinCount >= peopleCount){
                    return "已达参团上线";
                }
                if(mCollageMarketingEntity.getEndTime().getTime() < new Date().getTime()){
                    return "活动已结束";
                }
            }
        }
        //砍价
        if(mTradeModel.getType() == 2){
            CutDownMarketingEntity mCutDownMarketingEntity = mCutDownMarketingService.findCutDownDatailById(mTradeModel.getMarketingId());
            if(mCutDownMarketingEntity.getEnabledFlag() == 2){
                return "活动已停止参与";
            }
            if(mCutDownMarketingEntity.getBeginTime().getTime() > new Date().getTime()){
                return "活动还未开始";
            }
            if(mCutDownMarketingEntity.getEndTime().getTime() < new Date().getTime()){
                return "活动已结束";
            }
            if(mCutDownMarketingEntity.getSalesCount() <= mCutDownMarketingEntity.getSoldCount()){
                return "活动已售罄";
            }

        }
        //秒杀
        if(mTradeModel.getType() == 3){
            FlashSalesMarketingEntity mFlashSalesMarketingEntity = mFlashSalesMarketingService.queryFlashSalesById(mTradeModel.getMarketingId());
            if(mFlashSalesMarketingEntity.getEnabledFlag() == 2){
                return "活动已停止参与";
            }
            if(mFlashSalesMarketingEntity.getBeginTime().getTime() > new Date().getTime()){
                return "活动还未开始";
            }
            if(mFlashSalesMarketingEntity.getEndTime().getTime() < new Date().getTime()){
                return "活动已结束";
            }
            if(mFlashSalesMarketingEntity.getSalesCount() <= mFlashSalesMarketingEntity.getSoldCount()){
                return "活动已售罄";
            }
        }

        return "";
    }

    /**
     * 查询活动会员参与次数
     * @param mTradeModel
     * @return
     */
    @Override
    public Integer queryJoinCountByCustomer(TradeModel mTradeModel) throws Exception{
        EntityWrapper<WxTradeCustomerEntity> eWrapper = new EntityWrapper<>(new WxTradeCustomerEntity());
        eWrapper.eq("brand_identy", mTradeModel.getBrandIdenty());
        eWrapper.eq("shop_identy", mTradeModel.getShopIdenty());
        eWrapper.eq("customer_id", mTradeModel.getCustomerId());
        eWrapper.eq("marketing_id", mTradeModel.getMarketingId());
        eWrapper.eq("type", mTradeModel.getType());
//        eWrapper.eq("enabled_flag", 1);
        eWrapper.eq("status_flag", 1);
        Integer count = selectCount(eWrapper);

        return count;
    }

    @Override
    public Boolean modfiyEnabledFlagByMainCollage(Long collageMainId) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.orNew().eq("id",collageMainId).or().eq("relation_id",collageMainId);

        Boolean isSuccess = baseMapper.modfiyEnabledFlagByMainCollage(eWrapper);
        return null;
    }

    //查询会员参与活动信息，可用来判断该会员是否参与过该活动
    public String queryJoinCountByCollageId(TradeModel mTradeModel) throws Exception{
        //判断用户是否参与过该活动，目前一个活动同一用户只能参加一次
        CollageCustomerEntity mCollageCustomer = new CollageCustomerEntity();
        mCollageCustomer.setShopIdentity(mTradeModel.getShopIdenty());
        mCollageCustomer.setBrandIdentity(mTradeModel.getBrandIdenty());
        mCollageCustomer.setCustomerId(mTradeModel.getCustomerId());
        mCollageCustomer.setCollageId(mTradeModel.getMarketingId());
        mCollageCustomer.setStatusFlag(1);
        mCollageCustomer.setEnabledFlag(1);

        CollageCustomerEntity cce = mCollageCustomerService.queryCollageByCustomerId(mCollageCustomer);
        if(cce != null){
            return "已参与过该活动，不允许多次参与";
        }else{
            return null;
        }
    }
}
