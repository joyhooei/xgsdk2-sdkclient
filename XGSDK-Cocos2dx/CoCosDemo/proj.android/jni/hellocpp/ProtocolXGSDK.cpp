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
	jstring encoding = (env)->NewStringUTF("utf-8");// 设置String, 保存语言类型,用于byte数组转换至String时的参数
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

XGSDKCallback *globalListener;
char *XG_TMP;
const char XG_PACKAGE[] = "com/xgsdk/client/api/cocos2dx/XGSDKCocos2dxWrapper";
void ProtocolXGSDK::setListener(XGSDKCallback *lis) {
	globalListener = lis;
}
void ProtocolXGSDK::prepare() {
	LOGI("PREPARE BEGIN");
	channelId = (char*)malloc(sizeof(char));
	XG_TMP = (char*)malloc(sizeof(char));

	JniMethodInfo t;
	if(JniHelper::getStaticMethodInfo(t, XG_PACKAGE, "getInstance", "()Lcom/xgsdk/client/api/cocos2dx/XGSDKCocos2dxWrapper;")) {
		LOGI("PREPARE ENTER METHOD");
		mXGEngine = t.env->CallStaticObjectMethod(t.classID, t.methodID);
	}
	CCMessageBox("PREPARE SUCCESS","INFO");
	LOGI("PREPARE END");
}

void ProtocolXGSDK::getChannelId_() {
	LOGI("GET CHANNELID BEGIN");
	JniMethodInfo t;

	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "getChannelId", "()Ljava/lang/String;")) {
		LOGI("ENTER GET CHANNELID METHOD");
		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}
		jstring jchannelId = static_cast<jstring>(t.env->CallObjectMethod(mXGEngine, t.methodID));
		free(channelId);
		channelId = jstringTostr(t.env, jchannelId);
	}
	LOGI("GET CHANNELID EXIT");
}

char *ProtocolXGSDK::getChannelId() {
	getChannelId_();
	return channelId;
}

void ProtocolXGSDK::login(const char *customParams) {
	LOGI("LOGIN BEGIN");
	JniMethodInfo t;

	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "login", "(Ljava/lang/String;)V")) {
		LOGI("LOGIN ENTER METHOD");
		jstring jtmp = stoJstring(t.env, customParams);
		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}
		t.env->CallVoidMethod(mXGEngine, t.methodID, jtmp);
		t.env->DeleteLocalRef(jtmp);
	}
	LOGI("EXIT LOGIN");
}

void ProtocolXGSDK::pay(PayInfo &payInfo) {
	LOGI("PAY BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "pay", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;IIILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;II)V")) {
		LOGI("ENTER PAY METHOD");

		jstring juid = stoJstring(t.env, payInfo.uid);
		jstring jproductId = stoJstring(t.env, payInfo.productId);
		jstring jproductName = stoJstring(t.env, payInfo.productName);
		jstring jproductDesc = stoJstring(t.env, payInfo.productDesc);
		jint jproductAmount = payInfo.productAmount;
		jstring jproductUnit = stoJstring(t.env, payInfo.productUnit);
		jint jproductUnitPrice = payInfo.productUnitPrice;
		jint jtotalPrice = payInfo.totalPrice;
		jint joriginalPrice = payInfo.originalPrice;
		jstring jcurrencyName = stoJstring(t.env, payInfo.currencyName);
		jstring jcustom = stoJstring(t.env, payInfo.custom);
		jstring jgameTradeNo = stoJstring(t.env, payInfo.gameTradeNo);
		jstring jgameCallbackUrl = stoJstring(t.env, payInfo.gameCallbackUrl);
		jstring jserverId = stoJstring(t.env, payInfo.serverId);
		jstring jserverName = stoJstring(t.env, payInfo.serverName);
		jstring jzoneId = stoJstring(t.env, payInfo.zoneId);
		jstring jzoneName = stoJstring(t.env, payInfo.zoneName);
		jstring jroleId = stoJstring(t.env, payInfo.roleId);
		jstring jroleName = stoJstring(t.env, payInfo.roleName);
		jint jlevel = payInfo.level;
		jint jvipLevel = payInfo.vipLevel;


		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID, juid, jproductId,jproductName,
				jproductDesc,jproductAmount,jproductUnit,jproductUnitPrice,jtotalPrice,
				joriginalPrice,jcurrencyName,jcustom,jgameTradeNo,jgameCallbackUrl,
				jserverId,jserverName,jzoneId,jzoneName,jroleId,jroleName,jlevel,
				jvipLevel);

		t.env->DeleteLocalRef(juid);
		t.env->DeleteLocalRef(jproductId);
		t.env->DeleteLocalRef(jproductName);
		t.env->DeleteLocalRef(jproductDesc);
		t.env->DeleteLocalRef(jproductUnit);
		t.env->DeleteLocalRef(jcurrencyName);
		t.env->DeleteLocalRef(jcustom);
		t.env->DeleteLocalRef(jgameTradeNo);
		t.env->DeleteLocalRef(jgameCallbackUrl);
		t.env->DeleteLocalRef(jserverId);
		t.env->DeleteLocalRef(jserverName);
		t.env->DeleteLocalRef(jzoneId);
		t.env->DeleteLocalRef(jzoneName);
		t.env->DeleteLocalRef(jroleId);
		t.env->DeleteLocalRef(jroleName);

	}

	LOGI("EXIT PAY");
}

