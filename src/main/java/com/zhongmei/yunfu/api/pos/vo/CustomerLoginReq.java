package com.zhongmei.yunfu.api.pos.vo;

import com.zhongmei.yunfu.api.PosReq;
import com.zhongmei.yunfu.domain.enums.ValueEnum;
import com.zhongmei.yunfu.domain.enums.ValueEnums;

/**
 * 会员登录的请求数据
 *
 * @version: 1.0
 * @date 2015年5月13日
 */
public class CustomerLoginReq extends PosReq {

    @Deprecated
    private String mobile;

    //新增登录参数（新接口）
    //private Long brandId; //品牌id
    //private Long shopId;//门店id
    private Integer loginType;//登录方式：0、手机号码；1、微信OPENID；2、座机号；101、微信会员卡卡号；102、顾客customerId
    private String loginId;//手机号码\微信openId\座机号码\顾客ID
    private String password;
    private boolean isNeedPwd;//是否需要密码(1:需要，其他不需要) 非必传入
    private boolean isNeedCredit;//是否挂账查询（1:需要，其他不需要）非必传入
    private boolean isNeedCard;//是否需要查询实体卡列表

    public String nation;

    public String country;

    public String nationalTelCode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginType getLoginType() {
        return ValueEnums.toEnum(LoginType.class, loginType);
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = ValueEnums.toValue(loginType);
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public boolean getIsNeedPwd() {
        return isNeedPwd;
    }

    public void setIsNeedPwd(boolean isNeedPwd) {
        this.isNeedPwd = isNeedPwd;
    }

    public boolean getIsNeedCredit() {
        return isNeedCredit;
    }

    public void setIsNeedCredit(boolean isNeedCredit) {
        this.isNeedCredit = isNeedCredit;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public boolean getIsNeedCard() {
        return isNeedCard;
    }

    public void setIsNeedCard(boolean isNeedCard) {
        this.isNeedCard = isNeedCard;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNationalTelCode() {
        return nationalTelCode;
    }

    public void setNationalTelCode(String nationalTelCode) {
        this.nationalTelCode = nationalTelCode;
    }

    public enum LoginType implements ValueEnum<Integer> {

        //"手机号码"
        MOBILE(1),

        //"顾客customerId"
        MEMBER_ID(2),

        //"微信openid"
        WECHAT_OPENID(3),

        //"微信会员卡卡号"
        WECHAT_MEMBERCARD_ID(101),

        //"人脸faceCode"
        FACE_CODE(104),


        /**
         * 未知的值
         *
         * @deprecated 为了避免转为enum出错而设置，不应直接使用
         */
        @Deprecated __UNKNOWN__;

        private final Helper<Integer> helper;

        private LoginType(Integer value) {
            // TODO Auto-generated constructor stub
            helper = Helper.valueHelper(value);
        }

        private LoginType() {
            // TODO Auto-generated constructor stub
            helper = Helper.unknownHelper();
        }

        @Override
        public Integer value() {
            return helper.value();
        }

        @Override
        public boolean equalsValue(Integer value) {
            return helper.equalsValue(this, value);
        }

        @Override
        public boolean isUnknownEnum() {
            return helper.isUnknownEnum();
        }

        @Override
        public void setUnknownValue(Integer value) {
            helper.setUnknownValue(value);
        }

        @Override
        public String toString() {
            return "" + value();
        }
    }
}
