package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.ActivitySalesEntity;
import com.zhongmei.yunfu.domain.entity.RedPacketsRecordEntity;

import java.util.List;

/**
 * <p>
 * 红包发放记录
 * </p>
 */
public interface RedPacketsRecordService extends IService<RedPacketsRecordEntity> {
    /**
     * 插入红包发放记录
     * @param entity
     * @return
     */
   Boolean addRecord(RedPacketsRecordEntity entity)throws Exception;


    /**
     * 查询会员红包获取记录
     * @param entity
     * @return
     * @throws Exception
     */
   Page<RedPacketsRecordEntity> queryByCustomer(RedPacketsRecordEntity entity,int pageNo,int pageSize)throws Exception;

    /**
     * 查询活动会员红包获取排行榜
     * @param entity
     * @return
     * @throws Exception
     */
    List<RedPacketsRecordEntity> queryRankingList(RedPacketsRecordEntity entity)throws Exception;
}
