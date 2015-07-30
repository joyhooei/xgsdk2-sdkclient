#include "HelloWorldScene.h"
#include "AppMacros.h"
//#include "PluginManager.h" 注释这行
#include "network/HttpClient.h"
#include "CocoStudio/Json/rapidjson/document.h"

USING_NS_CC;
//using namespace cocos2d::plugin; //注释这行
using namespace cocos2d::extension;

std::string g_strCurrentUid = "";
//cocos2d::plugin::ProtocolXGSDK* g_pXGSDK = NULL;这里修改为以下：
ProtocolXGSDK* g_pXGSDK = NULL;

class XgsdkServer : public CCObject
{
public:
    void verifySessionCompleted(cocos2d::CCNode *sender ,void *data)
    {
        cocos2d::extension::CCHttpResponse* response=(cocos2d::extension::CCHttpResponse*)data;
        if(!response) {CCLOG("Log:response =null,plase check it."); return;}
        
         //请求失败
        if(!response->isSucceed())
        {
            std::string errorMsg = "登录成功，会话验证错误。错误信息是:";
            errorMsg += response->getErrorBuffer();
            CCMessageBox(errorMsg.c_str(),"登录会话验证");
            return;
        }   

        int codeIndex=response->getResponseCode();
        const char* tag=response->getHttpRequest()->getTag();
        
        //请求成功
        std::vector<char>* buffer=response->getResponseData();
        std::string temp(buffer->begin(),buffer->end());
        
        rapidjson::Document _doc;
        _doc.Parse<0>(temp.c_str());
        if(_doc.HasParseError() || !_doc.HasMember("data"))
        {
            std::string errorMsg = "登录成功，会话验证错误。错误信息是:";
            errorMsg += "sdk server 返回值解析出错,返回值是";
            errorMsg += temp;
            CCMessageBox(errorMsg.c_str(),"会话验证失败");
            return;
        }
        
        if(!_doc["data"].HasMember("uId") || _doc["data"]["uId"].IsNull())
        {
            std::string errorMsg = "登录成功，会话验证错误。错误信息是:";
            errorMsg += "uId为空\r\n";
            errorMsg += temp;
            CCMessageBox(errorMsg.c_str(),"会话验证失败");
            return;
        }
        g_strCurrentUid = _doc["data"]["uId"].GetString();        
        
        //CCString* responseData=CCString::create(temp);
        //Json::Reader reader;//json解析  
        //Json::Value value;//表示一个json格式的对象  
        //if(reader.parse(responseData->getCString(),value))//解析出json放到json中区  
        //{  
              //这里就可以对返回来的信息做处理
        //}
        std::string info = "会话验证成功，uid是";
        info += g_strCurrentUid;
        info += ",服务端返回是：\r\n";
        info += temp;
        CCMessageBox(info.c_str(),"登录会话验证");        
    }
    void verifySession(const char* authInfo)
    {
        CCHttpRequest* httpRequest = new CCHttpRequest();
        httpRequest->setRequestType(cocos2d::extension::CCHttpRequest::kHttpGet);
        std::string url = "http://onsite.auth.xgsdk.com:8180/xgsdk/apiXgsdkAccount/verifySession?authInfo=";
        //std::string url = "http://dev.auth.xgsdk.com:38180/xgsdk/apiXgsdkAccount/verifySession?authInfo=";
        url += authInfo;
        httpRequest->setUrl(url.c_str());
        httpRequest->setResponseCallback(this,callfuncND_selector(XgsdkServer::verifySessionCompleted));

        cocos2d::extension::CCHttpClient* httpClient=cocos2d::extension::CCHttpClient::getInstance();
        httpClient->setTimeoutForConnect(10);//设置连接超时时间
        httpClient->setTimeoutForRead(10);//设置发送超时时间
        httpClient->send(httpRequest);//设置接收数据类型
        httpRequest->release();//释放
    }
};

class XGTestResultListener : public XGSDKResultListener
{
public:
    virtual void onInitFinish(int resultCode, const char* resultMsg)
    {
        
        if(resultCode == 0){
            CCMessageBox("初始化成功,当前连接的是正式环境","提示");
        }else{
            CCMessageBox("初始化失败","提示");
        }
        CCLOG("xgsdk callback(onInitFinish), ret code is %d, ret msg is %s",resultCode,resultMsg);
    }
    
    virtual void onLoginFinish(int resultCode, const char* resultMsg)
    {
        if(resultCode == 0){            
            XgsdkServer server;
            server.verifySession(resultMsg);
        }
        else
        {
            char strTemp[200];
            sprintf(strTemp,"登陆失败，code := %d",resultCode);
            CCMessageBox(strTemp,"提示");
        }
    
        CCLOG("xgsdk callback(onLoginFinish), ret code is %d, ret msg is %s",resultCode,resultMsg);
    }

