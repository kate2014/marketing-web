package com.zhongmei.yunfu.controller.model.excel;

import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

@RunWith(SpringRunner.class)
public class ExcelUtilsTest {

    @Test
    public void importExcel() throws Exception {
        Workbook workbook = new XSSFWorkbook(new FileInputStream(new File("./src/main/resources/static/templates/customer-import.xls").getCanonicalFile()));
        ExcelUtils.importExcelWorkbook(workbook, true, it -> {
            System.out.println(it);

            String name = it.get("姓名");
            String sex = it.get("性别");
            String mobile = it.get("手机号");
            String birthday = it.get("生日");
            String email = it.get("邮箱");
            String hobby = it.get("喜好");
            String address = it.get("所在地址");
            String desc = it.get("备注");

            if (StringUtils.isEmpty(name) || StringUtils.isEmpty(mobile)) {

            }

            CustomerEntity customerEntity = new CustomerEntity();
            customerEntity.setName(name);
            customerEntity.setGender("男".equals(sex) ? 1 : "女".equals(sex) ? 2 : 0);
            if (birthday != null) {
                customerEntity.setBirthday(new Date(Long.valueOf(birthday)));
            }
            customerEntity.setMobile(mobile);
            customerEntity.setEmail(email);
            customerEntity.setHobby(hobby);
            customerEntity.setAddress(address);
            customerEntity.setProfile(desc);
        });
    }

}