void ProtocolXGSDK::exit(const char *customParams) {
	LOGI("EXIT BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "exit", "(Ljava/lang/String;)V")) {
		LOGI("ENTER EXIT METHOD");
		jstring jtmp = stoJstring(t.env, customParams);
		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID, jtmp);
		t.env->DeleteLocalRef(jtmp);
	}
	if(channelId != NULL && XG_TMP != NULL){
		releaseResource();
	}
	LOGI("EXIT EXIT");
}

void ProtocolXGSDK::logout(const char *customParams) {
	LOGI("LOGOUT BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "logout", "(Ljava/lang/String;)V")) {
		LOGI("LOGOUT ENTER METHOD");
		jstring jtmp = stoJstring(t.env, customParams);
		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}
		t.env->CallVoidMethod(mXGEngine, t.methodID, jtmp);
		t.env->DeleteLocalRef(jtmp);
	}
	LOGI("EXIT LOGOUT");
}

void ProtocolXGSDK::switchAccount(const char *customParams) {
	LOGI("SWITCHACCOUNT BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "switchAccount", "(Ljava/lang/String;)V")) {
		LOGI("ENTER SWITCHACCOUNT METHOD");
		jstring jtmp = stoJstring(t.env, customParams);
		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID, jtmp);
		t.env->DeleteLocalRef(jtmp);
	}
	LOGI("EXIT SWITCHACCOUNT");
}
/*统计*/

void ProtocolXGSDK::onEnterGame(UserInfo &userInfo){
	LOGI("ONENTERGAME BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, "com/xgsdk/client/cocos2dx/XGSDKCocos2dxWrapper", "onEnterGame", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")) {
		LOGI("ENTER ONENTERGAME METOHD");
		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		jstring juid = stoJstring(t.env, userInfo.uid);
		jstring juserName = stoJstring(t.env, userInfo.userName);
		jstring jroleId = stoJstring(t.env, userInfo.roleId);
		jstring jroleName = stoJstring(t.env, userInfo.roleName);
		jstring jgender = stoJstring(t.env, userInfo.gender);
		jint jlevel = userInfo.level;
		jint jvipLevel = userInfo.vipLevel;
		jstring jpartyName = stoJstring(t.env, userInfo.partyName);
		jstring jserverId = stoJstring(t.env, userInfo.serverId);
		jstring jserverName = stoJstring(t.env, userInfo.serverName);

		t.env->CallVoidMethod(mXGEngine, t.methodID, juid, juserName, jroleId, jroleName, jgender, jlevel, jvipLevel, jpartyName, jserverId, jserverName);

		t.env->DeleteLocalRef(juid);
		t.env->DeleteLocalRef(juserName);
		t.env->DeleteLocalRef(jroleId);
		t.env->DeleteLocalRef(jroleName);
		t.env->DeleteLocalRef(jgender);
		t.env->DeleteLocalRef(jpartyName);
		t.env->DeleteLocalRef(jserverId);
		t.env->DeleteLocalRef(jserverName);

	}
	LOGI("EXIT ONENTERGAME");
}

void ProtocolXGSDK::onCreateRole(UserInfo &userInfo){
	LOGI("ONCREATEROLE BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "onCreateRole", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")) {
		LOGI("ENTER ONCREATEROLE METHOD");

		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		jstring juid = stoJstring(t.env, userInfo.uid);
		jstring juserName = stoJstring(t.env, userInfo.userName);
		jstring jroleId = stoJstring(t.env, userInfo.roleId);
		jstring jroleName = stoJstring(t.env, userInfo.roleName);
		jstring jgender = stoJstring(t.env, userInfo.gender);
		jint jlevel = userInfo.level;
		jint jvipLevel = userInfo.vipLevel;
		jstring jpartyName = stoJstring(t.env, userInfo.partyName);
		jstring jserverId = stoJstring(t.env, userInfo.serverId);
		jstring jserverName = stoJstring(t.env, userInfo.serverName);

		t.env->CallVoidMethod(mXGEngine, t.methodID, juid, juserName, jroleId, jroleName, jgender, jlevel, jvipLevel, jpartyName, jserverId, jserverName);

		t.env->DeleteLocalRef(juid);
		t.env->DeleteLocalRef(juserName);
		t.env->DeleteLocalRef(jroleId);
		t.env->DeleteLocalRef(jroleName);
		t.env->DeleteLocalRef(jgender);
		t.env->DeleteLocalRef(jpartyName);
		t.env->DeleteLocalRef(jserverId);
		t.env->DeleteLocalRef(jserverName);

	}
	LOGI("EXIT ONCREATEROLE");
}

void ProtocolXGSDK::onRoleLevelUp(UserInfo &userInfo){
	LOGI("ON ROLE LEVEL UP BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "onRoleLevelup", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")) {
		LOGI("ON ROLE LEVEL UP ENTER METHOD");

		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		jstring juid = stoJstring(t.env, userInfo.uid);
		jstring juserName = stoJstring(t.env, userInfo.userName);
		jstring jroleId = stoJstring(t.env, userInfo.roleId);
		jstring jroleName = stoJstring(t.env, userInfo.roleName);
		jstring jgender = stoJstring(t.env, userInfo.gender);
		jint jlevel = userInfo.level;
		jint jvipLevel = userInfo.vipLevel;
		jstring jpartyName = stoJstring(t.env, userInfo.partyName);
		jstring jserverId = stoJstring(t.env, userInfo.serverId);
		jstring jserverName = stoJstring(t.env, userInfo.serverName);

		t.env->CallVoidMethod(mXGEngine, t.methodID, juid, juserName, jroleId, jroleName, jgender, jlevel, jvipLevel, jpartyName, jserverId, jserverName);

		t.env->DeleteLocalRef(juid);
		t.env->DeleteLocalRef(juserName);
		t.env->DeleteLocalRef(jroleId);
		t.env->DeleteLocalRef(jroleName);
		t.env->DeleteLocalRef(jgender);
		t.env->DeleteLocalRef(jpartyName);
		t.env->DeleteLocalRef(jserverId);
		t.env->DeleteLocalRef(jserverName);
	}
	LOGI("EXIT ONROLELEVELUP");
}

