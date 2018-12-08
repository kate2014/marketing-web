package com.zhongmei.yunfu.mybatisplus;

import com.baomidou.mybatisplus.generator.AutoGenerator;

public class  AutoGeneratorEx extends AutoGenerator {

    @Override
    public void execute() {
        // 初始化配置
        if (null == config) {
            config = new ConfigBuilderEx(getPackageInfo(), getDataSource(), getStrategy(), getTemplate(), getGlobalConfig());
            if (null != injectionConfig) {
                injectionConfig.setConfig(config);
            }
        }
        super.execute();
    }
}
