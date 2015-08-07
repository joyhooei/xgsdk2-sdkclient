#include <jni.h>
#include <stdlib.h>

#ifndef PROTOCOLXGSDK_H
#define PROTOCOLXGSDK_H

#ifdef __cplusplus
extern "C" {
#endif

struct UserInfo {
	const char *uid;
	const char *userName;
	const char *roleId;
	const char *roleName;
	const char *gender;
	const char *level;
	const char *vipLevel;
	const char *balance;
	const char *partyName;
	const char *serverId;
	const char *serverName;
};

struct PayInfo {
	const char *uid;
	int productTotalPirce;
	int productCount;
	int productUnitPrice;
	const char *productId;
	const char *productName;
	const char *productDesc;
	const char *currencyName;
	const char *serverId;
	const char *serverName;
	const char *zoneId;
	const char *zoneName;
	const char *roleId;
	const char *roleName;
	const char *level;
	const char *vipLevel;
	const char *balance;
	const char *gameOrderId;
	const char *ext;
	const char *notifyURL;
};

struct EventInfo {
	const char *uid;
	const char *userName;
	const char *roleId;
	const char *roleName;
	const char *gender;
	const char *level;
	const char *vipLevel;
	const char *balance;
	const char *partyName;
	const char *serverId;
	const char *serverName;
	const char *eventId; //事件id
	const char *eventDesc; //事件描述
	int eventVal; //事件值
	const char *eventBody; //事件内容 必须是json格式
	const char *customParams; //扩展字段 必须是json格式
};

struct MissionInfo{
    const char *uid;
    const char *username;
    const char *roleId;
    const char *roleName;
    const char *gender;
    const char *level;
    const char *vipLevel;
    const char *balance;
    const char *partyName;
    const char *serverId;
    const char *serverName;
    const char *missionName; //任务名称
    const char *customParams; //扩展参数，必须是json
};

class XGSDKCallback {
public:
	virtual void onLogoutSuccess(const char *)=0;
	virtual void onLogoutFail(int, const char *)=0;

	virtual void onLoginSuccess(const char *)=0;
	virtual void onLoginFail(int, const char *)=0;
	virtual void onLoginCancel(const char *)=0;

	virtual void onInitFail(int, const char *)=0;

	virtual void onPaySuccess(const char *)=0;
	virtual void onPayFail(int, const char *)=0;
	virtual void onPayCancel(const char *)=0;
	virtual void onPayOthers(int, const char *)=0;

	virtual void onExit()=0;
	virtual void onNoChannelExiter()=0;
	virtual void onCancel()=0;

	virtual ~XGSDKCallback() {
	}
	;
};

class ProtocolXGSDK {
private:
	char *channelId;
	jobject mXGEngine;
public:
	ProtocolXGSDK() {

	}
private:
	~ProtocolXGSDK() {

	}
public:
	void prepare();
	void getChannelId_();
	void login(const char *msg = 0);
	void pay(PayInfo &);
	void exit(const char *msg = 0);
	void logout(const char *msg = 0);
	void switchAccount(const char *msg = 0);
	void onEnterGame(UserInfo &);
	void onCreateRole(UserInfo &);
	void onRoleLevelUp(UserInfo &);
	void onEvent(EventInfo &);
	void onMissionBegin(MissionInfo &);
	void onMissionSuccess(MissionInfo &);
	void onMissionFail(MissionInfo &);
	void openUserCenter(const char *msg = 0);
	bool isMethodSupport(const char *);

	char *getChannelId();

	void showCocosNoChannelDialog();
	void setListener(XGSDKCallback *);

	void releaseResource();
};

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxUserCallBack_onLogoutSuccess(
		JNIEnv *, jclass, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxUserCallBack_onLogoutFail(
		JNIEnv *, jclass, jint, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxUserCallBack_onLoginSuccess(
		JNIEnv *, jclass, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxUserCallBack_onLoginFail(
		JNIEnv *, jclass, jint, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxUserCallBack_onLoginCancel(
		JNIEnv *, jclass, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxUserCallBack_onInitFail(
		JNIEnv *, jclass, jint, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxPayCallBack_onSuccess(
		JNIEnv *, jclass, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxPayCallBack_onFail(
		JNIEnv *, jclass, jint, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxPayCallBack_onCancel(
		JNIEnv *, jclass, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxExitCallBack_onExit(
		JNIEnv *, jclass);

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxExitCallBack_onNoChannelExiter(
		JNIEnv *, jclass);

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxExitCallBack_onCancel(
		JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif

#endif

