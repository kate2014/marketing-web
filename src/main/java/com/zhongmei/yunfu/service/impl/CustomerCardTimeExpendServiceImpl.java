package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.CustomerCardTimeExpendEntity;
import com.zhongmei.yunfu.domain.enums.RecordType;
import com.zhongmei.yunfu.domain.mapper.CustomerCardTimeExpendMapper;
import com.zhongmei.yunfu.service.CustomerCardTimeExpendService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会员次卡消费表 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-10-02
 */
@Service
public class CustomerCardTimeExpendServiceImpl extends ServiceImpl<CustomerCardTimeExpendMapper, CustomerCardTimeExpendEntity> implements CustomerCardTimeExpendService {

    @Override
    public List<CustomerCardTimeExpendEntity> findByTradeId(Long tradeId, RecordType recordType) {
        CustomerCardTimeExpendEntity expendEntity = new CustomerCardTimeExpendEntity();
        expendEntity.setTradeId(tradeId);
        expendEntity.setRecordType(recordType.value());
        EntityWrapper<CustomerCardTimeExpendEntity> entityWrapper = new EntityWrapper();
        return selectList(entityWrapper);
    }
}
