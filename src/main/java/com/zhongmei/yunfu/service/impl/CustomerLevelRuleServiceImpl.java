package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.controller.model.CustomerLevelRuleModel;
import com.zhongmei.yunfu.domain.entity.CustomerLevelRuleEntity;
import com.zhongmei.yunfu.domain.mapper.CustomerLevelRuleMapper;
import com.zhongmei.yunfu.service.CustomerLevelRuleService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 会员等级积分表 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-07
 */
@Service
public class CustomerLevelRuleServiceImpl extends ServiceImpl<CustomerLevelRuleMapper, CustomerLevelRuleEntity> implements CustomerLevelRuleService {

    @Override
    public Boolean modifyCustomerLevelRule(CustomerLevelRuleModel mCustomerLevelRuleModel) throws Exception {

        Boolean isSuccess = true;

        Long idYK = mCustomerLevelRuleModel.getIdYK();
        Integer levelScoreYK = mCustomerLevelRuleModel.getLevelScoreYK();

        //通过判断是做新插入还是做更新
        if (idYK == null && levelScoreYK != null) {
            CustomerLevelRuleEntity mCustomerLevelRule = new CustomerLevelRuleEntity();
            mCustomerLevelRule.setBrandIdenty(mCustomerLevelRuleModel.getBrandIdenty());
            mCustomerLevelRule.setShopIdenty(mCustomerLevelRuleModel.getShopIdenty());
            mCustomerLevelRule.setLevelCode(1);
            mCustomerLevelRule.setLevelName("银卡会员");
            mCustomerLevelRule.setLevelScore(levelScoreYK);
            mCustomerLevelRule.setStatusFlag(1);
            mCustomerLevelRule.setCreatorId(mCustomerLevelRuleModel.getCreatorId());
            mCustomerLevelRule.setCreatorName(mCustomerLevelRuleModel.getCreatorName());
            mCustomerLevelRule.setUpdatorId(mCustomerLevelRuleModel.getUpdatorId());
            mCustomerLevelRule.setUpdatorName(mCustomerLevelRuleModel.getUpdatorName());
            mCustomerLevelRule.setServerCreateTime(new Date());
            mCustomerLevelRule.setServerUpdateTime(new Date());
            isSuccess = insert(mCustomerLevelRule);
        } else if (levelScoreYK != null) {
            CustomerLevelRuleEntity mCustomerLevelRule = new CustomerLevelRuleEntity();
            mCustomerLevelRule.setId(idYK);
            mCustomerLevelRule.setLevelScore(levelScoreYK);
            mCustomerLevelRule.setUpdatorId(mCustomerLevelRuleModel.getUpdatorId());
            mCustomerLevelRule.setUpdatorName(mCustomerLevelRuleModel.getUpdatorName());
            mCustomerLevelRule.setServerUpdateTime(new Date());
            isSuccess = updateById(mCustomerLevelRule);
        }


        Long idJK = mCustomerLevelRuleModel.getIdJK();
        Integer levelScoreJK = mCustomerLevelRuleModel.getLevelScoreJK();
        if (idJK == null && levelScoreJK != null) {
            CustomerLevelRuleEntity mCustomerLevelRule = new CustomerLevelRuleEntity();
            mCustomerLevelRule.setBrandIdenty(mCustomerLevelRuleModel.getBrandIdenty());
            mCustomerLevelRule.setShopIdenty(mCustomerLevelRuleModel.getShopIdenty());
            mCustomerLevelRule.setLevelCode(2);
            mCustomerLevelRule.setLevelName("金卡会员");
            mCustomerLevelRule.setLevelScore(levelScoreJK);
            mCustomerLevelRule.setStatusFlag(1);
            mCustomerLevelRule.setCreatorId(mCustomerLevelRuleModel.getCreatorId());
            mCustomerLevelRule.setCreatorName(mCustomerLevelRuleModel.getCreatorName());
            mCustomerLevelRule.setUpdatorId(mCustomerLevelRuleModel.getUpdatorId());
            mCustomerLevelRule.setUpdatorName(mCustomerLevelRuleModel.getUpdatorName());
            mCustomerLevelRule.setServerCreateTime(new Date());
            mCustomerLevelRule.setServerUpdateTime(new Date());
            isSuccess = insert(mCustomerLevelRule);
        } else if (levelScoreJK != null) {
            CustomerLevelRuleEntity mCustomerLevelRule = new CustomerLevelRuleEntity();
            mCustomerLevelRule.setId(idJK);
            mCustomerLevelRule.setLevelScore(levelScoreJK);
            mCustomerLevelRule.setUpdatorId(mCustomerLevelRuleModel.getUpdatorId());
            mCustomerLevelRule.setUpdatorName(mCustomerLevelRuleModel.getUpdatorName());
            mCustomerLevelRule.setServerUpdateTime(new Date());
            isSuccess = updateById(mCustomerLevelRule);
        }


        Long idBJ = mCustomerLevelRuleModel.getIdBJ();
        Integer levelScoreBJ = mCustomerLevelRuleModel.getLevelScoreBJ();
        if (idBJ == null && levelScoreBJ != null) {
            CustomerLevelRuleEntity mCustomerLevelRule = new CustomerLevelRuleEntity();
            mCustomerLevelRule.setBrandIdenty(mCustomerLevelRuleModel.getBrandIdenty());
            mCustomerLevelRule.setShopIdenty(mCustomerLevelRuleModel.getShopIdenty());
            mCustomerLevelRule.setLevelCode(3);
            mCustomerLevelRule.setLevelName("白金会员");
            mCustomerLevelRule.setLevelScore(levelScoreBJ);
            mCustomerLevelRule.setStatusFlag(1);
            mCustomerLevelRule.setCreatorId(mCustomerLevelRuleModel.getCreatorId());
            mCustomerLevelRule.setCreatorName(mCustomerLevelRuleModel.getCreatorName());
            mCustomerLevelRule.setUpdatorId(mCustomerLevelRuleModel.getUpdatorId());
            mCustomerLevelRule.setUpdatorName(mCustomerLevelRuleModel.getUpdatorName());
            mCustomerLevelRule.setServerCreateTime(new Date());
            mCustomerLevelRule.setServerUpdateTime(new Date());
            isSuccess = insert(mCustomerLevelRule);
        } else if (levelScoreBJ != null) {
            CustomerLevelRuleEntity mCustomerLevelRule = new CustomerLevelRuleEntity();
            mCustomerLevelRule.setId(idBJ);
            mCustomerLevelRule.setLevelScore(levelScoreBJ);
            mCustomerLevelRule.setUpdatorId(mCustomerLevelRuleModel.getUpdatorId());
            mCustomerLevelRule.setUpdatorName(mCustomerLevelRuleModel.getUpdatorName());
            mCustomerLevelRule.setServerUpdateTime(new Date());
            isSuccess = updateById(mCustomerLevelRule);
        }


        Long idHJ = mCustomerLevelRuleModel.getIdHJ();
        Integer levelScoreHJ = mCustomerLevelRuleModel.getLevelScoreHJ();
        if (idHJ == null && levelScoreHJ != null) {
            CustomerLevelRuleEntity mCustomerLevelRule = new CustomerLevelRuleEntity();
            mCustomerLevelRule.setBrandIdenty(mCustomerLevelRuleModel.getBrandIdenty());
            mCustomerLevelRule.setShopIdenty(mCustomerLevelRuleModel.getShopIdenty());
            mCustomerLevelRule.setLevelCode(4);
            mCustomerLevelRule.setLevelName("黑金会员");
            mCustomerLevelRule.setLevelScore(levelScoreHJ);
            mCustomerLevelRule.setStatusFlag(1);
            mCustomerLevelRule.setCreatorId(mCustomerLevelRuleModel.getCreatorId());
            mCustomerLevelRule.setCreatorName(mCustomerLevelRuleModel.getCreatorName());
            mCustomerLevelRule.setUpdatorId(mCustomerLevelRuleModel.getUpdatorId());
            mCustomerLevelRule.setUpdatorName(mCustomerLevelRuleModel.getUpdatorName());
            mCustomerLevelRule.setServerCreateTime(new Date());
            mCustomerLevelRule.setServerUpdateTime(new Date());
            isSuccess = insert(mCustomerLevelRule);
        } else if (levelScoreHJ != null) {
            CustomerLevelRuleEntity mCustomerLevelRule = new CustomerLevelRuleEntity();
            mCustomerLevelRule.setId(idHJ);
            mCustomerLevelRule.setLevelScore(levelScoreHJ);
            mCustomerLevelRule.setUpdatorId(mCustomerLevelRuleModel.getUpdatorId());
            mCustomerLevelRule.setUpdatorName(mCustomerLevelRuleModel.getUpdatorName());
            mCustomerLevelRule.setServerUpdateTime(new Date());
            isSuccess = updateById(mCustomerLevelRule);
        }


        Long idZS = mCustomerLevelRuleModel.getIdZS();
        Integer levelScoreZS = mCustomerLevelRuleModel.getLevelScoreZS();
        if (idZS == null && levelScoreZS != null) {
            CustomerLevelRuleEntity mCustomerLevelRule = new CustomerLevelRuleEntity();
            mCustomerLevelRule.setBrandIdenty(mCustomerLevelRuleModel.getBrandIdenty());
            mCustomerLevelRule.setShopIdenty(mCustomerLevelRuleModel.getShopIdenty());
            mCustomerLevelRule.setLevelCode(5);
            mCustomerLevelRule.setLevelName("钻石会员");
            mCustomerLevelRule.setLevelScore(levelScoreZS);
            mCustomerLevelRule.setStatusFlag(1);
            mCustomerLevelRule.setCreatorId(mCustomerLevelRuleModel.getCreatorId());
            mCustomerLevelRule.setCreatorName(mCustomerLevelRuleModel.getCreatorName());
            mCustomerLevelRule.setUpdatorId(mCustomerLevelRuleModel.getUpdatorId());
            mCustomerLevelRule.setUpdatorName(mCustomerLevelRuleModel.getUpdatorName());
            mCustomerLevelRule.setServerCreateTime(new Date());
            mCustomerLevelRule.setServerUpdateTime(new Date());
            isSuccess = insert(mCustomerLevelRule);
        } else if (levelScoreZS != null) {
            CustomerLevelRuleEntity mCustomerLevelRule = new CustomerLevelRuleEntity();
            mCustomerLevelRule.setId(idZS);
            mCustomerLevelRule.setLevelScore(levelScoreZS);
            mCustomerLevelRule.setUpdatorId(mCustomerLevelRuleModel.getUpdatorId());
            mCustomerLevelRule.setUpdatorName(mCustomerLevelRuleModel.getUpdatorName());
            mCustomerLevelRule.setServerUpdateTime(new Date());
            isSuccess = updateById(mCustomerLevelRule);
        }


        Long idZZ = mCustomerLevelRuleModel.getIdZZ();
        Integer levelScoreZZ = mCustomerLevelRuleModel.getLevelScoreZZ();
        if (idZZ == null && levelScoreZZ != null) {
            CustomerLevelRuleEntity mCustomerLevelRule = new CustomerLevelRuleEntity();
            mCustomerLevelRule.setBrandIdenty(mCustomerLevelRuleModel.getBrandIdenty());
            mCustomerLevelRule.setShopIdenty(mCustomerLevelRuleModel.getShopIdenty());
            mCustomerLevelRule.setLevelCode(6);
            mCustomerLevelRule.setLevelName("至尊会员");
            mCustomerLevelRule.setLevelScore(levelScoreZZ);
            mCustomerLevelRule.setStatusFlag(1);
            mCustomerLevelRule.setCreatorId(mCustomerLevelRuleModel.getCreatorId());
            mCustomerLevelRule.setCreatorName(mCustomerLevelRuleModel.getCreatorName());
            mCustomerLevelRule.setUpdatorId(mCustomerLevelRuleModel.getUpdatorId());
            mCustomerLevelRule.setUpdatorName(mCustomerLevelRuleModel.getUpdatorName());
            mCustomerLevelRule.setServerCreateTime(new Date());
            mCustomerLevelRule.setServerUpdateTime(new Date());
            isSuccess = insert(mCustomerLevelRule);
        } else if (levelScoreZZ != null) {
            CustomerLevelRuleEntity mCustomerLevelRule = new CustomerLevelRuleEntity();
            mCustomerLevelRule.setId(idZZ);
            mCustomerLevelRule.setLevelScore(levelScoreZZ);
            mCustomerLevelRule.setUpdatorId(mCustomerLevelRuleModel.getUpdatorId());
            mCustomerLevelRule.setUpdatorName(mCustomerLevelRuleModel.getUpdatorName());
            mCustomerLevelRule.setServerUpdateTime(new Date());
            isSuccess = updateById(mCustomerLevelRule);
        }

        return isSuccess;
    }

