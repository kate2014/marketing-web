package com.zhongmei.yunfu.security;

import com.zhongmei.yunfu.core.security.Password;
import com.zhongmei.yunfu.core.security.Token;
import org.junit.Test;

import java.util.Arrays;

public class TokenTest {

    @Test
    public void test() {
        String generate = Password.create().generate("1001", "123456");
        System.out.println(generate);
        String encode = Token.getEncoder().encode("1001", generate);
        System.out.println(encode);
        String[] decode = Token.getDecoder().decode(encode);
        System.out.println(Arrays.toString(decode));
    }
}