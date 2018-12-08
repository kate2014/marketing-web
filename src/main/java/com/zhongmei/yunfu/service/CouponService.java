package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.controller.model.CouponSearchModel;
import com.zhongmei.yunfu.domain.entity.CouponEntity;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 优惠券 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
public interface CouponService extends IService<CouponEntity> {

    List<CouponEntity> selectByIds(Collection<Long> ids);

    List<CouponEntity> selectValidByIds(Collection<Long> ids);

    /**
     * 查询角色列表(分页)
     *
     * @param couponType
     * @param enableFlag
     * @param pageIdx
     * @param pageSize
     * @return
     */
    Page<CouponEntity> findListPage(Long brandIdenty, Long shopIdenty, Integer couponType, Integer enableFlag, String keyWord, int pageIdx, int pageSize);

    Page<CouponEntity> findListPage(CouponSearchModel searchModel);

    /**
     * 查询所有优惠券
     * @param brandIdenty
     * @param shopIdenty
     * @return
     */
    Page<CouponEntity> queryList(Long brandIdenty, Long shopIdenty, Integer type,int curPage, int pageSize);

    /**
     * 新增优惠卷
     *
     * @param coupon
     * @return
     */
    public boolean addCoupon(CouponEntity coupon);

    /**
     * 更新优惠卷
     *
     * @param coupon
     * @return
     */
    public boolean updateCoupon(CouponEntity coupon);

    /**
     * 删除优惠券
     *
     * @param id
     * @return
     */
    public boolean deleteCoupon(Long id);

    /**
     * 根据id查询新品推送
     *
     * @param id
     * @return
     */
    public CouponEntity queryByid(Long id);

    /**
     * 删除新品推送计划
     *
     * @param id
     * @return
     */
    public boolean enableCoupon(Long id, int planState);
}
