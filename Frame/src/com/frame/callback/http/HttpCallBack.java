package com.frame.callback.http;

import com.frame.callback.CallBack;

public abstract class HttpCallBack<T> extends CallBack{
	
	public abstract void onSuccess(T t);
	
	public abstract void onFailure(String result);
	
}
