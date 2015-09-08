package com.frame.util;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.frame.callback.http.NetworkAvailableCallBack;


public class Utils {
	
	/**
	 * 判断是否有SD卡
	 * @return
	 */
	public static boolean IsSDcard()
	{
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
	
	/**
	 * 获取SD卡目录
	 * @return
	 */
	public static String getPathSD()
	{
		String path=null;
		if(IsSDcard()){
			File SDRootdirectory=Environment.getExternalStorageDirectory();//根目录
			path=SDRootdirectory.getAbsolutePath();
		}
		return path;
	}
	
	/**
	 * 获取当前屏幕大小
	 * 
	 * @param activity
	 * @return width,hight
	 */
	public static int[] getScreenHeightAndWidth(Activity activity) {
		int[] size = new int[2];
		DisplayMetrics dm = activity.getResources().getDisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int W = dm.widthPixels;
		int H = dm.heightPixels;
		size[0] = W;
		size[1] = H;
		return size;
	}

	/**
	 * 格式化文件大小
	 * 
	 * @param total
	 * @return
	 */
	public static String getTrafficData(long total) {
		String dataText = "0";
		DecimalFormat df = new DecimalFormat("###.00");
		if (total == -1) {
			return dataText;
		} else if (total < 1024) {
			dataText += total + "b";
		} else if (total < 1024 * 1024) {
			dataText = df.format(total / 1024f) + "k";
		} else if (total < 1024 * 1024 * 1024) {
			dataText = df.format(total / 1024f / 1024f) + "m";
		} else if (total < 1024 * 1024 * 1024 * 1024) {
			dataText = df.format(total / 1024f / 1024f / 1024f) + "g";
		} else if (total < 1024 * 1024 * 1024 * 1024 * 1024) {
			dataText = df.format(total / 1024f / 1024f / 1024f / 1024f) + "T";
		}
		return dataText;
	}

	/**
	 * 缩放图片 按照指定大小
	 * 
	 * @param bm
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
	}

	/**
	 * 根据比例缩放图片
	 * 
	 * @param bm
	 * @param scale
	 * @return
	 */
	public static Bitmap zoomImg(Bitmap bm, float scale) {
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 计算缩放比例
		float scaleWidth = width * scale;
		float scaleHeight = height * scale;

		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
	}

	/**
	 * 格式化时间 将时间转化为XX之前
	 * 
	 * @param millisecond
	 * @return
	 */
	public static String formatDate(long millisecond) {
		Date startTime = new Date(millisecond);
		Date nowDate = Calendar.getInstance().getTime();
		if (startTime == null || nowDate == null) {
			return null;
		}
		long timeLong = nowDate.getTime() - millisecond;
		if (timeLong < 60 * 1000)
			return timeLong / 1000 + "秒前";
		else if (timeLong < 60 * 60 * 1000) {
			timeLong = timeLong / 1000 / 60;
			return timeLong + "分钟前";
		} else if (timeLong < 60 * 60 * 24 * 1000) {
			timeLong = timeLong / 60 / 60 / 1000;
			return timeLong + "小时前";
		} else if (timeLong < 60 * 60 * 24 * 1000 * 7) {
			timeLong = timeLong / 1000 / 60 / 60 / 24;
			return timeLong + "天前";
		} else if (timeLong < 60 * 60 * 24 * 1000 * 7 * 4) {
			timeLong = timeLong / 1000 / 60 / 60 / 24 / 7;
			return timeLong + "周前";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
					Locale.getDefault());
			return sdf.format(startTime);
		}
	}

	/**
	 * 获取当前网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static void isNetworkAvailable(Context context,final NetworkAvailableCallBack callBack) {
		ConnectivityManager mgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = mgr.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					final long l1=System.currentTimeMillis();
					AsyncTask<Void, Void, Boolean> task=new AsyncTask<Void, Void, Boolean>(){

						@Override
						protected Boolean doInBackground(Void... params) {
							// TODO Auto-generated method stub
							try {
								URL url=new URL("http://www.baidu.com");
								HttpURLConnection connection=(HttpURLConnection) url.openConnection();
								connection.setReadTimeout(5000);
								return true;
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								return false;
							}
						}

						@Override
						protected void onPostExecute(Boolean result) {
							// TODO Auto-generated method stub
							System.out.println("执行了");
							if(callBack!=null)
							{
								callBack.isAble(result);
							}
							System.out.println(System.currentTimeMillis()-l1);
						}
						
					};
					task.execute();
					break;
				}
			}
		}
	}

	public static boolean Matcher(String str, String reg) {
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(str);
		return m.find();
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
}
