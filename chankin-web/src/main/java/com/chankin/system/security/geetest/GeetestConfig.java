package com.chankin.system.security.geetest;

/**
 * GeetestWeb配置文件
 */
public class GeetestConfig {

    // 填入自己的captcha_id和private_key
    private static final String geetest_id = "c7668adb58225df1a0fa08b7809addd0";
    private static final String geetest_key = "520f453387943869a888ee711587a457";
    //private static final String geetest_id = "796c2461adf8051c835e4a758a6091f6";
    //private static final String geetest_key = "0edad631bed761ab039d8391dd3103ff";

    private static final boolean newfailback = true;

    public static final String getGeetest_id() {
        return geetest_id;
    }

    public static final String getGeetest_key() {
        return geetest_key;
    }

    public static final boolean isnewfailback() {
        return newfailback;
    }
}
