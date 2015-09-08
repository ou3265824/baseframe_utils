package com.frame.activity;

import com.frame.application.BaseApplication;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
//activity的基类
public class BaseActivity extends FragmentActivity{
	
	private BaseApplication myapplication;
	private DisplayMetrics displayMetrics;
	private SharedPreferences sp;
	public static String SP_KEY="Info";
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		myapplication=(BaseApplication) getApplication();
		myapplication.addactivity(this);
		displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		sp = getSharedPreferences();
		Editor editor=sp.edit();
		editor.putInt("ScreenWidth", displayMetrics.widthPixels);
		editor.putInt("ScreenHeight", displayMetrics.heightPixels);
		editor.commit();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		myapplication.removeactivity(this);
	}
	
	//退出当前activity
	public void back()
	{
		finish();
	}
	
	//退出程序
	public void exit()
	{
		myapplication.exit();
	}
	
	public SharedPreferences getSharedPreferences()
	{
		return getSharedPreferences("info", MODE_PRIVATE);
	}
}
