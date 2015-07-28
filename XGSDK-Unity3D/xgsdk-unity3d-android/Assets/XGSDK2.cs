using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using System;
using System.Runtime.InteropServices;

namespace XGSDK2
{
    public class instance : MonoBehaviour
    {

        void Awake()
        {
		}
		//配置SDK路径 
		private const string SDK_JAVA_CLASS = "com.xgsdk.client.unity3d.XGSDKUnity3DWrapper";
         
        private static void callSdkApi(string apiName, params object[] args)
        {
            Debug.Log("callSdkApi is ----------- " + apiName + ",class name:" + SDK_JAVA_CLASS);
            #if UNITY_ANDROID 
            using (AndroidJavaClass cls = new AndroidJavaClass(SDK_JAVA_CLASS)) {           
                AndroidJavaObject instance = cls.CallStatic<AndroidJavaObject> ("getInstance"); 
                instance.Call(apiName,args);   
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



		//获取渠道tag时调用
		public static string getChannelId()
		{
			Debug.Log ("call sgsdk getChannelId...");
			string channel = "";
			#if UNITY_ANDROID
			channel = callRetSdkApi("getChannelId");
			#endif
			Debug.Log ("The channel tag is: " + channel);
			return channel;
		}
    
        
		//登录时调用 必接接口
        public static void login(string customParams)
        {
            Debug.Log("call xgsdk login...");
            #if UNITY_ANDROID
            callSdkApi("login", customParams);
            #endif
        }       
        
		//支付时调用 必接接口（两种传参方式选其一即可）
		public static void pay(string userid, int productTotalPirce, int productCount,
		                       int productUnitPrice,string productId,
		                       string productName,string productDesc,
		                       string currencyName,string serverId,string serverName,string zoneId, string zoneName,
		                       string roleId,string roleName,string balance,string gameOrderId, string ext, string notifyURL)
        {
            Debug.Log("call xgsdk pay...");
            #if UNITY_ANDROID
			Debug.Log("call xgsdk set messageObj..." + userid + productTotalPirce + productCount + 
			          productUnitPrice + productId + 
			          productName + productDesc + 
			          currencyName + serverId + serverName + zoneId + zoneName +
			          roleId + roleName + balance + gameOrderId + ext + notifyURL);
			callSdkApi("pay",userid,productTotalPirce,productCount,
			           productUnitPrice,productId,
			           productName,productDesc,
			           currencyName,serverId,serverName,zoneId,zoneName,
			           roleId,roleName,balance,gameOrderId,ext,notifyURL);
            #endif
        }

		//支付时调用，使用封装类传输数据
		public static void pay(PayParameter pay)
		{
			Debug.Log("call xgsdk pay...");
			#if UNITY_ANDROID
			Debug.Log("call xgsdk set messageObj..." + pay.UserID + pay.ProductTotalprice + pay.ProductCount + 
			          pay.ProductUnitPrice + pay.ProductId + 
			          pay.ProductName + pay.ProductDesc + 
			          pay.CurrencyName + pay.ServerId + pay.ServerName + pay.ZoneId + pay.ZoneName +
			          pay.RoleId + pay.RoleName + pay.Balance + pay.GameOrderId + pay.Ext + pay.NotifyURL);
			callSdkApi("pay",pay.UserID, pay.ProductTotalprice, pay.ProductCount, 
			           pay.ProductUnitPrice, pay.ProductId, 
			           pay.ProductName, pay.ProductDesc, 
			           pay.CurrencyName, pay.ServerId, pay.ServerName,pay.ZoneId, pay.ZoneName,
			           pay.RoleId, pay.RoleName, pay.Balance, pay.GameOrderId, pay.Ext, pay.NotifyURL);
			#endif
		}


		
		//退出时调用 必接接口
		public static void exit(string customParams)
		{
			Debug.Log("call xgsdk exit...");
			#if UNITY_ANDROID 
			callSdkApi("exit", customParams);
			#endif
			
		}
		
		
		//登出时调用 必接接口
        public static void logout(string customParams)
        {
            Debug.Log("call xgsdk logout..."); 
            #if UNITY_ANDROID
            callSdkApi("logout", customParams);
            #endif
        }


		//切换账号时调用
		public static void switchAccount()
		{
			Debug.Log("call xgsdk switchAccount...");    
			#if UNITY_ANDROID               
			callSdkApi("switchAccount");
			#endif
		}


		//进入游戏后向渠道传递用户信息 必接接口
		public static void onEnterGame(string userId, string username, string roleId,
		                               string roleName, string gender, string level, string vipLevel,
		                               string balance, string partyName, string serverId, string serverName)
		{   
			Debug.Log("call xgsdk enterGame...");           
			#if UNITY_ANDROID
			callSdkApi("onEnterGame",userId, username, roleId,
			           roleName, gender, level, vipLevel,
			           balance, partyName, serverId, serverName);
			#endif
		}



		
		//创建角色成功后向渠道传递角色信息
		public static void onCreateRole(string roleId, string roleName, string gender,
		                                string level, string vipLevel, string balance, string partyName)
		{
			Debug.Log("call xgsdk createRole...");          
			#if UNITY_ANDROID
			callSdkApi("onCreateRole", roleId, roleName, gender,
			           level, vipLevel, balance, partyName);
			#endif
		}


		//角色升级后向渠道传递升级信息
		public static void onRoleLevelup(string level)
		{
			Debug.Log("call xgsdk onRoleLevelup");
			#if UNITY_ANDROID
			callSdkApi("onRoleLevelup", level);
			#endif
		}
        
		//vip升级后传递vip升级信息
		public static void onVipLevelup(string vipLevel)
		{
			Debug.Log("call xgsdk onVipLevelup");
			#if UNITY_ANDROID
			callSdkApi("onVipLevelup", vipLevel);
			#endif
		}
        

		
		//传递事件
		public static void onEvent(string eventID)
		{
			Debug.Log("call xgsdk onEvent...");
			#if UNITY_ANDROID
			callSdkApi("onEvent",eventID); 
			#endif
		}
		
		//访问用户中心
		public static void openUserCenter()
		{
			Debug.Log("call xgsdk openUserCenter...");      
			#if UNITY_ANDROID
			callSdkApi("openUserCenter");
			#endif
		}


		//判断当前渠道是否提供该接口
		public static bool isMethodSupport(string methodName)
		{
			Debug.Log("call xgsdk isMethodSupport...");    
			bool retMsg = false;
			#if UNITY_ANDROID
			using (AndroidJavaClass cls = new AndroidJavaClass(SDK_JAVA_CLASS)) {
				AndroidJavaObject instance = cls.CallStatic<AndroidJavaObject> ("getInstance"); 
				retMsg = instance.Call<bool>("isMethodSupport",methodName);    
			}
			#endif
			return retMsg;
		}


		//功能接口 用于提示弹出toast
		public static void showAndroidToast(string msg)
		{
			Debug.Log("call xgsdk showAndroidToast...");
			#if UNITY_ANDROID 
			callSdkApi("showAndroidToast", msg);
			#endif
		}
        

    }


	public class PayParameter
	{
		string userID;
		int productTotalprice;
		int productCount;
		int productUnitPrice;
		string productId;
		string productName;
		string productDesc;
		string currencyName;
		string serverId;
		string serverName;
		string zoneId;
		string zoneName;
		string roleId;
		string roleName;
		string balance;
		string gameOrderId;
		string ext;
		string notifyURL;
		public string UserID {
			set{ userID = value;}
			get{ return userID;}
		}

		public int ProductTotalprice {
			set{ productTotalprice = value;}
			get{ return productTotalprice;}
		}

		public int ProductCount {
			set{ productCount = value;}
			get{ return productCount;}
		}

		public int ProductUnitPrice {
			set{ productUnitPrice = value;}
			get{ return productUnitPrice;}
		}

		public string ProductId {
			set{ productId = value;}
			get{ return productId;}
		}

		public string ProductName {
			set{ productName = value;}
			get{ return productName;}
		}

		public string ProductDesc {
			set{ productDesc = value;}
			get{ return productDesc;}
		}

		public string CurrencyName {
			set{ currencyName = value;}
			get{ return currencyName;}
		}

		public string ServerId {
			set{ serverId = value;}
			get{ return serverId;}
		}

		public string ServerName {
			set{ serverName = value;}
			get{ return serverName;}
		}

		public string ZoneId {
			set{ zoneId = value;}
			get{ return zoneId;}
		}

		public string ZoneName{
			set{ zoneName = value;}
			get{ return zoneName;}
		}

		public string RoleId {
			set{ roleId = value;}
			get{ return roleId;}
		}

		public string RoleName {
			set{ roleName = value;}
			get{ return roleName;}
		}

		public string Balance {
			set{ balance = value;}
			get{ return balance;}
		}

		public string GameOrderId {
			set{ gameOrderId = value;}
			get{ return gameOrderId;}
		}

		public string Ext {
			set{ ext = value;}
			get{ return ext;}
		}

		public string NotifyURL {
			set{ notifyURL = value;}
			get{ return notifyURL;}
		}
	}
}