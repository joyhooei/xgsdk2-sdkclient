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
		private const string SDK_JAVA_CLASS = "com.xgsdk.client.api.unity3d.XGSDKUnity3DWrapper";
         
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
        

		//支付时调用，使用封装类传输数据
		public static void pay(PayInfo payInfo)
		{
			Debug.Log("call xgsdk pay...");
			#if UNITY_ANDROID
			Debug.Log("call xgsdk set messageObj..." + payInfo.Uid + payInfo.ProductId + payInfo.ProductName + payInfo.ProductDesc +
			          payInfo.ProductAmount + payInfo.ProductUnit + payInfo.ProductUnitPrice + payInfo.TotalPrice + payInfo.OriginalPrice +
			          payInfo.CurrencyName + payInfo.Custom + payInfo.GameTradeNo + payInfo.GameCallBackURL + payInfo.ServerId+
			          payInfo.ServerName + payInfo.ZoneId + payInfo.ZoneName + payInfo.RoleId + payInfo.RoleName + payInfo.Level +payInfo.VipLevel);
			callSdkApi("pay",payInfo.Uid, payInfo.ProductId, payInfo.ProductName, 
			           payInfo.ProductDesc, payInfo.ProductAmount, payInfo.ProductUnit,
			           payInfo.ProductUnitPrice, payInfo.TotalPrice, payInfo.OriginalPrice,
			           payInfo.CurrencyName, payInfo.Custom, payInfo.GameTradeNo,payInfo.GameCallBackURL, payInfo.ServerId,
			           payInfo.ServerName, payInfo.ZoneId, payInfo.ZoneName, payInfo.RoleId, payInfo.RoleName, payInfo.Level, payInfo.VipLevel);
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
		public static void switchAccount(string customParams)
		{
			Debug.Log("call xgsdk switchAccount...");    
			#if UNITY_ANDROID               
			callSdkApi("switchAccount");
			#endif
		}


		//进入游戏后向渠道传递用户信息 必接接口
		public static void onEnterGame(UserInfo userInfo)
		{   
			Debug.Log("call xgsdk enterGame...");           
			#if UNITY_ANDROID
			callSdkApi("onEnterGame", userInfo.Uid, userInfo.UserName, userInfo.RoleId,
			           userInfo.RoleName, userInfo.Gender, userInfo.Level, userInfo.VipLevel,
			           userInfo.PartyName, userInfo.ServerId, userInfo.ServerName);
			#endif
		}



		
		//创建角色成功后向渠道传递角色信息
		public static void onCreateRole(UserInfo userInfo)
		{
			Debug.Log("call xgsdk createRole...");          
			#if UNITY_ANDROID
			callSdkApi("onCreateRole", userInfo.RoleId, userInfo.RoleName, userInfo.Gender,
			           userInfo.Level, userInfo.VipLevel, userInfo.PartyName);
			#endif
		}


		//角色升级后向渠道传递升级信息
		public static void onRoleLevelup(UserInfo userInfo)
		{
			Debug.Log("call xgsdk onRoleLevelup");
			#if UNITY_ANDROID
			callSdkApi("onRoleLevelup", userInfo .Uid, userInfo.UserName,userInfo.RoleId, userInfo.RoleName, userInfo.Gender,
			           userInfo.Level, userInfo.VipLevel, userInfo.PartyName, userInfo.ServerId, userInfo.ServerName);
			#endif
		}
        

		
		//传递事件
		public static void onEvent(EventInfo eventInfo)
		{
			Debug.Log("call xgsdk onEvent...");
			#if UNITY_ANDROID
			callSdkApi("onEvent",eventInfo.EventUserInfo.Uid, eventInfo.EventUserInfo.UserName, eventInfo.EventUserInfo.RoleId,
			           eventInfo.EventUserInfo.RoleName, eventInfo.EventUserInfo.Gender, eventInfo.EventUserInfo.Level,
			           eventInfo.EventUserInfo.VipLevel, eventInfo.EventUserInfo.PartyName,
			           eventInfo.EventUserInfo.ServerId, eventInfo.EventUserInfo.ServerName, eventInfo.EventId,eventInfo.EventDesc,
			           eventInfo.EventVal, eventInfo.EventBody, eventInfo.CustomParams); 
			#endif
		}

		//任务开始时调用
		public void onMissionBegin(MissionInfo missionInfo)
		{
			Debug.Log("call xgsdk onMissionBegin...");
			#if UNITY_ANDROID
			callSdkApi("onMissionBegin", missionInfo.MissionUserInfo.Uid, missionInfo.MissionUserInfo.UserName,
			           missionInfo.MissionUserInfo.RoleId, missionInfo.MissionUserInfo.RoleName,missionInfo.MissionUserInfo.Gender,
			           missionInfo.MissionUserInfo.Level, missionInfo.MissionUserInfo.VipLevel,
			           missionInfo.MissionUserInfo.PartyName, missionInfo.MissionUserInfo.ServerId,missionInfo.MissionUserInfo.ServerName,
			           missionInfo.MissionName, missionInfo.CustomParams); 
			#endif
		}

		//任务成功时调用
		public void onMissionSuccess(MissionInfo missionInfo)
		{
			Debug.Log("call xgsdk onMissionSuccess...");
			#if UNITY_ANDROID
			callSdkApi("onMissionBegin", missionInfo.MissionUserInfo.Uid, missionInfo.MissionUserInfo.UserName,
			           missionInfo.MissionUserInfo.RoleId, missionInfo.MissionUserInfo.RoleName,missionInfo.MissionUserInfo.Gender,
			           missionInfo.MissionUserInfo.Level, missionInfo.MissionUserInfo.VipLevel,
			           missionInfo.MissionUserInfo.PartyName, missionInfo.MissionUserInfo.ServerId,missionInfo.MissionUserInfo.ServerName,
			           missionInfo.MissionName, missionInfo.CustomParams); 
			#endif
		}

		//任务失败时调用
		public void onMissionFail(MissionInfo missionInfo)
		{
			Debug.Log("call xgsdk onMissionFail...");
			#if UNITY_ANDROID
			callSdkApi("onMissionBegin", missionInfo.MissionUserInfo.Uid, missionInfo.MissionUserInfo.UserName,
			           missionInfo.MissionUserInfo.RoleId, missionInfo.MissionUserInfo.RoleName,missionInfo.MissionUserInfo.Gender,
			           missionInfo.MissionUserInfo.Level, missionInfo.MissionUserInfo.VipLevel,
			           missionInfo.MissionUserInfo.PartyName, missionInfo.MissionUserInfo.ServerId,missionInfo.MissionUserInfo.ServerName,
			           missionInfo.MissionName, missionInfo.CustomParams); 
			#endif
		}
		
		//访问用户中心
		public static void openUserCenter(string customParams)
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
        

    }


	public class UserInfo
	{
		string uid;
		string userName;
		string roleId;
		string roleName;
		string gender;
		int level;
		int vipLevel;
		string partyName;
		string serverId;
		string serverName;

		public string Uid
		{
			set{ uid = value;}
			get{ return uid;}
		}
		public string UserName
		{
			set{ userName = value;}
			get{ return userName;}
		}
		public string RoleId 
		{
			set{ roleId = value;}
			get{ return roleId;}
		}
		public string RoleName 
		{
			set{ roleName = value;}
			get{ return roleName;}
		}
		public string Gender 
		{
			set{ gender = value;}
			get{ return gender;}
		}
		public int Level 
		{
			set{ level = value;}
			get{ return level;}
		}
		public int VipLevel 
		{
			set{ vipLevel = value;}
			get{ return vipLevel;}
		}
		public string PartyName 
		{
			set{ partyName = value;}
			get{ return partyName;}
		}
		public string ServerId
		{
			set{ serverId = value;}
			get{ return serverId;}
		}
		public string ServerName
		{
			set{ serverName = value;}
			get{ return serverName;}
		}
	}

	public class EventInfo
	{
		UserInfo eventUserInfo;
		string eventId;
		string eventDesc;
		int eventVal;
		string eventBody;
		string customParams;

		public UserInfo EventUserInfo
		{
			set{ eventUserInfo = value;}
			get{ return eventUserInfo;}
		}
		public string EventId
		{
			set{ eventId = value;}
			get{ return eventId;}
		}
		public string EventDesc
		{
			set{ eventDesc = value;}
			get{ return eventDesc;}
		}
		public int EventVal
		{
			set{ eventVal = value;}
			get{ return eventVal;}
		}
		public string EventBody
		{
			set{ eventBody = value;}
			get{ return eventBody;}
		}
		public string CustomParams
		{
			set{ customParams = value;}
			get{ return customParams;}
		}
	}


	public class PayInfo
	{
		string uid;
		string productId;
		string productName;
		string productDesc;
		int productAmount;
		string productUnit;
		int productUnitPrice;
		int totalPirce;
		int originalPrice;
		string currencyName;
		string custom;
		string gameTradeNo;
		string gameCallBackURL;
		string serverId;
		string serverName;
		string zoneId;
		string zoneName;
		string roleId;
		string roleName;
		int level;
		int vipLevel;

		public string Uid 
		{
			set{ uid = value;}
			get{ return uid;}
		}
		public string ProductId 
		{
			set{ productId = value;}
			get{ return productId;}
		}
		
		public string ProductName 
		{
			set{ productName = value;}
			get{ return productName;}
		}
		
		public string ProductDesc 
		{
			set{ productDesc = value;}
			get{ return productDesc;}
		}
		public int ProductAmount 
		{
			set{ productAmount = value;}
			get{ return productAmount;}
		}
		public string ProductUnit
		{
			set{ productUnit = value;}
			get{ return productUnit;}
		}
		public int ProductUnitPrice 
		{
			set{ productUnitPrice = value;}
			get{ return productUnitPrice;}
		}
		public int TotalPrice 
		{
			set{ totalPirce = value;}
			get{ return totalPirce;}
		}

		public int OriginalPrice 
		{
			set{ originalPrice = value;}
			get{ return originalPrice;}
		}
		public string CurrencyName 
		{
			set{ currencyName = value;}
			get{ return currencyName;}
		}
		public string Custom
		{
			set{ custom = value;}
			get{ return custom;}
		}
		public string GameTradeNo 
		{
			set{ gameTradeNo = value;}
			get{ return gameTradeNo;}
		}
		public string GameCallBackURL
		{
			set{ gameCallBackURL = value;}
			get{ return gameCallBackURL;}
		}
		public string ServerId 
		{
			set{ serverId = value;}
			get{ return serverId;}
		}
		public string ServerName 
		{
			set{ serverName = value;}
			get{ return serverName;}
		}

		public string ZoneId 
		{
			set{ zoneId = value;}
			get{ return zoneId;}
		}

		public string ZoneName
		{
			set{ zoneName = value;}
			get{ return zoneName;}
		}

		public string RoleId 
		{
			set{ roleId = value;}
			get{ return roleId;}
		}

		public string RoleName 
		{
			set{ roleName = value;}
			get{ return roleName;}
		}

		public int Level
		{
			set{ level = value;}
			get{ return level;}
		}

		public int VipLevel
		{
			set{ vipLevel = value;}
			get{ return vipLevel;}
		}

	}

	public class MissionInfo
	{
		UserInfo missionUserInfo;
		string missionName;
		string customParams;

		public UserInfo MissionUserInfo
		{
			set{ missionUserInfo = value;}
			get{ return missionUserInfo;}
		}
		public string MissionName
		{
			set{ missionName = value;}
			get{ return missionName;}
		}
		public string CustomParams
		{
			set{ customParams = value;}
			get{ return customParams;}
		}
	}
}