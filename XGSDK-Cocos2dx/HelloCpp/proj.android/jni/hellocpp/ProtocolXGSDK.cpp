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

XGSDKResultListener *globalListener;
void ProtocolXGSDK::setResultListener(XGSDKResultListener *lis){
	globalListener = lis;
}
void ProtocolXGSDK::prepare(){
	LOGI("PREPARE BEGIN");
	channelId = (char*)malloc(sizeof(char));
	JniMethodInfo t;
	if(JniHelper::getStaticMethodInfo(t, "com/xgsdk/client/cocos2dx/XGSDKCocos2dxWrapper", "getInstance", "()Lcom/xgsdk/client/cocos2dx/XGSDKCocos2dxWrapper;")){
		LOGI("PREPARE ENTER METHOD");
		mXGEngine = t.env->CallStaticObjectMethod(t.classID, t.methodID);
	}
	CCMessageBox("PREPARE SUCCESS","INFO");
	LOGI("PREPARE END");
}

void ProtocolXGSDK::getChannelId(){
	LOGI("GET CHANNELID BEGIN");
	JniMethodInfo t;

	if(JniHelper::getMethodInfo(t, "com/xgsdk/client/cocos2dx/XGSDKCocos2dxWrapper","getChannelId", "()Ljava/lang/String;")){
		LOGI("ENTER GET CHANNELID METHOD");
		if(mXGEngine == NULL){
			LOGE("mXGEngine is NULL");
		}
		jstring jchannelId = static_cast<jstring>(t.env->CallObjectMethod(mXGEngine, t.methodID));
		free(channelId);
		channelId = jstringTostr(t.env, jchannelId);
	}
	LOGI("GET CHANNELID EXIT");
}

char *ProtocolXGSDK::getChannelID(){
	getChannelId();
	return channelId;
}

void ProtocolXGSDK::login(const char *msg){
	LOGI("LOGIN BEGIN");
	JniMethodInfo t;

	if(JniHelper::getMethodInfo(t, "com/xgsdk/client/cocos2dx/XGSDKCocos2dxWrapper", "login", "(Ljava/lang/String;)V")){
		LOGI("LOGIN ENTER METHOD");
		jstring jtmp = stoJstring(t.env,"");
		if(mXGEngine == NULL){
			LOGE("mXGEngine is NULL");
		}
		t.env->CallVoidMethod(mXGEngine, t.methodID, jtmp);
		t.env->DeleteLocalRef(jtmp);
	}
	LOGI("EXIT LOGIN");
}