void ProtocolXGSDK::onRoleLogout(UserInfo &userInfo, const char *customParams){
	LOGI("ON ROLE LOGOUT BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "onRoleLogout", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")) {
		LOGI("ON ROLE LOGOUT ENTER METHOD");

		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		jstring juid = stoJstring(t.env, userInfo.uid);
		jstring juserName = stoJstring(t.env, userInfo.userName);
		jstring jroleId = stoJstring(t.env, userInfo.roleId);
		jstring jroleName = stoJstring(t.env, userInfo.roleName);
		jstring jgender = stoJstring(t.env, userInfo.gender);
		jint jlevel = userInfo.level;
		jint jvipLevel = userInfo.vipLevel;
		jstring jpartyName = stoJstring(t.env, userInfo.partyName);
		jstring jserverId = stoJstring(t.env, userInfo.serverId);
		jstring jserverName = stoJstring(t.env, userInfo.serverName);
		jstring jcustomParams = stoJstring(t.env, customParams);

		t.env->CallVoidMethod(mXGEngine, t.methodID, juid, juserName, jroleId, jroleName, jgender, jlevel, jvipLevel, jpartyName, jserverId, jserverName, jcustomParams);

		t.env->DeleteLocalRef(juid);
		t.env->DeleteLocalRef(juserName);
		t.env->DeleteLocalRef(jroleId);
		t.env->DeleteLocalRef(jroleName);
		t.env->DeleteLocalRef(jgender);
		t.env->DeleteLocalRef(jpartyName);
		t.env->DeleteLocalRef(jserverId);
		t.env->DeleteLocalRef(jserverName);
		t.env->DeleteLocalRef(jcustomParams);
	}
	LOGI("EXIT ONROLELOGOUT");
}

void ProtocolXGSDK::onAccountCreate(const char *uid, const char *userName, const char *customParams){
	LOGI("ON ACCOUNT CREATE BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "onAccountCreate", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")) {
		jstring juid = stoJstring(t.env, uid);
		jstring juserName = stoJstring(t.env, userName);
		jstring jcustomParams = stoJstring(t.env, customParams);

		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID, juid, juserName, jcustomParams);

		t.env->DeleteLocalRef(juid);
		t.env->DeleteLocalRef(juserName);
		t.env->DeleteLocalRef(jcustomParams);

	}
	LOGI("EXIT ON ACCOUNT CREATE");
}

void ProtocolXGSDK::onAccountLogout(const char *uid, const char *userName, const char *customParams){
	LOGI("ON ACCOUNT LOGOUT BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "onAccountLogout", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")) {
		jstring juid = stoJstring(t.env, uid);
		jstring juserName = stoJstring(t.env, userName);
		jstring jcustomParams = stoJstring(t.env, customParams);

		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID, juid, juserName, jcustomParams);

		t.env->DeleteLocalRef(juid);
		t.env->DeleteLocalRef(juserName);
		t.env->DeleteLocalRef(jcustomParams);

	}
	LOGI("EXIT ON ACCOUNT LOGOUT");
}

void ProtocolXGSDK::onEvent(UserInfo &userInfo, EventInfo &eventInfo, const char *customParams){
	LOGI("ON EVENT BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "onEvent", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)V")) {
		LOGI("ON EVENT ENTER METHOD");

		jstring juid = stoJstring(t.env, userInfo.uid);
		jstring juserName = stoJstring(t.env, userInfo.userName);
		jstring jroleId = stoJstring(t.env, userInfo.roleId);
		jstring jroleName = stoJstring(t.env, userInfo.roleName);
		jstring jgender = stoJstring(t.env, userInfo.gender);
		jint jlevel = userInfo.level;
		jint jvipLevel = userInfo.vipLevel;
		jstring jpartyName = stoJstring(t.env, userInfo.partyName);
		jstring jserverId = stoJstring(t.env, userInfo.serverId);
		jstring jserverName = stoJstring(t.env, userInfo.serverName);

		jstring jeventId = stoJstring(t.env, eventInfo.eventId);
		jstring jeventDesc = stoJstring(t.env, eventInfo.eventDesc);
		jint eventVal = eventInfo.eventVal;
		jstring jeventBody = stoJstring(t.env, eventInfo.eventBody);

		jstring jcustomParams = stoJstring(t.env, customParams);

		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID, juid, juserName, jroleId, jroleName, jgender, jlevel,
								jvipLevel, jpartyName, jserverId, jserverName, jeventId, jeventDesc, eventVal, jeventBody,
								jcustomParams);

		t.env->DeleteLocalRef(juid);
		t.env->DeleteLocalRef(juserName);
		t.env->DeleteLocalRef(jroleId);
		t.env->DeleteLocalRef(jroleName);
		t.env->DeleteLocalRef(jgender);
		t.env->DeleteLocalRef(jpartyName);
		t.env->DeleteLocalRef(jserverId);
		t.env->DeleteLocalRef(jserverName);

		t.env->DeleteLocalRef(jeventId);
		t.env->DeleteLocalRef(jeventDesc);
		t.env->DeleteLocalRef(jeventBody);

		t.env->DeleteLocalRef(jcustomParams);
	}
	LOGI("EXIT ON EVENT");
}

