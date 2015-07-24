using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.Security.Cryptography;
using System.Runtime.InteropServices;
using System;
using System.IO;

public class GameController : MonoBehaviour
{
    
    public static GameController Instance;
    
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
	public static string callBackMessage = "";
    
    public static void CreateSDKManager()
    {
        Debug.Log("CreateSDKManager start");
		GameObject obj = new GameObject("GameController");
		obj.AddComponent<GameController>();
        UnityEngine.Object.DontDestroyOnLoad(obj);
        Debug.Log("CreateSDKManager end");
    }
	
	//登录成功回调
	public void onLoginSuccess(string msg) {
		Debug.Log ("LoginSuccess, authinfo is:" + msg);
		authinfo = msg;
		callBackMessage = "登录成功";
	}

	//登录失败回调
	public void onLoginFail(string msg){
		Debug.Log ("LoginFail, message:" + msg);
		callBackMessage = "登录失败";
	}

	//登出成功回调
	public void onLogoutSuccess(string msg){
		Debug.Log ("LogoutSuccess, message:" + msg);
		callBackMessage = "登出成功";
	}

	//登录取消回调
	public void onLoginCancel(string msg){
		Debug.Log ("LoginCancel, message: " + msg);
		callBackMessage = "取消登录";
	}

	//登出失败回调
	public void onLogoutFail(string msg){
		Debug.Log ("LogoutFail, message:" + msg);
		callBackMessage = "登出失败";
	}

	//初始化失败回调
	public void onInitFail(string msg){
		Debug.Log ("InitFail, message:" + msg);
		callBackMessage = "初始化失败";
	}

	//支付成功回调
	public void onPaySuccess(string msg){
		Debug.Log ("PaySuccess, message:" + msg);
		callBackMessage = "支付成功";
	}

	//支付失败回调
	public void onPayFail(string msg){
		Debug.Log ("PayFail, message:" + msg);
		callBackMessage = "支付失败";
	}

	//支付取消回调
	public void onPayCancel(string msg){
		Debug.Log ("PayCancel, message:" + msg);
		callBackMessage = "支付取消";
	}

	//直接退出回调
	public void onExit(string msg){
		Debug.Log ("Exit, result:" + msg);
		callBackMessage = "直接退出";
		Application.Quit();
	}

	//使用游戏方退出回调
	public void onNoChannelExiter(string msg){
		Debug.Log ("No ChannelExiter: ");
		callBackMessage = "使用游戏方退出";
		XgsdkDemo.Open();

	}

	//取消退出回调
	public void onExitCancel(string msg){
		Debug.Log("Cancel Exit");
		callBackMessage = "取消退出";
	}
	

    //Use the exemple provided above, you can fill other call back yourself.
    
}
