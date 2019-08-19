package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.domain.entity.BookingEntity;
import com.zhongmei.yunfu.domain.entity.CustomerCardTimeEntity;

import java.util.List;

public class CustomerModel {

    private Long id;

    private String name;

    private Integer gender;

    private String mobile;

    private String birthday;

    private String groupLevel;

    private String telephone;

    private String wxPhoto;

    private String hobby;

    private String address;

    private String profile;

    private String wxOpenId;

    private String password;

    private Integer sourceId;

    private Long expandedId;

    private Long shopIdenty;

    private Long brandIdenty;

    private String startDate;

    private String endDate;

    private List<CustomerCardTimeEntity> listCardTime;

    private List<CustomerCouponModel> listCoupon;

    private BookingEntity mBookingEntity;

    private Integer searchData;

    private Integer rquestSource;

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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(String groupLevel) {
        this.groupLevel = groupLevel;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWxPhoto() {
        return wxPhoto;
    }

    public void setWxPhoto(String wxPhoto) {
        this.wxPhoto = wxPhoto;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Long getExpandedId() {
        return expandedId;
    }

    public void setExpandedId(Long expandedId) {
        this.expandedId = expandedId;
    }

    public Long getShopIdenty() {
        return shopIdenty;
    }

    public void setShopIdenty(Long shopIdenty) {
        this.shopIdenty = shopIdenty;
    }

    public Long getBrandIdenty() {
        return brandIdenty;
    }

    public void setBrandIdenty(Long brandIdenty) {
        this.brandIdenty = brandIdenty;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<CustomerCardTimeEntity> getListCardTime() {
        return listCardTime;
    }

    public void setListCardTime(List<CustomerCardTimeEntity> listCardTime) {
        this.listCardTime = listCardTime;
    }

    public List<CustomerCouponModel> getListCoupon() {
        return listCoupon;
    }

    public void setListCoupon(List<CustomerCouponModel> listCoupon) {
        this.listCoupon = listCoupon;
    }

    public BookingEntity getmBookingEntity() {
        return mBookingEntity;
    }

    public void setmBookingEntity(BookingEntity mBookingEntity) {
        this.mBookingEntity = mBookingEntity;
    }

    public Integer getSearchData() {
        return searchData;
    }

    public void setSearchData(Integer searchData) {
        this.searchData = searchData;
    }

    public Integer getRquestSource() {
        return rquestSource;
    }

    public void setRquestSource(Integer rquestSource) {
        this.rquestSource = rquestSource;
    }
}
