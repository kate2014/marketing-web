package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CutDownModel;
import com.zhongmei.yunfu.controller.model.CutDownReportModel;
import com.zhongmei.yunfu.controller.model.ReportMarketingModel;
import com.zhongmei.yunfu.domain.entity.CutDownMarketingEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 砍价活动 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2018-09-10
 */
public interface CutDownMarketingService extends IService<CutDownMarketingEntity> {

    /**
     * 创建砍价活动
     *
     * @param mCutDownMarketing
     * @return
     */
    Boolean createCutDown(CutDownMarketingEntity mCutDownMarketing) throws Exception;

    /**
     * 获取砍价数据列表
     *
     * @param mCutDownModel
     * @return
     */
    Page<CutDownMarketingEntity> queryCutDownList(CutDownModel mCutDownModel) throws Exception;

    /**
     * 根据拼砍价动id获取活动详情
     *
     * @param id
     * @return
     */
    CutDownMarketingEntity findCutDownById(Long id) throws Exception;

    /**
     * 根据拼砍价动id获取活动详情
     *
     * @param id
     * @return
     */
    CutDownMarketingEntity findCutDownDatailById(Long id) throws Exception;

    /**
     * 获取砍价区间
     *
     * @param id
     * @return
     * @throws Exception
     */
    CutDownMarketingEntity findCutPice(Long id) throws Exception;

    /**
     * 根据砍价Id删除拼团数据
     *
     * @param id
     * @return
     */
    Boolean deleteCutDownById(Long id) throws Exception;

    /**
     * 编辑砍价详情
     *
     * @param mCutDownMarketing
     * @return
     */
    Boolean modifyCutDown(CutDownMarketingEntity mCutDownMarketing) throws Exception;

    /**
     * 修改参情况
     * @return
     * @throws Exception
     */
    void modifyCutDownCount(CutDownMarketingEntity mCutDownMarketing)throws Exception;

    /**
     * 修改活动状态
     *
     * @param id
     * @param enabledFlag
     * @return
     */
    Boolean updateState(Long id, Integer enabledFlag) throws Exception;

    /**
     * 获取最新一条砍价活动
     * @param mCutDownMarketing
     * @return
     * @throws Exception
     */
    CutDownMarketingEntity queryNewCutDown(CutDownMarketingEntity mCutDownMarketing)throws Exception;

    /**
     * 获取砍价报表
     * @param mReportMarketingModel
     * @return
     * @throws Exception
     */
    public List<CutDownReportModel> queryCutDownReport(ReportMarketingModel mReportMarketingModel) throws Exception;

}