void ProtocolXGSDK::onMissionBegin(UserInfo &userInfo, MissionInfo &missionInfo, const char *customParams){
	LOGI("ON MISSION BEGIN BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "onMissionBegin", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")){
		LOGI("ON MISSION BEGIN ENTER METHOD");

		jstring juid = stoJstring(t.env, userInfo.uid);
		jstring juserName = stoJstring(t.env, userInfo.userName);
		jstring jroleId = stoJstring(t.env, userInfo.roleId);
		jstring jroleName = stoJstring(t.env, userInfo.roleName);
		jstring jgender = stoJstring(t.env, userInfo.gender);
		jint jlevel = userInfo.level;
		jint jvipLevel = userInfo.vipLevel;
		jstring jpartyName = stoJstring(t.env, userInfo.partyName);
		jstring jserverId = stoJstring(t.env, userInfo.serverId);
		jstring jserverName = stoJstring(t.env, userInfo.serverName);

		jstring jmissionId = stoJstring(t.env, missionInfo.missionId);
		jstring jmissionName = stoJstring(t.env, missionInfo.missionName);

		jstring jcustomParams = stoJstring(t.env, customParams);

		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID, juid, juserName, jroleId, jroleName, jgender, jlevel,
										jvipLevel, jpartyName, jserverId, jserverName,
										jmissionId, jmissionName, jcustomParams);

		t.env->DeleteLocalRef(juid);
		t.env->DeleteLocalRef(juserName);
		t.env->DeleteLocalRef(jroleId);
		t.env->DeleteLocalRef(jroleName);
		t.env->DeleteLocalRef(jgender);
		t.env->DeleteLocalRef(jpartyName);
		t.env->DeleteLocalRef(jserverId);
		t.env->DeleteLocalRef(jserverName);

		t.env->DeleteLocalRef(jmissionId);
		t.env->DeleteLocalRef(jmissionName);

		t.env->DeleteLocalRef(jcustomParams);
	}
	LOGI("ON MISSION BEGIN EXIT");
}

void ProtocolXGSDK::onMissionSuccess(UserInfo &userInfo, MissionInfo &missionInfo, const char *customParams){
	LOGI("ON MISSION SUCCESS BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "onMissionSuccess", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")){
		LOGI("ON MISSION SUCCESS ENTER METHOD");

		jstring juid = stoJstring(t.env, userInfo.uid);
		jstring juserName = stoJstring(t.env, userInfo.userName);
		jstring jroleId = stoJstring(t.env, userInfo.roleId);
		jstring jroleName = stoJstring(t.env, userInfo.roleName);
		jstring jgender = stoJstring(t.env, userInfo.gender);
		jint jlevel = userInfo.level;
		jint jvipLevel = userInfo.vipLevel;
		jstring jpartyName = stoJstring(t.env, userInfo.partyName);
		jstring jserverId = stoJstring(t.env, userInfo.serverId);
		jstring jserverName = stoJstring(t.env, userInfo.serverName);

		jstring jmissionId = stoJstring(t.env, missionInfo.missionId);
		jstring jmissionName = stoJstring(t.env, missionInfo.missionName);

		jstring jcustomParams = stoJstring(t.env, customParams);

		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID, juid, juserName, jroleId, jroleName, jgender, jlevel,
										jvipLevel, jpartyName, jserverId, jserverName,
										jmissionId, jmissionName, jcustomParams);

		t.env->DeleteLocalRef(juid);
		t.env->DeleteLocalRef(juserName);
		t.env->DeleteLocalRef(jroleId);
		t.env->DeleteLocalRef(jroleName);
		t.env->DeleteLocalRef(jgender);
		t.env->DeleteLocalRef(jpartyName);
		t.env->DeleteLocalRef(jserverId);
		t.env->DeleteLocalRef(jserverName);

		t.env->DeleteLocalRef(jmissionId);
		t.env->DeleteLocalRef(jmissionName);

		t.env->DeleteLocalRef(jcustomParams);
	}
	LOGI("ON MISSION SUCCESS EXIT");
}

void ProtocolXGSDK::onMissionFail(UserInfo &userInfo, MissionInfo &missionInfo, const char *customParams){
	LOGI("ON MISSION FAIL BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "onMissionFail", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")){
		LOGI("ON MISSION FAIL ENTER METHOD");

		jstring juid = stoJstring(t.env, userInfo.uid);
		jstring juserName = stoJstring(t.env, userInfo.userName);
		jstring jroleId = stoJstring(t.env, userInfo.roleId);
		jstring jroleName = stoJstring(t.env, userInfo.roleName);
		jstring jgender = stoJstring(t.env, userInfo.gender);
		jint jlevel = userInfo.level;
		jint jvipLevel = userInfo.vipLevel;
		jstring jpartyName = stoJstring(t.env, userInfo.partyName);
		jstring jserverId = stoJstring(t.env, userInfo.serverId);
		jstring jserverName = stoJstring(t.env, userInfo.serverName);

		jstring jmissionId = stoJstring(t.env, missionInfo.missionId);
		jstring jmissionName = stoJstring(t.env, missionInfo.missionName);

		jstring jcustomParams = stoJstring(t.env, customParams);

		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID, juid, juserName, jroleId, jroleName, jgender, jlevel,
										jvipLevel, jpartyName, jserverId, jserverName,
										jmissionId, jmissionName, jcustomParams);

		t.env->DeleteLocalRef(juid);
		t.env->DeleteLocalRef(juserName);
		t.env->DeleteLocalRef(jroleId);
		t.env->DeleteLocalRef(jroleName);
		t.env->DeleteLocalRef(jgender);
		t.env->DeleteLocalRef(jpartyName);
		t.env->DeleteLocalRef(jserverId);
		t.env->DeleteLocalRef(jserverName);

		t.env->DeleteLocalRef(jmissionId);
		t.env->DeleteLocalRef(jmissionName);

		t.env->DeleteLocalRef(jcustomParams);
	}
	LOGI("ON MISSION FAIL EXIT");
}

