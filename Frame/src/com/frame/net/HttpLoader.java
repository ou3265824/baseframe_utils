package com.frame.net;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.frame.analysis.GsonUtil;
import com.frame.callback.http.FileCallBack;
import com.frame.callback.http.GsonCallBack;
import com.frame.callback.http.HttpCallBack;
import com.frame.callback.http.TextCallBack;
import com.frame.entity.BaseEntity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class HttpLoader {
	
	private HttpUtils httpUtils;
	private static HttpLoader httpLoader;
	
	private HttpLoader() {
		// TODO Auto-generated constructor stub
		if(httpUtils==null)
		{
			//链接网络的时间
			httpUtils=new HttpUtils(5000);
		}
	}

	public static HttpLoader getInstance(){
		if(httpLoader==null)
		{
			httpLoader=new HttpLoader();
		}
		return httpLoader;
	}
	
	/**
	 * 设置默认编码为utf-8
	 * @param s
	 */
	public static String UrlEncode(String s)
	{
		try {
			return URLEncoder.encode(s, "UTF-8").replace("+", "%20");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * 设置编码
	 * @param s
	 * @param charsetName
	 */
	public static String UrlEncode(String s,String charsetName)
	{
		try {
			return URLEncoder.encode(s, charsetName);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * 设置默认解码为utf-8
	 * @param s
	 */
	public static String Url(String s)
	{
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * 设置解码
	 * @param s
	 * @param charsetName
	 */
	public static String Url(String s,String charsetName)
	{
		try {
			return URLDecoder.decode(s, charsetName);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * get提交方式
	 * @param url
	 * @param callback
	 */
	public void SendByGet(String url,final HttpCallBack<?> callback){
		SendByGet(url, new RequestParams(), callback);
	}
	/**
	 * get回调函数
	 * @param url
	 * @param params
	 * @param callback
	 */
	
	public void SendByGet(String url,RequestParams params, final HttpCallBack<?> callback)
	{
		httpUtils.send(HttpMethod.GET, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Failure(callback, arg1);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				Success(callback, arg0.result);
			}
		});
	}
	
	/**
	 * post提交方式
	 * @param url
	 * @param callback
	 */
	public void SendByPost(String url,final HttpCallBack<?> callback){
		
		SendByPost(url, new RequestParams(), callback);
	}
		
		
	
	/**
	 * post回调函数
	 * @param url
	 * @param params
	 * @param callback
	 */
	public void SendByPost(String url,RequestParams params,final HttpCallBack<?> callback){
		
		httpUtils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Failure(callback, arg1);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				Success(callback, arg0.result);
			}
		});
		
	}
	
	/**
	 * 下载上传
	 * @param url
	 * @param target
	 * @param params  
	 * @param callback
	 */
	public void DownLoad(String url,String target,RequestParams params,final HttpCallBack<File> callback){
		DownLoad(url, target, params, false, callback);
	}
	
	/**
	 * 下载
	 * @param url
	 * @param target
	 * @param callback
	 */
	public void DownLoad(String url,String target,final HttpCallBack<File> callback){
		DownLoad(url, target, new RequestParams(), false, callback);
	}
	/**
	 * 下载
	 * @param url
	 * @param target目标
	 * @param params//
	 * @param autoResume是否断点
	 * @param callback
	 */
	public void DownLoad(String url,String target,RequestParams params,boolean autoResume,final HttpCallBack<File> callback){
		httpUtils.download(url, target, params, autoResume, new RequestCallBack<File>() {
			
			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				// TODO Auto-generated method stub
				System.out.println(arg0.result);
				if(arg0!=null&&arg0.result!=null)
				{
					callback.onSuccess(arg0.result);
				}
			}
			
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				arg0.printStackTrace();
				Failure(callback, arg1);
			}
			
			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				// TODO Auto-generated method stub
				if(callback!=null)
				{
					((FileCallBack)callback).onLoading(total, current, isUploading);
				}
				super.onLoading(total, current, isUploading);
			}
		});
	}
	
	
	//成功回调
	@SuppressWarnings("unchecked")
	private <T> void Success(HttpCallBack<T> callback,String result)
	{
		if(callback!=null)
		{
			if (callback instanceof GsonCallBack) 
			{
				GsonCallBack gsonCallBack=(GsonCallBack)callback;
				callback.onSuccess((T) GsonUtil.getBeanFromJson(result, gsonCallBack.getType()));
			}else if(callback instanceof TextCallBack){
				((TextCallBack)callback).onSuccess(result);
			}
		}
			
	}
	
	//失败回调
	private <T> void Failure(HttpCallBack<T> callback,String result){
		if(callback!=null)
		{
			callback.onFailure(result);
		}
	}
	
}
