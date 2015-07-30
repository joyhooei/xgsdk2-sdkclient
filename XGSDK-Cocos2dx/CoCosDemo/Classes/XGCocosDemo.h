#ifndef __HELLOWORLD_SCENE_H__
#define __HELLOWORLD_SCENE_H__

#include "cocos2d.h"

#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)

#include "../proj.android/jni/hellocpp/ProtocolXGSDK.h"

#endif

class XGSDKTestCallback : public XGSDKCallback{
public:

	void onLogoutSuccess(const char *msg);
	void onLogoutFail(int retCode, const char *msg);
	void onInitFail(int retCode, const char* msg);
	void onLoginSuccess(const char* msg);
	void onLoginFail(int retCode, const char* msg);
	void onLoginCancel(const char* msg);

	void onPaySuccess(const char* msg);
	void onPayFail(int retCode, const char *msg);
	void onPayCancel(const char * msg);
	void onPayOthers(int retCode, const char *msg);
	
	void onExit();
	void onNoChannelExiter();
	void onCancel();

    ~XGSDKTestCallback(){
    }
};

class HelloWorld : public cocos2d::CCLayer
{
public:
    // Here's a difference. Method 'init' in cocos2d-x returns bool, instead of returning 'id' in cocos2d-iphone
    virtual bool init();  

    // there's no 'id' in cpp, so we recommend returning the class instance pointer
    static cocos2d::CCScene* scene();
    
    // a selector callback
    void menuCloseCallback(CCObject* pSender);
    
    ProtocolXGSDK *mXgSdk;
    XGSDKTestCallback *mTestListener;
    char *channelId;

    // implement the "static node()" method manually
    CREATE_FUNC(HelloWorld);

    void cleanup();
private:
	void login(CCObject* pSender);
	void logout(CCObject* pSender);
	void pay(CCObject* pSender);
	void openUserCenter(CCObject* pSender);
    void switchAccount(CCObject* pSender);
    void exitGame(CCObject* pSender);
	void createMenu(char const* name, int x, int y, cocos2d::SEL_MenuHandler handler);    	
};

#endif // __HELLOWORLD_SCENE_H__
