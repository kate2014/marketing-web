package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.domain.entity.CouponEntity;

import java.math.BigDecimal;

public class CouponModel {

    /**
     * 自增主键
     */
    private Long id;
    /**
     * 优惠券名称
     */
    private String name;
    /**
     * 券类型(1：折扣、2：代金、3：礼品、)
     */
    private Integer couponType;
    /**
     * 投放量
     */
    private Integer pushNumber;
    /**
     * 使用量
     */
    private Integer useNumber;
    /**
     * 消费满额
     */
    private Integer fullValue;

    /**
     * 1：折扣数值、
     */
    private BigDecimal discountValueD;
    /**
     * 2：抵用金额
     */
    private BigDecimal discountValueZ;

    /**
     * 品项id
     */
    private Long dishId;
    /**
     * 品项名称
     */
    private String dishName;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 券内容
     */
    private String content;
    /**
     * 适用商品：1.全部商品可用；2.部分商品可用；3.部分商品不可用
     */
    private Integer applyDish;

    /**
     * 优惠卷状态（1, 进行中;2, 停止; 3：品牌下发未接受 4：品牌下发已接受  5：数据刷新未接受）
     */
    private Integer couponState;

    /**
     * 是否限制使用门店（0：否、1：是）
     */
    private Integer restrictUseCommercial;
    /**
     * 优惠叠加类型（1可叠加同享|2不可叠加同享|3部分可叠加）
     */
    private Integer sharingPrivilegeType;
    /**
     * 活动结束时间
     */
    private String endTime;

    /**
     * 服务器创建时间
     */
    private String serverCreateTime;

    private String selectShopList;

    private Integer sourceType;

    private Long sourceId;


    public CouponModel() {
    }

    public CouponModel(CouponEntity coupon) {
        this.id = coupon.getId();
        this.name = coupon.getName();
        this.couponType = coupon.getCouponType();
        this.pushNumber = coupon.getPushNumber();
        this.fullValue = coupon.getFullValue();
        this.useNumber = coupon.getUseNumber();
        this.discountValueD = coupon.getDiscountValue();
        this.discountValueZ = coupon.getDiscountValue();
        this.dishId = coupon.getDishId();
        this.dishName = coupon.getDishName();
        this.remark = coupon.getRemark();
        this.content = coupon.getContent();
        this.applyDish = coupon.getApplyDish();
        this.couponState = coupon.getCouponState();
        this.restrictUseCommercial = coupon.getRestrictUseCommercial();
        this.sharingPrivilegeType = coupon.getSharingPrivilegeType();
        this.sourceType = coupon.getSourceType();
        this.sourceId = coupon.getSourceId();

        if (coupon.getEndTime() != null) {
            this.endTime = DateFormatUtil.format(coupon.getEndTime(), DateFormatUtil.FORMAT_FULL_DATE);
        }
        if (coupon.getServerCreateTime() != null) {
            this.serverCreateTime = DateFormatUtil.format(coupon.getServerCreateTime(), DateFormatUtil.FORMAT_FULL_DATE);
        }
    }


    public CouponEntity obtainCoupon() {
        CouponEntity coupon = new CouponEntity();

        coupon.setId(id);
        coupon.setName(name);
        coupon.setCouponType(couponType);
        coupon.setPushNumber(pushNumber);
        coupon.setFullValue(fullValue);
        coupon.setUseNumber(useNumber);
        //1：折扣、2：代金、3：礼品
        if(couponType ==1 && discountValueD != null){
            coupon.setDiscountValue(discountValueD);
        }
        if(couponType ==2 && discountValueZ != null){
            coupon.setDiscountValue(discountValueZ);
        }
        coupon.setDishId(dishId);
        coupon.setDishName(dishName);
        coupon.setRemark(remark);
        coupon.setContent(content);
        coupon.setApplyDish(applyDish);
        coupon.setCouponState(couponState);
        coupon.setRestrictUseCommercial(restrictUseCommercial);
        coupon.setSharingPrivilegeType(sharingPrivilegeType);
        try {
            if (endTime != null) {
                coupon.setEndTime(DateFormatUtil.parseDate(endTime, DateFormatUtil.FORMAT_DATE));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return coupon;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public Integer getPushNumber() {
        return pushNumber;
    }

    public void setPushNumber(Integer pushNumber) {
        this.pushNumber = pushNumber;
    }

    public Integer getUseNumber() {
        return useNumber;
    }

    public void setUseNumber(Integer useNumber) {
        this.useNumber = useNumber;
    }

    public Integer getFullValue() {
        return fullValue;
    }

    public void setFullValue(Integer fullValue) {
        this.fullValue = fullValue;
    }

    public BigDecimal getDiscountValueD() {
        return discountValueD;
    }

    public void setDiscountValueD(BigDecimal discountValueD) {
        this.discountValueD = discountValueD;
    }

    public BigDecimal getDiscountValueZ() {
        return discountValueZ;
    }

    public void setDiscountValueZ(BigDecimal discountValueZ) {
        this.discountValueZ = discountValueZ;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getApplyDish() {
        return applyDish;
    }

    public void setApplyDish(Integer applyDish) {
        this.applyDish = applyDish;
    }

    public Integer getCouponState() {
        return couponState;
    }

    public void setCouponState(Integer couponState) {
        this.couponState = couponState;
    }

    public Integer getRestrictUseCommercial() {
        return restrictUseCommercial;
    }

    public void setRestrictUseCommercial(Integer restrictUseCommercial) {
        this.restrictUseCommercial = restrictUseCommercial;
    }

    public Integer getSharingPrivilegeType() {
        return sharingPrivilegeType;
    }

    public void setSharingPrivilegeType(Integer sharingPrivilegeType) {
        this.sharingPrivilegeType = sharingPrivilegeType;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getServerCreateTime() {
        return serverCreateTime;
    }

    public void setServerCreateTime(String serverCreateTime) {
        this.serverCreateTime = serverCreateTime;
    }

    public String getSelectShopList() {
        return selectShopList;
    }

    public void setSelectShopList(String selectShopList) {
        this.selectShopList = selectShopList;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String obtainCouponTypeStr() {
        if (getCouponType() == 1) {
            return "折扣券";
        } else if (getCouponType() == 2) {
            return "代金券";
        } else if (getCouponType() == 3) {
            return "礼品券";
        }

        return "未知类型";
    }

    public String obtainStateStr() {
        if (getCouponState() == 1) {
            return "进行中";
        } else if (getCouponState() == 2) {
            return "停止";
        } else if (getCouponState() == 3) {
            return "品牌下发未接受";
        } else if (getCouponState() == 4) {
            return "品牌下发已接受";
        } else if (getCouponState() == 5) {
            return "数据刷新未接受";
        }
        return "未知状态";
    }
}
