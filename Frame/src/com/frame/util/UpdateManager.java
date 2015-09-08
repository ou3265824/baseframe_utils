package com.frame.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.frame.R;

public class UpdateManager {

	private  Context mContext;
	private Activity activity;
	// 提示语
	private String updateMsg = "有最新的软件包哦，亲快下载吧~";

	// 返回的安装包url
//	private String apkUrl = "http://down.ddearn.com/down/android.apk";

	private String apkUrl;
	
	private Dialog noticeDialog;

	private Dialog downloadDialog;
	
	private int name;
	/* 下载包安装路径 */
	private static String savePath = getSDPath() + "/thumb/";

	// private static final String savePath = "/sdcard/updatedemo/";

//	private  String saveFileName = savePath
//			+ "thumb"+name+".apk";
	
	private  String saveFileName;
	/* 进度条与通知ui刷新的handler和msg常量 */
	private ProgressBar mProgress;

	private static final int DOWN_UPDATE = 201;

	private static final int DOWN_OVER = 202;

	private int progress;

	private Thread downLoadThread;

	private boolean interceptFlag = false;

	public static boolean isSDcardable() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取SDcard的路径
	 * 
	 * @return
	 */
	public static String getSDPath() {
		String path = null;
		if (isSDcardable()) {
			File sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			path = sdDir.getAbsolutePath();
		}
		return path;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				break;
			case DOWN_OVER:

				installApk();
				activity.finish();
				break;
			default:
				
				if(msg.what>=99)
				{
					tv_number_progress.setText("100%");
				}else{
					tv_number_progress.setText(msg.what+"%");
				}
				break;
			}
		};
	};

	public UpdateManager(Context context,String apkUrl) {
		this.mContext = context;
		this.apkUrl=apkUrl;
		this.activity=(Activity) mContext;
	}

	public UpdateManager(Context context,String apkUrl,int name) {
		this.mContext = context;
		this.apkUrl=apkUrl;
		this.activity=(Activity) mContext;
		Log.i("test", "版本号："+name);
		this.name=name;
		saveFileName = savePath
				+ "thumb"+name+".apk";
	}
	
	// 外部接口让主Activity调用
	public void checkUpdateInfo() {
		
//		showNoticeDialog();
		
		showDownloadDialog();
		
	}

//	// 判断是否要更新
//	private boolean isupdate() {
//		boolean falgg = false;
//		// 得到系统的版本号
//		// int systemversion=getsystemsion(context);
//
//		String systemversion = getVersion(mContext);
//		Log.i("test", "系统版本号" + systemversion);
//		// DomParserUtil dom=new DomParserUtil();
//		// InputStream
//		// is=dom.getClass().getClassLoader().getResourceAsStream("updateapp.xml");
//		// updateapp = dom.getparser(is);
//
//		// xml取到的版本号
//		// double version=updateapp.getVersion();
//		if (!systemversion.equals("1.2.0"))// 当系统版本号小于xml版本号时
//		{
//			falgg = true;
//		}
//		return falgg;
//	}
//
//	// 取到系统当前版本号 code
//	public static int getsystemsion(Context context) {
//		int systemversion = 0;
//		try {
//			// 取到系统当前版本号
//			systemversion = context.getPackageManager().getPackageInfo(
//					context.getPackageName(), 0).versionCode;
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return systemversion;
//	}
//
////	/**
////	 * 2 * 获取版本号name 3 * @return 当前应用的版本号 4
////	 */
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

//	private void showNoticeDialog() {
//		AlertDialog.Builder builder = new Builder(mContext);
//		builder.setTitle("软件版本更新");
//		builder.setMessage(updateMsg);
//		builder.setPositiveButton("下载", new OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//				showDownloadDialog();
//			}
//		});
//		builder.setNegativeButton("以后再说", new OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//			}
//		});
//		noticeDialog = builder.create();
//		noticeDialog.show();
//	}
//
	private void showDownloadDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
//		builder.setTitle("软件版本更新");
		Log.i("test", "url:"+apkUrl);
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.progress);
		tv_number_progress = (TextView) v.findViewById(R.id.tv_number_progress);
		builder.setCancelable(false);
		builder.setView(v);
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				activity.finish();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.show();

		downloadApk();
	}

	private Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				Log.i("test", savePath);
				URL url = new URL(apkUrl);

				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();

				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdir();
				}
				
				String apkFile = saveFileName;
				Log.i("test","mingcheng:"+apkFile );
				File ApkFile = new File(apkFile);
				FileOutputStream fos = new FileOutputStream(ApkFile);

				int count = 0;
				byte buf[] = new byte[1024];

				do {
					int numread = is.read(buf);
					count += numread;
					progress = (int) (((float) count / length) * 100);
					mHandler.sendEmptyMessage(progress);
					// 更新进度
					mHandler.sendEmptyMessage(DOWN_UPDATE);
					if (numread <= 0) {
						// 下载完成通知安装
						mHandler.sendEmptyMessage(DOWN_OVER);
						downloadDialog.cancel();
						break;
					}
					fos.write(buf, 0, numread);
				} while (!interceptFlag);// 点击取消就停止下载.

				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	};

	private TextView tv_number_progress;

	/**
	 * 下载apk
	 * 
	 * @param url
	 */

	private void downloadApk() {
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
		
	}

	/**
	 * 安装apk
	 * 
	 * @param url
	 */
	private void installApk() {
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);

	}
}
