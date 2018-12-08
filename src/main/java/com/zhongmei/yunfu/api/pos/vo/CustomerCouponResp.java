package com.zhongmei.yunfu.api.pos.vo;

import java.io.Serializable;

/**
 * Created by lj199 on 2018/6/12.
 */

public class CustomerCouponResp implements Serializable {
    private Long id;// 会员的优惠券id 对应后台coup_instance 表的id
    private String codeNumber;// 优惠券编码
    private String boundWxCardNumber; //关联的微信卡券号
    private Long couponId;// 优惠券模板id(coupon.id)
    private Integer validType;//券有效期类型(1:动态, 2:固定)
    private Integer isCurDay;//是否当天有效(validType=1时指定)
    private Integer validDayNum;//券有效期天数(validType=1时指定)
    private String validStartDate;// 有效期开始时间
    private String validEndDate;// 有效期结束时间
    public String periodStart; //add v8.15 start多时段限制
    public String periodEnd;
    public String extraPeriodStart;
    public String extraPeriodEnd;
    //add v8.15 end多时段限制
    private String discount;//折扣券折扣
    private Integer faceValue;
    private Integer useScene; //使用场景，0:全部 ; 1:堂食; 2:外卖

    public Integer getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(Integer faceValue) {
        this.faceValue = faceValue;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(String codeNumber) {
        this.codeNumber = codeNumber;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getValidStartDate() {
        return validStartDate;
    }

    public void setValidStartDate(String validStartDate) {
        this.validStartDate = validStartDate;
    }

    public String getValidEndDate() {
        return validEndDate;
    }

    public void setValidEndDate(String validEndDate) {
        this.validEndDate = validEndDate;
    }

    public Integer getUseScene() {
        return useScene;
    }

    public void setUseScene(Integer useScene) {
        this.useScene = useScene;
    }

    public Integer getValidType() {
        return validType;
    }

    public void setValidType(Integer validType) {
        this.validType = validType;
    }

    public Integer getIsCurDay() {
        return isCurDay;
    }

    public void setIsCurDay(Integer isCurDay) {
        this.isCurDay = isCurDay;
    }

    public Integer getValidDayNum() {
        return validDayNum;
    }

    public void setValidDayNum(Integer validDayNum) {
        this.validDayNum = validDayNum;
    }

    public boolean isCurDay(){
        return isCurDay != null && isCurDay.intValue() == 1;
    }

    public boolean isFixedPeriod(){
        return validType != null && validType.intValue() == 1;
    }

    public String getBoundWxCardNumber() {
        return boundWxCardNumber;
    }

    public void setBoundWxCardNumber(String boundWxCardNumber) {
        this.boundWxCardNumber = boundWxCardNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
       CustomerCouponResp other = (CustomerCouponResp) obj;
        if (this.id == null) {
            return false;
        } else {
            return this.id.equals(other.id);
        }
    }
}
