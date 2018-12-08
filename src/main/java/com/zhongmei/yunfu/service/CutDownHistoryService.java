package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.domain.entity.CutDownHistoryEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 砍价记录 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2018-09-13
 */
public interface CutDownHistoryService extends IService<CutDownHistoryEntity> {

    /**
     * 插入参与砍价会员信息
     *
     * @param mCutDownHistory
     * @return
     */
    Boolean installData(CutDownHistoryEntity mCutDownHistory) throws Exception;

    /**
     * 查询会员发起的所有砍价活动
     *
     * @param mCutDownHistory
     * @return
     */
    List<CutDownHistoryEntity> queryDataList(CutDownHistoryEntity mCutDownHistory) throws Exception;

    /**
     * 查询同一活动参数次数
     * @param mCutDownHistory
     * @return
     * @throws Exception
     */
    Integer queryJoinCount(CutDownHistoryEntity mCutDownHistory) throws Exception;
}
