using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.Security.Cryptography;
using System.Runtime.InteropServices;
using System;
using System.IO;

//using XUPorterJSON;

public class GameController : MonoBehaviour
{
    // share instance
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
    // add listener
    public static void CreateSDKManager()
    {
        Debug.Log("CreateSDKManager start");
        GameObject obj = new GameObject("GameController");
        obj.AddComponent<GameController>();
        UnityEngine.Object.DontDestroyOnLoad(obj);
        Debug.Log("CreateSDKManager end");
    }

    // call back method
	//回调方法设置成静态方法
    public static void XgsdkCallback(string json)
    {
        Debug.Log("receive callback from xgsdk, json is:" + json);
        Dictionary<string, object> retTable = MiniJSON.Json.Deserialize(json) as Dictionary<string, object>;
        string type = retTable ["callbackType"] as string;
        Debug.Log("callbackType is :" + type);
        string dataStr = retTable ["data"] as string;
        Debug.Log("data is :" + dataStr);
		string successFlag = retTable ["successFlag"] as String;
		Debug.Log ("successFlag is: " + successFlag);
        //Hashtable data = XUPorterJSON.MiniJsonExtensions.hashtableFromJson(dataStr);
        Dictionary<string, object> data = MiniJSON.Json.Deserialize(dataStr) as Dictionary<string, object>;
        switch (type)
        {
            case "XgsdkInitResult":
                OnInitResult(data);
                break;
            case "XgsdkLoginResult":
                OnLoginResult(data);
                break;
            case "XgsdkRechargeResult":
                OnPayResult(data);
                break;
            case "XgsdkLogoutResult":
                OnLogoutResult(data);
                break;
            case "XgsdkPlatformLogoutResult":
                OnPlatformLogoutResult(data);
                break;
            case "XgsdkPlatformLoginResult":
                OnPlatformLoginResult(data);
                break;  
			case "XgsdkExitResult":
				OnExitResult(data);
                break;
            default:
                break;
        }
        Debug.Log(" XgsdkCallback:" + type + " is completed successfully");
    }
    
    

    // init
    public static void OnInitResult(Dictionary<string, object> data)
    {
        Debug.Log("enter xgsdk init callback...data is:" + data);
        string retCode = data ["retCode"] as String;
        string retMsg = data ["retMsg"] as String;
        Debug.Log("init retCode is:" + retCode);
        Debug.Log("init retMsg is:" + retMsg);
        switch (retCode)
        {
            case "0":
                Debug.Log("sucess");
                break;
            case "1":
                Debug.Log("canceled");
                break;
            default:
                break;
        }
    }  
    
    // login
    public static void OnLoginResult(Dictionary<string, object> data)
    {
        Debug.Log("enter xgsdk login callback...");
        string retCode = data ["retCode"] as String;
        // Here , retMsg means authinfo. 
        authinfo = data ["retMsg"] as String;
        Debug.Log("auth info is:" + authinfo);
        
        /** 
     Assume that authinfo = "eyJuYW1lIjoiam10dzQ3OCIsImFwcElkIjoiMTAyNGFwcGlkIiwiYXV0aFRva2VuIjoiODBhMmQ2MjQ3ZmE1NDAwNTkyMDVjNDQ1MWM4NjIxOWEiLCJjaGFubmVsSWQiOiJoYWltYSIsInNpZ24iOiI0NGUyMzFjZjUyODI1ZWFjMTRjM2RiYmE3NDdkMzRiOCJ9"
     You should send a verify request to xgsdk server use a string method like this:
          "http://auth.xgsdk.com:8180/xgsdk/apiXgsdkAccount/verifySession?authInfo=" + "authinfo"
     So the url may be like this:
         verify_url = http://auth.xgsdk.com:8180/xgsdk/apiXgsdkAccount/verifySession?authInfo=eyJuYW1lIjoiam10dzQ3OCIsImFwcElkIjoiMTAyNGFwcGlkIiwiYXV0aFRva2VuIjoiODBhMmQ2MjQ3ZmE1NDAwNTkyMDVjNDQ1MWM4NjIxOWEiLCJjaGFubmVsSWQiOiJoYWltYSIsInNpZ24iOiI0NGUyMzFjZjUyODI1ZWFjMTRjM2RiYmE3NDdkMzRiOCJ9
   **/
        switch (retCode)
        {
            case "0":
                Debug.Log("success");
            // Here, you could send http request ...
            // Platform.inst.SendAuthinfo(verify_url);
                break;
            case "1":
                Debug.Log("canceled");
                break;
            default:
                break;
        }
        
    }  

