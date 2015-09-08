package com.frame.callback.http;



public abstract class TextCallBack extends HttpCallBack<String>{
	@Override
	public abstract void onSuccess(String arg0);
	
	@Override
	public abstract void onFailure(String result);

}
