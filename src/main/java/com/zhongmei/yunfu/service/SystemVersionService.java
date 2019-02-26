package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.api.pos.vo.SystemVersionReq;
import com.zhongmei.yunfu.domain.entity.SystemVersionEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pigeon88
 * @since 2019-02-26
 */
public interface SystemVersionService extends IService<SystemVersionEntity> {

    /**
     * 验证系统版本号
     * @param mSystemVersionReq
     * @return
     */
    SystemVersionEntity checkVersion(SystemVersionReq mSystemVersionReq) throws Exception;

    /**
     * 获取当前系统所有版本
     * @return
     * @throws Exception
     */
    List<SystemVersionEntity> queryList()throws Exception;

}
