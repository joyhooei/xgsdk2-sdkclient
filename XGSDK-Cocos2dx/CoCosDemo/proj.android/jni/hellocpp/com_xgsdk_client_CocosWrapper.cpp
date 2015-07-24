#include <jni.h>
#include "com_xgsdk_client_CocosWrapper.h"
#include "platform/android/jni/JniHelper.h"
#include "cocos2d.h"
#include <android/log.h>


#define TAG "myDemo-jni" // 这个是自定义的LOG的标识
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型

using namespace cocos2d;

#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
static char* jstringTostr(JNIEnv* jniEnv, jstring jstr) {
	LOGD("enter JNI");
    char* pStr = NULL;
    jclass jstrObj = jniEnv->FindClass("java/lang/String");
    jstring encode = jniEnv->NewStringUTF("utf-8");
    jmethodID methodId = jniEnv->GetMethodID(jstrObj, "getBytes",
                                             "(Ljava/lang/String;)[B");
    jbyteArray byteArray = (jbyteArray) jniEnv->CallObjectMethod(jstr,
                                                                 methodId, encode);
    jsize strLen = jniEnv->GetArrayLength(byteArray);
    jbyte *jBuf = jniEnv->GetByteArrayElements(byteArray, JNI_FALSE);
    if (jBuf > 0) {
        pStr = (char*) malloc(strLen + 1);
        if (!pStr) {
            return NULL;
        }
        memcpy(pStr, jBuf, strLen);
        pStr[strLen] = 0;
    }
    jniEnv->ReleaseByteArrayElements(byteArray, jBuf, 0);
    LOGD("exit JNI");
    return pStr;
}


JNIEXPORT void JNICALL Java_com_xgsdk_client_CocosWrapper_onResult
  (JNIEnv *env, jclass obj, jstring msg){
	char *data = jstringTostr(env, msg);
	CCMessageBox(data, "INFO");
	free(data);
}

void login_(){
	LOGD("enter JNI login");
	JniMethodInfo t;
	jobject sInstance;
	if(JniHelper::getStaticMethodInfo(t, "com/xgsdk/client/CocosWrapper", "getInstance", "()Lcom/xgsdk/client/CocosWrapper;")){
		sInstance = t.env->CallStaticObjectMethod(t.classID,t.methodID);
	}
	JniMethodInfo loginInfo;
	if(JniHelper::getMethodInfo(loginInfo, "com/xgsdk/client/CocosWrapper", "login", "(Ljava/lang/String;)V")){
		LOGD("enter method login");
		jstring jMsg = t.env->NewStringUTF("do not touch me again!!");
		loginInfo.env->CallVoidMethod(sInstance, loginInfo.methodID, jMsg);
	}
	LOGD("exit JNI login");
}
#endif

