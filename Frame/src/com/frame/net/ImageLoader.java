package com.frame.net;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.frame.util.Utils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.lidroid.xutils.bitmap.core.BitmapSize;

public class ImageLoader {
	
	private BitmapUtils bitmapUtils;
	private Context context;
	private static ImageLoader bitmapUtil;
	private ColorDrawable COLOR_TRANSPARENT=new ColorDrawable(android.R.color.transparent);
	
	private ImageLoader(Context context,String diskCachePath) {
		// TODO Auto-generated constructor stub
		if(diskCachePath!=null)
		{
			bitmapUtils=new BitmapUtils(context, diskCachePath);
		}else{
			if (Utils.IsSDcard()) {
				bitmapUtils = new BitmapUtils(context,Utils.getPathSD()+"/"+context.getPackageName());
			} else {
				bitmapUtils = new BitmapUtils(context);
			}
		}
		configDefaultBitmapConfig();
	}
	
	public static ImageLoader getInstance(Context context,String diskCachePath){
		if(bitmapUtil==null)
		{
			bitmapUtil=new ImageLoader(context, diskCachePath);
		}
		return bitmapUtil;
	}
	
	/**
	 * 默认的压缩类型
	 */
	public void configDefaultBitmapConfig(){
		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
	}
	/**
	 * 压缩图片
	 * @param config
	 */
	public void configDefaultBitmapConfig(Bitmap.Config config){
		bitmapUtils.configDefaultBitmapConfig(config);
	}
	
	/**
	 * 设置图片的最大宽高
	 * @param maxSize
	 */
	public void configDefaultBitmapMaxSize(BitmapSize maxSize){
		bitmapUtils.configDefaultBitmapMaxSize(maxSize);
	}
	/**
	 * 设置图片的最大宽高
	 * @param maxWidth
	 * @param maxHeight
	 */
	public void configDefaultBitmapMaxSize(int maxWidth,int maxHeight){
		bitmapUtils.configDefaultBitmapMaxSize(maxWidth, maxHeight);
	}
	
	/**
	 * 加载失败显示的图片
	 * @param bitmap
	 */
	public void configDefaultLoadFailedImage(Bitmap bitmap){
		bitmapUtils.configDefaultLoadFailedImage(bitmap);
	}
	/**
	 * 加载失败显示的图片
	 * @param drawable
	 */
	public void configDefaultLoadFailedImage(Drawable drawable){
		bitmapUtils.configDefaultLoadFailedImage(drawable);
	}
	/**
	 * 加载失败显示的图片
	 * @param resId
	 */
	public void configDefaultLoadFailedImage(int resId){
		bitmapUtils.configDefaultLoadFailedImage(resId);
	}
	
	/**
	 * 加载中显示的图片
	 * @param bitmap
	 */
	public void configDefaultLoadingImage(Bitmap bitmap){
		bitmapUtils.configDefaultLoadingImage(bitmap);
	}
	/**
	 * 加载中显示的图片
	 * @param drawable
	 */
	public void configDefaultLoadingImage(Drawable drawable){
		bitmapUtils.configDefaultLoadingImage(drawable);
	}
	/**
	 * 加载中显示的图片
	 * @param resId
	 */
	public void configDefaultLoadingImage(int resId){
		bitmapUtils.configDefaultLoadingImage(resId);
	}
	/**
	 * 异步加载图片
	 * @param container
	 * @param uri
	 */
	public void display(ImageView container,String uri){
		bitmapUtils.display(container, uri, new CompletedDefaultBitmapLoadCallBack());
	}
	
	/**
	 * 异步加载图片
	 * @param container
	 * @param uri
	 * @param callBack
	 */
	public <T extends View> void display(T container,String uri,BitmapLoadCallBack<View> callBack){
		bitmapUtils.display(container, uri, callBack);
	}
	
	public <T extends View> void display(T container,String uri,BitmapDisplayConfig displayConfig){
		bitmapUtils.display(container, uri, displayConfig);
	}
	
	public class CompletedDefaultBitmapLoadCallBack extends DefaultBitmapLoadCallBack<ImageView>{
		
		@Override
		public void onLoadCompleted(ImageView container, String uri,
				Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
			// TODO Auto-generated method stub
//			super.onLoadCompleted(container, uri, bitmap, config, from);
			Log.i("test", "-----"+bitmap.getWidth()+"------"+bitmap.getHeight());
			fadeIndisplay(container, bitmap);
		}
	}
	
	/**
	 * 显示图片的渐变效果
	 * @param imageView
	 * @param bitmap
	 */
	public void fadeIndisplay(ImageView container,Bitmap bitmap){
		TransitionDrawable transition=new TransitionDrawable(new Drawable[]{COLOR_TRANSPARENT,new BitmapDrawable(container.getResources(),bitmap)});
		container.setImageDrawable(transition);
		transition.startTransition(500);
	}
	
}
