package com.zhongmei.yunfu.service.impl;

import com.zhongmei.yunfu.domain.entity.DishSetmealGroupEntity;
import com.zhongmei.yunfu.domain.mapper.DishSetmealGroupMapper;
import com.zhongmei.yunfu.service.DishSetmealGroupService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 套餐内菜品分组 : dish_type表只能存在两个级别的分类 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2019-01-10
 */
@Service
public class DishSetmealGroupServiceImpl extends ServiceImpl<DishSetmealGroupMapper, DishSetmealGroupEntity> implements DishSetmealGroupService {

}
