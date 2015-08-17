#include "XGCocosDemo.h"
#include "AppMacros.h"
#include "cocos-ext.h"

#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)

#include "../proj.android/jni/hellocpp/ProtocolXGSDK.h"

#endif

USING_NS_CC;
USING_NS_CC_EXT;
void XGSDKTestCallback::onLogoutSuccess(const char *msg){
    CCLOG("LOGOUT Call");
    cocos2d::CCMessageBox(msg, "INFO");
}
void XGSDKTestCallback::onLogoutFail(int retCode, const char *msg){
    cocos2d::CCMessageBox(msg, "INFO");
}
void XGSDKTestCallback::onInitFail(int retCode, const char* msg){
    cocos2d::CCMessageBox(msg, "INFO");
}
void XGSDKTestCallback::onLoginSuccess(const char* msg){
    cocos2d::CCMessageBox(msg, "INFO");
}
void XGSDKTestCallback::onLoginFail(int retCode, const char* msg){
    cocos2d::CCMessageBox(msg, "INFO");
}
void XGSDKTestCallback::onLoginCancel(const char* msg){
    cocos2d::CCMessageBox(msg, "INFO");
}
void XGSDKTestCallback::onPaySuccess(const char* msg){
    cocos2d::CCMessageBox(msg, "INFO");
}
void XGSDKTestCallback::onPayFail(int retCode, const char *msg){
    cocos2d::CCMessageBox(msg, "INFO");
}
void XGSDKTestCallback::onPayCancel(const char * msg){
    cocos2d::CCMessageBox(msg, "INFO");
}
void XGSDKTestCallback::onPayOthers(int retCode, const char *msg){
    cocos2d::CCMessageBox(msg, "INFO");
}
void XGSDKTestCallback::onExit(){
    cocos2d::CCMessageBox("Exit Success", "INFO");
	CCDirector::sharedDirector()->end();
}
void XGSDKTestCallback::onNoChannelExiter(){
    cocos2d::CCMessageBox("Exit OnNoChannel", "INFO");
}
void XGSDKTestCallback::onCancel(){
    cocos2d::CCMessageBox("Exit onCancel", "INFO");
}


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
    mXgSdk->prepare();
    mTestListener = new XGSDKTestCallback();
    mXgSdk->setListener(mTestListener);
    channelId = mXgSdk->getChannelId();
    CCMessageBox(channelId, "INFO");

    int x = origin.x+150;
    int y = origin.y+visibleSize.height-50;     
	createMenu("登陆",x,y,menu_selector(HelloWorld::login));
	createMenu("登出",x+150,y,menu_selector(HelloWorld::logout));
	createMenu("支付",x,y-80,menu_selector(HelloWorld::pay));
	createMenu("用户中心",x+150,y-80,menu_selector(HelloWorld::openUserCenter));
	createMenu("切换账号",x,y-160,menu_selector(HelloWorld::switchAccount));
	createMenu("退游戏",x+150,y-160,menu_selector(HelloWorld::exitGame));
    return true;
}

void HelloWorld::login(CCObject* pSender)
{
    CCLOG("xgsdk call login...");
    mXgSdk->login();
}

void HelloWorld::logout(CCObject* pSender)
{
    CCLOG("xgsdk call logout...");
	mXgSdk->logout();
}

void HelloWorld::pay(CCObject* pSender)
{
    CCLOG("xgsdk call pay...");
    PayInfo payInfo;
    payInfo.uid = "4fd0144f02840ae77b6f42346c90d8bd";
    payInfo.productId = "payment017";
    payInfo.productName = "大宝剑";
    payInfo.productDesc = "倚天不出谁与争锋";
    payInfo.productAmount = 1;
    payInfo.productUnit = "个";
    payInfo.productUnitPrice = 1;
    payInfo.totalPrice = 1;
    payInfo.originalPrice = 1;
    payInfo.currencyName = "CNY";
    payInfo.custom = "ext";
    payInfo.gameTradeNo = "12480";
    payInfo.gameCallbackUrl = "http://console.xgsdk.com/sdkserver/receivePayResult";
    payInfo.serverId = "11";
    payInfo.serverName = "zhengfuzhihai";
    payInfo.zoneId = "zoneId";
    payInfo.zoneName = "zoneName";
    payInfo.roleId = "1234";
    payInfo.roleName = "yeye";
    payInfo.level = 10;
    payInfo.vipLevel = 7;
    
	mXgSdk->pay(payInfo);
}
void HelloWorld::switchAccount(CCObject* pSender)
{
    CCLOG("xgsdk demo call switch account...");
	mXgSdk->switchAccount();
}

void HelloWorld::openUserCenter(CCObject* pSender)
{
    CCLOG("call user center...");
	mXgSdk->openUserCenter();

}

void HelloWorld::exitGame(CCObject* pSender)
{
    CCLOG("xgsdk demo call exist game...");
	mXgSdk->exit();
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

void HelloWorld::cleanup(){
    delete mXgSdk;
    delete mTestListener;
    CCLayer::cleanup();
}