void ProtocolXGSDK::onLevelsBegin(UserInfo &userInfo, const char *levelsId, const char *customParams){
	LOGI("ON MISSION FAIL BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "onLevelsBegin", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")){
		LOGI("ON LEVELS BEGIN ENTER METHOD");

		jstring juid = stoJstring(t.env, userInfo.uid);
		jstring juserName = stoJstring(t.env, userInfo.userName);
		jstring jroleId = stoJstring(t.env, userInfo.roleId);
		jstring jroleName = stoJstring(t.env, userInfo.roleName);
		jstring jgender = stoJstring(t.env, userInfo.gender);
		jint jlevel = userInfo.level;
		jint jvipLevel = userInfo.vipLevel;
		jstring jpartyName = stoJstring(t.env, userInfo.partyName);
		jstring jserverId = stoJstring(t.env, userInfo.serverId);
		jstring jserverName = stoJstring(t.env, userInfo.serverName);

		jstring jlevelsId = stoJstring(t.env, levelsId);

		jstring jcustomParams = stoJstring(t.env, customParams);

		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID, juid, juserName, jroleId, jroleName, jgender,
				jlevel, jvipLevel, jpartyName, jserverId, jserverName,
				jlevelsId, jcustomParams);


		t.env->DeleteLocalRef(juid);
		t.env->DeleteLocalRef(juserName);
		t.env->DeleteLocalRef(jroleId);
		t.env->DeleteLocalRef(jroleName);
		t.env->DeleteLocalRef(jgender);
		t.env->DeleteLocalRef(jpartyName);
		t.env->DeleteLocalRef(jserverId);
		t.env->DeleteLocalRef(jserverName);

		t.env->DeleteLocalRef(jlevelsId);

		t.env->DeleteLocalRef(jcustomParams);
	}
	LOGI("EXIT LEVELS BEGIN");
}

void ProtocolXGSDK::onLevelsSuccess(UserInfo &userInfo, const char *levelsId, const char *customParams){
	LOGI("ON MISSION FAIL BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "onLevelsSuccess", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")){
		LOGI("ON LEVELS BEGIN ENTER METHOD");

		jstring juid = stoJstring(t.env, userInfo.uid);
		jstring juserName = stoJstring(t.env, userInfo.userName);
		jstring jroleId = stoJstring(t.env, userInfo.roleId);
		jstring jroleName = stoJstring(t.env, userInfo.roleName);
		jstring jgender = stoJstring(t.env, userInfo.gender);
		jint jlevel = userInfo.level;
		jint jvipLevel = userInfo.vipLevel;
		jstring jpartyName = stoJstring(t.env, userInfo.partyName);
		jstring jserverId = stoJstring(t.env, userInfo.serverId);
		jstring jserverName = stoJstring(t.env, userInfo.serverName);

		jstring jlevelsId = stoJstring(t.env, levelsId);

		jstring jcustomParams = stoJstring(t.env, customParams);

		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID, juid, juserName, jroleId, jroleName, jgender,
				jlevel, jvipLevel, jpartyName, jserverId, jserverName,
				jlevelsId, jcustomParams);


		t.env->DeleteLocalRef(juid);
		t.env->DeleteLocalRef(juserName);
		t.env->DeleteLocalRef(jroleId);
		t.env->DeleteLocalRef(jroleName);
		t.env->DeleteLocalRef(jgender);
		t.env->DeleteLocalRef(jpartyName);
		t.env->DeleteLocalRef(jserverId);
		t.env->DeleteLocalRef(jserverName);

		t.env->DeleteLocalRef(jlevelsId);

		t.env->DeleteLocalRef(jcustomParams);
	}
	LOGI("EXIT LEVELS SUCCESS");
}

void ProtocolXGSDK::onLevelsFail(UserInfo &userInfo, const char *levelsId, const char *reason, const char *customParams){
	LOGI("ON LEVELS FAIL BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "onLevelsFail", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")){
		LOGI("ON LEVELS BEGIN ENTER METHOD");

		jstring juid = stoJstring(t.env, userInfo.uid);
		jstring juserName = stoJstring(t.env, userInfo.userName);
		jstring jroleId = stoJstring(t.env, userInfo.roleId);
		jstring jroleName = stoJstring(t.env, userInfo.roleName);
		jstring jgender = stoJstring(t.env, userInfo.gender);
		jint jlevel = userInfo.level;
		jint jvipLevel = userInfo.vipLevel;
		jstring jpartyName = stoJstring(t.env, userInfo.partyName);
		jstring jserverId = stoJstring(t.env, userInfo.serverId);
		jstring jserverName = stoJstring(t.env, userInfo.serverName);

		jstring jlevelsId = stoJstring(t.env, levelsId);

		jstring jreason = stoJstring(t.env, reason);

		jstring jcustomParams = stoJstring(t.env, customParams);

		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID, juid, juserName, jroleId, jroleName, jgender,
				jlevel, jvipLevel, jpartyName, jserverId, jserverName,
				jlevelsId, jreason, jcustomParams);


		t.env->DeleteLocalRef(juid);
		t.env->DeleteLocalRef(juserName);
		t.env->DeleteLocalRef(jroleId);
		t.env->DeleteLocalRef(jroleName);
		t.env->DeleteLocalRef(jgender);
		t.env->DeleteLocalRef(jpartyName);
		t.env->DeleteLocalRef(jserverId);
		t.env->DeleteLocalRef(jserverName);

		t.env->DeleteLocalRef(jlevelsId);
		t.env->DeleteLocalRef(jreason);

		t.env->DeleteLocalRef(jcustomParams);
	}
	LOGI("ON LEVELS FAIL EXIT");
}

