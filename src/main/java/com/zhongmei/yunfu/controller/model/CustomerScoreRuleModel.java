package com.zhongmei.yunfu.controller.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

public class CustomerScoreRuleModel {

    private Long idS;
    private Integer typeS;
    private Integer convertValueS;

    private Long idD;
    private Integer typeD;
    private Integer convertValueD;

    private Long idM;
    private Integer typeM;
    private Integer convertValueM;

    private Long shopIdenty;
    private Long brandIdenty;
    /**
     * 状态标识1:有效 2:无效
     */
    private Integer statusFlag;
    /**
     * 创建者id
     */
    private Long creatorId;
    /**
     * 创建者名称
     */
    private String creatorName;
    /**
     * 更新者id
     */
    private Long updatorId;
    /**
     * 最后修改者姓名
     */
    private String updatorName;
    /**
     * 服务器创建时间
     */
    private Date serverCreateTime;
    /**
     * 服务器更新时间
     */
    private Date serverUpdateTime;

    public Long getIdS() {
        return idS;
    }

    public void setIdS(Long idS) {
        this.idS = idS;
    }

    public Integer getTypeS() {
        return typeS;
    }

    public void setTypeS(Integer typeS) {
        this.typeS = typeS;
    }

    public Long getIdD() {
        return idD;
    }

    public void setIdD(Long idD) {
        this.idD = idD;
    }

    public Integer getTypeD() {
        return typeD;
    }

    public void setTypeD(Integer typeD) {
        this.typeD = typeD;
    }

    public Integer getConvertValueS() {
        return convertValueS;
    }

    public void setConvertValueS(Integer convertValueS) {
        this.convertValueS = convertValueS;
    }

    public Integer getConvertValueD() {
        return convertValueD;
    }

    public void setConvertValueD(Integer convertValueD) {
        this.convertValueD = convertValueD;
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

    public Integer getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(Integer statusFlag) {
        this.statusFlag = statusFlag;
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

    public Long getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(Long updatorId) {
        this.updatorId = updatorId;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public Date getServerCreateTime() {
        return serverCreateTime;
    }

    public void setServerCreateTime(Date serverCreateTime) {
        this.serverCreateTime = serverCreateTime;
    }

    public Date getServerUpdateTime() {
        return serverUpdateTime;
    }

    public void setServerUpdateTime(Date serverUpdateTime) {
        this.serverUpdateTime = serverUpdateTime;
    }

    public Integer getConvertValueM() {
        return convertValueM;
    }

    public void setConvertValueM(Integer convertValueM) {
        this.convertValueM = convertValueM;
    }

    public Long getIdM() {
        return idM;
    }

    public void setIdM(Long idM) {
        this.idM = idM;
    }

    public Integer getTypeM() {
        return typeM;
    }

    public void setTypeM(Integer typeM) {
        this.typeM = typeM;
    }
}
