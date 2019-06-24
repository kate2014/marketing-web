package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CustomerArchivesEntity;
import com.zhongmei.yunfu.erp.model.ERPBrandModel;

import java.util.List;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
public interface CustomerArchivesService extends IService<CustomerArchivesEntity> {

    /**
     * 添加会员档案
     * @param mCustomerArchivesEntity
     * @return
     * @throws Exception
     */
    boolean addCustomerArchives(CustomerArchivesEntity mCustomerArchivesEntity)throws Exception;

    /**
     * 获取会员档案分页列表
     * @param mCustomerArchivesEntity
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    Page<CustomerArchivesEntity> queryArchivesPage(CustomerArchivesEntity mCustomerArchivesEntity,int pageNo,int pageSize)throws Exception;

    /**
     * 获取会员档案分页列表
     * @param mCustomerArchivesEntity
     * @return
     * @throws Exception
     */
    List<CustomerArchivesEntity> queryArchivesList(CustomerArchivesEntity mCustomerArchivesEntity)throws Exception;

    /**
     * 更新会员档案
     * @param mCustomerArchivesEntity
     * @return
     * @throws Exception
     */
    boolean modfityArchives(CustomerArchivesEntity mCustomerArchivesEntity)throws Exception;

    /**
     * 根据档案id删除档案内容
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteArchives(Long id)throws Exception;
}
