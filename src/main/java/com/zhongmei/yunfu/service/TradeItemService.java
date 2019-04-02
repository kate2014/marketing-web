package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.DishReport;
import com.zhongmei.yunfu.domain.entity.DishSaleReport;
import com.zhongmei.yunfu.domain.entity.TradeItemEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 交易明细 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-17
 */
public interface TradeItemService extends IService<TradeItemEntity> {

    /**
     * 添加tradeItem
     *
     * @param mTradeItem
     * @return
     * @throws Exception
     */
    Boolean addTradeItem(TradeItemEntity mTradeItem) throws Exception;

    /**
     * 根据订单查询tradeItem
     *
     * @param tradeId
     * @return
     */
    List<TradeItemEntity> querTradeItemByTradeId(Long tradeId);

    /**
     * 根据tradeId查询item所有信息
     * @param tradeId
     * @return
     */
    List<TradeItemEntity> queryTradeItemAllByTradeId(Long tradeId);

    /**
     * 查收商品销售排行榜
     *
     * @param brandIdenty
     * @param shopIdenty
     * @param start
     * @param end
     * @return
     */
    List<DishReport> selectDishSalesReport(Long brandIdenty, Long shopIdenty, Date start, Date end) throws Exception;

    /**
     * 查询次卡销售情况
     * @param brandIdenty
     * @param shopIdenty
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    List<DishReport> selectCardTimeReport(Long brandIdenty, Long shopIdenty, Date start, Date end) throws Exception;
    /**
     * 根据tradeId删除trade_item
     * @param tradeId
     * @return
     */
    Boolean deleteByTradeId(Long tradeId);

    /**
     * 查收商品销售排行榜
     *
     * @param mTradeModel
     * @return
     */
    List<DishReport> dishSalesExportExcel(TradeModel mTradeModel) throws Exception;

    /**
     * 查询品项销售情况
     * @param mTradeModel
     * @return
     * @throws Exception
     */
    List<TradeItemEntity> dishSaleDetail(TradeModel mTradeModel) throws Exception;

    /**
     * 菜品销售情况
     * @param mTradeModel
     * @return
     * @throws Exception
     */
    List<TradeItemEntity> dishSaleData(TradeModel mTradeModel) throws Exception;

    /**
     * 销售报表
     * @param mTradeModel
     * @return
     * @throws Exception
     */
    List<DishSaleReport> listSaleReport (TradeModel mTradeModel) throws Exception;
}