void ProtocolXGSDK::pay(PayInfo payInfo){
	LOGI("PAY BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, "com/xgsdk/client/cocos2dx/XGSDKCocos2dxWrapper", "pay", "(Ljava/lang/String;IIILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")){
		LOGI("ENTER PAY METHOD");
		jstring juid = stoJstring(t.env, payInfo.uid);
		jint jproductTotalPirce = payInfo.productTotalPirce;
		jint jproductCount = payInfo.productCount;
		jint jproductUnitPrice = payInfo.productUnitPrice;
		jstring jproductId = stoJstring(t.env, payInfo.productId);
		jstring jproductName = stoJstring(t.env, payInfo.productName);
		jstring jproductDesc = stoJstring(t.env, payInfo.productDesc);
		jstring jcurrencyName = stoJstring(t.env, payInfo.currencyName);
		jstring jserverId = stoJstring(t.env, payInfo.serverId);
		jstring jserverName = stoJstring(t.env, payInfo.serverName);
		jstring jzoneId = stoJstring(t.env, payInfo.zoneId);
		jstring jzoneName = stoJstring(t.env, payInfo.zoneName);
		jstring jroleId = stoJstring(t.env, payInfo.roleId);
		jstring jroleName = stoJstring(t.env, payInfo.roleName);
		jstring jbalance = stoJstring(t.env, payInfo.balance);
		jstring jgameOrderId = stoJstring(t.env, payInfo.gameOrderId);
		jstring jext = stoJstring(t.env, payInfo.ext);
		jstring jnotifyURL = stoJstring(t.env, payInfo.notifyURL);

		if(mXGEngine == NULL){
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID, juid, jproductTotalPirce, jproductCount,
				jproductUnitPrice, jproductId, jproductName, jproductDesc, jcurrencyName,
				jserverId, jserverName, jzoneId, jzoneName, jroleId, jroleName, jbalance, jgameOrderId,
				jext, jnotifyURL);

		t.env->DeleteLocalRef(juid);
		t.env->DeleteLocalRef(jproductId);
		t.env->DeleteLocalRef(jproductName);
		t.env->DeleteLocalRef(jproductDesc);
		t.env->DeleteLocalRef(jcurrencyName);
		t.env->DeleteLocalRef(jserverId);
		t.env->DeleteLocalRef(jserverName);
		t.env->DeleteLocalRef(jzoneId);
		t.env->DeleteLocalRef(jzoneName);
		t.env->DeleteLocalRef(jroleId);
		t.env->DeleteLocalRef(jroleName);
		t.env->DeleteLocalRef(jbalance);
		t.env->DeleteLocalRef(jgameOrderId);
		t.env->DeleteLocalRef(jext);
		t.env->DeleteLocalRef(jnotifyURL);
	}

	LOGI("EXIT PAY");
}

void ProtocolXGSDK::onExitGame(const char *msg){
	LOGI("EXIT BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, "com/xgsdk/client/cocos2dx/XGSDKCocos2dxWrapper", "exit", "(Ljava/lang/String;)V")){
		LOGI("ENTER EXIT METHOD");

		if(mXGEngine == NULL){
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID, stoJstring(t.env,""));
	}
	LOGI("EXIT EXIT");
}

void ProtocolXGSDK::logout(const char *msg){
	LOGI("LOGOUT BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, "com/xgsdk/client/cocos2dx/XGSDKCocos2dxWrapper", "logout", "(Ljava/lang/String;)V")){
		LOGI("LOGOUT ENTER METHOD");
		if(mXGEngine == NULL){
			LOGE("mXGEngine is NULL");
		}
		t.env->CallVoidMethod(mXGEngine, t.methodID, stoJstring(t.env,""));
	}
	LOGI("EXIT LOGOUT");
}

void ProtocolXGSDK::switchUser(const char *msg){
	LOGI("SWITCHACCOUNT BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, "com/xgsdk/client/cocos2dx/XGSDKCocos2dxWrapper", "switchAccount", "(Ljava/lang/String;)V")){
		LOGI("ENTER SWITCHACCOUNT METHOD");

		if(mXGEngine == NULL){
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID, stoJstring(t.env,""));
	}
	LOGI("EXIT SWITCHACCOUNT");
}

void ProtocolXGSDK::onEnterGame(UserInfo userInfo){
	LOGI("ONENTERGAME BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, "com/xgsdk/client/cocos2dx/XGSDKCocos2dxWrapper", "onEnterGame", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")){
		LOGI("ENTER ONENTERGAME METOHD");
		if(mXGEngine == NULL){
			LOGE("mXGEngine is NULL");
		}

		jstring juid = stoJstring(t.env, userInfo.uid);
		jstring juserName = stoJstring(t.env, userInfo.userName);
		jstring jroleId = stoJstring(t.env, userInfo.roleId);
		jstring jroleName = stoJstring(t.env, userInfo.roleName);
		jstring jgender = stoJstring(t.env, userInfo.gender);
		jstring jlevel = stoJstring(t.env, userInfo.level);
		jstring jvipLevel = stoJstring(t.env, userInfo.vipLevel);
		jstring jbalance = stoJstring(t.env, userInfo.balance);
		jstring jpartyName = stoJstring(t.env, userInfo.partyName);
		jstring jserverId = stoJstring(t.env, userInfo.serverId);
		jstring jserverName = stoJstring(t.env, userInfo.serverName);

		t.env->CallVoidMethod(mXGEngine, t.methodID, juid, juserName, jroleId, jroleName, jgender, jlevel, jvipLevel, jbalance, jpartyName, jserverId, jserverName);

		t.env->DeleteLocalRef(juid);
		t.env->DeleteLocalRef(juserName);
		t.env->DeleteLocalRef(jroleId);
		t.env->DeleteLocalRef(jroleName);
		t.env->DeleteLocalRef(jgender);
		t.env->DeleteLocalRef(jlevel);
		t.env->DeleteLocalRef(jvipLevel);
		t.env->DeleteLocalRef(jbalance);
		t.env->DeleteLocalRef(jpartyName);
		t.env->DeleteLocalRef(jserverId);
		t.env->DeleteLocalRef(jserverName);
	}
	LOGI("EXIT ONENTERGAME");
}

