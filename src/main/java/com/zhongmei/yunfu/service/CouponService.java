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

    Page<CouponEntity> findBrandCouponList(CouponSearchModel searchModel);

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
    public boolean modfityCouponState(Long id, int planState);

    /**
     * 获取下发门店的优惠券信息
     * @param brandIdenty
     * @param sourceId
     * @return
     * @throws Exception
     */
    public List<CouponEntity> queryDataBySourceId(Long brandIdenty,Long sourceId)throws Exception;

    /**
     * 批量添加
     * @param listData
     * @return
     * @throws Exception
     */
    public boolean batchAdd(List<CouponEntity> listData)throws Exception;

    /**
     * 批量删除
     * @param ids
     * @return
     * @throws Exception
     */
    public boolean batchDelete(List<Long> ids)throws Exception;

    /**
     * 批量更新
     * @param entity
     * @param ids
     * @return
     * @throws Exception
     */
    public boolean batchUpdate(CouponEntity entity, String ids)throws Exception;

    /**
     * 根据sourceId修改对应的优惠券状态
     * @param state
     * @param sourceId
     * @return
     * @throws Exception
     */
    public boolean batchUpdateState(Long brandIdenty,Integer state,Long sourceId)throws Exception;

    /**
     * 根据sourceId删除对应的优惠券
     * @param sourceId
     * @return
     * @throws Exception
     */
    public boolean batchDeleteBySouceId(Long brandIdenty,Long sourceId)throws Exception;

    /**
     * 更新优惠券使用数量
     * @param entity
     * @return
     * @throws Exception
     */
    public Boolean modfityUseNumber(CouponEntity entity)throws Exception;
}
