package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.controller.model.CouponSearchModel;
import com.zhongmei.yunfu.domain.entity.CouponEntity;
import com.zhongmei.yunfu.domain.enums.StatusFlag;
import com.zhongmei.yunfu.domain.mapper.CouponMapper;
import com.zhongmei.yunfu.service.CouponService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 优惠券 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-26
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, CouponEntity> implements CouponService {

    @Override
    public List<CouponEntity> selectByIds(Collection<Long> ids) {
        CouponEntity entity = new CouponEntity();
        //entity.setStatusFlag(StatusFlag.VAILD.value());
        Wrapper<CouponEntity> entityWrapper = new EntityWrapper<>(entity).in("id", ids);
        return selectList(entityWrapper);
    }

    @Override
    public List<CouponEntity> selectValidByIds(Collection<Long> ids) {
        CouponEntity entity = new CouponEntity();
        entity.setStatusFlag(StatusFlag.VALiD.value());
        Wrapper<CouponEntity> entityWrapper = new EntityWrapper<>(entity).in("id", ids);
        //entityWrapper.gt("end_time", DateFormatUtil.format(new Date(), DateFormatUtil.FORMAT_MYSQL_TIMESTAMP));
        entityWrapper.and("end_time >= NOW()");
        return selectList(entityWrapper);
    }

    @Override
    public Page<CouponEntity> findListPage(Long brandIdenty, Long shopIdenty, Integer couponType, Integer couponState, String keyWord, int curPage, int pageSize) {
        CouponEntity coupon = new CouponEntity();

        if (couponType != null && couponType != 0) {
            coupon.setCouponType(couponType);
        }

        if (couponState != null && couponState != 0) {
            coupon.setCouponState(couponState);
        }
        coupon.setShopIdenty(shopIdenty);
        coupon.setBrandIdenty(brandIdenty);
        //coupon
        Page<CouponEntity> page = new Page<>(curPage, pageSize);
        EntityWrapper<CouponEntity> eWrapper = new EntityWrapper<>(coupon);
        eWrapper.setSqlSelect("id", "name", "brand_identy", "shop_identy", "coupon_type", "push_number", "use_number", "coupon_state", "end_time", "server_create_time");
        eWrapper.and().like("name", keyWord);
        eWrapper.and().eq("status_flag", StatusFlag.VALiD.value());
        eWrapper.orderBy("server_create_time",false);
        Page<CouponEntity> roleDOList = selectPage(page, eWrapper);
        return roleDOList;

    }

    @Override
    public Page<CouponEntity> findListPage(CouponSearchModel searchModel) {
        CouponEntity coupon = new CouponEntity();
        if (searchModel.getCouponType() != 0) {
            coupon.setCouponType(searchModel.getCouponType());
        }

        if (searchModel.getCouponState() != 0) {
            coupon.setCouponState(searchModel.getCouponState());
        }
        coupon.setBrandIdenty(searchModel.getUser().getBrandIdenty());
        coupon.setShopIdenty(searchModel.getUser().getShopIdenty());
        Page<CouponEntity> page = new Page<>(searchModel.getPageNo(), searchModel.getPageSize());
        EntityWrapper<CouponEntity> eWrapper = new EntityWrapper<>(coupon);
        Page<CouponEntity> roleDOList = selectPage(page, eWrapper);
        return roleDOList;
    }

    @Override
    public Page<CouponEntity> queryList(Long brandIdenty, Long shopIdenty, Integer type,int curPage, int pageSize) {
        CouponEntity coupon = new CouponEntity();

        EntityWrapper<CouponEntity> eWrapper = new EntityWrapper<>(coupon);
        eWrapper.setSqlSelect("id", "name", "brand_identy", "shop_identy", "coupon_type", "push_number", "use_number", "coupon_state", "end_time", "server_create_time");
        eWrapper.and().eq("brand_identy", brandIdenty);
        eWrapper.and().eq("shop_identy", shopIdenty);
        eWrapper.and().eq("status_flag", StatusFlag.VALiD.value());
        eWrapper.and().eq("coupon_type", type);
        eWrapper.orderBy("server_create_time",false);
        Page<CouponEntity> page = new Page<>(curPage, pageSize);
        Page<CouponEntity> listData = selectPage(page, eWrapper);

        return listData;
    }

    @Override
    public boolean addCoupon(CouponEntity coupon) {
        return insert(coupon);
    }

    @Override
    public boolean updateCoupon(CouponEntity coupon) {
        return updateById(coupon);
    }

    @Override
    public boolean deleteCoupon(Long id) {
        CouponEntity coupon = selectById(id);
        coupon.setStatusFlag(2);
        return updateById(coupon);
    }

    @Override
    public CouponEntity queryByid(Long id) {
        return selectById(id);
    }

    @Override
    public boolean enableCoupon(Long id, int planState) {
        CouponEntity coupon = selectById(id);
        coupon.setCouponState(planState);
        return updateById(coupon);
    }


}