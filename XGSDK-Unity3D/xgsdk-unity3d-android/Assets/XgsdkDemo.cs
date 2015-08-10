using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using System.Text;

public class XgsdkDemo : MonoBehaviour
{
	private int controlWidth = 150;
	private int controlHeight = 100;
	private Rect windowRect = new Rect ((Screen.width - 200)/2, (Screen.height - 300)/2, 500, 600);
	public static bool show = false;
	void Awake()
	{    
		XGSDKCallback.CreateSDKManager ();
	}
	void Start()
	{ 
	}
	
	void Update()
	{ 
	}
	
	void OnGUI()
	{           
		XGSDK2.PayInfo payinfo = new XGSDK2.PayInfo ();
		//初始化调用接口需要的参数
		payinfo.Uid = "";
		payinfo.ProductTotalPrice = 10;
		payinfo.ProductCount = 2;
		payinfo.ProductUnitPrice = 5;
		payinfo.ProductId = "199";
		payinfo.ProductName = "gift";
		payinfo.ProductDesc = "Description";
		payinfo.CurrencyName = "RMB";
		payinfo.ServerId = "001";
		payinfo.ServerName = "GD1";
		payinfo.ZoneId = "1025";
		payinfo.ZoneName = "ZoneName";
		payinfo.RoleId = "12345";
		payinfo.RoleName = "RoleName";
		payinfo.Level = 2;
		payinfo.VipLevel = 1;
		payinfo.Balance = "50";
		payinfo.GameOrderId = "1001";
		payinfo.Ext = "ext";
		payinfo.NotifyURL = "http://console.xgsdk.com/sdkserver/receivePayResult";

		
		//设置GUI格式
		GUIStyle style=GUI.skin.button;
		style.fontSize = 40;       
		GUIStyle labelStyle=GUI.skin.label;
		labelStyle.fontSize = 40;   
		
		
		//游戏方退出的弹出框
		if (show) 
		{
			windowRect = GUI.Window (0, windowRect, DialogWindow, "");
		}
		
		//创建demo所需按钮
		GUILayout.BeginHorizontal();
		GUILayout.Label("登录验证:", labelStyle, GUILayout.Width(200), GUILayout.Height(controlHeight));
		if (GUILayout.Button("登录",style, GUILayout.Width(controlWidth), GUILayout.Height(controlHeight)))
		{
			Debug.Log("call xgsdk login...");
			XGSDK2.instance.login("");
		}
		GUILayout.EndHorizontal();
		
		GUILayout.BeginHorizontal();
		GUILayout.Label("支付验证:",labelStyle, GUILayout.Width(200), GUILayout.Height(controlHeight));
		if (GUILayout.Button("支付",style,  GUILayout.Width(controlWidth), GUILayout.Height(controlHeight)))
		{
			Debug.Log("call xgsdk recharge...");
			if (XGSDKCallback.authinfo != "")
			{                           
				byte[] outputb = GetDecoded(XGSDKCallback.authinfo);
				string info = Encoding.Default.GetString(outputb);
				Dictionary<string, object> data = MiniJSON.Json.Deserialize(info) as Dictionary<string, object>;
				payinfo.UserID = data ["uId"].ToString();
				Debug.Log("authinfo :" + XGSDKCallback.authinfo);
				
				
				XGSDK2.instance.pay(payinfo);
			}else
			{
				Debug.Log("please login first...");
				showAndroidToast("请先登录");
			}
		}
		GUILayout.EndHorizontal();
		
		
		GUILayout.BeginHorizontal ();
		GUILayout.Label ("登出验证: ", labelStyle, GUILayout.Width (200), GUILayout.Height(controlHeight));
		if(GUILayout.Button("登出",style,GUILayout.Width(250), GUILayout.Height(controlHeight)))
		{
			Debug.Log("call xgsdk logout...");
			XGSDK2.instance.logout("");
		}
		GUILayout.EndHorizontal();

		
		
		GUILayout.BeginHorizontal ();
		GUILayout.Label ("用户中心： ", labelStyle, GUILayout.Width (200), GUILayout.Height (controlHeight));
		if (GUILayout.Button ("打开用户中心", style, GUILayout.Width (250), GUILayout.Height (controlHeight)))
		{
			Debug.Log("call xgsdk OpenUserCenter");
			
			XGSDK2.instance.openUserCenter("");
		}
		GUILayout.EndHorizontal ();
		
		
		GUILayout.BeginHorizontal ();
		if (GUILayout.Button ("退出", style, GUILayout.Width (250), GUILayout.Height (controlHeight)))
		{
			Debug.Log("call xgsdk exitGame");
			XGSDK2.instance.exit("");
			
		}
		GUILayout.EndHorizontal ();    
		
	}
	
