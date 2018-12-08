package com.zhongmei.yunfu.api.pos.vo;

import java.io.Serializable;

/**
 * 次卡服务
 *

 * @date 2018/6/14
 */
public class CustomerCardTimeResp implements Serializable{
    public Long brandIdenty;
    /**
     * 次卡id
     */
    public Long cardInstanceId;
    /**
     * 服务id = brand_dish_id
     */
    public Long serviceId;
    /**
     * 服务名
     */
    public String serviceName;
    /**
     * 服务总次数
     */
    public Integer serviceTotalTime;
    /**
     * 服务剩余次数
     */
    public Integer serviceRemainderTime;

//    /**
//     * 服务商品
//     */
//    public DishShop dishShop;


    public Long getBrandIdenty() {
        return brandIdenty;
    }

    public void setBrandIdenty(Long brandIdenty) {
        this.brandIdenty = brandIdenty;
    }

    public Long getCardInstanceId() {
        return cardInstanceId;
    }

    public void setCardInstanceId(Long cardInstanceId) {
        this.cardInstanceId = cardInstanceId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getServiceTotalTime() {
        return serviceTotalTime;
    }

    public void setServiceTotalTime(Integer serviceTotalTime) {
        this.serviceTotalTime = serviceTotalTime;
    }

    public Integer getServiceRemainderTime() {
        return serviceRemainderTime;
    }

    public void setServiceRemainderTime(Integer serviceRemainderTime) {
        this.serviceRemainderTime = serviceRemainderTime;
    }
}