void ProtocolXGSDK::onCreateRole(UserInfo userInfo){
	LOGI("ONCREATEROLE BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, "com/xgsdk/client/cocos2dx/XGSDKCocos2dxWrapper", "onCreateRole", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")){
		LOGI("ENTER ONCREATEROLE METHOD");
		jstring jroleId = stoJstring(t.env, userInfo.roleId);
		jstring jroleName = stoJstring(t.env, userInfo.roleName);
		jstring jgender = stoJstring(t.env, userInfo.gender);
		jstring jlevel = stoJstring(t.env, userInfo.level);
		jstring jvipLevel = stoJstring(t.env, userInfo.vipLevel);
		jstring jbalance = stoJstring(t.env, userInfo.balance);
		jstring jpartyName = stoJstring(t.env, userInfo.partyName);

		t.env->CallVoidMethod(mXGEngine, t.methodID, jroleId, jroleName, jgender, jlevel, jvipLevel, jbalance, jpartyName);

		t.env->DeleteLocalRef(jroleId);
		t.env->DeleteLocalRef(jroleName);
		t.env->DeleteLocalRef(jgender);
		t.env->DeleteLocalRef(jlevel);
		t.env->DeleteLocalRef(jvipLevel);
		t.env->DeleteLocalRef(jbalance);
		t.env->DeleteLocalRef(jpartyName);
	}
	LOGI("EXIT ONCREATEROLE");
}

void ProtocolXGSDK::showUserCenter(){
	LOGI("OPENUSERCENTER BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, "com/xgsdk/client/cocos2dx/XGSDKCocos2dxWrapper", "openUserCenter", "()V")){
		LOGI("ENTER OPENUSERCENTER METHOD");

		if(mXGEngine == NULL){
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID);
	}
	LOGI("EXIT OPENCENTER");
}

bool ProtocolXGSDK::isMethodSupport(const char *methodName){
	LOGI("IS METHOD SUPPORT BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, "com/xgsdk/client/cocos2dx/XGSDKCocos2dxWrapper", "isMethodSupport", "(Ljava/lang/String;)Z")){
		LOGI("IS METHOD SUPPORT ENTER");
		if(mXGEngine == NULL){
			LOGE("mXGEngine is NULL");
		}
		return t.env->CallBooleanMethod(mXGEngine, t.methodID);
	}else{
		LOGE("JNI METHOD CAN NOT BE FOUND");
		return false;
	}
	LOGI("IS METHOD SUPPORT EXIT");
}

void ProtocolXGSDK::showCocosNoChannelDialog(){
	LOGI("SHOW_COCOS_NO_CHANNEL_DIALOG BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, "com/xgsdk/client/cocos2dx/XGSDKCocos2dxWrapper", "showCocosNoChannelDialog","()V")){
		LOGI("ENTER SHOW_COCOS_NO_CHANNEL_DIALOG METHOD");

		if(mXGEngine == NULL){
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID);
	}
	LOGI("EXIT SHOW_COCOS_NO_CHANNEL_DIALOG");
}

