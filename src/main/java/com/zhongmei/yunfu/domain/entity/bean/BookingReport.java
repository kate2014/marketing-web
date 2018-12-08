package com.zhongmei.yunfu.domain.entity.bean;

import java.util.Date;

public class BookingReport {

    /**
     * 订单来源 1：pos ，2：微信小程序
     */
    private Integer bookingSource;

    private Integer bookingCount;
    /**
     * 订单状态,-1:用户未到店,1:用户到店,2:用户离店,9:已取消,-2:未处理,-3:已拒绝,-4:逾期未到店
     */
    private Integer orderStatus;

    private Date createTime;

    public Integer getBookingSource() {
        return bookingSource;
    }

    public void setBookingSource(Integer bookingSource) {
        this.bookingSource = bookingSource;
    }

    public Integer getBookingCount() {
        return bookingCount;
    }

    public void setBookingCount(Integer bookingCount) {
        this.bookingCount = bookingCount;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
