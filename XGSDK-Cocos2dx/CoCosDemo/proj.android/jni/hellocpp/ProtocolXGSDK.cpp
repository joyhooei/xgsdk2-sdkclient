#include <jni.h>
#include "ProtocolXGSDK.h"
#include "platform/android/jni/JniHelper.h"
#include "cocos2d.h"
#include <android/log.h>


#define TAG "XGSDK" // 这个是自定义的LOG的标识
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型

using namespace cocos2d;

#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)

static jstring stoJstring( JNIEnv* env, const char* pat )
{
    //定义java String类 strClass
    jclass strClass = (env)->FindClass("java/lang/String");
    //获取java String类方法String(byte[],String)的构造器,用于将本地byte[]数组转换为一个新String
    jmethodID ctorID = (env)->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
    jbyteArray bytes = (env)->NewByteArray(strlen(pat));//建立byte数组
    (env)->SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte*)pat);//将char* 转换为byte数组
    jstring encoding = (env)->NewStringUTF("utf-8"); // 设置String, 保存语言类型,用于byte数组转换至String时的参数
    return (jstring)(env)->NewObject(strClass, ctorID, bytes, encoding);//将byte数组转换为java String,并输出
}

static char* jstringTostr(JNIEnv* jniEnv, jstring jstr) {
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
    return pStr;
}

XGSDKListener *globalListener;
void ProtocolXGSDK::setListener(XGSDKListener *lis){
	globalListener = lis;
}
void ProtocolXGSDK::init(){
	LOGI("INIT BEGIN");
	JniMethodInfo t;
	if(JniHelper::getStaticMethodInfo(t, "com/xgsdk/client/cocos2dx/XGSDKCocos2dxWrapper", "getInstance", "()Lcom/xgsdk/client/cocos2dx/XGSDKCocos2dxWrapper;")){
		LOGI("INIT Find getInstance");
		mXGEngine = t.env->CallStaticObjectMethod(t.classID, t.methodID);
	}
	CCMessageBox("Dont touch me!!","INFO");
	LOGI("INIT END");
}


void ProtocolXGSDK::login(){
	LOGI("LOGIN BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, "com/xgsdk/client/cocos2dx/XGSDKCocos2dxWrapper", "login", "()V")){
		LOGI("LOGIN ENTER METHOD");
		if(mXGEngine == NULL){
			LOGI("XGENGINE IS NULL");
			return;
		}
		t.env->CallVoidMethod(mXGEngine, t.methodID);
	}
	LOGI("EXIT LOGIN");
}

void ProtocolXGSDK::pay(const char *uid, int productTotalPirce, int productCount, int productUnitPrice,
		const char *productId, const char *productName, const char *productDesc, const char *currencyName,
		const char *serverId, const char *serverName, const char *roleId, const char *roleName,
		const char *balance, const char *gameOrderId, const char *ext, const char *notifyURL){
	LOGI("PAY BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, "com/xgsdk/client/cocos2dx/XGSDKCocos2dxWrapper", "pay", "(Ljava/lang/String;IIILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")){
		LOGI("ENTER PAY METHOD");
		jstring juid = stoJstring(t.env, uid);
		jint jproductTotalPirce = productTotalPirce;
		jint jproductCount = productCount;
		jint jproductUnitPrice = productUnitPrice;
		jstring jproductId = stoJstring(t.env, productId);
		jstring jproductName = stoJstring(t.env, productName);
		jstring jproductDesc = stoJstring(t.env, productDesc);
		jstring jcurrencyName = stoJstring(t.env, currencyName);
		jstring jserverId = stoJstring(t.env, serverId);
		jstring jserverName = stoJstring(t.env, serverName);
		jstring jroleId = stoJstring(t.env, roleId);
		jstring jroleName = stoJstring(t.env, roleName);
		jstring jbalance = stoJstring(t.env, balance);
		jstring jgameOrderId = stoJstring(t.env, gameOrderId);
		jstring jext = stoJstring(t.env, ext);
		jstring jnotifyURL = stoJstring(t.env, notifyURL);

		t.env->CallVoidMethod(mXGEngine, t.methodID, juid, jproductTotalPirce, jproductCount,
				jproductUnitPrice, jproductId, jproductName, jproductDesc, jcurrencyName,
				jserverId, jserverName, jroleId, jroleName, jbalance, jgameOrderId,
				jext, jnotifyURL);
	}
	LOGI("EXIT PAY");
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onLoginSuccess
  (JNIEnv *env, jclass obj, jstring msg){
	char *tmp = jstringTostr(env,msg);
	globalListener->onLoginSuccess(tmp);
	free(tmp);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onLoginFail
  (JNIEnv *env, jclass obj, jstring msg){
	char *tmp = jstringTostr(env,msg);
	globalListener->onLoginFail(tmp);
	free(tmp);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onLogoutSuccess
  (JNIEnv *env, jclass obj, jstring msg){
	char *tmp = jstringTostr(env,msg);
	globalListener->onLogoutSuccess(tmp);
	free(tmp);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onLogoutFail
  (JNIEnv *env, jclass obj, jstring msg){
	char *tmp = jstringTostr(env,msg);
	globalListener->onLogoutFail(tmp);
	free(tmp);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onInitFail
  (JNIEnv *env, jclass obj, jstring msg){
	char *tmp = jstringTostr(env,msg);
	globalListener->onInitFail(tmp);
	free(tmp);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxPayCallBack_onSuccess
  (JNIEnv *env, jclass obj, jstring msg){
	char *tmp = jstringTostr(env,msg);
	globalListener->onPaySuccess(tmp);
	free(tmp);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxPayCallBack_onFail
  (JNIEnv *env, jclass obj, jstring msg){
	char *tmp = jstringTostr(env,msg);
	globalListener->onPayFail(tmp);
	free(tmp);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxPayCallBack_onCancel
  (JNIEnv *env, jclass obj, jstring msg){
	char *tmp = jstringTostr(env,msg);
	globalListener->onPayCancel(tmp);
	free(tmp);
}

#endif
