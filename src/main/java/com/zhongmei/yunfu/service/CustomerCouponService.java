package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.controller.model.CustomerCouponModel;
import com.zhongmei.yunfu.domain.entity.CouponEntity;
import com.zhongmei.yunfu.domain.entity.CustomerCouponEntity;
import com.zhongmei.yunfu.domain.entity.bean.CustomerCouponReport;
import com.zhongmei.yunfu.service.vo.CustomerCouponVo;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会员优惠券关联表 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-29
 */
public interface CustomerCouponService extends IService<CustomerCouponEntity> {

    /**
     * 根据会员ID获取优惠券数
     *
     * @param customerId
     * @return
     */
    int selectCouponEntityCount(Long customerId, Long shopId);

    /**
     * 根据会员获取优惠券id
     *
     * @param shopIdenty
     * @param customerId
     * @return
     */
    List<CustomerCouponVo> getCouponEntityBy(Long shopIdenty, Long customerId);

    /**
     * 会员添加优惠券
     *
     * @param mCustomerCoupon
     * @return
     */
    Boolean addCustomerCoupon(CustomerCouponEntity mCustomerCoupon) throws Exception;

    /**
     * 批量插入数据
     *
     * @param listData
     * @return
     * @throws Exception
     */
    Boolean addCustomerCouponBatch(List<CustomerCouponEntity> listData) throws Exception;

    /**
     * 获取会员优惠券
     *
     * @param mCustomerCoupon
     * @return
     */
    List<CustomerCouponModel> queryCouponByCustomer(CustomerCouponEntity mCustomerCoupon) throws Exception;

    /**
     * 批量获取会员优惠券
     *
     * @param brandIdenty
     * @param shopIdenty
     * @param listCustomer 多个customerId 用逗号隔开
     * @return
     * @throws Exception
     */
    List<CustomerCouponModel> queryCouponByCustomerGroup(Long brandIdenty, Long shopIdenty, String listCustomer, Integer status) throws Exception;

    /**
     * 获取
     *
     * @param brandIdenty
     * @param shopIdenty
     * @param listCustomer
     * @param status
     * @return
     * @throws Exception
     */
    Integer queryCouponCountByCustomer(Long brandIdenty, Long shopIdenty, String listCustomer, Integer status) throws Exception;

    /**
     * 验证会员优惠券有效性
     *
     * @param id
     * @return
     */
    CustomerCouponEntity queryCouponById(Long id) throws Exception;

    /**
     * 修改优惠券状态，可用于优惠券核销与反核销
     *
     * @param mCustomerCoupon
     * @return
     */
    Boolean modfiyCouponState(CustomerCouponEntity mCustomerCoupon) throws Exception;

    /**
     * 查询优惠券发放和使用情况
     *
     * @param brandIdenty
     * @param shopIdenty
     * @param start
     * @param end
     * @param status
     * @return
     * @throws Exception
     */
    List<CustomerCouponReport> selectCouponReport(Long brandIdenty, Long shopIdenty, Date start, Date end, Integer status) throws Exception;

    /**
     * 查询所以优惠来源类型
     *
     * @return
     */
    List<CustomerCouponReport> selectCouponSource() throws Exception;

    /**
     * 根据自动投优惠券发送优惠给会员
     *
     * @param brandIdenty
     * @param shopIdenty
     * @param customerId
     * @param wxOpenId
     * @param sourceId    优惠券来源 1：商户会员模块触发 2：首次进入小程序触发 3：参与砍价获取 4：小程序绑定手机推送优惠 5：完成交易获取 6：预约成功获取 7：分享门店获取 8：分享活动获取 9：分享新品获取 10：全员推广获取 11：同行特惠获取 12：会员消费评价成功获取 13：参与特价活动推出成单获取
     * @param palnId      投放方案ID  1：进入小程序推送优惠券  2：参与砍价活动推送优惠券 3：小程序绑定手机推送优惠 4：支付交易完成推送优惠券 5：预约完成推送优惠券  6：会员消费评价成功推送优惠券
     * @return
     * @throws Exception
     */
    Boolean putOnCoupon(Long brandIdenty, Long shopIdenty, Long customerId, String wxOpenId, Integer sourceId, Integer palnId) throws Exception;

    Page<CustomerCouponEntity> selectPage(Page<CustomerCouponEntity> page, Long customerId, Long shopId);

    /**
     * 发放优惠券
     * @param brandIdenty
     * @param shopIdenty
     * @param customerId
     * @param wxOpenId
     * @param couponId
     * @param couponName
     * @param sourceId
     * @return
     * @throws Exception
     */
    Boolean sendCustomerCoupon(Long brandIdenty, Long shopIdenty, Long customerId, String wxOpenId,Long couponId,String couponName,Long activityId, Integer sourceId) throws Exception;
}
