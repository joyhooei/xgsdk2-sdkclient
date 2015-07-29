using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using System;
using System.Runtime.InteropServices;

namespace Xgsdk
{
	//新增zoneId, zoneName gameOrderId以及notifyURL字段
    public struct XGBuyInfo
    {
        // 必传字段
        public string uId;
        public string productId;
        public string productName;
        public string productDescription;
        public string productTotalPrice;
        public string productUnitPrice;
        public string productCount;
        public string ext;

        // 非必传字段
        public string serverId;
        public string serverName;
        public string roleId;
        public string roleName;
        public string currencyName;
        public string balance;
		public string zoneId;
		public string zoneName;
		public string gameOrderId;
		public string notifyURL;
    }

    public struct RoleConsumeInfo
    {
        public string accountId;
        public string accountName;
        public string roleId;
        public string roleName;
        public string roleType;
        public string roleLevel;
        public string activity;
        public string itemCatalog;
        public string itemId;
        public string itemName;
        public string consumeGold;
        public string consumeBindingGold;
    }

    public class instance : MonoBehaviour
    {
        void Awake()
        {
        }
        #if UNITY_IPHONE 
        //xgsdk_ios basic interface
        
        [DllImport("__Internal")]
        private static extern  void XGSDKSetMessageObjName (string messageObjName);
        
        [DllImport("__Internal")]
        private static extern void XGSDKInitSDK (string custInfo);
        
        [DllImport("__Internal")]
        private static extern void XGSDKLogin (string custInfo);
        
        [DllImport("__Internal")]
        private static extern void XGSDKPay (string payInfo);
        
        [DllImport("__Internal")]
        private static extern void XGSDKLogout (string custInfo);
        
        [DllImport("__Internal")]
        private static extern string XGSDKGetChannelID ();
        
        
        //xgsdk_ios optional interface
        
        [DllImport("__Internal")]
        private static extern string XGSDKisSupportUserCenter();
        
        [DllImport("__Internal")]
        private static extern void XGSDKSetAutoRotation(string flag);
        
        [DllImport("__Internal")]
        private static extern void XGSDKOpenUserCenter();
        
        [DllImport("__Internal")]
        private static extern void XGSDKShowToolBar(string place);
        
        [DllImport("__Internal")]
        private static extern void XGSDKHideToolBar();
        
        [DllImport("__Internal")]
        private static extern void XGSDKSwitchAccount(string custInfo);
        
        [DllImport("__Internal")]
        private static extern void XGSDKSetOrientation(string orient);
        
        [DllImport("__Internal")]
        private static extern void XGSDKBindAccount(string custInfo);

        //xgsdk_ios tongji interface
        [DllImport("__Internal")]
        private static extern void XGSDKOnStart(string appID,string appkey,string channelID,string deviceID,String serverID,String serverName, string other);
        
        [DllImport("__Internal")]
        private static extern void XGSDKOnEnterGame(String accountID, String roleID, String roleName,String roleLever, String serverID,String serverName, String others);
        
        [DllImport("__Internal")]
        private static extern void XGSDKOnEvent(string eventID, String content);
        
        [DllImport("__Internal")]
        private static extern void XGSDKOnCreateRole(String accountID, String roleID, String roleName, String serverID, String serverName, String others);

        //xgsdk_ios application share interface
        
     //   [DllImport("__Internal")]
     //   private static extern int XGSDKShareInit(String jsonParms);
        
     //   [DllImport("__Internal")]
     //   private static extern int XGSDKShareText(String text, String title);
        
     //   [DllImport("__Internal")]
     //   private static extern int XGSDKShareImage(String imagePath, String title);
        
     //   [DllImport("__Internal")]
     //   private static extern int XGSDKShareURL(String url, String imagePath, String title);
        
     //  [DllImport("__Internal")]
     //   private static extern int XGSDKShareOpenView( );
        
     //   [DllImport("__Internal")]
     //   private static extern int XGSDKShareDirect(int channelValue);
        
     //   [DllImport("__Internal")]
     //   private static extern bool XGSDKShareOpenURL(String url);

