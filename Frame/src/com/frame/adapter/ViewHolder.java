package com.frame.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewHolder {
	
	private SparseArray<View> mviews;
	private View mconvertview;
	private int mposition;
	
	public ViewHolder(Context context, ViewGroup parent,int layoutId,int position) {
		
		mconvertview=LayoutInflater.from(context).inflate(layoutId, parent, false);
		this.mviews=new SparseArray<View>();
		this.mposition=position;
		mconvertview.setTag(this);
	}
	
	/**
	 * 判断view是否为空，为空就实例化view，不为空就直接用
	 * @param context
	 * @param convertview
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static ViewHolder get(Context context,View convertview, ViewGroup parent,int layoutId,int position){
		
		if(convertview==null)
		{
			return new ViewHolder(context, parent, layoutId, position);
		}else{
			ViewHolder viewHolder=(ViewHolder) convertview.getTag();
			viewHolder.mposition=position;
			return viewHolder;
		}
		
	}
	
	/**
	 * 返回值T为view的控件
	 * 
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getview(int viewId)
	{
		View view=mviews.get(viewId);
		if(view==null)
		{
			view=mconvertview.findViewById(viewId);
			mviews.put(viewId, view);
		}
		return (T)view;
	}
	
	/**
	 * 返回一个view
	 * @return
	 */
	public View getConvertView()
	{
		return mconvertview;
	}
	
	/**
	 * 给TextView控件添加内容
	 * @param textId
	 * @param text
	 * @return
	 */
	public ViewHolder setText(int textId,String text)
	{
		TextView textView=getview(textId);
		textView.setText(text);
		return this;
	}
	
}
