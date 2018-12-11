package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CollageMarketingModel;
import com.zhongmei.yunfu.controller.model.CollageReportModel;
import com.zhongmei.yunfu.controller.model.ReportMarketingModel;
import com.zhongmei.yunfu.domain.entity.CollageMarketingEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 拼团活动 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-10
 */
public interface CollageMarketingService extends IService<CollageMarketingEntity> {

    /**
     * 新建拼团活动
     *
     * @param mCollageMarketing
     * @return
     */
    Boolean createCollage(CollageMarketingEntity mCollageMarketing) throws Exception;

    /**
     * 获取拼团数据列表
     *
     * @param mCollageMarketingModel
     * @return
     */
    Page<CollageMarketingEntity> queryCollageList(CollageMarketingModel mCollageMarketingModel) throws Exception;

    /**
     * 根据拼团活动id获取活动详情
     *
     * @param id
     * @return
     */
    CollageMarketingEntity findCollageById(Long id) throws Exception;

    /**
     * 根据拼团Id删除拼团数据
     *
     * @param id
     * @return
     */
    Boolean deleteCollageById(Long id) throws Exception;

    /**
     * 编辑拼团详情
     *
     * @param mCollageMarketing
     * @return
     */
    Boolean modifyCollage(CollageMarketingEntity mCollageMarketing) throws Exception;

    /**
     * 修改活动状态
     *
     * @param id
     * @param enabledFlag
     * @return
     */
    Boolean updateState(Long id, Integer enabledFlag) throws Exception;

    /**
     * 查询拼团基本信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    public CollageMarketingEntity queryCollageById(Long id) throws Exception;

    /**
     * 查询最新一条拼团活动
     * @return
     * @throws Exception
     */
    public CollageMarketingEntity queryNewCollage(CollageMarketingEntity mCollageMarketing) throws Exception;

    /**
     * 获取拼团数据报表
     * @param mReportMarketingModel
     * @return
     * @throws Exception
     */
    public List<CollageReportModel> queryCollageReport(ReportMarketingModel mReportMarketingModel)throws Exception;
}