    virtual void onLogoutFinish(int resultCode, const char* resultMsg)
    {
        if(resultCode == 0){
            CCMessageBox("登出成功","提示");
        }else{
            char strTemp[200];
            sprintf(strTemp,"登出失败，code := %d",resultCode);
            CCMessageBox(strTemp,"提示");
        }
        CCLOG("xgsdk callback(onLogoutFinish), ret code is %d, ret msg is %s",resultCode,resultMsg);
    }
    virtual void onPayFinish(int resultCode, const char* resultMsg)
    {       
        if(resultCode == 0){
            CCMessageBox("支付成功","提示");
        }else{
            char strTemp[200];
            sprintf(strTemp,"支付失败，msg := %s, code := %d", resultMsg, resultCode);
            CCMessageBox(strTemp,"提示");
        }
        CCLOG("xgsdk callback(onPayFinish), ret code is %d, ret msg is %s",resultCode,resultMsg);
    }
    
    virtual void onPlatformLoginFinish(int resultCode, const char* resultMsg)
    {
        char strTemp[200];
        sprintf(strTemp,"平台用户登录回调，返回值是%d",resultCode);
        CCMessageBox(strTemp,"提示");
        CCLOG("xgsdk callback(onPlatformLoginFinish), ret code is %d, ret msg is %s",resultCode,resultMsg);
    }
    virtual void onPlatformLogoutFinish(int resultCode, const char* resultMsg)
    {
        char strTemp[200];
        sprintf(strTemp,"平台用户登出回调，返回值是%d",resultCode);
        CCMessageBox(strTemp,"提示");        
        CCLOG("xgsdk callback(onPlatformLogoutFinish), ret code is %d, ret msg is %s",resultCode,resultMsg);
    }
    
    virtual void onShareFinish(int resultCode, char const* resultMsg)
    {
        char strTemp[200];
        sprintf(strTemp,"分享回调，返回值是%d",resultCode);
        CCMessageBox(strTemp,"提示");
        CCLOG("xgsdk callback(onShareFinish), ret code is %d, ret msg is %s",resultCode,resultMsg);
    }

