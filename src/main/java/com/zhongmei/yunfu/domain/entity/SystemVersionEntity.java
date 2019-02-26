package com.zhongmei.yunfu.domain.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableName;
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author pigeon88
 * @since 2019-02-26
 */
@TableName("system_version")
public class SystemVersionEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 系统版本号
     */
    private Integer versionCode;
    /**
     * 版本名称
     */
    private String versionName;
    /**
     * 版本简介
     */
    private String versionDescribe;
    /**
     * 版本下载地址
     */
    private String downloadUrl;
    /**
     * 升级模式：1、强制升级 2、不强制升级
     */
    private Integer upgradeModel;
    /**
     * 发布时间
     */
    private Date createDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionDescribe() {
        return versionDescribe;
    }

    public void setVersionDescribe(String versionDescribe) {
        this.versionDescribe = versionDescribe;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Integer getUpgradeModel() {
        return upgradeModel;
    }

    public void setUpgradeModel(Integer upgradeModel) {
        this.upgradeModel = upgradeModel;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "SystemVersionEntity{" +
        ", id=" + id +
        ", versionCode=" + versionCode +
        ", versionName=" + versionName +
        ", versionDescribe=" + versionDescribe +
        ", downloadUrl=" + downloadUrl +
        ", upgradeModel=" + upgradeModel +
        ", createDate=" + createDate +
        "}";
    }
}