    // plateform login
	public static void OnPlatformLoginResult(Dictionary<string, object> data)
    {
        Debug.Log("enter xgsdk platform login callback...");
        string retCode = data ["retCode"] as String;
        // Here , retMsg means authinfo. 
        authinfo = data ["retMsg"] as String;
        Debug.Log("auth info is:" + authinfo);
        
        /** 
     Assume that authinfo = "eyJuYW1lIjoiam10dzQ3OCIsImFwcElkIjoiMTAyNGFwcGlkIiwiYXV0aFRva2VuIjoiODBhMmQ2MjQ3ZmE1NDAwNTkyMDVjNDQ1MWM4NjIxOWEiLCJjaGFubmVsSWQiOiJoYWltYSIsInNpZ24iOiI0NGUyMzFjZjUyODI1ZWFjMTRjM2RiYmE3NDdkMzRiOCJ9"
     You should send a verify request to xgsdk server use a string method like this:
          "http://auth.xgsdk.com:8180/xgsdk/apiXgsdkAccount/verifySession?authInfo=" + "authinfo"
     So the url may be like this:
         verify_url = http://auth.xgsdk.com:8180/xgsdk/apiXgsdkAccount/verifySession?authInfo=eyJuYW1lIjoiam10dzQ3OCIsImFwcElkIjoiMTAyNGFwcGlkIiwiYXV0aFRva2VuIjoiODBhMmQ2MjQ3ZmE1NDAwNTkyMDVjNDQ1MWM4NjIxOWEiLCJjaGFubmVsSWQiOiJoYWltYSIsInNpZ24iOiI0NGUyMzFjZjUyODI1ZWFjMTRjM2RiYmE3NDdkMzRiOCJ9
   **/
        switch (retCode)
        {
            case "0":
                Debug.Log("success");
            // Here, you could send http request ...
            // Platform.inst.SendAuthinfo(verify_url);
                break;
            case "1":
                Debug.Log("canceled");
                break;
            default:
                break;
        }
        
    }  

    // pay
	public static void OnPayResult(Dictionary<string, object> data)
    {
        Debug.Log("enter xgsdk pay callback...");
        string retCode = data ["retCode"] as String;
        
        switch (retCode)
        {
            case "0":
                Debug.Log("success");
                break;
            case "1":
				Debug.Log("failed");
                break;
            case "2":
                Debug.Log("canceled");
                break;
            case "3":
                Debug.Log("unknown");
                break;
            default:
                break;
        }
        
    }  
    
    // logout
	public static void OnLogoutResult(Dictionary<string, object> data)
    {
        Debug.Log("enter xgsdk logout callback...");
        string retCode = data ["retCode"] as String;
        
        switch (retCode)
        {
            case "0":
                Debug.Log("success");
                break;
            case "1":
                Debug.Log("failed");
                break;
            default:
                break;
        }
    }  
    
    // platform logout
	public static void OnPlatformLogoutResult(Dictionary<string, object> data)
    {
        Debug.Log("enter xgsdk platform logout callback...");
        string retCode = data ["retCode"] as String;
        
        switch (retCode)
        {
            case "0":
                Debug.Log("sucess");
                break;
            case "1":
                Debug.Log("failed");
                break;
            default:
                break;
        }
    } 
	// exit
	public static void OnExitResult(Dictionary<string, object> data)
    {
        Debug.Log("enter xgsdk exit callback...");
        string retCode = data ["retCode"] as String;
        
        switch (retCode)
        {
            case "0":
                Debug.Log("exit success");
                break;
            case "1":
                Debug.Log("exit failed");
                break;
			case "2":
                Debug.Log("use channel exit.");
                break;
            default:
                break;
        }
    }
}