void ProtocolXGSDK::onItemBuy(UserInfo &userInfo, ItemInfo &itemInfo, const char *customParams){
	LOGI("ON ITEM BUY BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "onItemBuy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIIIIIIIILjava/lang/String;)V")){
		LOGI("ON ITEM BUY ENTER METHOD");
		jstring juid = stoJstring(t.env, userInfo.uid);
		jstring juserName = stoJstring(t.env, userInfo.userName);
		jstring jroleId = stoJstring(t.env, userInfo.roleId);
		jstring jroleName = stoJstring(t.env, userInfo.roleName);
		jstring jgender = stoJstring(t.env, userInfo.gender);
		jint jlevel = userInfo.level;
		jint jvipLevel = userInfo.vipLevel;
		jstring jpartyName = stoJstring(t.env, userInfo.partyName);
		jstring jserverId = stoJstring(t.env, userInfo.serverId);
		jstring jserverName = stoJstring(t.env, userInfo.serverName);

		jstring jitemId = stoJstring(t.env, itemInfo.itemId);
		jstring jitemName = stoJstring(t.env, itemInfo.itemName);
		jstring jitemCount = stoJstring(t.env, itemInfo.itemCount);
		jint jlistPrice = itemInfo.listPrice;
		jint jtransPrice = itemInfo.transPrice;
		jint jpayGold = itemInfo.payGold;
		jint jpayBindingGold = itemInfo.payBindingGold;
		jint jcurGold = itemInfo.curGold;
		jint jcurBindingGold = itemInfo.curBindingGold;
		jint jtotalGold = itemInfo.totalGold;
		jint jtotalBindingGold = itemInfo.totalBindingGold;

		jstring jcustomParams = stoJstring(t.env, customParams);

		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID, juid, juserName, jroleId, jroleName, jgender,
				jlevel, jvipLevel, jpartyName, jserverId, jserverName,
				jitemId, jitemName,jitemCount, jlistPrice, jtransPrice, jpayGold,
				jpayBindingGold,jcurGold, jcurBindingGold, jtotalGold, jtotalBindingGold, jcustomParams);


		t.env->DeleteLocalRef(juid);
		t.env->DeleteLocalRef(juserName);
		t.env->DeleteLocalRef(jroleId);
		t.env->DeleteLocalRef(jroleName);
		t.env->DeleteLocalRef(jgender);
		t.env->DeleteLocalRef(jpartyName);
		t.env->DeleteLocalRef(jserverId);
		t.env->DeleteLocalRef(jserverName);

		t.env->DeleteLocalRef(jitemId);
		t.env->DeleteLocalRef(jitemName);
		t.env->DeleteLocalRef(jitemCount);

		t.env->DeleteLocalRef(jcustomParams);
	}
	LOGI("ON ITEM BUY EXIT");
}

void ProtocolXGSDK::onItemGet(UserInfo &userInfo, ItemInfo &itemInfo, const char *customParams){
	LOGI("ON ITEM GET BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "onItemGet", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIIIIIIIILjava/lang/String;)V")){
		LOGI("ON ITEM GET ENTER METHOD");
		jstring juid = stoJstring(t.env, userInfo.uid);
		jstring juserName = stoJstring(t.env, userInfo.userName);
		jstring jroleId = stoJstring(t.env, userInfo.roleId);
		jstring jroleName = stoJstring(t.env, userInfo.roleName);
		jstring jgender = stoJstring(t.env, userInfo.gender);
		jint jlevel = userInfo.level;
		jint jvipLevel = userInfo.vipLevel;
		jstring jpartyName = stoJstring(t.env, userInfo.partyName);
		jstring jserverId = stoJstring(t.env, userInfo.serverId);
		jstring jserverName = stoJstring(t.env, userInfo.serverName);

		jstring jitemId = stoJstring(t.env, itemInfo.itemId);
		jstring jitemName = stoJstring(t.env, itemInfo.itemName);
		jstring jitemCount = stoJstring(t.env, itemInfo.itemCount);
		jint jlistPrice = itemInfo.listPrice;
		jint jtransPrice = itemInfo.transPrice;
		jint jpayGold = itemInfo.payGold;
		jint jpayBindingGold = itemInfo.payBindingGold;
		jint jcurGold = itemInfo.curGold;
		jint jcurBindingGold = itemInfo.curBindingGold;
		jint jtotalGold = itemInfo.totalGold;
		jint jtotalBindingGold = itemInfo.totalBindingGold;

		jstring jcustomParams = stoJstring(t.env, customParams);

		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID, juid, juserName, jroleId, jroleName, jgender,
				jlevel, jvipLevel, jpartyName, jserverId, jserverName,
				jitemId, jitemName,jitemCount, jlistPrice, jtransPrice, jpayGold,
				jpayBindingGold,jcurGold, jcurBindingGold, jtotalGold, jtotalBindingGold, jcustomParams);


		t.env->DeleteLocalRef(juid);
		t.env->DeleteLocalRef(juserName);
		t.env->DeleteLocalRef(jroleId);
		t.env->DeleteLocalRef(jroleName);
		t.env->DeleteLocalRef(jgender);
		t.env->DeleteLocalRef(jpartyName);
		t.env->DeleteLocalRef(jserverId);
		t.env->DeleteLocalRef(jserverName);

		t.env->DeleteLocalRef(jitemId);
		t.env->DeleteLocalRef(jitemName);
		t.env->DeleteLocalRef(jitemCount);

		t.env->DeleteLocalRef(jcustomParams);
	}
	LOGI("ON ITEM GET EXIT");
}

