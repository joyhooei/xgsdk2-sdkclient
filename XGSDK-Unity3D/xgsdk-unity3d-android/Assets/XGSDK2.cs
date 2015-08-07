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
		public static void pay(PayInfo pay)
		{
			Debug.Log("call xgsdk pay...");
			#if UNITY_ANDROID
			Debug.Log("call xgsdk set messageObj..." + pay.UserID + pay.ProductTotalprice + pay.ProductCount + 
			          pay.ProductUnitPrice + pay.ProductId + 
			          pay.ProductName + pay.ProductDesc + 
			          pay.CurrencyName + pay.ServerId + pay.ServerName + pay.ZoneId + pay.ZoneName +
			          pay.RoleId + pay.RoleName + pay.Level + pay.VipLevel + pay.Balance + pay.GameOrderId + pay.Ext + pay.NotifyURL);
			callSdkApi("pay",pay.UserID, pay.ProductTotalprice, pay.ProductCount, 
			           pay.ProductUnitPrice, pay.ProductId, 
			           pay.ProductName, pay.ProductDesc, 
			           pay.CurrencyName, pay.ServerId, pay.ServerName,pay.ZoneId, pay.ZoneName,
			           pay.RoleId, pay.RoleName, pay.Level, pay.VipLevel, pay.Balance, pay.GameOrderId, pay.Ext, pay.NotifyURL);
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
		public static void onCreateRole(RoleInfo roleInfo)
		{
			Debug.Log("call xgsdk createRole...");          
			#if UNITY_ANDROID
			callSdkApi("onCreateRole", roleInfo.RoleID, roleInfo.RoleName, roleInfo.Gender,
			           roleInfo.Level, roleInfo.VipLevel, roleInfo.Balance, roleInfo.PartyName);
			#endif
		}


		//角色升级后向渠道传递升级信息
		public static void onRoleLevelup(RoleInfo roleInfo)
		{
			Debug.Log("call xgsdk onRoleLevelup");
			#if UNITY_ANDROID
			callSdkApi("onRoleLevelup", roleInfo .Uid, roleInfo.UserName,roleInfo.RoleID, roleInfo.RoleName, roleInfo.Gender,
			           roleInfo.Level, roleInfo.VipLevel, roleInfo.Balance, roleInfo.PartyName, roleInfo.ServerId, roleInfo.ServerName);
			#endif
		}
        

		
		//传递事件
		public static void onEvent(EventInfo eventInfo)
		{
			Debug.Log("call xgsdk onEvent...");
			#if UNITY_ANDROID
			callSdkApi("onEvent",eventInfo.EventRoleInfo.Uid, eventInfo.EventRoleInfo.UserName, eventInfo.EventRoleInfo.RoleID,
			           eventInfo.EventRoleInfo.RoleName, eventInfo.EventRoleInfo.Gender, eventInfo.EventRoleInfo.Level,
			           eventInfo.EventRoleInfo.VipLevel, eventInfo.EventRoleInfo.Balance, eventInfo.EventRoleInfo.PartyName,
			           eventInfo.EventRoleInfo.ServerId, eventInfo.EventRoleInfo.ServerName, eventInfo.EventId,eventInfo.EventDesc,
			           eventInfo.EventVal, eventInfo.EventBody, eventInfo.CustomParams); 
			#endif
		}

		//任务开始时调用
		public void onMissionBegin(MissionInfo missionInfo)
		{
			Debug.Log("call xgsdk onMissionBegin...");
			#if UNITY_ANDROID
			callSdkApi("onMissionBegin", missionInfo.MissionRoleInfo.Uid, missionInfo.MissionRoleInfo.UserName,
			           missionInfo.MissionRoleInfo.RoleID, missionInfo.MissionRoleInfo.RoleName,missionInfo.MissionRoleInfo.Gender,
			           missionInfo.MissionRoleInfo.Level, missionInfo.MissionRoleInfo.VipLevel, missionInfo.MissionRoleInfo.Balance,
			           missionInfo.MissionRoleInfo.PartyName, missionInfo.MissionRoleInfo.ServerId,missionInfo.MissionRoleInfo.ServerName,
			           missionInfo.MissionName, missionInfo.CustomParams); 
			#endif
		}

		//任务成功时调用
		public void onMissionSuccess(MissionInfo missionInfo)
		{
			Debug.Log("call xgsdk onMissionSuccess...");
			#if UNITY_ANDROID
			callSdkApi("onMissionBegin", missionInfo.MissionRoleInfo.Uid, missionInfo.MissionRoleInfo.UserName,
			           missionInfo.MissionRoleInfo.RoleID, missionInfo.MissionRoleInfo.RoleName,missionInfo.MissionRoleInfo.Gender,
			           missionInfo.MissionRoleInfo.Level, missionInfo.MissionRoleInfo.VipLevel, missionInfo.MissionRoleInfo.Balance,
			           missionInfo.MissionRoleInfo.PartyName, missionInfo.MissionRoleInfo.ServerId,missionInfo.MissionRoleInfo.ServerName,
			           missionInfo.MissionName, missionInfo.CustomParams); 
			#endif
		}

		//任务失败时调用
		public void onMissionFail(MissionInfo missionInfo)
		{
			Debug.Log("call xgsdk onMissionFail...");
			#if UNITY_ANDROID
			callSdkApi("onMissionBegin", missionInfo.MissionRoleInfo.Uid, missionInfo.MissionRoleInfo.UserName,
			           missionInfo.MissionRoleInfo.RoleID, missionInfo.MissionRoleInfo.RoleName,missionInfo.MissionRoleInfo.Gender,
			           missionInfo.MissionRoleInfo.Level, missionInfo.MissionRoleInfo.VipLevel, missionInfo.MissionRoleInfo.Balance,
			           missionInfo.MissionRoleInfo.PartyName, missionInfo.MissionRoleInfo.ServerId,missionInfo.MissionRoleInfo.ServerName,
			           missionInfo.MissionName, missionInfo.CustomParams); 
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

	public class RoleInfo
	{
		string uid;
		string userName;
		string roleID;
		string roleName;
		string gender;
		string level;
		string vipLevel;
		string balance;
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
		public string RoleID 
		{
			set{ roleID = value;}
			get{ return roleID;}
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
		public string Level 
		{
			set{ level = value;}
			get{ return level;}
		}
		public string VipLevel 
		{
			set{ vipLevel = value;}
			get{ return vipLevel;}
		}
		public string Balance 
		{
			set{ balance = value;}
			get{ return balance;}
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
		RoleInfo eventRoleInfo;
		string eventId;
		string eventDesc;
		int eventVal;
		string eventBody;
		string customParams;

		public RoleInfo EventRoleInfo
		{
			set{ eventRoleInfo = value;}
			get{ return eventRoleInfo;}
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
		string level;
		string vipLevel;
		string balance;
		string gameOrderId;
		string ext;
		string notifyURL;
		public string UserID 
		{
			set{ userID = value;}
			get{ return userID;}
		}

		public int ProductTotalprice 
		{
			set{ productTotalprice = value;}
			get{ return productTotalprice;}
		}

		public int ProductCount 
		{
			set{ productCount = value;}
			get{ return productCount;}
		}

		public int ProductUnitPrice 
		{
			set{ productUnitPrice = value;}
			get{ return productUnitPrice;}
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

		public string CurrencyName 
		{
			set{ currencyName = value;}
			get{ return currencyName;}
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

		public string Level
		{
			set{ level = value;}
			get{ return level;}
		}

		public string VipLevel
		{
			set{ vipLevel = value;}
			get{ return vipLevel;}
		}

		public string Balance 
		{
			set{ balance = value;}
			get{ return balance;}
		}

		public string GameOrderId 
		{
			set{ gameOrderId = value;}
			get{ return gameOrderId;}
		}

		public string Ext 
		{
			set{ ext = value;}
			get{ return ext;}
		}

		public string NotifyURL
		{
			set{ notifyURL = value;}
			get{ return notifyURL;}
		}
	}

	public class MissionInfo
	{
		RoleInfo missionRoleInfo;
		string missionName;
		string customParams;

		public RoleInfo MissionRoleInfo
		{
			set{ missionRoleInfo = value;}
			get{ return missionRoleInfo;}
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