        [DllImport("__Internal")]
        private static extern int openShare(String mediaObject);

        [DllImport("__Internal")]
        private static extern int directShare(int platform, String mediaObject);

        #endif

        public static string authinfo = "";
        private const string SDK_JAVA_CLASS = "org.unity3d.plugin.XGSDKUnity3dImpl";
         
        private static void callSdkApi(string apiName, params object[] args)
        {
            Debug.Log("callSdkApi is ----------- " + apiName + ",class name:" + SDK_JAVA_CLASS);
            #if UNITY_ANDROID 
            using (AndroidJavaClass cls = new AndroidJavaClass(SDK_JAVA_CLASS)) {           
                using (AndroidJavaObject instance = cls.CallStatic<AndroidJavaObject> ("getInstance")) {
                    instance.Call(apiName,args);   
                }
            } 
            #endif
        }

        private static string callRetSdkApi(string apiName, params object[] args)
        {
            Debug.Log("call xgsdk callRetSdkApi...");
            string retString = "";
            Debug.Log("calback is ----------- " + apiName);
            #if UNITY_ANDROID 
            using (AndroidJavaClass cls = new AndroidJavaClass(SDK_JAVA_CLASS)) {
                AndroidJavaObject instance = cls.CallStatic<AndroidJavaObject> ("getInstance"); 
                retString = instance.Call<string>(apiName,args);    
            }
            Debug.Log ("callRetSdkApi is ----------- " + apiName+",ret:"+retString);
            #endif   
            return retString;
        }
    
        //-------------xgsdk_ios basic method ---------------
        
        public static void setMessageObjName(string messageObjName)
        {
            Debug.Log("call xgsdk set messageObj...");
            #if UNITY_ANDROID
            //callSdkApi("setMessageObjName",messageObjName);
            #endif
            #if UNITY_IPHONE 
            XGSDKSetMessageObjName (messageObjName);
            #endif
        }
        
        //游戏初始化调用
        public static void init(string others)
        {
            Debug.Log("call xgsdk init...");
            #if UNITY_ANDROID
            Debug.Log("xg init for android is remove");
            //callSdkApi("init",others);      
            #endif
            #if UNITY_IPHONE 
            XGSDKInitSDK (others);
            #endif
        }
        
        public static void login(string others)
        {
            Debug.Log("call xgsdk login...");
            #if UNITY_ANDROID
            Debug.Log("call xgsdk login...");
            //callSdkApi("login",others);
			XGSDK2.instance.login(others);
            #endif
            #if UNITY_IPHONE 
            XGSDKLogin(others);
            #endif
        }       
        
