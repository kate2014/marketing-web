package com.zhongmei.yunfu.controller.model;

public class CustomerLevelRuleModel {

    private Long idYK;
    /**
     * 银卡等级编号
     */
    private Integer levelCodeYK;
    /**
     * 银卡等级名称
     */
    private String levelNameYK;
    /**
     * 等级对应积分
     */
    private Integer levelScoreYK;

    private Long idJK;
    /**
     * 金卡等级编号
     */
    private Integer levelCodeJK;
    /**
     * 金卡等级名称
     */
    private String levelNameJK;
    /**
     * 等级对应积分
     */
    private Integer levelScoreJK;

    private Long idBJ;
    /**
     * 白金卡等级编号
     */
    private Integer levelCodeBJ;
    /**
     * 白金等级名称
     */
    private String levelNameBJ;
    /**
     * 等级对应积分
     */
    private Integer levelScoreBJ;

    private Long idHJ;
    /**
     * 黑金等级编号
     */
    private Integer levelCodeHJ;
    /**
     * 黑金等级名称
     */
    private String levelNameHJ;
    /**
     * 等级对应积分
     */
    private Integer levelScoreHJ;

    private Long idZS;
    /**
     * 钻石等级编号
     */
    private Integer levelCodeZS;
    /**
     * 钻石等级名称
     */
    private String levelNameZS;
    /**
     * 等级对应积分
     */
    private Integer levelScoreZS;

    private Long idZZ;
    /**
     * 至尊等级编号
     */
    private Integer levelCodeZZ;
    /**
     * 至尊等级名称
     */
    private String levelNameZZ;
    /**
     * 等级对应积分
     */
    private Integer levelScoreZZ;

    /**
     * 如果归属门店，则为门店id；如果归属品牌，则为品牌id.
     * 新的权限体系下，全部为品牌id
     * 就是登录标示!!仅登录使用
     */
    private Long shopIdenty;
    /**
     * 品牌标识 : 品牌标识
     */
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

    private String successOrfail;

    public Integer getLevelCodeYK() {
        return levelCodeYK;
    }

    public void setLevelCodeYK(Integer levelCodeYK) {
        this.levelCodeYK = levelCodeYK;
    }

    public String getLevelNameYK() {
        return levelNameYK;
    }

    public void setLevelNameYK(String levelNameYK) {
        this.levelNameYK = levelNameYK;
    }

    public Integer getLevelCodeJK() {
        return levelCodeJK;
    }

    public void setLevelCodeJK(Integer levelCodeJK) {
        this.levelCodeJK = levelCodeJK;
    }

    public String getLevelNameJK() {
        return levelNameJK;
    }

    public void setLevelNameJK(String levelNameJK) {
        this.levelNameJK = levelNameJK;
    }

    public Integer getLevelCodeBJ() {
        return levelCodeBJ;
    }

    public void setLevelCodeBJ(Integer levelCodeBJ) {
        this.levelCodeBJ = levelCodeBJ;
    }

    public String getLevelNameBJ() {
        return levelNameBJ;
    }

    public void setLevelNameBJ(String levelNameBJ) {
        this.levelNameBJ = levelNameBJ;
    }

    public Integer getLevelCodeHJ() {
        return levelCodeHJ;
    }

    public void setLevelCodeHJ(Integer levelCodeHJ) {
        this.levelCodeHJ = levelCodeHJ;
    }

    public String getLevelNameHJ() {
        return levelNameHJ;
    }

    public void setLevelNameHJ(String levelNameHJ) {
        this.levelNameHJ = levelNameHJ;
    }

    public Integer getLevelCodeZS() {
        return levelCodeZS;
    }

    public void setLevelCodeZS(Integer levelCodeZS) {
        this.levelCodeZS = levelCodeZS;
    }

    public String getLevelNameZS() {
        return levelNameZS;
    }

    public void setLevelNameZS(String levelNameZS) {
        this.levelNameZS = levelNameZS;
    }

    public Integer getLevelCodeZZ() {
        return levelCodeZZ;
    }

    public void setLevelCodeZZ(Integer levelCodeZZ) {
        this.levelCodeZZ = levelCodeZZ;
    }

    public String getLevelNameZZ() {
        return levelNameZZ;
    }

    public void setLevelNameZZ(String levelNameZZ) {
        this.levelNameZZ = levelNameZZ;
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

    public Long getIdYK() {
        return idYK;
    }

    public void setIdYK(Long idYK) {
        this.idYK = idYK;
    }

    public Long getIdJK() {
        return idJK;
    }

    public void setIdJK(Long idJK) {
        this.idJK = idJK;
    }

    public Long getIdBJ() {
        return idBJ;
    }

    public void setIdBJ(Long idBJ) {
        this.idBJ = idBJ;
    }

    public Long getIdHJ() {
        return idHJ;
    }

    public void setIdHJ(Long idHJ) {
        this.idHJ = idHJ;
    }

    public Long getIdZS() {
        return idZS;
    }

    public void setIdZS(Long idZS) {
        this.idZS = idZS;
    }

    public Long getIdZZ() {
        return idZZ;
    }

    public void setIdZZ(Long idZZ) {
        this.idZZ = idZZ;
    }

    public Integer getLevelScoreYK() {
        return levelScoreYK;
    }

    public void setLevelScoreYK(Integer levelScoreYK) {
        this.levelScoreYK = levelScoreYK;
    }

    public Integer getLevelScoreJK() {
        return levelScoreJK;
    }

    public void setLevelScoreJK(Integer levelScoreJK) {
        this.levelScoreJK = levelScoreJK;
    }

    public Integer getLevelScoreBJ() {
        return levelScoreBJ;
    }

    public void setLevelScoreBJ(Integer levelScoreBJ) {
        this.levelScoreBJ = levelScoreBJ;
    }

    public Integer getLevelScoreHJ() {
        return levelScoreHJ;
    }

    public void setLevelScoreHJ(Integer levelScoreHJ) {
        this.levelScoreHJ = levelScoreHJ;
    }

    public Integer getLevelScoreZS() {
        return levelScoreZS;
    }

    public void setLevelScoreZS(Integer levelScoreZS) {
        this.levelScoreZS = levelScoreZS;
    }

    public Integer getLevelScoreZZ() {
        return levelScoreZZ;
    }

    public void setLevelScoreZZ(Integer levelScoreZZ) {
        this.levelScoreZZ = levelScoreZZ;
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

    public String getSuccessOrfail() {
        return successOrfail;
    }

    public void setSuccessOrfail(String successOrfail) {
        this.successOrfail = successOrfail;
    }
}
