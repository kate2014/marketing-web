package com.zhongmei.yunfu.mybatisplus;
/**
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.toolkit.StringUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * <p>
 * 配置汇总 传递给文件生成工具
 * </p>
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-30
 */
public class ConfigBuilderEx extends ConfigBuilder {

    /**
     * <p>
     * 在构造器中处理配置
     * </p>
     *
     * @param packageConfig    包配置
     * @param dataSourceConfig 数据源配置
     * @param strategyConfig   表配置
     * @param template         模板配置
     * @param globalConfig     全局配置
     */
    public ConfigBuilderEx(PackageConfig packageConfig, DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig, TemplateConfig template, GlobalConfig globalConfig) {
        super(packageConfig, dataSourceConfig, strategyConfig, template, globalConfig);
    }

    @Override
    public List<TableInfo> getTableInfoList() {
        List<TableInfo> tableInfoList = super.getTableInfoList();
        for (TableInfo tableInfo : tableInfoList) {
            if (!tableInfo.getEntityName().endsWith("Entity")) {
                tableInfo.setConvert(true);
                tableInfo.setEntityName(getStrategyConfig(), tableInfo.getEntityName() + "Entity");
            }
        }
        return tableInfoList;
    }

    /*@Override
    public ConfigBuilder setTableInfoList(List<TableInfo> tableInfoList) {
        for (TableInfo tableInfo : tableInfoList) {
            if (!tableInfo.getEntityName().startsWith("Entity")) {
                tableInfo.setConvert(true);
                tableInfo.setEntityName(getStrategyConfig(), tableInfo.getEntityName() + "Entity");
            }
        }
        return super.setTableInfoList(tableInfoList);
    }*/
}
