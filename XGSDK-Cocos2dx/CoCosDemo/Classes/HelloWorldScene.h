#ifndef __HELLOWORLD_SCENE_H__
#define __HELLOWORLD_SCENE_H__

#include "cocos2d.h"

#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)

#include "../proj.android/jni/hellocpp/ProtocolXGSDK.h"

#endif

class XGSDKTestListener : public XGSDKListener{
public:

	void onLogoutSuccess(const char *msg);
	void onLogoutFail(const char *msg);
	void onInitFail(const char* msg);
	void onLoginSuccess(const char* msg);
	void onLoginFail(const char* msg);
	void onLoginCancel(const char* msg);
	void onPaySuccess(const char* msg);
	void onPayFail(const char *msg);
	void onPayCancel(const char * msg);
	void onExit();
	void onNoChannelExiter();
	void onCancel();

    ~XGSDKTestListener(){
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
    XGSDKTestListener *mTestListener;

    // implement the "static node()" method manually
    CREATE_FUNC(HelloWorld);

    void cleanup();
private:
	void login(CCObject* pSender);
    void loginQQ(CCObject* pSender);
    void loginWeixin(CCObject* pSender);
	void logout(CCObject* pSender);
	void pay(CCObject* pSender);
	void userCenter(CCObject* pSender);
    void switchUser(CCObject* pSender);
    void exitGame(CCObject* pSender);
	void createMenu(char const* name, int x, int y, cocos2d::SEL_MenuHandler handler);    	
};

#endif // __HELLOWORLD_SCENE_H__
