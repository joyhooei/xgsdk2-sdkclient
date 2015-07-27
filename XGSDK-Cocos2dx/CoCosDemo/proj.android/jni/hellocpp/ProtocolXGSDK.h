#include <jni.h>

#ifndef PROTOCOLXGSDK_H
#define PROTOCOLXGSDK_H

#ifdef __cplusplus
extern "C" {
#endif

class XGSDKListener{
public:
	virtual void onLoginSuccess(const char *)=0;
	virtual void onLoginCancel(const char *)=0;
	virtual void onLoginFail(const char *)=0;
	virtual void onLogoutSuccess(const char *)=0;
	virtual void onLogoutFail(const char *)=0;
	virtual void onInitFail(const char *)=0;

	virtual void onPaySuccess(const char *)=0;
	virtual void onPayFail(const char *)=0;
	virtual void onPayCancel(const char *)=0;

	virtual void onExit()=0;
	virtual void onNoChannelExiter()=0;
	virtual void onCancel()=0;

	virtual ~XGSDKListener(){};
};

class ProtocolXGSDK{
private:
	jobject mXGEngine;

public:
	ProtocolXGSDK(){

	}

	~ProtocolXGSDK(){

	}

	void prepare();
	void login();
	void logout();
	void pay(const char *uid, int productTotalPirce, int productCount, int productUnitPrice,
			const char *productId, const char *productName, const char *productDesc, const char *currencyName,
			const char *serverId, const char *serverName, const char *roleId, const char *roleName,
			const char *balance, const char *gameOrderId, const char *ext, const char *notifyURL);
	void openUserCenter();
	void switchAccount();
	void exit();

	void showCocosNoChannelDialog();

	void setListener(XGSDKListener *);
};


JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onLogoutSuccess
  (JNIEnv *, jclass, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onLogoutFail
  (JNIEnv *, jclass, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onLoginSuccess
  (JNIEnv *, jclass, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onLoginFail
  (JNIEnv *, jclass, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onLoginCancel
  (JNIEnv *, jclass, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onInitFail
  (JNIEnv *, jclass, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxPayCallBack_onSuccess
  (JNIEnv *, jclass, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxPayCallBack_onFail
  (JNIEnv *, jclass, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxPayCallBack_onCancel
  (JNIEnv *, jclass, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxExitCallBack_onExit
  (JNIEnv *, jclass);

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxExitCallBack_onNoChannelExiter
  (JNIEnv *, jclass);

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxExitCallBack_onCancel
  (JNIEnv *, jclass);


#ifdef __cplusplus
}
#endif

#endif

