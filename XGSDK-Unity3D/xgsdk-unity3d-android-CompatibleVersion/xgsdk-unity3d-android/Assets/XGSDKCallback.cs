using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.Security.Cryptography;
using System.Runtime.InteropServices;
using System;
using System.IO;

public class XGSDKCallback : MonoBehaviour
{
    
	public static XGSDKCallback Instance;
    
    void Awake()
    {
        Instance = this;
    }   // Use this for initialization
    void Start()
    { 
    }
    
    // Update is called once per frame
    void Update()
    {
    }
    public static string authinfo = "";
    public static void CreateSDKManager()
    {
        Debug.Log("CreateSDKManager start");
		GameObject obj = new GameObject("XGSDKCallback");
		obj.AddComponent<XGSDKCallback>();
        UnityEngine.Object.DontDestroyOnLoad(obj);
        Debug.Log("CreateSDKManager end");
    }

	public void CallbackToGameController(string retCode, string msg, string callbackType, string successFlag)
	{
		Dictionary<string, string> payJSON = new Dictionary<string, string>();
		payJSON.Add("retCode",retCode);
		payJSON.Add ("retMsg", msg);
		string payInfoJson = MiniJSON.Json.Serialize(payJSON);
		Dictionary<string, string> callbackJSON = new Dictionary<string, string>();
		callbackJSON.Add ("callbackType", callbackType);
		callbackJSON.Add ("data", payInfoJson);
		callbackJSON.Add ("successFlag", successFlag);
		string callbackInfoJson = MiniJSON.Json.Serialize(callbackJSON);
		GameController.XgsdkCallback(callbackInfoJson);
	}
	
	//登录成功回调
	public void onLoginSuccess(string msg) {
		Debug.Log ("LoginSuccess, authinfo is:" + msg);
		authinfo = msg;
		XGSDK2.instance.showAndroidToast("登录成功");
		CallbackToGameController("0", msg, "XgsdkLoginResult", "YES");
	}

	//登录失败回调
	public void onLoginFail(string json){
		Dictionary<string, object> retTable = MiniJSON.Json.Deserialize(json) as Dictionary<string, object>;
		int code = Convert.ToInt32(retTable ["code"]);
		string msg = retTable ["msg"] as String;
		Debug.Log ("LoginFail,code: " + code + " message:" + msg);
		XGSDK2.instance.showAndroidToast("登录失败");
		CallbackToGameController("1", json, "XgsdkLoginResult", "NO");
	}

	//登出成功回调
	public void onLogoutSuccess(string msg){
		Debug.Log ("LogoutSuccess, message:" + msg);
		authinfo = "";
		XGSDK2.instance.showAndroidToast("登出成功");
		CallbackToGameController("0", msg, "XgsdkLogoutResult", "YES");

	}

	//登录取消回调
	public void onLoginCancel(string msg){
		Debug.Log ("LoginCancel, message: " + msg);
		XGSDK2.instance.showAndroidToast("取消登录");
		CallbackToGameController ("2", msg, "XgsdkLoginResult", "NO");

	}

	//登出失败回调
	public void onLogoutFail(string json){
		Dictionary<string, object> retTable = MiniJSON.Json.Deserialize(json) as Dictionary<string, object>;
		int code = Convert.ToInt32(retTable ["code"]);
		string msg = retTable ["msg"] as String;
		Debug.Log ("LogoutFail,code: " + code + " message:" + msg);
		XGSDK2.instance.showAndroidToast("登出失败");
		CallbackToGameController ("1", json, "XgsdkLogoutResult", "NO");

	}

	//初始化失败回调
	public void onInitFail(string json){
		Dictionary<string, object> retTable = MiniJSON.Json.Deserialize(json) as Dictionary<string, object>;
		int code = Convert.ToInt32(retTable ["code"]);
		string msg = retTable ["msg"] as String;
		Debug.Log ("InitFail,code: " + code + " message:" + msg);
		XGSDK2.instance.showAndroidToast("初始化失败");
		CallbackToGameController ("2", json, "XgsdkInitResult", "NO");

	}

	//支付成功回调
	public void onPaySuccess(string msg){
		Debug.Log ("PaySuccess, message:" + msg);
		XGSDK2.instance.showAndroidToast("支付成功");
		CallbackToGameController ("0", msg, "XgsdkRechargeResult", "YES");

	}

	//支付失败回调
	public void onPayFail(string json){
		Dictionary<string, object> retTable = MiniJSON.Json.Deserialize(json) as Dictionary<string, object>;
		int code = Convert.ToInt32(retTable ["code"]);
		string msg = retTable ["msg"] as String;
		Debug.Log ("PayFail,code: " + code + " message:" + msg);
		XGSDK2.instance.showAndroidToast("支付失败");
		CallbackToGameController ("1", json, "XgsdkRechargeResult", "NO");

	}

	//支付取消回调
	public void onPayCancel(string msg){
		Debug.Log ("PayCancel, message:" + msg);
		XGSDK2.instance.showAndroidToast("支付取消");
		CallbackToGameController ("2", msg, "XgsdkRechargeResult", "NO");

	}

	//直接退出回调
	public void onExit(string msg){
		Debug.Log ("Exit, result:" + msg);
		XGSDK2.instance.showAndroidToast("直接退出");
		CallbackToGameController ("0", msg, "XgsdkExitResult", "YES");
		Application.Quit();
	}

	//使用游戏方退出回调
	public void onNoChannelExiter(string msg){
		Debug.Log ("No ChannelExiter: ");
		XGSDK2.instance.showAndroidToast("使用游戏渠道退出");
		CallbackToGameController ("2", msg, "XgsdkExitResult", "YES");
//		XgsdkDemo.Open();

	}

	//取消退出回调
	public void onExitCancel(string msg){
		Debug.Log("ExitCancel");
		XGSDK2.instance.showAndroidToast("取消退出");
		CallbackToGameController ("1", msg, "XgsdkExitResult", "NO");
	}
	

    //Use the exemple provided above, you can fill other call back yourself.
    
}
