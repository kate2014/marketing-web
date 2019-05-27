package com.zhongmei.yunfu.service.impl;

import com.zhongmei.yunfu.domain.entity.CustomerEntityCardEntity;
import com.zhongmei.yunfu.domain.mapper.CustomerEntityCardMapper;
import com.zhongmei.yunfu.service.CustomerEntityCardService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2019-05-20
 */
@Service
public class CustomerEntityCardServiceImpl extends ServiceImpl<CustomerEntityCardMapper, CustomerEntityCardEntity> implements CustomerEntityCardService {

    @Override
    public List<CustomerEntityCardEntity> getByCustomerId(Long customerId) {
        return baseMapper.selectByCustomerId(customerId);
    }
}
