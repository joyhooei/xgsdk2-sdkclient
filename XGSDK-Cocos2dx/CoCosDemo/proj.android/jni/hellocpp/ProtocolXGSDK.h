#include <jni.h>

#ifndef PROTOCOLXGSDK_H
#define PROTOCOLXGSDK_H

#ifdef __cplusplus
extern "C" {
#endif

typedef enum
{
	XGInitSuccess = 0,
	XGInitFail
} XGInitResult;

typedef enum
{
	XGLoginSuccess = 0,
	XGLoginFail,
	XGLoginCancel
} XGLoginResult;

typedef enum
{
	XGLogoutSuccess = 0,
	XGLogoutFail
} XGLogoutResult;

typedef enum
{
    XGPayResultSuccess = 0,
    XGPayResultCancle,
    XGPayResultFail,
    XGPayResultUnknow,
    XGPayHighRiskCurrency
} XGPayResult;

typedef enum
{
    XGToolBarTopLeft = 0,
    XGToolBarTopRight,
    XGToolBarMidLeft,
    XGToolBarMidRight,
    XGToolBarBottomLeft,
    XGToolBarBottomRight
} XGToolBarPlace;

typedef enum
{
	XGOrientationPortrait = 0,
	XGOrientationLandscape = 1
} XGOrientation;

struct XGBuyInfo
{
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
	const char *roleId;
	const char *roleName;
	const char *balance;
	const char *gameOrderId;
	const char *ext;
	const char *notifyURL;
};

class XGSDKListener{
public:
	virtual void onLoginSuccess(const char *)=0;
	virtual void onLoginFail(const char *)=0;
	virtual void onLogoutSuccess(const char *)=0;
	virtual void onLogoutFail(const char *)=0;
	virtual void onInitFail(const char *)=0;

	virtual void onPaySuccess(const char *)=0;
	virtual void onPayFail(const char *)=0;
	virtual void onPayCancel(const char *)=0;

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

	void init();
	void login();
	void logout();
	void pay(const char *uid, int productTotalPirce, int productCount, int productUnitPrice,
			const char *productId, const char *productName, const char *productDesc, const char *currencyName,
			const char *serverId, const char *serverName, const char *roleId, const char *roleName,
			const char *balance, const char *gameOrderId, const char *ext, const char *notifyURL);

	void setListener(XGSDKListener *);
};

/*
 * Class:     com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack
 * Method:    onLogoutSuccess
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onLogoutSuccess
  (JNIEnv *, jclass, jstring);

/*
 * Class:     com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack
 * Method:    onLogoutFail
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onLogoutFail
  (JNIEnv *, jclass, jstring);


JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onLoginSuccess
  (JNIEnv *, jclass, jstring);

/*
 * Class:     com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack
 * Method:    onLoginFail
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onLoginFail
  (JNIEnv *, jclass, jstring);

/*
 * Class:     com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack
 * Method:    onLoginCancel
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onLoginCancel
  (JNIEnv *, jclass, jstring);

/*
 * Class:     com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack
 * Method:    onInitFail
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxUserCallBack_onInitFail
  (JNIEnv *, jclass, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxPayCallBack_onSuccess
  (JNIEnv *, jclass, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxPayCallBack_onFail
  (JNIEnv *, jclass, jstring);

JNIEXPORT void JNICALL Java_com_xgsdk_client_cocos2dx_Cocos2dxPayCallBack_onCancel
  (JNIEnv *, jclass, jstring);


#ifdef __cplusplus
}
#endif

#endif

