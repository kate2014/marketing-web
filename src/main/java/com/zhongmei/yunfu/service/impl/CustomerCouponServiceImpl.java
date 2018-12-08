package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.controller.model.CustomerCouponModel;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.CustomerCouponEntity;
import com.zhongmei.yunfu.domain.entity.MarketingPutOnEntity;
import com.zhongmei.yunfu.domain.entity.bean.CustomerCouponReport;
import com.zhongmei.yunfu.domain.enums.StatusFlag;
import com.zhongmei.yunfu.domain.mapper.CustomerCouponMapper;
import com.zhongmei.yunfu.service.CustomerCouponService;
import com.zhongmei.yunfu.service.CustomerService;
import com.zhongmei.yunfu.service.MarketingPutOnService;
import com.zhongmei.yunfu.service.vo.CustomerCouponVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 会员优惠券关联表 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-29
 */
@Service
public class CustomerCouponServiceImpl extends ServiceImpl<CustomerCouponMapper, CustomerCouponEntity> implements CustomerCouponService {

    @Autowired
    MarketingPutOnService mMarketingPutOnService;
    @Autowired
    CustomerCouponService mCustomerCouponService;
    @Autowired
    CouponServiceImpl mCouponServiceImpl;
    @Autowired
    CustomerService customerService;

    @Override
    public int selectCouponEntityCount(Long customerId, Long shopId) {
        Collection<Long> customerIdsById = customerService.getCustomerIdsById(customerId);
        Wrapper wrapper = Condition.create().isWhere(true).in("b.customer_id", customerIdsById);
        return baseMapper.selectCouponEntityCount(wrapper);
    }

    @Override
    public List<CustomerCouponVo> getCouponEntityBy(Long shopIdenty, Long customerId) {
        //Map<Long, Long> couponIds = getCustomerCouponIdsMap(shopIdenty, customerId);
        Collection<Long> customerIdsById = customerService.getCustomerIdsById(customerId);
        Wrapper wrapper = Condition.create().isWhere(true).in("b.customer_id", customerIdsById);
        List<CustomerCouponVo> couponEntities = baseMapper.selectCouponEntity(wrapper);

        /*List<CustomerCouponVo> results = new ArrayList<>();
        couponEntities.forEach(it -> {
            CustomerCouponVo customerCouponVo = new CustomerCouponVo();
            customerCouponVo.setId(it.getId());
            customerCouponVo.setBrandIdenty(it.getBrandIdenty());
            customerCouponVo.setShopIdenty(it.getShopIdenty());
            customerCouponVo.setName(it.getName());
            customerCouponVo.setCouponType(it.getCouponType());
            customerCouponVo.setFullValue(it.getFullValue());
            customerCouponVo.setDiscountValue(it.getDiscountValue());
            customerCouponVo.setDishId(it.getDishId());
            customerCouponVo.setDishName(it.getDishName());
            customerCouponVo.setRemark(it.getRemark());
            customerCouponVo.setContent(it.getContent());
            customerCouponVo.setApplyDish(it.getApplyDish());
            customerCouponVo.setRestrictUseCommercial(it.getRestrictUseCommercial());
            customerCouponVo.setSharingPrivilegeType(it.getSharingPrivilegeType());
            customerCouponVo.setCouponState(it.getCouponState());
            customerCouponVo.setEndTime(it.getEndTime());
            customerCouponVo.setCustomerCouponId(getCustomerCouponId(couponIds, it.getId()));
            results.add(customerCouponVo);
        });*/

        return couponEntities;
    }

    public Map<Long, Long> getCustomerCouponIdsMap(Long shopIdenty, Long customerId) {
        Collection<Long> customerIdsById = customerService.getCustomerIdsById(customerId);
        CustomerCouponEntity entity = new CustomerCouponEntity();
        entity.setStatusFlag(StatusFlag.VALiD.value());
        entity.setShopIdenty(shopIdenty);
        entity.setStatus(1);
        Wrapper<CustomerCouponEntity> entityWrapper = new EntityWrapper<>(entity)
                .setSqlSelect("id", "coupon_id")
                .in("customer_id", customerIdsById);
        Map<Long, Long> couponIds = new HashMap<>();
        selectList(entityWrapper).forEach(it -> couponIds.put(it.getId(), it.getCouponId()));
        return couponIds;
    }

    private Long getCustomerCouponId(Map<Long, Long> couponIds, Long couponId) {
        Iterator<Long> iterator = couponIds.keySet().iterator();
        while (iterator.hasNext()) {
            Long customerCouponId = iterator.next();
            if (couponIds.get(customerCouponId).equals(couponId)) {
                return customerCouponId;
            }
        }
        return null;
    }

    @Override
    public Boolean addCustomerCoupon(CustomerCouponEntity mCustomerCoupon) throws Exception {

        Boolean isSuccess = insert(mCustomerCoupon);
        return isSuccess;
    }


    @Override
    public Boolean addCustomerCouponBatch(List<CustomerCouponEntity> listData) throws Exception {
        Boolean isSuccess = insertBatch(listData);
        return isSuccess;
    }