void ProtocolXGSDK::onItemConsume(UserInfo &userInfo, ItemInfo &itemInfo, const char *customParams){
	LOGI("ON ITEM GET BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "onItemConsume", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIIIIIIIILjava/lang/String;)V")){
		LOGI("ON ITEM GET ENTER METHOD");
		jstring juid = stoJstring(t.env, userInfo.uid);
		jstring juserName = stoJstring(t.env, userInfo.userName);
		jstring jroleId = stoJstring(t.env, userInfo.roleId);
		jstring jroleName = stoJstring(t.env, userInfo.roleName);
		jstring jgender = stoJstring(t.env, userInfo.gender);
		jint jlevel = userInfo.level;
		jint jvipLevel = userInfo.vipLevel;
		jstring jpartyName = stoJstring(t.env, userInfo.partyName);
		jstring jserverId = stoJstring(t.env, userInfo.serverId);
		jstring jserverName = stoJstring(t.env, userInfo.serverName);

		jstring jitemId = stoJstring(t.env, itemInfo.itemId);
		jstring jitemName = stoJstring(t.env, itemInfo.itemName);
		jstring jitemCount = stoJstring(t.env, itemInfo.itemCount);
		jint jlistPrice = itemInfo.listPrice;
		jint jtransPrice = itemInfo.transPrice;
		jint jpayGold = itemInfo.payGold;
		jint jpayBindingGold = itemInfo.payBindingGold;
		jint jcurGold = itemInfo.curGold;
		jint jcurBindingGold = itemInfo.curBindingGold;
		jint jtotalGold = itemInfo.totalGold;
		jint jtotalBindingGold = itemInfo.totalBindingGold;

		jstring jcustomParams = stoJstring(t.env, customParams);

		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID, juid, juserName, jroleId, jroleName, jgender,
				jlevel, jvipLevel, jpartyName, jserverId, jserverName,
				jitemId, jitemName,jitemCount, jlistPrice, jtransPrice, jpayGold,
				jpayBindingGold,jcurGold, jcurBindingGold, jtotalGold, jtotalBindingGold, jcustomParams);


		t.env->DeleteLocalRef(juid);
		t.env->DeleteLocalRef(juserName);
		t.env->DeleteLocalRef(jroleId);
		t.env->DeleteLocalRef(jroleName);
		t.env->DeleteLocalRef(jgender);
		t.env->DeleteLocalRef(jpartyName);
		t.env->DeleteLocalRef(jserverId);
		t.env->DeleteLocalRef(jserverName);

		t.env->DeleteLocalRef(jitemId);
		t.env->DeleteLocalRef(jitemName);
		t.env->DeleteLocalRef(jitemCount);

		t.env->DeleteLocalRef(jcustomParams);
	}
	LOGI("ON ITEM CONSUME EXIT");
}

void ProtocolXGSDK::onGoldGain(UserInfo &userInfo, GoldGainInfo &goldGainInfo, const char *customParams){
	LOGI("ON GOLD GAIN BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "onGoldGain", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIIIIILjava/lang/String;)V")){
		LOGI("ON ITEM GET ENTER METHOD");
		jstring juid = stoJstring(t.env, userInfo.uid);
		jstring juserName = stoJstring(t.env, userInfo.userName);
		jstring jroleId = stoJstring(t.env, userInfo.roleId);
		jstring jroleName = stoJstring(t.env, userInfo.roleName);
		jstring jgender = stoJstring(t.env, userInfo.gender);
		jint jlevel = userInfo.level;
		jint jvipLevel = userInfo.vipLevel;
		jstring jpartyName = stoJstring(t.env, userInfo.partyName);
		jstring jserverId = stoJstring(t.env, userInfo.serverId);
		jstring jserverName = stoJstring(t.env, userInfo.serverName);

		jstring jgainChannel = stoJstring(t.env, goldGainInfo.gainChannel);
		jint jgold = goldGainInfo.gold;
		jint jbindingGold = goldGainInfo.bindingGold;
		jint jcurGold = goldGainInfo.curGold;
		jint jcurBindingGold = goldGainInfo.curBindingGold;
		jint jtotalGold = goldGainInfo.totalGold;
		jint jtotalBindingGold = goldGainInfo.totalBindingGold;

		t.env->DeleteLocalRef(juid);
		t.env->DeleteLocalRef(juserName);
		t.env->DeleteLocalRef(jroleId);
		t.env->DeleteLocalRef(jroleName);
		t.env->DeleteLocalRef(jgender);
		t.env->DeleteLocalRef(jpartyName);
		t.env->DeleteLocalRef(jserverId);
		t.env->DeleteLocalRef(jserverName);

		t.env->DeleteLocalRef(jgainChannel);

	}
	LOGI("ON GOLD GAIN EXIT");
}