        //支付
        //public static void pay(string payInfo)
        //{
        //    Debug.Log("call xgsdk pay...");
        //    #if UNITY_ANDROID
        //    Debug.Log("call xgsdk set messageObj..." + payInfo);
        //    callSdkApi("pay",payInfo);
        //    #endif
        //    #if UNITY_IPHONE 
        //    XGSDKPay (payInfo);
        //    #endif
        //}
		//新增zoneId, zoneName gameOrderId以及notifyURL
        public static void pay(XGBuyInfo buyInfo)
        {
            Debug.Log("call xgsdk pay...");
            // check arg
            // convert pay struct to pay json...
//            Dictionary<string, string> payJson = new Dictionary<string, string>();
//            payJson.Add("uId", buyInfo.uId);
//            payJson.Add("productId", buyInfo.productId);
//            payJson.Add("productName", buyInfo.productName);
//            payJson.Add("productDec", buyInfo.productDescription);
//            payJson.Add("price", buyInfo.productTotalPrice);
//            payJson.Add("productUnitPrice", buyInfo.productUnitPrice);
//            payJson.Add("amount", buyInfo.productCount);
//            payJson.Add("ext", buyInfo.ext);
//
//            payJson.Add("serverId", buyInfo.serverId);
//            payJson.Add("serverName", buyInfo.serverName);
//            payJson.Add("roleId", buyInfo.roleId);
//            payJson.Add("roleName", buyInfo.roleName);
//            payJson.Add("currencyName", buyInfo.currencyName);
//            payJson.Add("balance", buyInfo.balance);
//
//            string payInfoJson = MiniJSON.Json.Serialize(payJson);
            #if UNITY_ANDROID
			Debug.Log("call xgsdk to pay..." + buyInfo.uId + Convert.ToInt32(buyInfo.productTotalPrice) + 
			          Convert.ToInt32(buyInfo.productCount) + 
			          Convert.ToInt32(buyInfo.productUnitPrice) + 
			          buyInfo.productId + buyInfo.productName + buyInfo.productDescription + buyInfo.currencyName +
			          buyInfo.serverId + buyInfo.serverName + buyInfo.zoneId + buyInfo.zoneName + buyInfo.roleId + 
			          buyInfo.roleName + buyInfo.balance + buyInfo.gameOrderId + buyInfo.ext + buyInfo.notifyURL);
				XGSDK2.instance.pay(buyInfo.uId,Convert.ToInt32(buyInfo.productTotalPrice),Convert.ToInt32(buyInfo.productCount),
				                Convert.ToInt32(buyInfo.productUnitPrice), 
			                    buyInfo.productId, buyInfo.productName, buyInfo.productDescription, buyInfo.currencyName,
			                    buyInfo.serverId, buyInfo.serverName, buyInfo.zoneId, buyInfo.zoneName, buyInfo.roleId, 
			                    buyInfo.roleName, buyInfo.balance,buyInfo.gameOrderId, buyInfo.ext, buyInfo.notifyURL);
            //callSdkApi("pay", payInfoJson);
            #endif
            #if UNITY_IPHONE 
            XGSDKPay (payInfoJson);
            #endif
        }

        public static void roleConsume(RoleConsumeInfo roleConsumeInfo)
        {
            Debug.Log("call role consume...");
            Dictionary<string, string> consume = new Dictionary<string, string>();
            consume.Add("accountId", roleConsumeInfo.accountId);
            consume.Add("accountName", roleConsumeInfo.accountName);
            consume.Add("roleId", roleConsumeInfo.roleId);
            consume.Add("roleName", roleConsumeInfo.roleName);
            consume.Add("roleType", roleConsumeInfo.roleType);
            consume.Add("roleLevel", roleConsumeInfo.roleLevel);
            consume.Add("activity", roleConsumeInfo.activity);
            consume.Add("itemCatalog", roleConsumeInfo.itemCatalog);
            consume.Add("itemId", roleConsumeInfo.itemId);
            consume.Add("itemName", roleConsumeInfo.itemName);
            consume.Add("consumeGold", roleConsumeInfo.consumeGold);
            consume.Add("consumeBindingGold", roleConsumeInfo.consumeBindingGold);

            string consumeJson = MiniJSON.Json.Serialize(consume);
            #if UNITY_ANDROID
            Debug.Log("call xgsdk set messageObj..." + consumeJson);
            //callSdkApi("roleConsume", consumeJson);
            #endif
        }
        
        public static void setOrientation(string param)
        {
            Debug.Log("call xgsdk setOrientation...");
            #if UNITY_ANDROID
            Debug.Log("call xgsdk setOrientation...");      
            //callSdkApi("setOrientation", param);
            #endif
            #if UNITY_IPHONE
            XGSDKSetOrientation(param);
            #endif
        }
        
        public static void showToolBar(string param)
        {
            Debug.Log("call xgsdk showToolBar..."); 
            #if UNITY_ANDROID
            Debug.Log("call xgsdk showToolBar...");         
            //callSdkApi("showToolBar", param);
            #endif
            #if UNITY_IPHONE 
            XGSDKShowToolBar(param);
            #endif
        }
        
        public static void hideToolBar()
        {
            Debug.Log("call xgsdk hideToolBar..."); 
            #if UNITY_ANDROID
            Debug.Log("call xgsdk hideToolBar...");
            //callSdkApi("hideToolBar");
            #endif
            #if UNITY_IPHONE 
            XGSDKHideToolBar();
            #endif
        }
        