    @Override
    public List<CustomerLevelRuleEntity> queryRuleData(CustomerLevelRuleModel mCustomerLevelRuleModel) {

        Map<String, Object> map = new HashMap<>();
        map.put("brand_identy", mCustomerLevelRuleModel.getBrandIdenty());
        map.put("shop_identy", mCustomerLevelRuleModel.getShopIdenty());
        map.put("status_flag", 1);
        List<CustomerLevelRuleEntity> listData = selectByMap(map);

        return listData;
    }

    @Override
    public CustomerLevelRuleEntity getCustomerLevelRuleEntity(Long shopIdenty, Long brandIdenty, int integral) {
        CustomerLevelRuleModel levelRuleModel = new CustomerLevelRuleModel();
        levelRuleModel.setBrandIdenty(brandIdenty);
        levelRuleModel.setShopIdenty(shopIdenty);
        List<CustomerLevelRuleEntity> levelRuleEntityList = queryRuleData(levelRuleModel);
        Collections.sort(levelRuleEntityList, Comparator.comparingInt(CustomerLevelRuleEntity::getLevelScore).thenComparingInt(CustomerLevelRuleEntity::getLevelCode));

        CustomerLevelRuleEntity customerLevelRuleEntity = null;
        if (levelRuleEntityList != null && levelRuleEntityList.size() > 0) {
            for (int i = levelRuleEntityList.size() - 1; i >= 0; i--) {
                CustomerLevelRuleEntity entity = levelRuleEntityList.get(i);
                if (integral >= entity.getLevelScore()) {
                    customerLevelRuleEntity = entity;
                    break;
                }
            }
            if (customerLevelRuleEntity == null) {
                customerLevelRuleEntity = levelRuleEntityList.get(0);
            }
        } else {
            customerLevelRuleEntity = new CustomerLevelRuleEntity();
            customerLevelRuleEntity.setBrandIdenty(brandIdenty);
            customerLevelRuleEntity.setShopIdenty(shopIdenty);
            customerLevelRuleEntity.setLevelCode(1);
            customerLevelRuleEntity.setLevelScore(1);
            customerLevelRuleEntity.setLevelName("银卡会员");
        }
        return customerLevelRuleEntity;
    }

    @Override
    public CustomerLevelRuleEntity queryLevelByScore(Long shopIdenty, Long brandIdenty, Integer score) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("brand_identy", brandIdenty);
        map.put("shop_identy", shopIdenty);
        map.put("status_flag", 1);
        List<CustomerLevelRuleEntity> listData = selectByMap(map);
        CustomerLevelRuleEntity mCustomerLevelRuleEntity = new CustomerLevelRuleEntity();
        mCustomerLevelRuleEntity.setLevelScore(0);
        for (CustomerLevelRuleEntity cre : listData) {
            if (score < cre.getLevelScore() && mCustomerLevelRuleEntity.getLevelScore() <= score) {
                break;
            }
            mCustomerLevelRuleEntity = cre;
        }

        return mCustomerLevelRuleEntity;
    }
}