void ProtocolXGSDK::openUserCenter(const char *customParams) {
	LOGI("OPENUSERCENTER BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "openUserCenter", "(Ljava/lang/String;)V")) {
		LOGI("ENTER OPENUSERCENTER METHOD");
		jstring jtmp = stoJstring(t.env, customParams);
		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID, jtmp);
		t.env->DeleteLocalRef(jtmp);
	}
	LOGI("EXIT OPENCENTER");
}

bool ProtocolXGSDK::isMethodSupport(const char *methodName) {
	LOGI("IS METHOD SUPPORT BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "isMethodSupport", "(Ljava/lang/String;)Z")) {
		LOGI("IS METHOD SUPPORT ENTER");
		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}
		return t.env->CallBooleanMethod(mXGEngine, t.methodID);
	} else {
		LOGE("JNI METHOD CAN NOT BE FOUND");
		return false;
	}
	LOGI("IS METHOD SUPPORT EXIT");
}

void ProtocolXGSDK::showCocosNoChannelDialog() {
	LOGI("SHOW_COCOS_NO_CHANNEL_DIALOG BEGIN");
	JniMethodInfo t;
	if(JniHelper::getMethodInfo(t, XG_PACKAGE, "showCocosNoChannelDialog","()V")) {
		LOGI("ENTER SHOW_COCOS_NO_CHANNEL_DIALOG METHOD");

		if(mXGEngine == NULL) {
			LOGE("mXGEngine is NULL");
		}

		t.env->CallVoidMethod(mXGEngine, t.methodID);
	}
	LOGI("EXIT SHOW_COCOS_NO_CHANNEL_DIALOG");
}

void ProtocolXGSDK::releaseResource(){
	free(channelId);
	free(XG_TMP);
	channelId = NULL;
	XG_TMP = NULL;
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxUserCallBack_onLogoutSuccess
(JNIEnv *env, jclass obj, jstring msg) {
	free(XG_TMP);
	XG_TMP = jstringTostr(env,msg);
	if(globalListener == NULL) {
		LOGE("globalListener is NULL");
	}
	globalListener->onLogoutSuccess(XG_TMP);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxUserCallBack_onLogoutFail
(JNIEnv *env, jclass obj, jint retCode, jstring msg) {
	free(XG_TMP);
	XG_TMP = jstringTostr(env,msg);
	if(globalListener == NULL) {
		LOGE("globalListener is NULL");
	}
	globalListener->onLogoutFail(retCode, XG_TMP);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxUserCallBack_onLoginSuccess
(JNIEnv *env, jclass obj, jstring msg) {
	free(XG_TMP);
	XG_TMP = jstringTostr(env,msg);
	if(globalListener == NULL) {
		LOGE("globalListener is NULL");
	}
	globalListener->onLoginSuccess(XG_TMP);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxUserCallBack_onLoginFail
(JNIEnv *env, jclass obj, jint retCode, jstring msg) {
	free(XG_TMP);
	XG_TMP = jstringTostr(env,msg);
	if(globalListener == NULL) {
		LOGE("globalListener is NULL");
	}
	globalListener->onLoginFail(retCode, XG_TMP);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxUserCallBack_onLoginCancel
(JNIEnv *env, jclass obj, jstring msg) {
	free(XG_TMP);
	XG_TMP = jstringTostr(env,msg);
	if(globalListener == NULL) {
		LOGE("globalListener is NULL");
	}
	globalListener->onLoginCancel(XG_TMP);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxUserCallBack_onInitFail
(JNIEnv *env, jclass obj, jint retCode, jstring msg) {
	free(XG_TMP);
	XG_TMP = jstringTostr(env,msg);
	if(globalListener == NULL) {
		LOGE("globalListener is NULL");
	}
	globalListener->onInitFail(retCode, XG_TMP);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxPayCallBack_onSuccess
(JNIEnv *env, jclass obj, jstring msg) {
	free(XG_TMP);
	XG_TMP = jstringTostr(env,msg);
	if(globalListener == NULL) {
		LOGE("globalListener is NULL");
	}
	globalListener->onPaySuccess(XG_TMP);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxPayCallBack_onFail
(JNIEnv *env, jclass obj, jint retCode, jstring msg) {
	free(XG_TMP);
	XG_TMP = jstringTostr(env,msg);
	if(globalListener == NULL) {
		LOGE("globalListener is NULL");
	}
	globalListener->onPayFail(retCode, XG_TMP);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxPayCallBack_onCancel
(JNIEnv *env, jclass obj, jstring msg) {
	free(XG_TMP);
	XG_TMP = jstringTostr(env,msg);
	if(globalListener == NULL) {
		LOGE("globalListener is NULL");
	}
	globalListener->onPayCancel(XG_TMP);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxPayCallBack_onOthers
(JNIEnv *env, jclass obj, jint retCode, jstring msg) {
	free(XG_TMP);
	XG_TMP = jstringTostr(env,msg);
	if(globalListener == NULL) {
		LOGE("globalListener is NULL");
	}
	globalListener->onPayOthers(retCode, XG_TMP);
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxExitCallBack_onExit
  (JNIEnv *, jclass) {
	if(globalListener == NULL) {
		LOGE("globalListener is NULL");
	}
	globalListener->onExit();
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxExitCallBack_onNoChannelExiter
(JNIEnv *, jclass) {
	if(globalListener == NULL) {
		LOGE("globalListener is NULL");
	}
	globalListener->onNoChannelExiter();
}

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxExitCallBack_onCancel
(JNIEnv *, jclass) {
	if(globalListener == NULL) {
		LOGE("globalListener is NULL");
	}
	globalListener->onCancel();
}

#endif
