package com.zhongmei.yunfu.service.impl;

import com.zhongmei.yunfu.controller.model.CustomerScoreRuleModel;
import com.zhongmei.yunfu.domain.entity.CustomerScoreRuleEntity;
import com.zhongmei.yunfu.domain.mapper.CustomerScoreRuleMapper;
import com.zhongmei.yunfu.service.CustomerScoreRuleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员等级积分成长规则、积分使用规则 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-07
 */
@Service
public class CustomerScoreRuleServiceImpl extends ServiceImpl<CustomerScoreRuleMapper, CustomerScoreRuleEntity> implements CustomerScoreRuleService {

    @Override
    public List<CustomerScoreRuleEntity> findScoreRule(Map<String, Object> map) {
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put("brand_identy", map.get("brandIdenty"));
        tempMap.put("shop_identy", map.get("shopIdenty"));
        tempMap.put("status_flag", 1);

        List<CustomerScoreRuleEntity> listData = selectByMap(tempMap);

        return listData;
    }

    @Override
    public Boolean modifyScoreRule(CustomerScoreRuleModel mCustomerScoreRuleMode) throws Exception {

        Boolean isSuccess = true;
        //积分成长
        if (mCustomerScoreRuleMode.getTypeS() != null && mCustomerScoreRuleMode.getTypeS() == 1) {
            if (mCustomerScoreRuleMode.getIdS() == null && mCustomerScoreRuleMode.getConvertValueS() != null) {
                CustomerScoreRuleEntity mCustomerScoreRule = new CustomerScoreRuleEntity();
                mCustomerScoreRule.setBrandIdenty(mCustomerScoreRuleMode.getBrandIdenty());
                mCustomerScoreRule.setShopIdenty(mCustomerScoreRuleMode.getShopIdenty());
                mCustomerScoreRule.setType(1);
                mCustomerScoreRule.setConvertValue(mCustomerScoreRuleMode.getConvertValueS());
                mCustomerScoreRule.setStatusFlag(1);
                mCustomerScoreRule.setCreatorId(mCustomerScoreRuleMode.getCreatorId());
                mCustomerScoreRule.setCreatorName(mCustomerScoreRuleMode.getCreatorName());
                mCustomerScoreRule.setUpdatorId(mCustomerScoreRuleMode.getUpdatorId());
                mCustomerScoreRule.setUpdatorName(mCustomerScoreRuleMode.getUpdatorName());
                mCustomerScoreRule.setServerCreateTime(new Date());
                mCustomerScoreRule.setServerUpdateTime(new Date());

                isSuccess = insert(mCustomerScoreRule);
            }else if(mCustomerScoreRuleMode.getConvertValueS() != null){
                CustomerScoreRuleEntity mCustomerScoreRule = new CustomerScoreRuleEntity();
                mCustomerScoreRule.setId(mCustomerScoreRuleMode.getIdS());
                mCustomerScoreRule.setConvertValue(mCustomerScoreRuleMode.getConvertValueS());
                mCustomerScoreRule.setUpdatorId(mCustomerScoreRuleMode.getUpdatorId());
                mCustomerScoreRule.setUpdatorName(mCustomerScoreRuleMode.getUpdatorName());
                mCustomerScoreRule.setServerUpdateTime(new Date());
                isSuccess = updateById(mCustomerScoreRule);
            }
        }

        //积分抵用
        if (mCustomerScoreRuleMode.getTypeD() != null && mCustomerScoreRuleMode.getTypeD() == 2) {
            //抵用规则：多少积分抵用多少金额
            if (mCustomerScoreRuleMode.getIdD() == null && mCustomerScoreRuleMode.getConvertValueD() != null) {
                CustomerScoreRuleEntity mCustomerScoreRule = new CustomerScoreRuleEntity();
                mCustomerScoreRule.setBrandIdenty(mCustomerScoreRuleMode.getBrandIdenty());
                mCustomerScoreRule.setShopIdenty(mCustomerScoreRuleMode.getShopIdenty());
                mCustomerScoreRule.setType(2);
                mCustomerScoreRule.setConvertValue(mCustomerScoreRuleMode.getConvertValueD());
                mCustomerScoreRule.setStatusFlag(1);
                mCustomerScoreRule.setCreatorId(mCustomerScoreRuleMode.getCreatorId());
                mCustomerScoreRule.setCreatorName(mCustomerScoreRuleMode.getCreatorName());
                mCustomerScoreRule.setUpdatorId(mCustomerScoreRuleMode.getUpdatorId());
                mCustomerScoreRule.setUpdatorName(mCustomerScoreRuleMode.getUpdatorName());
                mCustomerScoreRule.setServerCreateTime(mCustomerScoreRuleMode.getServerCreateTime());
                mCustomerScoreRule.setServerUpdateTime(mCustomerScoreRuleMode.getServerUpdateTime());

                isSuccess = insert(mCustomerScoreRule);
            }else if(mCustomerScoreRuleMode.getConvertValueD() != null){
                CustomerScoreRuleEntity mCustomerScoreRule = new CustomerScoreRuleEntity();
                mCustomerScoreRule.setId(mCustomerScoreRuleMode.getIdD());
                mCustomerScoreRule.setConvertValue(mCustomerScoreRuleMode.getConvertValueD());
                mCustomerScoreRule.setUpdatorId(mCustomerScoreRuleMode.getUpdatorId());
                mCustomerScoreRule.setUpdatorName(mCustomerScoreRuleMode.getUpdatorName());
                mCustomerScoreRule.setServerUpdateTime(new Date());
                isSuccess = updateById(mCustomerScoreRule);
            }
            //抵用规则：单笔订单抵用上限
            if (mCustomerScoreRuleMode.getIdM() == null && mCustomerScoreRuleMode.getConvertValueM() != null) {
                CustomerScoreRuleEntity mCustomerScoreRule = new CustomerScoreRuleEntity();
                mCustomerScoreRule.setBrandIdenty(mCustomerScoreRuleMode.getBrandIdenty());
                mCustomerScoreRule.setShopIdenty(mCustomerScoreRuleMode.getShopIdenty());
                mCustomerScoreRule.setType(3);
                mCustomerScoreRule.setConvertValue(mCustomerScoreRuleMode.getConvertValueM());
                mCustomerScoreRule.setStatusFlag(1);
                mCustomerScoreRule.setCreatorId(mCustomerScoreRuleMode.getCreatorId());
                mCustomerScoreRule.setCreatorName(mCustomerScoreRuleMode.getCreatorName());
                mCustomerScoreRule.setUpdatorId(mCustomerScoreRuleMode.getUpdatorId());
                mCustomerScoreRule.setUpdatorName(mCustomerScoreRuleMode.getUpdatorName());
                mCustomerScoreRule.setServerCreateTime(mCustomerScoreRuleMode.getServerCreateTime());
                mCustomerScoreRule.setServerUpdateTime(mCustomerScoreRuleMode.getServerUpdateTime());

                isSuccess = insert(mCustomerScoreRule);
            }else if(mCustomerScoreRuleMode.getConvertValueM() != null) {
                CustomerScoreRuleEntity mCustomerScoreRule = new CustomerScoreRuleEntity();
                mCustomerScoreRule.setId(mCustomerScoreRuleMode.getIdM());
                mCustomerScoreRule.setConvertValue(mCustomerScoreRuleMode.getConvertValueM());
                mCustomerScoreRule.setUpdatorId(mCustomerScoreRuleMode.getUpdatorId());
                mCustomerScoreRule.setUpdatorName(mCustomerScoreRuleMode.getUpdatorName());
                mCustomerScoreRule.setServerUpdateTime(new Date());
                isSuccess = updateById(mCustomerScoreRule);
            }

        }

        return isSuccess;
    }
}
