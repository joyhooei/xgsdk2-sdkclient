using UnityEngine;
using System.Collections;

public class SafetyValue {
	/// <summary>
	/// 字符串转换成int，赋予默认值0
	/// </summary>
	/// <returns>The parse int.</returns>
	/// <param name="str">String.</param>
	public static int TryParseInt(object str)
	{
		int tryInt = 0;
		if(str != null)
		{
			int.TryParse(str.ToString(), out tryInt);
		}
		return tryInt;
	}

	/// <summary>
	/// 字符串转换成float，赋予默认值0
	/// </summary>
	/// <returns>The parse int.</returns>
	/// <param name="str">String.</param>
	public static float TryParseFloat(object str)
	{
		float tryFlo = 0;
		if(str != null)
		{
			float.TryParse(str.ToString(), out tryFlo);
		}
		return tryFlo;
	}

	/// <summary>
	/// 字符串转换成double，赋予默认值0
	/// </summary>
	/// <returns>The parse int.</returns>
	/// <param name="str">String.</param>
	public static double TryParseDouble(object str)
	{
		double tryDou = 0;
		if(str != null)
		{
			double.TryParse(str.ToString(), out tryDou);
		}
		return tryDou;
	}

	/// <summary>
	/// 字符串转换成long，赋予默认值0
	/// </summary>
	/// <returns>The parse int.</returns>
	/// <param name="str">String.</param>
	public static long TryParseLong(object str)
	{
		long trylong = 0;
		if(str != null)
		{
			long.TryParse(str.ToString(), out trylong);
		}
		return trylong;
	}

	/// <summary>
	/// 字符串赋予默认值""
	/// </summary>
	/// <returns>The parse int.</returns>
	/// <param name="str">String.</param>
	public static string TryParseString(object str)
	{
		string trystring = "";
		if(str != null)
		{
			trystring = str.ToString();
		}
		return trystring;
	}
}
