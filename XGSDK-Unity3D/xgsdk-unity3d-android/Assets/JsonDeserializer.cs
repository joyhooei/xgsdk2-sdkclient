using UnityEngine;
using System.Reflection;
using System.Collections;

public class JsonDeserializer {
	public static T Deserialize<T>(MiniJsonData json)
	{
		T temp = System.Activator.CreateInstance<T>();
        foreach (PropertyInfo pi in typeof(T).GetProperties())
        {
            if (pi.PropertyType == typeof(int))
            {
                pi.SetValue(temp, SafetyValue.TryParseInt(json[pi.Name]), null);
            }
            else if (pi.PropertyType == typeof(string))
            {
                pi.SetValue(temp, SafetyValue.TryParseString(json[pi.Name]), null);
            }
            else if (pi.PropertyType == typeof(float))
            {
                pi.SetValue(temp, SafetyValue.TryParseFloat(json[pi.Name]), null);
            }
            else if (pi.PropertyType == typeof(double))
            {
                pi.SetValue(temp, SafetyValue.TryParseDouble(json[pi.Name]), null);
            }
            else if (pi.PropertyType == typeof(long))
            {
                pi.SetValue(temp, SafetyValue.TryParseLong(json[pi.Name]), null);
            }
        }

        foreach (FieldInfo pi in typeof(T).GetFields())
        {
            if (pi.FieldType == typeof(int))
            {
                pi.SetValue(temp, SafetyValue.TryParseInt(json[pi.Name]));
            }
            else if (pi.FieldType == typeof(string))
            {
                pi.SetValue(temp, SafetyValue.TryParseString(json[pi.Name]));
            }
            else if (pi.FieldType == typeof(float))
            {
                pi.SetValue(temp, SafetyValue.TryParseFloat(json[pi.Name]));
            }
            else if (pi.FieldType == typeof(double))
            {
                pi.SetValue(temp, SafetyValue.TryParseDouble(json[pi.Name]));
            }
            else if (pi.FieldType == typeof(long))
            {
                pi.SetValue(temp, SafetyValue.TryParseLong(json[pi.Name]));
            }
        }

		return temp; 
	}


    public static T DeserializeWithProperty<T>(MiniJsonData json)
    {
        T temp = System.Activator.CreateInstance<T>();
        foreach (PropertyInfo pi in typeof(T).GetProperties())
        {
            if (pi.PropertyType == typeof(int))
            {

                pi.SetValue(temp, SafetyValue.TryParseInt(json[pi.Name]), null);
            }
            else if (pi.PropertyType == typeof(string))
            {
                pi.SetValue(temp, SafetyValue.TryParseString(json[pi.Name]), null);
            }
            else if (pi.PropertyType == typeof(float))
            {
                pi.SetValue(temp, SafetyValue.TryParseFloat(json[pi.Name]), null);
            }
            else if (pi.PropertyType == typeof(double))
            {
                pi.SetValue(temp, SafetyValue.TryParseDouble(json[pi.Name]), null);
            }
            else if (pi.PropertyType == typeof(long))
            {
                pi.SetValue(temp, SafetyValue.TryParseLong(json[pi.Name]), null);
            }
        }
 
        return temp;
    }

    public static T DeserializeWithFieldInfo<T>(MiniJsonData json)
    {
        T temp = System.Activator.CreateInstance<T>();
        foreach (FieldInfo pi in typeof(T).GetFields())
        {
            if (pi.FieldType == typeof(int))
            {
                pi.SetValue(temp, SafetyValue.TryParseInt(json[pi.Name]));
            }
            else if (pi.FieldType == typeof(string))
            {
                pi.SetValue(temp, SafetyValue.TryParseString(json[pi.Name]));
            }
            else if (pi.FieldType == typeof(float))
            {
                pi.SetValue(temp, SafetyValue.TryParseFloat(json[pi.Name]));
            }
            else if (pi.FieldType == typeof(double))
            {
                pi.SetValue(temp, SafetyValue.TryParseDouble(json[pi.Name]));
            }
            else if (pi.FieldType == typeof(long))
            {
                pi.SetValue(temp, SafetyValue.TryParseLong(json[pi.Name]));
            }
        }

        return temp;
    }

}
