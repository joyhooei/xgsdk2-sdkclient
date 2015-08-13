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



		//获取渠道ID时调用
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
        

		//支付时调用
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


		
		//退出时调用
		public static void exit(string customParams)
		{
			Debug.Log("call xgsdk exit...");
			#if UNITY_ANDROID 
			callSdkApi("exit", customParams);
			#endif
			
		}
		
		
		//登出时调用
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
			callSdkApi("switchAccount", customParams);
			#endif
		}


		//进入游戏后向渠道传递用户信息
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
			callSdkApi("onCreateRole",userInfo.Uid, userInfo.UserName, userInfo.RoleId, userInfo.RoleName, userInfo.Gender,
			           userInfo.Level, userInfo.VipLevel, userInfo.PartyName,userInfo.ServerId, userInfo.ServerName);
			#endif
		}


		//角色升级后向渠道传递升级信息
		public static void onRoleLevelup(UserInfo userInfo)
		{
			Debug.Log("call xgsdk onRoleLevelup...");
			#if UNITY_ANDROID
			callSdkApi("onRoleLevelup", userInfo.Uid, userInfo.UserName,userInfo.RoleId, userInfo.RoleName, userInfo.Gender,
			           userInfo.Level, userInfo.VipLevel, userInfo.PartyName, userInfo.ServerId, userInfo.ServerName);
			#endif
		}
        
		//角色登出的时候调用
		public static void onRoleLogout(UserInfo userInfo, string customParams)
		{
			Debug.Log("call xgsdk onRoleLogout...");
			#if UNITY_ANDROID
			callSdkApi("onRoleLogout", userInfo.Uid, userInfo.UserName,userInfo.RoleId, userInfo.RoleName, userInfo.Gender,
				           userInfo.Level, userInfo.VipLevel, userInfo.PartyName, userInfo.ServerId, userInfo.ServerName, customParams);
			#endif
		}

		//创建账号时调用
		public static void onAccountCreate(string uid, string userName, string customParams)
		{
			Debug.Log("call xgsdk onAccountLogout...");
			#if UNITY_ANDROID
			callSdkApi("onAccountCreate", uid, userName, customParams);
			#endif
		}

		//账号登出时调用
		public static void onAccountLogout(string uid, string userName, string customParams)
		{
			Debug.Log("call xgsdk onAccountLogout...");
			#if UNITY_ANDROID
			callSdkApi("onAccountLogout", uid, userName, customParams);
			#endif
		}

		//关卡开始
		public static void onLevelsBegin(UserInfo userInfo, string levelsId, string customParams)
		{
			Debug.Log("call xgsdk onLevelsBegin...");
			#if UNITY_ANDROID
			callSdkApi("onLevelsBegin", userInfo.Uid, userInfo.UserName, userInfo.RoleId, userInfo.RoleName, userInfo.Gender,
			           userInfo.Level, userInfo.VipLevel, userInfo.PartyName, userInfo.ServerId, userInfo.ServerName, levelsId,customParams);
			#endif
		}

		//关卡完成
		public static void onLevelsSuccess(UserInfo userInfo, string levelsId, string customParams)
		{
			Debug.Log("call xgsdk onLevelsSuccess...");
			#if UNITY_ANDROID
			callSdkApi("onLevelsSuccess", userInfo.Uid, userInfo.UserName, userInfo.RoleId, userInfo.RoleName, userInfo.Gender,
			           userInfo.Level, userInfo.VipLevel, userInfo.PartyName, userInfo.ServerId, userInfo.ServerName, levelsId,customParams);
			#endif
		}

		//关卡失败
		public static void onLevelsFail(UserInfo userInfo, string levelsId, string reason, string customParams)
		{
			Debug.Log("call xgsdk onLevelsFail...");
			#if UNITY_ANDROID
			callSdkApi("onLevelsFail", userInfo.Uid, userInfo.UserName, userInfo.RoleId, userInfo.RoleName, userInfo.Gender,
			           userInfo.Level, userInfo.VipLevel, userInfo.PartyName, userInfo.ServerId, userInfo.ServerName, levelsId, reason, customParams);
			#endif
		}

		//购买道具
		public static void onItemBuy(UserInfo userInfo, ItemInfo itemInfo, string customParams)
		{
			Debug.Log("call xgsdk onItemBuy...");
			#if UNITY_ANDROID
			callSdkApi("onItemBuy", userInfo.Uid, userInfo.UserName, userInfo.RoleId, userInfo.RoleName, userInfo.Gender, userInfo.Level,
			           userInfo.VipLevel, userInfo.PartyName, userInfo.ServerId, userInfo.ServerName, itemInfo.ItemId, itemInfo.ItemName,
			           itemInfo.ItemCount, itemInfo.ListPrice, itemInfo.TransPrice, itemInfo.PayGold, itemInfo.PayBindingGold,
			           itemInfo.CurGold, itemInfo.CurBindingGold, itemInfo.TotalGold, itemInfo.TotalBindingGold, customParams);
			#endif
		}

		//获取道具
		public static void onItemGet(UserInfo userInfo, ItemInfo itemInfo, string customParams)
		{
			Debug.Log("call xgsdk onItemGet...");
			#if UNITY_ANDROID
			callSdkApi("onItemGet", userInfo.Uid, userInfo.UserName, userInfo.RoleId, userInfo.RoleName, userInfo.Gender, userInfo.Level,
			           userInfo.VipLevel, userInfo.PartyName, userInfo.ServerId, userInfo.ServerName, itemInfo.ItemId, itemInfo.ItemName,
			           itemInfo.ItemCount, itemInfo.ListPrice, itemInfo.TransPrice, itemInfo.PayGold, itemInfo.PayBindingGold,
			           itemInfo.CurGold, itemInfo.CurBindingGold, itemInfo.TotalGold, itemInfo.TotalBindingGold, customParams);
			#endif
		}

		//消耗道具
		public static void onItemConsume(UserInfo userInfo, ItemInfo itemInfo, string customParams)
		{
			Debug.Log("call xgsdk onItemConsume...");
			#if UNITY_ANDROID
			callSdkApi("onItemConsume", userInfo.Uid, userInfo.UserName, userInfo.RoleId, userInfo.RoleName, userInfo.Gender, userInfo.Level,
			           userInfo.VipLevel, userInfo.PartyName, userInfo.ServerId, userInfo.ServerName, itemInfo.ItemId, itemInfo.ItemName,
			           itemInfo.ItemCount, itemInfo.ListPrice, itemInfo.TransPrice, itemInfo.PayGold, itemInfo.PayBindingGold,
			           itemInfo.CurGold, itemInfo.CurBindingGold, itemInfo.TotalGold, itemInfo.TotalBindingGold, customParams);
			#endif
		}

		//获取金币
		public static void onGoldGain(UserInfo userInfo, GoldGainInfo goldGainInfo, string customParams)
		{
			Debug.Log("call xgsdk onGoldGain...");
			#if UNITY_ANDROID
			callSdkApi("onGoldGain", userInfo.Uid, userInfo.UserName, userInfo.RoleId, userInfo.RoleName, userInfo.Gender, userInfo.Level,
			           userInfo.VipLevel, userInfo.PartyName, userInfo.ServerId, userInfo.ServerName, goldGainInfo.GainChannel,
			           goldGainInfo.Gold, goldGainInfo.BindingGold, goldGainInfo.CurGold, goldGainInfo.CurBindingGold,
			           goldGainInfo.TotalGold, goldGainInfo.TotalBindingGold, customParams);
			#endif
		}

		//传递事件
		public static void onEvent(UserInfo userInfo, EventInfo eventInfo, string customParams)
		{
			Debug.Log("call xgsdk onEvent...");
			#if UNITY_ANDROID
			callSdkApi("onEvent",userInfo.Uid, userInfo.UserName, userInfo.RoleId,
			           userInfo.RoleName, userInfo.Gender, userInfo.Level,
			           userInfo.VipLevel, userInfo.PartyName,
			           userInfo.ServerId, userInfo.ServerName, eventInfo.EventId,eventInfo.EventDesc,
			           eventInfo.EventVal, eventInfo.EventBody, customParams); 
			#endif
		}

		//任务开始时调用
		public void onMissionBegin(UserInfo userInfo, MissionInfo missionInfo, string customParams)
		{
			Debug.Log("call xgsdk onMissionBegin...");
			#if UNITY_ANDROID
			callSdkApi("onMissionBegin", userInfo.Uid, userInfo.UserName,
			           userInfo.RoleId, userInfo.RoleName,userInfo.Gender,
			           userInfo.Level, userInfo.VipLevel,
			           userInfo.PartyName, userInfo.ServerId,userInfo.ServerName, missionInfo.MissionId,
			           missionInfo.MissionName, customParams); 
			#endif
		}

		//任务成功时调用
		public void onMissionSuccess(UserInfo userInfo, MissionInfo missionInfo, string customParams)
		{
			Debug.Log("call xgsdk onMissionSuccess...");
			#if UNITY_ANDROID
			callSdkApi("onMissionBegin", userInfo.Uid, userInfo.UserName,
			           userInfo.RoleId, userInfo.RoleName,userInfo.Gender,
			           userInfo.Level, userInfo.VipLevel,
			           userInfo.PartyName, userInfo.ServerId,userInfo.ServerName, missionInfo.MissionId,
			           missionInfo.MissionName, customParams);
			#endif
		}

		//任务失败时调用
		public void onMissionFail(UserInfo userInfo, MissionInfo missionInfo, string customParams)
		{
			Debug.Log("call xgsdk onMissionFail...");
			#if UNITY_ANDROID
			callSdkApi("onMissionBegin", userInfo.Uid, userInfo.UserName,
			           userInfo.RoleId, userInfo.RoleName,userInfo.Gender,
			           userInfo.Level, userInfo.VipLevel,
			           userInfo.PartyName, userInfo.ServerId,userInfo.ServerName, missionInfo.MissionId,
			           missionInfo.MissionName, customParams);
			#endif
		}
		
		//访问用户中心
		public static void openUserCenter(string customParams)
		{
			Debug.Log("call xgsdk openUserCenter...");      
			#if UNITY_ANDROID
			callSdkApi("openUserCenter", customParams);
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
		string eventId;
		string eventDesc;
		int eventVal;
		string eventBody;

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
		string missionId;
		string missionName;

		public string MissionId
		{
			set{ missionId = value;}
			get{ return missionId;}
		}
		public string MissionName
		{
			set{ missionName = value;}
			get{ return missionName;}
		}
	}
	

	public class ItemInfo
	{
		string itemId;
		string itemName;
		int itemCount;
		int listPrice;
		int transPrice;
		int payGold;
		int payBindingGold;
		int curGold;
		int curBindingGold;
		int totalGold;
		int totalBindingGold;

		public string ItemId
		{
			set{ itemId = value;}
			get{ return itemId;}
		}
		public string ItemName
		{
			set{ itemName = value;}
			get{ return itemName;}
		}
		public int ItemCount
		{
			set{ itemCount = value;}
			get{ return itemCount;}
		}
		public int ListPrice
		{
			set{ listPrice = value;}
			get{ return listPrice;}
		}
		public int TransPrice
		{
			set{ transPrice = value;}
			get{ return transPrice;}
		}
		public int PayGold
		{
			set{ payGold = value;}
			get{ return payGold;}
		}
		public int PayBindingGold
		{
			set{ payBindingGold = value;}
			get{ return payBindingGold;}
		}
		public int CurGold
		{
			set{ curGold = value;}
			get{ return curGold;}
		}
		public int CurBindingGold
		{
			set{ curBindingGold = value;}
			get{ return curBindingGold;}
		}
		public int TotalGold
		{
			set{ totalGold = value;}
			get{ return totalGold;}
		}
		public int TotalBindingGold
		{
			set{ totalBindingGold = value;}
			get{ return totalBindingGold;}
		}

	}

	public class GoldGainInfo
	{
		string gainChannel;
		int gold;
		int bindingGold;
		int curGold;
		int curBindingGold;
		int totalGold;
		int totalBindingGold;

		public string GainChannel
		{
			set{ gainChannel = value;}
			get{ return gainChannel;}
		}
		public int Gold
		{
			set{ gold = value;}
			get{ return gold;}
		}
		public int BindingGold
		{
			set{ bindingGold = value;}
			get{ return bindingGold;}
		}
		public int CurGold
		{
			set{ curGold = value;}
			get{ return curGold;}
		}
		public int CurBindingGold
		{
			set{ curBindingGold = value;}
			get{ return curBindingGold;}
		}
		public int TotalGold
		{
			set{ totalGold = value;}
			get{ return totalGold;}
		}
		public int TotalBindingGold
		{
			set{ totalBindingGold = value;}
			get{ return totalBindingGold;}
		}
	}
}