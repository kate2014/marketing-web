package com.zhongmei.yunfu.api.pos.vo;

public class CustomerListResp {

    public Long customerId;
    public String name;
    public int sex;
    public String mobile;
    public Long groupId;
    public Long levelId; //分组（会员等级）id
    public String levelName; //分组（会员等级）
    public int source;
    public String sourceName;
    public int isDisable = 2;
    public Integer loginType;
    public String loginId;
    public String modifyDateTime;
    public Integer hasFaceCode;
}
