package com.frame.util;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

import com.frame.activity.BaseActivity;
import com.frame.analysis.GsonUtil;

public class CacheUtil {
	private static CacheUtil cacheUtil;
	private BaseActivity activity;
	public CacheUtil(BaseActivity activity) {
		// TODO Auto-generated constructor stub
		this.activity=activity;
	}
	public static CacheUtil getInstence(BaseActivity activity)
	{
		if(cacheUtil==null)
		{
			cacheUtil=new CacheUtil(activity);
		}
		return cacheUtil;
	}
	/***
	 * 定义SharedPreferences
	 * @return
	 */
	private SharedPreferences getSharedPreferences()
	{
		return activity.getSharedPreferences("Object", Context.MODE_PRIVATE);
	}
	
	/**
	 * 把解析的集合数据存sp
	 * @param key
	 * @param list
	 */
	public <T> void setObjects(String key,List<T> list)
	{
		SharedPreferences sharedPreferences = getSharedPreferences();
		sharedPreferences.edit().putString(key, GsonUtil.getBeanToJson(list)).commit();
	}
	/**
	 * 把解析的集合数据存sp
	 * @param key
	 * @param objects
	 */
	public <T> void setObjects(String key,T[] objects)
	{
		setObjects(key, Arrays.asList(objects));
	}
	
	/**
	 * 把解析的数据存sp
	 * @param key
	 * @param object
	 */
	public <T> void setObjects(String key,T object)
	{
		SharedPreferences sharedPreferences = getSharedPreferences();
		sharedPreferences.edit().putString(key, GsonUtil.getBeanToJson(object)).commit();
	}
	/**
	 * 从sp读数据
	 * @param key
	 * @param type
	 * @return
	 */
	public <T> T getObjects(String key,Type type)
	{
		SharedPreferences sharedPreferences = getSharedPreferences();
		String json=sharedPreferences.getString(key, null);
		return GsonUtil.getBeanFromJson(json, type);
	}
	/**
	 * 从sp读数据
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T>T getObjects(String key,Class<T> clazz)
	{
		SharedPreferences sharedPreferences = getSharedPreferences();
		String json=sharedPreferences.getString(key, null);
		return GsonUtil.getBeanFromJson(json, clazz);
	}
	
	/**
	 * 删除sp某条数据
	 * @param key
	 */
	public void clean(String key)
	{
		SharedPreferences sharedPreferences = getSharedPreferences();
		sharedPreferences.edit().putString(key, null).commit();
	}
	/**
	 * 清空sp
	 */
	public void cleanAll()
	{
		SharedPreferences sharedPreferences = getSharedPreferences();
		sharedPreferences.edit().clear().commit();
	}
}
