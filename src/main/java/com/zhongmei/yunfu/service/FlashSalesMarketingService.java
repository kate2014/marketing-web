package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.FlashSalesModel;
import com.zhongmei.yunfu.controller.model.FlashSalesReportModel;
import com.zhongmei.yunfu.controller.model.ReportMarketingModel;
import com.zhongmei.yunfu.domain.entity.FlashSalesMarketingEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 秒杀活动 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2018-09-10
 */
public interface FlashSalesMarketingService extends IService<FlashSalesMarketingEntity> {

    /**
     * 新建秒杀活动
     *
     * @param mFlashSalesMarketing
     * @return
     */
    Boolean createFlashSales(FlashSalesMarketingEntity mFlashSalesMarketing) throws Exception;

    /**
     * 获取秒杀数据列表
     *
     * @param mFlashSalesModel
     * @return
     */
    Page<FlashSalesMarketingEntity> queryFlashSalesList(FlashSalesModel mFlashSalesModel) throws Exception;

    /**
     * 根据拼秒杀动id获取活动详情
     *
     * @param id
     * @return
     */
    FlashSalesMarketingEntity findFlashSalesById(Long id) throws Exception;

    /**
     * 根据拼秒杀动id获取活动基本信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    FlashSalesMarketingEntity queryFlashSalesById(Long id) throws Exception;

    /**
     * 获取最新一条秒杀活动信息
     *
     * @param mFlashSalesMarketing
     * @return
     * @throws Exception
     */
    FlashSalesMarketingEntity queryNewFlashSales(FlashSalesMarketingEntity mFlashSalesMarketing) throws Exception;

    /**
     * 根据秒杀Id删除拼团数据
     *
     * @param id
     * @return
     */
    Boolean deleteFlashSalesById(Long id) throws Exception;

    /**
     * 编辑秒杀详情
     *
     * @param mFlashSalesMarketing
     * @return
     */
    Boolean modifyFlashSales(FlashSalesMarketingEntity mFlashSalesMarketing) throws Exception;

    /**
     * 修改秒杀活动购买情况
     * @param mFlashSalesMarketing
     * @return
     * @throws Exception
     */
    void modifyFlashSalesCount(FlashSalesMarketingEntity mFlashSalesMarketing) throws Exception;
    /**
     * 修改活动状态
     *
     * @param id
     * @param enabledFlag
     * @return
     */
    Boolean updateState(Long id, Integer enabledFlag) throws Exception;

    /**
     * 获取秒杀活动报表
     * @param mReportMarketingModel
     * @return
     * @throws Exception
     */
    List<FlashSalesReportModel> queryFlashSalesReport(ReportMarketingModel mReportMarketingModel)throws Exception;
}
