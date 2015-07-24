#include "HelloWorldScene.h"
#include "AppMacros.h"
#include "cocos-ext.h"

#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)

#include "../proj.android/jni/hellocpp/ProtocolXGSDK.h"

#endif

USING_NS_CC;
USING_NS_CC_EXT;

class XGSDKTestListener : public XGSDKListener{
public:
    void onLogoutSuccess(const char *msg){
        CCMessageBox(msg, "INFO");
    }
    void onLogoutFail(const char *msg){
        CCMessageBox(msg, "INFO");
    }
    void onInitFail(const char* msg){
        CCMessageBox(msg, "INFO");
    }
    void onLoginSuccess(const char* msg){
        CCMessageBox(msg, "INFO");
    }
    void onLoginFail(const char* msg){
        CCMessageBox(msg, "INFO");
    }
    void onPaySuccess(const char* msg){
        CCMessageBox(msg, "INFO");
    }
    void onPayFail(const char *msg){
        CCMessageBox(msg, "INFO");
    }
    void onPayCancel(const char * msg){
        CCMessageBox(msg, "INFO");
    }
    ~XGSDKTestListener(){
    }
};


CCScene* HelloWorld::scene()
{
    // 'scene' is an autorelease object
    CCScene *scene = CCScene::create();
    
    // 'layer' is an autorelease object
    HelloWorld *layer = HelloWorld::create();

    // add layer as a child to scene
    scene->addChild(layer);

    // return the scene
    return scene;
}

// on "init" you need to initialize your instance
bool HelloWorld::init()
{
    //////////////////////////////
    // 1. super init first
    if ( !CCLayer::init() )
    {
        return false;
    }
    
    CCSize visibleSize = CCDirector::sharedDirector()->getVisibleSize();
    CCPoint origin = CCDirector::sharedDirector()->getVisibleOrigin();

    mXgSdk = new ProtocolXGSDK();
    mXgSdk->init();
    XGSDKTestListener *mTestListener = new XGSDKTestListener();
    mXgSdk->setListener(mTestListener);

    int x = origin.x+150;
    int y = origin.y+visibleSize.height-50;     
	createMenu("登陆",x,y,menu_selector(HelloWorld::login));
	createMenu("登出",x+150,y,menu_selector(HelloWorld::logout));
	createMenu("支付",x,y-80,menu_selector(HelloWorld::pay));
	createMenu("用户中心",x+150,y-80,menu_selector(HelloWorld::userCenter));
	createMenu("切换账号",x,y-160,menu_selector(HelloWorld::switchUser));
	createMenu("退游戏",x+150,y-160,menu_selector(HelloWorld::exitGame));
    return true;
}


void HelloWorld::menuCloseCallback(CCObject* pSender)
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_WINRT) || (CC_TARGET_PLATFORM == CC_PLATFORM_WP8)
	CCMessageBox("You pressed the close button. Windows Store Apps do not implement a close button.","Alert");
#else
    CCDirector::sharedDirector()->end();
#if (CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    exit(0);
#endif
#endif
}

void HelloWorld::login(CCObject* pSender)
{
    CCLOG("xgsdk call login...");
    mXgSdk->login();
}

void HelloWorld::loginQQ(CCObject* pSender)
{
    CCLOG("xgsdk call loginQQ...");
	CCMessageBox("LOGINQQ", "INFO");
}

void HelloWorld::loginWeixin(CCObject* pSender)
{
    CCLOG("xgsdk call loginWeixin...");
	CCMessageBox("LOGINWEIXIN", "INFO");
}

void HelloWorld::logout(CCObject* pSender)
{
    CCLOG("xgsdk call logout...");
	CCMessageBox("LOGINOUT","INFO");
}

void HelloWorld::pay(CCObject* pSender)
{
    CCLOG("xgsdk call pay...");
	mXgSdk->pay("123", 1, 1, 1, "p123", "test product", "for test", "currencyName", "A qu", "zhengfuzhihai", "1234", "yeye", "1000", "12480", "", "www.sina.com");
}
void HelloWorld::switchUser(CCObject* pSender)
{
    CCLOG("xgsdk demo call switch account...");
	CCMessageBox("SWITCHUSER", "INFO");
}

void HelloWorld::userCenter(CCObject* pSender)
{
    CCLOG("call user center...");
	CCMessageBox("USERCENTER","INFO");
}

void HelloWorld::exitGame(CCObject* pSender)
{
    CCLOG("xgsdk demo call exist game...");
	CCMessageBox("EXITGAME", "INFO");
}

void HelloWorld::createMenu(char const* name, int x, int y, SEL_MenuHandler handler)
{
    CCLabelTTF *label = CCLabelTTF::create(name, "Arial", 16); // create a exit botton
    CCMenuItemLabel *pMenuItem = CCMenuItemLabel::create(label, this,handler);
    pMenuItem->setPosition(ccp(x,y));
    CCMenu* pMenu = CCMenu::create(pMenuItem,NULL);
    pMenu->setPosition(CCPointZero);
    this->addChild(pMenu, 1);
}

