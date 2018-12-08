package com.zhongmei.yunfu.domain.entity.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 会员优惠券关联表
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-29
 */
public class CustomerCouponReport implements Serializable {

    /**
     * 优惠券来源 1：商户会员模块触发 2：进入小程序触发 3：参与砍价获取 4：注册新会员获取 5：完成交易获取 6：预约成功获取 7：分析门店获取 8：分析活动获取 9：分析新品获取 10：全员推广获取 11：同行特惠获取
     */
    private Integer sourceId;
    /**
     * 来源名称
     */
    private String sourceName;
    /**
     * 优惠券类型
     */
    private Integer couponType;
    /**
     * 优惠券名称
     */
    private String couponName;
    /**
     * 使用状态 : 1:未使用;2:已使用
     */
    private Integer status;
    /**
     * 优惠券发放数量
     */
    private Long couponCount;


    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(Long couponCount) {
        this.couponCount = couponCount;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }


    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