void ProtocolXGSDK::releaseResource(){
	free(channelId);

	delete globalListener;
	delete this;
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onLogoutSuccess
  (JNIEnv *env, jclass obj, jstring msg){
	char *tmp = jstringTostr(env,msg);
	if(globalListener == NULL){
		LOGE("globalListener is NULL");
	}
	globalListener->onLogoutFinish(0, tmp);
	free(tmp);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onLogoutFail
  (JNIEnv *env, jclass obj, jint retCode, jstring msg){
	char *tmp = jstringTostr(env,msg);
	if(globalListener == NULL){
		LOGE("globalListener is NULL");
	}
	globalListener->onLogoutFinish(-1, tmp);
	free(tmp);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onLoginSuccess
  (JNIEnv *env, jclass obj, jstring msg){
	char *tmp = jstringTostr(env,msg);
	if(globalListener == NULL){
		LOGE("globalListener is NULL");
	}
	globalListener->onLoginFinish(0, tmp);
	free(tmp);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onLoginFail
  (JNIEnv *env, jclass obj, jint retCode, jstring msg){
	char *tmp = jstringTostr(env,msg);
	if(globalListener == NULL){
		LOGE("globalListener is NULL");
	}
	globalListener->onLoginFinish(-1, tmp);
	free(tmp);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onLoginCancel
  (JNIEnv *env, jclass obj, jstring msg){
	char *tmp = jstringTostr(env,msg);
	if(globalListener == NULL){
		LOGE("globalListener is NULL");
	}
	globalListener->onLoginFinish(-2, tmp);
	free(tmp);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onInitFail
  (JNIEnv *env, jclass obj, jint retCode, jstring msg){
	char *tmp = jstringTostr(env,msg);
	if(globalListener == NULL){
		LOGE("globalListener is NULL");
	}
	globalListener->onInitFinish(-1, tmp);
	free(tmp);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxPayCallBack_onSuccess
  (JNIEnv *env, jclass obj, jstring msg){
	char *tmp = jstringTostr(env,msg);
	if(globalListener == NULL){
		LOGE("globalListener is NULL");
	}
	globalListener->onPayFinish(0, tmp);
	free(tmp);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxPayCallBack_onFail
  (JNIEnv *env, jclass obj, jint retCode, jstring msg){
	char *tmp = jstringTostr(env,msg);
	if(globalListener == NULL){
		LOGE("globalListener is NULL");
	}
	globalListener->onPayFinish(-1, tmp);
	free(tmp);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxPayCallBack_onCancel
  (JNIEnv *env, jclass obj, jstring msg){
	char *tmp = jstringTostr(env,msg);
	if(globalListener == NULL){
		LOGE("globalListener is NULL");
	}
	globalListener->onPayFinish(-2, tmp);
	free(tmp);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxPayCallBack_onOthers
  (JNIEnv *env, jclass obj, jint retCode, jstring msg){
	char *tmp = jstringTostr(env,msg);
	if(globalListener == NULL){
		LOGE("globalListener is NULL");
	}
	globalListener->onPayFinish(-3, tmp);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxExitCallBack_onExit
  (JNIEnv *env, jclass){
	char exitTmp[100] = "onExit";
	if(globalListener == NULL){
		LOGE("globalListener is NULL");
	}
	globalListener->onExitGame(0, exitTmp);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxExitCallBack_onNoChannelExiter
  (JNIEnv *env, jclass){
	char exitTmp[100] = "onNoChannelExit";
	if(globalListener == NULL){
		LOGE("globalListener is NULL");
	}
	globalListener->onExitGame(-1, exitTmp);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxExitCallBack_onCancel
  (JNIEnv *env, jclass){
	char exitTmp[100] = "onExitCancel";
	if(globalListener == NULL){
		LOGE("globalListener is NULL");
	}
	globalListener->onExitGame(-2, exitTmp);
}

#endif
