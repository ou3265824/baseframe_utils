package com.example.frame;


import org.apache.http.entity.StringEntity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.base.BaseActivity;
import com.frame.callback.http.TextCallBack;
import com.frame.net.HttpLoader;
import com.frame.net.ImageLoader;
import com.frame.util.CacheUtil;
import com.frame.util.NetUtils;
import com.frame.util.ToastUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;

public class MainActivity extends BaseActivity {

	private TextView text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SharedPreferences sp=getSharedPreferences();
		text = (TextView) findViewById(R.id.text);
		text.setText("手机型号："+android.os.Build.MODEL+"------手机版本号："+android.os.Build.VERSION.RELEASE);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public static String getVersion(Context context) {
		String version = null;
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			version = info.versionName;

		} catch (Exception e) {
			e.printStackTrace();

		}
		return version;
	}
	
	
}
