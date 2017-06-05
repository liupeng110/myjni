#include <string.h>
#include <jni.h>
#include "../hello/hello.h"
#include <android/log.h>

 #include "iostream"
 #include "Point.cpp"
 #include <stdio.h>
 #include <cstring>
 using namespace std;


#ifdef __cplusplus
extern "C" {
#endif
#define LOG_TAG "jni_my"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
     static JavaVM *jvm = NULL;
     static jclass mClass = NULL;
     static jmethodID mMethodID = NULL;
     static jobject mObject = NULL;


    JNIEXPORT jstring
    JNICALL Java_com_andlp_myjni_jni_JniUtil_test(JNIEnv *env, jclass clazz) {
      LOGE("into  JniUtil.test()");
     //char msg[] = "callJava";
     //jstring jMsg = env->NewStringUTF(msg);
     //return jMsg;
     return env->NewStringUTF(hello());
    }


    JNIEXPORT jstring
        JNICALL Java_com_andlp_myjni_jni_JniUtil_callJava(JNIEnv *env, jclass clazz) {
        LOGE("into  callJava!");
         char msg[] = "callJava";
             jstring jMsg = env->NewStringUTF(msg);
       return jMsg;
  }

   JNIEXPORT jstring
          JNICALL Java_com_andlp_myjni_jni_JniUtil_callCpp(JNIEnv *env, jclass clazz) {
          LOGE("into  callcpp!");
           char msg[] = "callcpp";



             Point M;                  //用定义好的类创建一个对象 点M
          M.setPoint(100, 20);         //设置 M点 的x,y值
          M.printPoint();             //输出 M点 的信息
          cout<< M.xPos <<endl;       //尝试通过对象M访问属性xPos
             LOGE("into  callcpp!x:%d",M.xPos);
             LOGE("into  callcpp!y:%d",M.yPos);
             LOGE("into  callcpp!x+y:%d",M.yPos+M.xPos);
          char buf[8]="\0";
          sprintf( buf , "%s%d", msg ,M.yPos+M.xPos);
          //jstring jMsg = env->NewStringUTF(msg);
          jstring jMsg = env->NewStringUTF(buf);
         return jMsg;
    }




JNIEnv* getJNIEnv() {
    JNIEnv* env = NULL;
    int res = jvm->GetEnv((void**)&env, JNI_VERSION_1_4);

    if (res == JNI_EDETACHED) {
        //LOGI("GetEnv: not attached");
        if (jvm->AttachCurrentThread(&env, NULL) != 0) {
            LOGI("Failed to attach");
            return NULL;
        }
    } else if (res == JNI_OK) {
        //
    } else if (res == JNI_EVERSION) {
        LOGI("GetEnv: version not supported");
        return NULL;
    }
    return env;
}
jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    LOGI("JNI_OnLoad");
    JNIEnv* env = NULL;
    jint result = -1;
    if(vm->GetEnv((void**)&env, JNI_VERSION_1_4) != JNI_OK){
        return result;
    }
    jvm = vm;
    LOGI("JNI_VERSION_1_4");
    return JNI_VERSION_1_4;
}







    #if defined(__arm__)
      #if defined(__ARM_ARCH_7A__)
        #if defined(__ARM_NEON__)
          #if defined(__ARM_PCS_VFP)
            #define ABI "armeabi-v7a/NEON (hard-float)"
          #else
            #define ABI "armeabi-v7a/NEON"
          #endif
        #else
          #if defined(__ARM_PCS_VFP)
            #define ABI "armeabi-v7a (hard-float)"
          #else
            #define ABI "armeabi-v7a"
          #endif
        #endif
      #else
       #define ABI "armeabi"
      #endif
    #elif defined(__i386__)
       #define ABI "x86"
    #elif defined(__x86_64__)
       #define ABI "x86_64"
    #elif defined(__mips64)  /* mips64el-* toolchain defines __mips__ too */
       #define ABI "mips64"
    #elif defined(__mips__)
       #define ABI "mips"
    #elif defined(__aarch64__)
       #define ABI "arm64-v8a"
    #else
       #define ABI "unknown"
    #endif

  JNIEXPORT jstring
  JNICALL Java_com_andlp_myjni_jni_JniUtil_getCpu(JNIEnv *env, jclass clazz) {

     char msg[] = "jni测试cpu:" ABI ".";
     jstring jMsg = env->NewStringUTF(msg);//env->CallVoidMethod(thiz, mMethodID, jMsg);

    return jMsg;
   }

#ifdef __cplusplus
}
#endif