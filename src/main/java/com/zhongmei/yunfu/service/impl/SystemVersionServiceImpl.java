package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.api.pos.vo.SystemVersionReq;
import com.zhongmei.yunfu.domain.entity.PushPlanActivityEntity;
import com.zhongmei.yunfu.domain.entity.SystemVersionEntity;
import com.zhongmei.yunfu.domain.mapper.SystemVersionMapper;
import com.zhongmei.yunfu.service.SystemVersionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2019-02-26
 */
@Service
public class SystemVersionServiceImpl extends ServiceImpl<SystemVersionMapper, SystemVersionEntity> implements SystemVersionService {

    @Override
    public SystemVersionEntity checkVersion(SystemVersionReq mSystemVersionReq) throws Exception {

        EntityWrapper<SystemVersionEntity> eWrapper = new EntityWrapper<>(new SystemVersionEntity());
        eWrapper.orderBy("version_code",false);
        eWrapper.last("limit 1");
        SystemVersionEntity mSystemVersionEntity = selectOne(eWrapper);

        return mSystemVersionEntity;
    }

    @Override
    public List<SystemVersionEntity> queryList() throws Exception {

        EntityWrapper<SystemVersionEntity> eWrapper = new EntityWrapper<>(new SystemVersionEntity());

        List<SystemVersionEntity> listData = selectList(eWrapper);

        return listData;
    }

}