	// 设置游戏方退出时弹出的窗口
	void DialogWindow (int windowID)
	{
		GUI.Label(new Rect(160,20, 400, 60), "确认退出");
		
		if(GUI.Button(new Rect(20,100, 200, 100), "取消退出"))
		{
			Application.LoadLevel (0);
			show = false;
		}
		
		if(GUI.Button(new Rect(280,100, 200, 100), "确认退出"))
		{
			Application.Quit();
			show = false;
		}
	}
	
	// 点击游戏方退出弹出对话框
	public static void Open()
	{
		show = true;
	}
	
	//解析authinfo所需的参数及方法
	char[] source;
	int length, length2, length3;
	int blockCount;
	int paddingCount;
	
	private void init(char[] input)
	{
		int temp = 0;
		source = input;
		length = input.Length;
		
		for (int x = 0; x < 2; x++)
		{
			if (input [length - x - 1] == '=')
				temp++;
		}
		paddingCount = temp;
		
		blockCount = length / 4;
		length2 = blockCount * 3;
	}
	
	public byte[] GetDecoded(string strInput)
	{
		//初始化
		init(strInput.ToCharArray());
		
		byte[] buffer = new byte[length];
		byte[] buffer2 = new byte[length2];
		
		for (int x = 0; x < length; x++)
		{
			buffer [x] = char2sixbit(source [x]);
		}
		
		byte b, b1, b2, b3;
		byte temp1, temp2, temp3, temp4;
		
		for (int x = 0; x < blockCount; x++)
		{
			temp1 = buffer [x * 4];
			temp2 = buffer [x * 4 + 1];
			temp3 = buffer [x * 4 + 2];
			temp4 = buffer [x * 4 + 3];
			
			b = (byte)(temp1 << 2);
			b1 = (byte)((temp2 & 48) >> 4);
			b1 += b;
			
			b = (byte)((temp2 & 15) << 4);
			b2 = (byte)((temp3 & 60) >> 2);
			b2 += b;
			
			b = (byte)((temp3 & 3) << 6);
			b3 = temp4;
			b3 += b;
			
			buffer2 [x * 3] = b1;
			buffer2 [x * 3 + 1] = b2;
			buffer2 [x * 3 + 2] = b3;
		}
		
		length3 = length2 - paddingCount;
		byte[] result = new byte[length3];
		
		for (int x = 0; x < length3; x++)
		{
			result [x] = buffer2 [x];
		}
		
		return result;
	}
	
	private byte char2sixbit(char c)
	{
		char[] lookupTable = new char[64]{  
			'A','B','C','D','E','F','G','H','I','J','K','L','M','N',
			'O','P','Q','R','S','T','U','V','W','X','Y', 'Z',
			'a','b','c','d','e','f','g','h','i','j','k','l','m','n',
			'o','p','q','r','s','t','u','v','w','x','y','z',
			'0','1','2','3','4','5','6','7','8','9','+','/'};
		if (c == '=')
			return 0;
		else
		{
			for (int x = 0; x < 64; x++)
			{
				if (lookupTable [x] == c)
					return (byte)x;
			}
			
			return 0;
		}
		
	}

	//用于提示弹出toast
	public static void showAndroidToast(string msg)
	{
		Debug.Log("call xgsdk showAndroidToast...");
		#if UNITY_ANDROID 
		using (AndroidJavaClass cls = new AndroidJavaClass("com.xgsdk.client.api.unity3d.XGSDKUnity3DWrapper")) {           
			AndroidJavaObject instance = cls.CallStatic<AndroidJavaObject> ("getInstance"); 
			instance.Call("showAndroidToast",msg);   
		}
		#endif
	}
	
	
	
}
