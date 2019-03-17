package com.zhongmei.yunfu.erp.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;

import java.util.Date;
import java.util.List;

public class ERPBrandModel extends WebBaseModel {

    private Long id;

    private String name;

    /**
     * 商户组logo
     */
    private String logo;
    /**
     * 商户组联系人
     */
    private String contacts;
    /**
     * 联系人电话
     */
    private String contactsPhone;
    /**
     * 联系人邮箱
     */
    private String contactsMail;
    /**
     * 商户组地址
     */
    private String commercialGroupAdress;
    /**
     * 状态：0有效 -1无效
     */
    private Integer status;

    private Integer statusFlag;
    /**
     * 父ID=0时，代表的是集团
     */
    private Long parentId;
    /**
     * 类型 0代表集团 1代表公司
     */
    private Integer type;

    private String province;

    private String city;

    private String area;

    private Long creatorId;

    private String creatorName;

    private Date serverCreateTime;

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsPhone() {
        return contactsPhone;
    }

    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    public String getContactsMail() {
        return contactsMail;
    }

    public void setContactsMail(String contactsMail) {
        this.contactsMail = contactsMail;
    }

    public String getCommercialGroupAdress() {
        return commercialGroupAdress;
    }

    public void setCommercialGroupAdress(String commercialGroupAdress) {
        this.commercialGroupAdress = commercialGroupAdress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Integer getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(Integer statusFlag) {
        this.statusFlag = statusFlag;
    }

    public Date getServerCreateTime() {
        return serverCreateTime;
    }

    public void setServerCreateTime(Date serverCreateTime) {
        this.serverCreateTime = serverCreateTime;
    }
}