    @Override
    public List<CustomerCouponModel> queryCouponByCustomer(CustomerCouponEntity mCustomerCoupon) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("m.brand_identy", mCustomerCoupon.getBrandIdenty());
        eWrapper.eq("m.shop_identy", mCustomerCoupon.getShopIdenty());
        eWrapper.eq("m.customer_id", mCustomerCoupon.getCustomerId());
        eWrapper.eq("m.status", mCustomerCoupon.getStatus());
        eWrapper.eq("m.status_flag", 1);
        List<CustomerCouponModel> listData = baseMapper.selectCouponByCustomer(eWrapper);
        return listData;
    }

    @Override
    public List<CustomerCouponModel> queryCouponByCustomerGroup(Long brandIdenty, Long shopIdenty, String listCustomer, Integer status) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("m.brand_identy", brandIdenty);
        eWrapper.eq("m.shop_identy", shopIdenty);
        eWrapper.in("m.customer_id", listCustomer);
        eWrapper.eq("m.status", status);
        eWrapper.eq("m.status_flag", 1);
        List<CustomerCouponModel> listData = baseMapper.selectCouponByCustomer(eWrapper);
        return listData;
    }

    @Override
    public Integer queryCouponCountByCustomer(Long brandIdenty, Long shopIdenty, String listCustomer, Integer status) throws Exception {
        return null;
    }

    @Override
    public CustomerCouponEntity queryCouponById(Long id) throws Exception {
        CustomerCouponEntity cc = selectById(id);
        return cc;
    }

    @Override
    public Boolean modfiyCouponState(CustomerCouponEntity mCustomerCoupon) throws Exception {
        EntityWrapper<CustomerCouponEntity> eWrapper = new EntityWrapper<>(new CustomerCouponEntity());
        mCustomerCoupon.setServerUpdateTime(new Date());
        eWrapper.eq("id", mCustomerCoupon.getId());
        Boolean isSuccess = update(mCustomerCoupon, eWrapper);
        return isSuccess;
    }

    @Override
    public List<CustomerCouponReport> selectCouponReport(Long brandIdenty, Long shopIdenty, Date start, Date end, Integer status) throws Exception {

        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("brand_identy", brandIdenty);
        eWrapper.eq("shop_identy", shopIdenty);
        eWrapper.eq("enabled_flag", 1);
        eWrapper.eq("status_flag", 1);
        eWrapper.eq("status", status);
        eWrapper.between("server_create_time", start, end);

        List<CustomerCouponReport> listData = baseMapper.selectCouponReport(eWrapper);
        return listData;
    }

    @Override
    public List<CustomerCouponReport> selectCouponSource() throws Exception {

        Condition eWrapper = ConditionFilter.create();
        List<CustomerCouponReport> listData = baseMapper.selectCouponSource(eWrapper);
        return listData;
    }

    @Override
    public Boolean putOnCoupon(Long brandIdenty, Long shopIdenty, Long customerId, String wxOpenId, Integer sourceId, Integer palnId) throws Exception {

        //投放方案ID  1：进入小程序推送优惠券  2：参与砍价活动推送优惠券 3：注册为新会员推送优惠券 4：支付交易完成推送优惠券 5：预约完成推送优惠券
        MarketingPutOnEntity mMarketingPutOnEntity = mMarketingPutOnService.queryMarketingPutOnByType(brandIdenty, shopIdenty, palnId);
        if (mMarketingPutOnEntity != null && mMarketingPutOnEntity.getCouponId() != null) {
//            CouponEntity mCouponEntity = mCouponServiceImpl.queryByid(Long.valueOf(mMarketingPutOnEntity.getCouponId()));
            CustomerCouponEntity mCustomerCoupon = new CustomerCouponEntity();
            mCustomerCoupon.setBrandIdenty(brandIdenty);
            mCustomerCoupon.setShopIdenty(shopIdenty);
            mCustomerCoupon.setSourceId(sourceId);
            mCustomerCoupon.setCouponId(Long.valueOf(mMarketingPutOnEntity.getCouponId()));
            mCustomerCoupon.setCouponName(mMarketingPutOnEntity.getCouponName());
//            mCustomerCoupon.setCouponType(mCouponEntity.getCouponType());
            mCustomerCoupon.setCustomerId(customerId);
            mCustomerCoupon.setWxCustomerOpenid(wxOpenId);
            mCustomerCoupon.setStatus(1);
            mCustomerCoupon.setEnabledFlag(1);
            mCustomerCoupon.setServerCreateTime(new Date());
            mCustomerCoupon.setServerUpdateTime(new Date());
            Boolean isSuccess = mCustomerCouponService.addCustomerCoupon(mCustomerCoupon);
            return isSuccess;
        } else {
            return false;
        }

    }

    @Override
    public Page<CustomerCouponEntity> selectPage(Page<CustomerCouponEntity> page, Long customerId, Long shopId) {
        Collection<Long> idsById = customerService.getCustomerIdsById(customerId);
        CustomerCouponEntity customerCouponEntity = new CustomerCouponEntity();
        //customerCouponEntity.setBrandIdenty(req.getHeader().getBrandId());
        customerCouponEntity.setShopIdenty(shopId);
        customerCouponEntity.setCustomerId(customerId);
        return selectPage(page, new EntityWrapper<>(customerCouponEntity).in("customer_id", idsById));
    }
}