        public static void logout(string others)
        {
            Debug.Log("call xgsdk logout..."); 
            #if UNITY_ANDROID
            Debug.Log("call xgsdk logout...");
            //callSdkApi("logout",others);
			XGSDK2.instance.logout(others);
            #endif
            #if UNITY_IPHONE
            XGSDKLogout (others);
            #endif
        }
        
        public static string getChannelId()
        {
            Debug.Log("call xgsdk getChannelId...");                    
            string channelId = "";
            #if UNITY_ANDROID           
			channelId = XGSDK2.instance.getChannelId();
            #endif
            #if UNITY_IPHONE 
            channelId = XGSDKGetChannelID();
            #endif
            Debug.Log("the channelId is:" + channelId);
            return channelId;   
        }
        
        //-------------xgsdk_ios optional method ---------------
        //判断用户中心是否可用
        public static bool isSupportUserCenter()
        {
            Debug.Log("call xgsdk isSupportUserCenter...");          
            string flag = "";
            #if UNITY_ANDROID
            //flag = callRetSdkApi("isSupportUserCenter");
            #endif
            #if UNITY_IPHONE
            flag = XGSDKisSupportUserCenter();
            #endif
            if (flag == "YES" || flag == "1")
            {
                Debug.Log("the methond |openUserCenter| is available...");
                return true;
            } else
            {
                Debug.Log("the methond |openUserCenter| is not available...");
                return false;
            }

        }
        
        public static void openUserCenter()
        {
            Debug.Log("call xgsdk openUserCenter...");      
            #if UNITY_ANDROID
            //callSdkApi("openUserCenter");
			XGSDK2.instance.openUserCenter();
            #endif
            #if UNITY_IPHONE
            XGSDKOpenUserCenter();
            #endif
        }
        
        public static void switchUser(string others)
        {
            Debug.Log("call xgsdk switchUser...");    
            #if UNITY_ANDROID               
            //callSdkApi("switchUser",others);
			XGSDK2.instance.switchAccount();
            #endif
            #if UNITY_IPHONE
            XGSDKSwitchAccount(others);
            #endif
        }

        public static void  setAutoRotation(string param)
        {
            #if UNITY_ANDROID
            Debug.Log("call xgsdk setAutoRotation...");     
            //callSdkApi("setAutoRotation",param);
            #endif
        }

        // currently used in ios_xiaomi 
        public static void  bindAccount(string param)
        {
            Debug.Log("call xgsdk bindAccount ..."); 
            #if UNITY_IPHONE
            XGSDKBindAccount(param);
            #endif
        }
        public static void onNewIntent()
        {
            Debug.Log("call xgsdk onNewIntent..."); 
            #if UNITY_ANDROID   
           // callSdkApi("onNewIntent");
            #endif
            #if UNITY_IPHONE 

            #endif
        }               
        
        // Applicaiton Event-------------xgsdk_ios tongji method ---------------

        public static void onStart(string channelID, string others)
        {
            Debug.Log("call xgsdk onStart V2..."); 
            #if UNITY_ANDROID    
            //callSdkApi("onStart", channelID, others);
            #endif
        }

        public static void onStart(string appID, string appKey, string channelID, string deviceID, string serverID, string serverName, string others)
        {
            Debug.Log("call xgsdk onStart V1...");  
            #if UNITY_ANDROID
            Debug.Log("call xgsdk onStart...");         
            //callSdkApi("onStart", appID, appKey, channelID, deviceID, others);
            #endif
            #if UNITY_IPHONE 
            XGSDKOnStart(appID,  appKey,  channelID,  deviceID, serverID,serverName,others);
            #endif
        }
		//置空处理
        public static void onCreateRole(string accountID, string roleID, string roleName, string serverID, string serverName, string others)
        {
            Debug.Log("call xgsdk createRole...");          
            #if UNITY_ANDROID
            //callSdkApi("onCreateRole", accountID,  roleID,  roleName,  serverID, serverName, others);
            #endif
            #if UNITY_IPHONE 
            XGSDKOnCreateRole( accountID,  roleID,  roleName,serverID, serverName, others);
            #endif
        }
		//增加userName, gender, vipLevel, balance, partyName，新接口去除others字段
        public static void onEnterGame(string accountID, string roleID, string roleName, string roleLevel, string serverID, string serverName, string userName,
		                               string gender, string vupLevel, string balance, string partyName, string others)
        {   
            Debug.Log("call xgsdk enterGame...");           
            #if UNITY_ANDROID
            //callSdkApi("onEnterGame", accountID, roleID, roleName, roleLevel, serverID, serverName, others);
			XGSDK2.instance.onEnterGame(accountID, userName, roleID, roleName, gender, roleLevel, vupLevel, balance,
			                            partyName, serverID, serverName);
            #endif
            #if UNITY_IPHONE
            XGSDKOnEnterGame (accountID,  roleID,  roleName, roleLevel, serverID, serverName, others);
            #endif
        }
        
