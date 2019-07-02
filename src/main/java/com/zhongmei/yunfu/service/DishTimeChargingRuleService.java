package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.controller.model.CardTimeModel;
import com.zhongmei.yunfu.domain.entity.DishShopEntity;
import com.zhongmei.yunfu.domain.entity.DishTimeChargingRuleEntity;

import java.util.List;

/**
 * <p>
 * 项目计时规则
 * </p>
 *
 * @author pigeon88
 * @since 2019-01-10
 */
public interface DishTimeChargingRuleService extends IService<DishTimeChargingRuleEntity> {

    /**
     * 添加/编辑计时规则
     * @param mDishTimeChargingRuleEntity
     * @return
     * @throws Exception
     */
    boolean addOrUpdateRule(DishTimeChargingRuleEntity mDishTimeChargingRuleEntity)throws Exception;

    /**
     * 查询品项对应的及时规则
     * @param brandIdenty
     * @param shopIdenty
     * @param dishId
     * @return
     * @throws Exception
     */
    DishTimeChargingRuleEntity queryByDishId(Long brandIdenty,Long shopIdenty,Long dishId)throws Exception;

    /**
     * 删除品项对应的计时销售规则
     * @param brandIdenty
     * @param shopIdenty
     * @param dishId
     * @return
     * @throws Exception
     */
    boolean deleteByDishId(Long brandIdenty,Long shopIdenty,Long dishId)throws Exception;

}
