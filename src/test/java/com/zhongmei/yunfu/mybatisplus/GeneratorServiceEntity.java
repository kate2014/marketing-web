package com.zhongmei.yunfu.mybatisplus;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.zhongmei.yunfu.mybatisplus.AutoGeneratorEx;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;
import org.springframework.util.FileSystemUtils;

import java.io.File;

/**
 * <p>
 * 测试生成代码
 * </p>
 *
 * @author K神
 * @date 2017/12/18
 */
public class GeneratorServiceEntity {

    @Test
    public void generateCode() {
        String packageName = "com.zhongmei.yunfu";
        boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService
        generateByTables(serviceNameStartWithI, packageName,
                "app_config",
                "attachment",
                "auth_permission",
                "auth_role",
                "auth_role_permission",
                "auth_user",
                "booking",
                "booking_trade_item",
                "booking_trade_item_user",
                "brand",
                "collage_customer",
                "collage_marketing",
                "commercial",
                "commercial_custom_settings",
                "commercial_pay_setting",
                "coupon",
                "coupon_rule_dish",
                "coupon_source",
                "customer",
                "customer_card_time",
                "customer_card_time_expend",
                "customer_coupon",
                "customer_extra",
                "customer_group_level",
                "customer_integral",
                "customer_level_rule",
                "customer_marketing_expanded",
                "customer_marketing_together",
                "customer_score_rule",
                "customer_stored",
                "cut_down_customer",
                "cut_down_history",
                "cut_down_marketing",
                "dish_brand_property",
                "dish_brand_type",
                "dish_property",
                "dish_property_type",
                "dish_setmeal",
                "dish_setmeal_group",
                "dish_shop",
                "dish_unit_dictionary",
                "expanded_commission",
                "flash_sales_marketing",
                "marketing_expanded",
                "marketing_put_on",
                "marketing_share",
                "marketing_together",
                "payment",
                "payment_item",
                "payment_item_extra",
                "payment_mode",
                "pos_sync_config",
                "push_message_customer",
                "push_plan_activity",
                "push_plan_new_dish",
                "shop_device",
                "tables",
                "table_area",
                "talent_plan",
                "talent_role",
                "talent_rule",
                "trade",
                "trade_customer",
                "trade_item",
                "trade_item_property",
                "trade_privilege",
                "trade_table",
                "trade_user",
                "wx_trade_customer",
                "customer_search_rule",
                "wx_form"
        );
    }

    private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        File outputDir = new File("out/gen");
        FileSystemUtils.deleteRecursively(outputDir);
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:mysql://rm-m5e838987b5zfu576jo.mysql.rds.aliyuncs.com:3306/zhongmei";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("zhongmeiyunfu")
                .setPassword("Zs463423266")
                .setDriverName("com.mysql.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(false)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setSuperEntityClass("com.zhongmei.yunfu.domain.entity.base.BaseEntity")
                .setSuperEntityColumns(new String[]{
                        "status_flag",
                        //"shop_identy","brand_identy",
                        "creator_id", "creator_name",
                        "updator_id", "updator_name",
                        "server_create_time", "server_update_time"})
                .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
        config.setOutputDir(outputDir.getPath())
                //.setKotlin(true)
                .setFileOverride(true)
                .setOpen(false) //生成后打开文件夹
                .setActiveRecord(false) //开启 activeRecord 模式
                .setEnableCache(false) //XML 二级缓存
                .setAuthor("pigeon88");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        config.setMapperName("%sMapper")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController");

        new AutoGeneratorEx()
                //.setTemplateEngine(new FreemarkerTemplateEngine())
                .setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setEntity("domain.entity")
                                .setMapper("domain.mapper")
                                //.setServiceImpl("service.impl")
                                .setController("controller")

                ).execute();
        System.out.println("output dir: " + outputDir.getAbsolutePath());
    }

    private void generateByTables(String packageName, String... tableNames) {
        generateByTables(true, packageName, tableNames);
    }
}

