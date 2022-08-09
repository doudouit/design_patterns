package com.tools;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ReUtil;

import java.util.regex.Pattern;

/**
 * 校验工具类
 */
public class Valid {
    public static void main(String[] args) {
        validPhone();
        validPassword();
    }

    public static void validPhone() {
        String mobile = "13101587778";
        boolean b = Validator.isMobile(mobile);
        System.out.println("手机号是否满足规则：" + b);
    }

    /**
     * 大小写字母，数字，特殊字符!@#$^&*，8-16位
     */
    public static void validPassword() {
        String password = "1Ddoujianxin#";
//        Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$&^*])[A-Za-z0-9!@#$&^*]{8,16}$");
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$^&*,\\._])[0-9a-zA-Z!@#$^&*,\\\\._]{8,16}$");
        boolean match = ReUtil.isMatch(pattern, password);
        System.out.println("密码是否满足规则：" + match);
    }
}
