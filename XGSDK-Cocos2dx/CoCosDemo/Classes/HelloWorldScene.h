#ifndef __HELLOWORLD_SCENE_H__
#define __HELLOWORLD_SCENE_H__

#include "cocos2d.h"

#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)

#include "../proj.android/jni/hellocpp/ProtocolXGSDK.h"

#endif

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

    // implement the "static node()" method manually
    CREATE_FUNC(HelloWorld);
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
