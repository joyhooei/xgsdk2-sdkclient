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
	
	//登录成功回调
	public void onLoginSuccess(string msg) {
		Debug.Log ("LoginSuccess, authinfo is:" + msg);
		authinfo = msg;
		XgsdkDemo.showAndroidToast("登录成功");
	}

	//登录失败回调
	public void onLoginFail(string json){
		Dictionary<string, object> retTable = MiniJSON.Json.Deserialize(json) as Dictionary<string, object>;
		int code = Convert.ToInt32(retTable ["code"]);
		string msg = retTable ["msg"] as String;
		Debug.Log ("LoginFail,code: " + code + " message:" + msg);
		XgsdkDemo.showAndroidToast("登录失败");
	}

	//登出成功回调
	public void onLogoutSuccess(string msg){
		Debug.Log ("LogoutSuccess");
		authinfo = "";
		XgsdkDemo.showAndroidToast("登出成功");
	}

	//登录取消回调
	public void onLoginCancel(string msg){
		Debug.Log ("LoginCancel, message: " + msg);
		XgsdkDemo.showAndroidToast("登录取消");
	}

	//登出失败回调
	public void onLogoutFail(string json){
		Dictionary<string, object> retTable = MiniJSON.Json.Deserialize(json) as Dictionary<string, object>;
		int code = Convert.ToInt32(retTable ["code"]);
		string msg = retTable ["msg"] as String;
		Debug.Log ("LogoutFail,code: " + code + " message:" + msg);
		XgsdkDemo.showAndroidToast("登出失败");
	}

	//初始化失败回调
	public void onInitFail(string json){
		Dictionary<string, object> retTable = MiniJSON.Json.Deserialize(json) as Dictionary<string, object>;
		int code = Convert.ToInt32(retTable ["code"]);
		string msg = retTable ["msg"] as String;
		Debug.Log ("InitFail,code: " + code + " message:" + msg);
		XgsdkDemo.showAndroidToast("初始化失败");

	}

	//支付成功回调
	public void onPaySuccess(string msg){
		Debug.Log ("PaySuccess, message:" + msg);
		XgsdkDemo.showAndroidToast("支付成功");
	}

	//支付失败回调
	public void onPayFail(string json){
		Dictionary<string, object> retTable = MiniJSON.Json.Deserialize(json) as Dictionary<string, object>;
		int code = Convert.ToInt32(retTable ["code"]);
		string msg = retTable ["msg"] as String;
		Debug.Log ("PayFail,code: " + code + " message:" + msg);
		XgsdkDemo.showAndroidToast("支付失败");
	}

	//支付取消回调
	public void onPayCancel(string msg){
		Debug.Log ("PayCancel, message:" + msg);
		XgsdkDemo.showAndroidToast("支付取消");
	}

	//直接退出回调
	public void onExit(string msg){
		Debug.Log ("Exit");
		XgsdkDemo.showAndroidToast("直接退出");
		Application.Quit();
	}

	//使用游戏方退出回调
	public void onNoChannelExiter(string msg){
		Debug.Log ("No ChannelExiter");
		XgsdkDemo.showAndroidToast("使用游戏渠道退出");
		XgsdkDemo.Open();
	}

	//取消退出回调
	public void onExitCancel(string msg){
		Debug.Log("ExitCancel");
		XgsdkDemo.showAndroidToast("取消退出");
	}
	

    //Use the exemple provided above, you can fill other call back yourself.
    
}
