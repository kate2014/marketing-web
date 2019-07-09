package com.zhongmei.yunfu.controller.api;

import com.zhongmei.yunfu.controller.api.model.WxFeedbackReq;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.CutDownModel;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.FeedbackEntity;
import com.zhongmei.yunfu.domain.entity.StarRatingEntity;
import com.zhongmei.yunfu.domain.entity.TradeEntity;
import com.zhongmei.yunfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/wxapp/feedback")
public class FeedbackApiController {

    @Autowired
    FeedbackService mFeedbackService;
    @Autowired
    StarRatingService mStarRatingService;
    @Autowired
    CustomerService mCustomerService;
    @Autowired
    TradeService mTradeService;

    @RequestMapping("/addFeedback")
    public BaseDataModel addForm(ModelMap model,WxFeedbackReq mWxFeedbackReq) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {

            Long brandIdenty = mWxFeedbackReq.getBrandIdenty();
            Long shopIdenty = mWxFeedbackReq.getShopIdenty();

            CustomerEntity mCustomerEntity = mCustomerService.queryCustomerByMobile(brandIdenty,shopIdenty,mWxFeedbackReq.getMobile());

            FeedbackEntity mFeedbackEntity = new FeedbackEntity();
            mFeedbackEntity.setBrandIdenty(brandIdenty);
            mFeedbackEntity.setShopIdenty(shopIdenty);
            mFeedbackEntity.setTradeId(mWxFeedbackReq.getTradeId());
            mFeedbackEntity.setContent(mWxFeedbackReq.getContent());
            if(mWxFeedbackReq.getTradeId() != null){
                mFeedbackEntity.setType(1);
            }else {
                mFeedbackEntity.setType(2);
            }
            mFeedbackEntity.setStart(1);
            mFeedbackEntity.setCustomerId(mCustomerEntity.getId());
            mFeedbackEntity.setCustomerName(mCustomerEntity.getName());
            mFeedbackEntity.setStatusFlag(1);
            mFeedbackEntity.setServerCreateTime(new Date());

            int minScore = 0;

            minScore = mWxFeedbackReq.getHj();

            if(minScore > mWxFeedbackReq.getXg()){
                minScore = mWxFeedbackReq.getXg();
            }
            if(minScore > mWxFeedbackReq.getTd()){
                minScore = mWxFeedbackReq.getTd();
            }
            mFeedbackEntity.setMinScore(minScore);

            mFeedbackService.addFeedback(mFeedbackEntity);

            List<StarRatingEntity> listStar = new ArrayList<>();

            StarRatingEntity hjStarRatingEntity = new StarRatingEntity();
            hjStarRatingEntity.setBrandIdenty(brandIdenty);
            hjStarRatingEntity.setShopIdenty(shopIdenty);
            hjStarRatingEntity.setStarLable("服务环境");
            hjStarRatingEntity.setStarNum(mWxFeedbackReq.getHj());
            hjStarRatingEntity.setTradeId(mWxFeedbackReq.getTradeId());
            if(mWxFeedbackReq.getTradeId() != null){
                hjStarRatingEntity.setType(1);
            }else{
                hjStarRatingEntity.setType(2);
            }
            hjStarRatingEntity.setCustomerId(mCustomerEntity.getId());
            hjStarRatingEntity.setCustomerName(mCustomerEntity.getName());
            hjStarRatingEntity.setStatusFlag(1);
            hjStarRatingEntity.setServerCreateTime(new Date());

            listStar.add(hjStarRatingEntity);

            StarRatingEntity xgStarRatingEntity = new StarRatingEntity();
            xgStarRatingEntity.setBrandIdenty(brandIdenty);
            xgStarRatingEntity.setShopIdenty(shopIdenty);
            xgStarRatingEntity.setStarLable("服务效果");
            xgStarRatingEntity.setStarNum(mWxFeedbackReq.getXg());
            xgStarRatingEntity.setTradeId(mWxFeedbackReq.getTradeId());
            if(mWxFeedbackReq.getTradeId() != null){
                xgStarRatingEntity.setType(1);
            }else{
                xgStarRatingEntity.setType(2);
            }
            xgStarRatingEntity.setCustomerId(mWxFeedbackReq.getCustomerId());
            xgStarRatingEntity.setStatusFlag(1);
            xgStarRatingEntity.setServerCreateTime(new Date());

            listStar.add(xgStarRatingEntity);


            StarRatingEntity tdStarRatingEntity = new StarRatingEntity();
            tdStarRatingEntity.setBrandIdenty(brandIdenty);
            tdStarRatingEntity.setShopIdenty(shopIdenty);
            tdStarRatingEntity.setStarLable("服务态度");
            tdStarRatingEntity.setStarNum(mWxFeedbackReq.getTd());
            tdStarRatingEntity.setTradeId(mWxFeedbackReq.getTradeId());
            if(mWxFeedbackReq.getTradeId() != null){
                tdStarRatingEntity.setType(1);
            }else{
                tdStarRatingEntity.setType(2);
            }
            tdStarRatingEntity.setCustomerId(mWxFeedbackReq.getCustomerId());
            tdStarRatingEntity.setStatusFlag(1);
            tdStarRatingEntity.setServerCreateTime(new Date());

            listStar.add(tdStarRatingEntity);

            mStarRatingService.batchAddStar(listStar);

            if(mWxFeedbackReq.getTradeId() != null) {
                TradeEntity mTradeEntity = new TradeEntity();
                mTradeEntity.setId(mWxFeedbackReq.getTradeId());
                mTradeEntity.setFeedback(1);
                mTradeService.updateTrade(mTradeEntity);
            }

            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("添加评价反馈成功");
            mBaseDataModel.setData(true);
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("添加评价反馈失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }
}
