package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.controller.api.model.WxTempMsgFormId;
import com.zhongmei.yunfu.domain.entity.WxFormEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-11-09
 */
public interface WxFormService extends IService<WxFormEntity> {

    /**
     * 添加form
     *
     * @return
     * @throws Exception
     */
    Boolean addOrUpdateWxForm(WxTempMsgFormId wxTempMsgFormId) throws Exception;

    /**
     * 修改form
     *
     * @param mWxFormEntity
     * @return
     * @throws Exception
     */
    Boolean updateWxForm(WxFormEntity mWxFormEntity) throws Exception;

    /**
     * 根据openId获取form
     *
     * @param mWxFormEntity
     * @return
     * @throws Exception
     */
    WxFormEntity queryFormByOpenId(WxFormEntity mWxFormEntity) throws Exception;

    /**
     * 查询未使用的fromId
     *
     * @param shopIdenty
     * @param brandIdenty
     * @param customerId
     * @return
     * @throws Exception
     */
    WxFormEntity queryFormUnusedByOpenId(Long shopIdenty, Long brandIdenty, Long customerId) throws Exception;

    /**
     * 批量查询form
     *
     * @param mWxFormEntity
     * @return
     * @throws Exception
     */
    List<WxFormEntity> queryFormList(WxFormEntity mWxFormEntity) throws Exception;
}
