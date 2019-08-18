package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.ActivitySalesEntity;

import java.util.List;

/**
 * <p>
 * 特价活动 服务类
 * </p>
 */
public interface ActivitySalesService extends IService<ActivitySalesEntity> {

    /**
     * 添加/编辑门店活动
     * @param mActivitySalesEntity
     * @return
     */
    ActivitySalesEntity modifityActivity(ActivitySalesEntity mActivitySalesEntity) throws Exception;

    /**
     * 删除活动
     * @param id
     * @return
     */
    boolean deleteById(Long id)throws Exception;

    /**
     * 查询门店关联所有活动
     * @param mActivitySalesEntity
     * @return
     */
    List<ActivitySalesEntity> queryListData(ActivitySalesEntity mActivitySalesEntity)throws Exception;

    /**
     * 获取活动详情
     * @param id
     * @return
     */
    ActivitySalesEntity queryById(Long id)throws Exception;

    /**
     * 查询活动每人可参与次数
     * @param id
     * @return
     * @throws Exception
     */
    Integer queryJoinCountById(Long id)throws Exception;

}
