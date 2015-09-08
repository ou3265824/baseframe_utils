package com.frame.callback.http;

import java.lang.reflect.Type;

import com.frame.callback.CallBack;
import com.frame.entity.BaseEntity;

public abstract class GsonCallBack extends HttpCallBack<BaseEntity>{
	
	private Type type;

	public Type getType() {
		return type;
	}
	
	public GsonCallBack(Type type) {
		// TODO Auto-generated constructor stub
		this.type=type;
	}
	
	public abstract void onSuccess(BaseEntity base);
	
	public abstract void onFailure(BaseEntity base);
	
}
