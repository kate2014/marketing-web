package com.zhongmei.yunfu.controller.model;

import java.util.List;

public class SalaryListDetailModel {

    private String commissionsName;

    private List<SalaryDetailModel> listSalaryDetailModel;

    public String getCommissionsName() {
        return commissionsName;
    }

    public void setCommissionsName(String commissionsName) {
        this.commissionsName = commissionsName;
    }

    public List<SalaryDetailModel> getListSalaryDetailModel() {
        return listSalaryDetailModel;
    }

    public void setListSalaryDetailModel(List<SalaryDetailModel> listSalaryDetailModel) {
        this.listSalaryDetailModel = listSalaryDetailModel;
    }
}