        public static void onEvent(string eventID, string content)
        {
            Debug.Log("call xgsdk onEvent...");
            #if UNITY_ANDROID
            //callSdkApi("onEvent",eventID,content); 
			XGSDK2.instance.onEvent(eventID,content);
            #endif
            #if UNITY_IPHONE 
            XGSDKOnEvent(eventID,content);
            #endif
        }

		//新增onRoleLevelip 角色升级后向渠道传递升级信息
		public static void onRoleLevelup(string level)
		{
			Debug.Log("call xgsdk onRoleLevelup");
			#if UNITY_ANDROID
			XGSDK2.instance.onRoleLevelup(level);
			#endif
		}

		//新增onRoleLevelip 角色升级后向渠道传递升级信息
		public static void onVipLevelup(string viplevel)
		{
			Debug.Log("call xgsdk onVipLevelup");
			#if UNITY_ANDROID
			XGSDK2.instance.onVipLevelup(viplevel);
			#endif
		}
        
        /**
     * 退出SDK，游戏退出前必须调用此方法，以清理SDK占用的系统资源。如果游戏退出时不调用该方法，可能会引起程序错误。
     */
        public static void exitGame(string others)
        {
            Debug.Log("call xgsdk exitGame...");
            #if UNITY_ANDROID
            Debug.Log("call xgsdk onExitGame...");  
            //callSdkApi("onExitGame", others);
			XGSDK2.instance.exit(others);
            #endif
            #if UNITY_IPHONE 
            
            #endif
        }
        
        /**
     * 暂停游戏,一般指游戏切后台
     */
        public static void pauseGame(string others)
        {
            Debug.Log("call xgsdk pauseGame...");
            #if UNITY_ANDROID   
            //callSdkApi("onPause", others);
            #endif
            #if UNITY_IPHONE
            XGSDKOnPause(others);
            #endif
        }
        
        /**
     * 恢复游戏,一般指游戏从后台恢复
     */
        public static void resumeGame(string others)
        {
            Debug.Log("call xgsdk resumeGame...");  
            #if UNITY_ANDROID
            //callSdkApi("onResume", others);
            #endif
            #if UNITY_IPHONE 
            XGSDKOnResume(others);
            #endif
        }
        
        public static void onDestroy(string others)
        {
            Debug.Log("call xgsdk onDestroy...");  
            #if UNITY_ANDROID
            Debug.Log("call xgsdk onDestroy...");   
            //callSdkApi("onDestroy", others);
            #endif
        } 

        public static void shareOpenView(String mediaObject)
        {
            Debug.Log("call xgsdk XGSDKShareHandleURL...");  
            #if UNITY_ANDROID
            //callSdkApi("openShare", mediaObject);
          
            #endif
            #if UNITY_IPHONE 
            openShare(mediaObject);
            #endif
        
        }

        public static void shareDirectSent(int platform, String mediaObject)
        {
            Debug.Log("call xgsdk XGSDKShareHandleURL...");  
            #if UNITY_ANDROID
            //callSdkApi("directShare", platform , mediaObject);
          
            #endif
            #if UNITY_IPHONE 
            directShare(platform, mediaObject);
            #endif
           
        }
        //验证接口，游戏不需要关心
        public static void verify(String json)
        {
            Debug.Log("call xgsdk XGSDKShareHandleURL...");  
            #if UNITY_ANDROID
            //callSdkApi("verify", json);           
            #endif
        }
    }
}