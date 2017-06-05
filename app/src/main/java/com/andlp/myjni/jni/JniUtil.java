package com.andlp.myjni.jni;

/**
 * 717219917@qq.com  2017/3/9 14:28
 */

public class JniUtil {

    public  static native String test();
    public  static native String getCpu();
    public  static native String callJava();
    public  static native String callCpp();
    public  static String callJava2(){
        return "callJava2";
    }
}
