package com.frame.callback.http;

import java.io.File;

import com.frame.entity.BaseEntity;

public abstract class FileCallBack extends HttpCallBack<File>{
	
	public abstract void onSuccess(File file);
	
	public abstract void onFailure(String result);
	
	public void onLoading(long total,long current,boolean isUploading){};
	
}
