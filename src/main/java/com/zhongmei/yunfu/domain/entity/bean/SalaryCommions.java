package com.zhongmei.yunfu.domain.entity.bean;

import java.util.Date;
import java.util.List;

public class SalaryCommions {

    private String baseSalary;
    private String brandIdenty;
    private Date endDate;
    private List<ProjectCommions> projectCommionsDetailBos;
    private String projectCommissions;
    private String salarySum;
    private String salesSum;
    private String salesCommissions;
    private String salesCommissionsDetail;
    private String saveSum;
    private String saveCommissions;
    private String saveCommissionsDetail;
    private String shopIdenty;
    private Date startDate;
    private String userId;
    private String userName;

    public String getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(String baseSalary) {
        this.baseSalary = baseSalary;
    }

    public String getBrandIdenty() {
        return brandIdenty;
    }

    public void setBrandIdenty(String brandIdenty) {
        this.brandIdenty = brandIdenty;
    }

    public List<ProjectCommions> getProjectCommionsDetailBos() {
        return projectCommionsDetailBos;
    }

    public void setProjectCommionsDetailBos(List<ProjectCommions> projectCommionsDetailBos) {
        this.projectCommionsDetailBos = projectCommionsDetailBos;
    }

    public String getProjectCommissions() {
        return projectCommissions;
    }

    public void setProjectCommissions(String projectCommissions) {
        this.projectCommissions = projectCommissions;
    }

    public String getSalarySum() {
        return salarySum;
    }

    public void setSalarySum(String salarySum) {
        this.salarySum = salarySum;
    }

    public String getSalesCommissions() {
        return salesCommissions;
    }

    public void setSalesCommissions(String salesCommissions) {
        this.salesCommissions = salesCommissions;
    }

    public String getSalesCommissionsDetail() {
        return salesCommissionsDetail;
    }

    public void setSalesCommissionsDetail(String salesCommissionsDetail) {
        this.salesCommissionsDetail = salesCommissionsDetail;
    }

    public String getSaveCommissions() {
        return saveCommissions;
    }

    public void setSaveCommissions(String saveCommissions) {
        this.saveCommissions = saveCommissions;
    }

    public String getSaveCommissionsDetail() {
        return saveCommissionsDetail;
    }

    public void setSaveCommissionsDetail(String saveCommissionsDetail) {
        this.saveCommissionsDetail = saveCommissionsDetail;
    }

    public String getShopIdenty() {
        return shopIdenty;
    }

    public void setShopIdenty(String shopIdenty) {
        this.shopIdenty = shopIdenty;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSalesSum() {
        return salesSum;
    }

    public void setSalesSum(String salesSum) {
        this.salesSum = salesSum;
    }

    public String getSaveSum() {
        return saveSum;
    }

    public void setSaveSum(String saveSum) {
        this.saveSum = saveSum;
    }
}
