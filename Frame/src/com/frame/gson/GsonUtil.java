package com.frame.gson;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class GsonUtil {
	
	private static Gson gson=new Gson();

	//解析json数据
	public static <T> T getBeanFromJson(String json,Type type){
		return gson.fromJson(json, type);
	}
	
	public static <T> T getBeanFromJson(String json,Class<T> clazz){
		return gson.fromJson(json, clazz);
	}
	
	//对象转换成json数据
	public static String getBeanToJson(Object json)
	{
		return gson.toJson(json);
	}
	
}
