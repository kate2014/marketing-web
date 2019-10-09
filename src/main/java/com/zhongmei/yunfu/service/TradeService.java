package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.*;
import com.zhongmei.yunfu.domain.entity.*;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.bean.ShopSalesReport;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 交易记录主单（相当于ORDERS）。
 * 主单及其所有子单中的数量、金额在退货时都取反 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-17
 */
public interface TradeService extends IService<TradeEntity> {

    /**
     * 添加订单
     *
     * @param mTrade
     * @return
     */
    TradeEntity addTrade(TradeEntity mTrade) throws Exception;

    /**
     * 根据订单id删除数据
     * @param id
     * @return
     * @throws Exception
     */
    Boolean deleteTradeById(Long id)throws Exception;
    /**
     * 更新订单
     *
     * @param mTrade
     * @return
     */
    Boolean updateTrade(TradeEntity mTrade) throws Exception;

    /**
     * 根据id查询订单
     *
     * @param id
     * @return
     * @throws Exception
     */
    TradeEntity queryTradeById(Long id) throws Exception;

    /**
     * 根据id查询订单
     *
     * @param relateId
     * @return
     * @throws Exception
     */
    TradeEntity queryTradeByRelateId(Long relateId) throws Exception;

    /**
     * 获取订单基本信息
     * @param id
     * @return
     * @throws Exception
     */
    TradeEntity queryTradeBaseData(Long id) throws Exception;

    /**
     * 获取会员的所有订单
     *
     * @param brandIdenty
     * @param shopIdenty
     * @param tradeId
     * @return
     * @throws Exception
     */
    List<TradeEntity> queryTradeByCustomer(Long brandIdenty, Long shopIdenty, String tradeId, Integer tradeStatus) throws Exception;

    /**
     * 查询时段内销售情况
     *
     * @param mTradeModel
     * @return
     */
    List<SalesReport> querySalesReport(TradeModel mTradeModel) throws Exception;

    /**
     * 查询每个渠道销售额度
     *
     * @param mTradeModel
     * @return
     */
    BigDecimal querySalesAmount(TradeModel mTradeModel) throws Exception;

    /**
     * 查询所以订单
     *
     * @param mTradeModel
     * @return
     * @throws Exception
     */
    List<TradeEntity> queryListTrade(TradeModel mTradeModel) throws Exception;

    /**
     * 获取订单列表
     * @param mTradeModel
     * @return
     * @throws Exception
     */
    Page<TradeEntity> queryListTradePage(TradeModel mTradeModel) throws Exception;

    /**
     * 获取会员消费记录
     * @param customerIdsById
     */
    Map<String, String> queryCustomerExpenseList(Collection<Long> customerIdsById);

    /**
     * 获取当前用户在门店的消费记录
     * @param customerId
     * @param shopId
     * @return
     */
    List<TradeEntity> getTradeEntityBy(Long customerId, Long shopId);

    /**
     * 获取订单详情
     * @param mTradeModel
     * @return
     */
    TradeDataModel queryTradeDetail(TradeModel mTradeModel)throws Exception;

    /**
     * 根据订单id查询订单基本信息
     * @param tradeId
     * @return
     * @throws Exception
     */
    TradeEntity queryBaseTradeById(Long tradeId)throws Exception;

    /**
     * 门店会员储值报表
     * @return
     * @throws Exception
     */
    List<CustomerSaveReport> customerSaveReport(TradeModel mTradeModel)throws Exception;


    /**
     * 门店会员储值明细报表
     * @return
     * @throws Exception
     */
    List<CustomerSaveReport> customerSaveDetailReport(TradeModel mTradeModel)throws Exception;

    /**
     * 门店销售排行TOP 10
     * @param mTradeModel
     * @return
     */
    List<ShopSalesReport> shopSalesReport(TradeModel mTradeModel);

    /**
     * 获取营业报表导出数据
     * @param mTradeModel
     * @return
     */
    List<ReportSalesExportModel> querySalseExportExcel(TradeModel mTradeModel);

    /**
     * 获取会员消费信息
     * @param mTradeModel
     * @return
     */
    List<CustomerSaleModel> queryCustomerSale(TradeModel mTradeModel)throws Exception;

    /**
     * 获取会员消费详情
     * @param mTradeModel
     * @return
     */
    List<CustomerSaleModel> queryCustomerSaleDetail(TradeModel mTradeModel)throws Exception;

    /**
     * 订单优惠信息
     * @param mReportMarketingModel
     * @return
     * @throws Exception
     */
    List<TradePrivilegeReport> queryTradePrivilege(ReportMarketingModel mReportMarketingModel)throws Exception;

    /**
     * 获取门店业绩排行榜
     * @param mTradeModel
     * @return
     * @throws Exception
     */
    List<ShopSalesReport> queryShopOrderSales(TradeModel mTradeModel)throws Exception;

    /**
     * 获取门店业绩详情
     * @param mTradeModel
     * @return
     * @throws Exception
     */
    List<ShopSalesReport> queryShopSalesData(TradeModel mTradeModel)throws Exception;

    /**
     * 批量查询门店销售情况
     * @param mTradeModel
     * @param shopIds
     * @return
     * @throws Exception
     */
    List<ShopSalesReport> batchQuerySalesReport(TradeModel mTradeModel,String shopIds)throws Exception;


}
