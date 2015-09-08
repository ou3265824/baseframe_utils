package com.frame.callback.http;

import com.frame.base.BaseCallBack;

public abstract class HttpCallBack<T> extends BaseCallBack{
	
	public abstract void onSuccess(T t);
	
	public abstract void onFailure(String result);
	
}
