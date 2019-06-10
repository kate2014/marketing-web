package com.zhongmei.yunfu.api.pos.vo;

import java.math.BigDecimal;
import java.util.List;

public class CustomerInfoResp {

    public Long id;
    public String name;
    public Long brandId;
    public Long customerId;
    public String customerName;
    public String mobile;
    public String hobby;
    public Integer sex;
    public Long levelId;
    public Long level;
    public String levelName;
    public String memo;
    public String address;
    public String birthday;
    public Integer isDisable;
    public BigDecimal remainValue = BigDecimal.ZERO;
    public Integer integral;
    public Integer cardCount;
    public Integer coupCount;
    public String password;
    public Long commercialId;
    public String commercialName;
    public Integer source = 1;
    public List<EntityCard> entityCards;

    static public class EntityCard {
        public Long cardId;
        public String cardNo;
    }
}
