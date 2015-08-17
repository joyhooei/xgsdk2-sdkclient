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
	int level;
	int vipLevel;
	const char *partyName;
	const char *serverId;
	const char *serverName;
};

struct PayInfo {
	const char *uid;
	const char *productId;
	const char *productName;
	const char *productDesc;
	int productAmount;
	const char *productUnit;
	int productUnitPrice;
	int totalPrice;
	int originalPrice;
	const char *currencyName;
	const char *custom;
	const char *gameTradeNo;
	const char *gameCallbackUrl;
	const char *serverId;
	const char *serverName;
	const char *zoneId;
	const char *zoneName;
	const char *roleId;
	const char *roleName;
	int level;
	int vipLevel;
};

struct EventInfo{
	const char *eventId; //事件id
	const char *eventDesc; //事件描述
	int eventVal; //事件值
	const char *eventBody; //事件内容 必须是json格式
};

struct MissionInfo{
    const char *missionId;
    const char *missionName; //任务名称
};

struct ItemInfo{
	const char *itemId;
	const char *itemName;
	const char *itemCount;
	int listPrice;
	int transPrice;
	int payGold;
	int payBindingGold;
	int curGold;
	int curBindingGold;
	int totalGold;
	int totalBindingGold;
};

struct GoldGainInfo{
	const char *gainChannel;
	int gold;
	int bindingGold;
	int curGold;
	int curBindingGold;
	int totalGold;
	int totalBindingGold;
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
	~ProtocolXGSDK() {

	}
public:
	void prepare();
	void getChannelId_();
	void login(const char *customParams = "");
	void pay(PayInfo &);
	void exit(const char *customParams = "");
	void logout(const char *customParams = "");
	void switchAccount(const char *customParams = "");

	void onEnterGame(UserInfo &);
	void onCreateRole(UserInfo &);
	void onRoleLevelUp(UserInfo &);
	void onRoleLogout(UserInfo &, const char *customParams);
	void onAccountCreate(const char *uid, const char *userName, const char *customParams);
	void onAccountLogout(const char *uid, const char *userName, const char *customParams);
	void onEvent(UserInfo &, EventInfo &, const char *customParams);
	void onMissionBegin(UserInfo &, MissionInfo &, const char *customParams);
	void onMissionSuccess(UserInfo &, MissionInfo &, const char *customParams);
	void onMissionFail(UserInfo &, MissionInfo &, const char *customParams);
	void onLevelsBegin(UserInfo &, const char *levelsId, const char *customParams);
	void onLevelsSuccess(UserInfo &, const char *levelsId, const char *customParams);
	void onLevelsFail(UserInfo &, const char *levelsId, const char *reason, const char *customParams);
	void onItemBuy(UserInfo &, ItemInfo &, const char *customParams);
	void onItemGet(UserInfo &, ItemInfo &, const char *customParams);
	void onItemConsume(UserInfo &, ItemInfo &, const char *customParams);
	void onGoldGain(UserInfo &, GoldGainInfo &, const char *customParams);

	void openUserCenter(const char *customParams = "");
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

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxExitCallBack_onExit
  (JNIEnv *, jclass);

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxExitCallBack_onNoChannelExiter(
		JNIEnv *, jclass);

JNIEXPORT void JNICALL Java_com_xgsdk_client_api_cocos2dx_Cocos2dxExitCallBack_onCancel(
		JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif

#endif