    virtual void onExitGame(int resultCode, const char* resultMsg)
    {
        CCLOG("xgsdk callback(onExitGame), ret code is %d, ret msg is %s",resultCode,resultMsg);
        if(resultCode == 0)
        {
            CCDirector::sharedDirector()->end();
        }else{
            char strTemp[200];
            sprintf(strTemp,"退出 msg:= %s", resultMsg, resultCode);
            CCMessageBox(strTemp, "提示");
        }
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

    /////////////////////////////
    // 2. add a menu item with "X" image, which is clicked to quit the program
    //    you may modify it.

    // add a "close" icon to exit the progress. it's an autorelease object
    /*CCMenuItemImage *pCloseItem = CCMenuItemImage::create(
                                        "CloseNormal.png",
                                        "CloseSelected.png",
                                        this,
                                        menu_selector(HelloWorld::menuCloseCallback));
    
	pCloseItem->setPosition(ccp(origin.x + visibleSize.width - pCloseItem->getContentSize().width/2 ,
                                origin.y + pCloseItem->getContentSize().height/2));

    // create menu, it's an autorelease object
    CCMenu* pMenu = CCMenu::create(pCloseItem, NULL);
    pMenu->setPosition(CCPointZero);
    this->addChild(pMenu, 1); */  


    //g_pXGSDK = (ProtocolXGSDK*)PluginManager::getInstance()->loadPlugin("XGSDKCocos2dx"); 修改这行如下：
    g_pXGSDK = new ProtocolXGSDK();
    if(g_pXGSDK != NULL){
        CCLOG("find xgsdk plugin");
        XGTestResultListener* pResultListener = new XGTestResultListener();        
        g_pXGSDK->setResultListener(pResultListener);
        CCLOG("begin to init xgsdk plugin");
        //XGSDKInitParamType param;删除初始化参数
        //g_pXGSDK->init(param);删除初始化 修改这行如下：
        g_pXGSDK->prepare();
    }else{
        CCLOG("Can't find xgsdk plugins");
    }
    
    int x = origin.x+150;
    int y = origin.y+visibleSize.height-50;     
    if(g_pXGSDK != NULL && strcmp(g_pXGSDK->getChannelID(),"yingyongbao") == 0)
    {
        createMenu("微信登录",x-20,y,menu_selector(HelloWorld::loginWeixin));
        createMenu("QQ登录",x+40,y,menu_selector(HelloWorld::loginQQ));
    }
    else
    {
        createMenu("登录",x,y,menu_selector(HelloWorld::login));
    }
    createMenu("登出",x+150,y,menu_selector(HelloWorld::logout)); 
    createMenu("充值",x,y-80,menu_selector(HelloWorld::pay));
    createMenu("用户中心",x+150,y-80,menu_selector(HelloWorld::userCenter));
    createMenu("切换账号",x,y-160,menu_selector(HelloWorld::switchUser));
    createMenu("退出",x+150,y-160,menu_selector(HelloWorld::exitGame));    
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
    g_strCurrentUid = "";
    if(g_pXGSDK != NULL)
    {
        g_pXGSDK->login();
    }
    CCLOG("xgsdk exit login");
}

void HelloWorld::loginQQ(CCObject* pSender)
{
    CCLOG("xgsdk call loginQQ...");
    /* 没有这个
    g_strCurrentUid = "";
    if(g_pXGSDK != NULL)
    {
        std::map<std::string,std::string> loginParams;
        loginParams["platform"] = "qq";
        g_pXGSDK->login(loginParams);
    }
    */
}

void HelloWorld::loginWeixin(CCObject* pSender)
{
    CCLOG("xgsdk call loginWeixin...");
    /*
    g_strCurrentUid = "";
    if(g_pXGSDK != NULL)
    {
        std::map<std::string,std::string> loginParams;
        loginParams["platform"] = "weixin";
        g_pXGSDK->login(loginParams);
    }  
    */  
}

void HelloWorld::logout(CCObject* pSender)
{
    
    CCLOG("xgsdk call logout...");
    if(g_pXGSDK != NULL)
    {
        g_pXGSDK->logout();
    }
    g_strCurrentUid = "";
    
}

void HelloWorld::pay(CCObject* pSender)
{   /*由于数据结构变了
    CCLOG("xgsdk call pay...");
    if(g_pXGSDK != NULL)
    {
        XGBuyInfo buyInfo;
        buyInfo.accountID = g_strCurrentUid.c_str();
        buyInfo.productId = "1000";
        buyInfo.productName = "test product";
        buyInfo.currencyName = "西瓜籽";
        buyInfo.productCount = "1";
        buyInfo.productTotalPrice = "2";
        buyInfo.productDescription = "test product desc";
        buyInfo.roleId = "121203";
        buyInfo.serverId = "001";
        buyInfo.roleName = "xigua";          
        buyInfo.customInfo = "hello i am custom";
        g_pXGSDK->pay(buyInfo);
    }
    */
    CCLOG("xgsdk call pay...");
    PayInfo payInfo;
    payInfo.uid = "123";
    payInfo.productTotalPirce = 1;
    payInfo.productCount = 1;
    payInfo.productUnitPrice = 1;
    payInfo.productId = "p123";
    payInfo.productName = "test product";
    payInfo.productDesc = "for test";
    payInfo.currencyName = "currencyName";
    payInfo.serverId = "A qu";
    payInfo.serverName = "zhengfuzhihai";
    payInfo.zoneId = "zoneId";
    payInfo.zoneName = "zoneName";
    payInfo.roleId = "1234";
    payInfo.roleName = "yeye";
    payInfo.balance = "1000";
    payInfo.gameOrderId = "12480";
    payInfo.ext = "";
    payInfo.notifyURL = "www.sina.com";
    g_pXGSDK->pay(payInfo);
}

void HelloWorld::switchUser(CCObject* pSender)
{
    
    CCLOG("xgsdk demo call switch account...");
    if(g_pXGSDK != NULL)
    {
        g_pXGSDK->switchUser();
    }
    
}

void HelloWorld::userCenter(CCObject* pSender)
{
    /*
    CCLOG("call user center...");
    if(g_pXGSDK != NULL)
    {
        //m_pXGSDK->isHideToolBarAvailable()? CCLOG("support hide tool bar"):CCLOG("not support hide tool bar");
        //m_pXGSDK->hideToolBar();
        //m_pXGSDK->showToolBar(0);
        
        //m_pXGSDK->isUserCenterAvaliable()? CCLOG("support user center"):CCLOG("not support user center");
        g_pXGSDK->showUserCenter();
        CCLOG("channel id is %s",g_pXGSDK->getChannelID());

        //m_pXGSDK->setAutoRotation(false);
        //m_pXGSDK->setOrientation(0);
                    
        //m_pXGSDK->onEnterGame("account1","roleid1","roleName1","serverid1","serverName1","others");
        //m_pXGSDK->onCreateRole("account1","roleid1","roleName1","serverid1","serverName1","others");
        //m_pXGSDK->onExitGame("others");
        //m_pXGSDK->onEvent("event1","event content");            
    }
    */
    CCLOG("call user center...");
    if(g_pXGSDK != NULL)
    {
        g_pXGSDK->showUserCenter();
    }
}

void HelloWorld::exitGame(CCObject* pSender)
{
    
    CCLOG("xgsdk demo call exist game...");
    if(g_pXGSDK != NULL)
    {
        std::string params = "";
        //g_pXGSDK->onExitGame(params); 修改如下
        g_pXGSDK->onExitGame(params.c_str());
    }
    
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


//cocos2d::plugin::ProtocolXGSDK* HelloWorld::getXGSDK()修改为以下
ProtocolXGSDK* HelloWorld::getXGSDK()
{
    return g_pXGSDK;
}
