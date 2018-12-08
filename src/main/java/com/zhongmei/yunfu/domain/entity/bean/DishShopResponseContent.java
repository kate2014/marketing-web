package com.zhongmei.yunfu.domain.entity.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zhongmei.yunfu.domain.entity.DishShopEntity;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DishShopResponseContent implements Serializable {

    private List<DishShopEntity> content;
    private boolean first;
    private boolean last;
    private Integer number;
    private Integer numberOfElements;
    private int size;
    private int totalElements;
    private int totalPages;
    private PageInfo pageable;

    public List<DishShopEntity> getContent() {
        return content;
    }

    public void setContent(List<DishShopEntity> content) {
        this.content = content;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public PageInfo getPageable() {
        return pageable;
    }

    public void setPageable(PageInfo pageable) {
        this.pageable = pageable;
    }
}
