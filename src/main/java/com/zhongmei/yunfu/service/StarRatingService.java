package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.StarRatingEntity;

import java.util.List;

/**
 * 服务评分
 */
public interface StarRatingService extends IService<StarRatingEntity> {

    /**
     * 批量插入评分
     * @param listData
     * @return
     * @throws Exception
     */
    boolean batchAddStar(List<StarRatingEntity> listData) throws Exception;

    /**
     * 查询评分
     * @param mStarRatingEntity
     * @return
     * @throws Exception
     */
    List<StarRatingEntity> queryStarList(StarRatingEntity mStarRatingEntity) throws Exception;
}
