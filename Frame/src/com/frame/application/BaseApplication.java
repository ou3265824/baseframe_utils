package com.frame.application;

import java.util.ArrayList;
import java.util.List;

import com.frame.activity.BaseActivity;

import android.app.Application;
//管理activity
public class BaseApplication extends Application{
	
	private List<BaseActivity> activitys=new ArrayList<BaseActivity>();
	
	//添加activity
	public void addactivity(BaseActivity base)
	{
		activitys.add(base);
	}
	
	//移除activity
	public void removeactivity(BaseActivity base)
	{
		activitys.remove(base);
	}
	
	//清空栈
	public void exit()
	{
		for (BaseActivity base : activitys) {
			if(base!=null){
				base.finish();
			}
		}
		activitys.clear();
	}
	
}
