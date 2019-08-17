package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CutDownCustomerModel;
import com.zhongmei.yunfu.domain.entity.CutDownCustomerEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 会员发起砍价记录 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-13
 */
public interface CutDownCustomerService extends IService<CutDownCustomerEntity> {

    /**
     * 插入会员发起的砍价活动
     *
     * @param mCutDownCustomer
     * @return
     */
    Boolean installData(CutDownCustomerEntity mCutDownCustomer) throws Exception;

    /**
     * 更改会员砍价活动信息
     *
     * @param mCutDownCustomer
     * @return
     */
    Boolean updateData(CutDownCustomerEntity mCutDownCustomer) throws Exception;

    /**
     * 查询会员发起的所有砍价活动
     *
     * @param mCutDownCustomer
     * @return
     */
    List<CutDownCustomerModel> queryDataList(CutDownCustomerEntity mCutDownCustomer) throws Exception;

    /**
     * 获取活动发起砍价情况
     * @param mCutDownCustomer
     * @return
     * @throws Exception
     */
    Page<CutDownCustomerEntity> queryLaunchData(CutDownCustomerEntity mCutDownCustomer, Integer pageNo, Integer pageSize) throws Exception;

    /**
     * 根据id查询某条发起的砍价
     *
     * @param id
     * @return
     * @throws Exception
     */
    CutDownCustomerEntity queryDataById(Long id) throws Exception;
}
