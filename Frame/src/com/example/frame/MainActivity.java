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

import com.frame.activity.BaseActivity;
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
//	private ImageView img,img1,img2,img3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SharedPreferences sp=getSharedPreferences();
		text = (TextView) findViewById(R.id.text);
		text.setText("手机型号："+android.os.Build.MODEL+"------手机版本号："+android.os.Build.VERSION.RELEASE);
//		img1 = (ImageView) findViewById(R.id.img1);
//		img2= (ImageView) findViewById(R.id.img2);
//		img3 = (ImageView) findViewById(R.id.img3);
//		img = (ImageView) findViewById(R.id.img);
//		ImageLoader loader=ImageLoader.getInstance(MainActivity.this, null);
//		loader.display(img, "http://test.ddearn.com/Act/Images/tuijdetail0630.png");
//		loader.display(img1, "http://test.ddearn.com/Act/Images/xins.png");
//		loader.display(img2, "http://test.ddearn.com/Act/Images/jiax.png");
//		loader.display(img3, "http://test.ddearn.com/Act/Images/tuij.png");
//		Log.i("test", "取出："+sp.getInt("ScreenWidth", 1555)+"---"+sp.getInt("ScreenHeight", 1555));
//		if(NetUtils.isConnected(getApplicationContext()))
//		{
////			ToastUtil.showShort(this, "网络连接成功");
//			if(NetUtils.isWifi(this))
//			{
//				
//				
////				BitmapDisplayConfig bit=new BitmapDisplayConfig();
////				BitmapSize size=bit.getBitmapMaxSize();
////				Log.i("test", "图："+size.getWidth()+"----"+size.getHeight());
////				ToastUtil.showShort(this, "wifi");
////				HttpUtils httpUtils=new HttpUtils(5000);
////				httpUtils.send(HttpMethod.POST, "http://172.18.5.239:8096/AppService.svc/PostData", "Cust_Name=朱晓明&Cust_ID=123456", new RequestCallBack<String>() {
////
////					@Override
////					public void onFailure(HttpException arg0, String arg1) {
////						// TODO Auto-generated method stub
//////						Failure(callback, arg1);
////					}
////
////					@Override
////					public void onSuccess(ResponseInfo<String> arg0) {
////						// TODO Auto-generated method stub
//////						Success(callback, arg0.result);
////					}
////				});
				HttpLoader http=HttpLoader.getInstance();
////				RequestParams params=new RequestParams();
////				params.addBodyParameter("Cust_Name", "朱晓明");
////				params.addBodyParameter("Cust_ID", "123456");
////				http.SendByPost("http://172.18.5.239:8096/AppService.svc/PostData",params, new TextCallBack() {
////					
////					@Override
////					public void onSuccess(String arg0) {
////						// TODO Auto-generated method stub
////						Log.i("test", arg0);
////					}
////					
////					@Override
////					public void onFailure(String result) {
////						// TODO Auto-generated method stub
////						Log.i("test", result+"++++");
////					}
////				});
				String str="http://test.ddearn.com/AppService.svc/";
				String releast=android.os.Build.MODEL;
				String url=HttpLoader.UrlEncode(releast);
				final String urlstring=str+"AddIdea/Cust_ID=19181&Idea_Content=1234asdfkj阿德否决了&Idea_Versions="+"android"+getVersion(this)+"&Idea_Device="+url+"&Idea_OS="+android.os.Build.VERSION.RELEASE+"&"+Chek.checkisfromphone();
				
				Log.i("test", url);
				Log.i("test", urlstring);
				http.SendByGet(urlstring, new TextCallBack() {
					
					@Override
					public void onSuccess(String arg0) {
						// TODO Auto-generated method stub
						text.setText(urlstring+"------"+arg0);
//						Gson gson=new Gson();
//						
//						CacheUtil cache=CacheUtil.getInstence(MainActivity.this);
//						cache.setObjects("ht", arg0);
						ToastUtil.showShort(MainActivity.this, "成功");
					}
					
					@Override
					public void onFailure(String result) {
						// TODO Auto-generated method stub
						Log.i("test","失败"+result);
						ToastUtil.showShort(MainActivity.this, "失败");
					}
				});
//			}else{
//				ToastUtil.showShort(this, "不是wifi");
//			}	
//		}else{
//			ToastUtil.showShort(this, "网络连接失败");
//			
//			
//		}
//		text.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				NetUtils.openSetting(MainActivity.this);
//			}
//		});
		
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
