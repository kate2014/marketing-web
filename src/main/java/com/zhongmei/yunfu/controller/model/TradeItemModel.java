package com.zhongmei.yunfu.controller.model;

import java.math.BigDecimal;

public class TradeItemModel {

    private String dishName;

    private BigDecimal price;

    private BigDecimal quantity;

    private BigDecimal amount;

    private String privilege;

    private BigDecimal actualAmount;

    private String tradeUser;


    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getTradeUser() {
        return tradeUser;
    }

    public void setTradeUser(String tradeUser) {
        this.tradeUser = tradeUser;
    }
}
