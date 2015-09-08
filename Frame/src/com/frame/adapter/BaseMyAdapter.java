package com.frame.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BaseMyAdapter<T> extends BaseAdapter{

	protected Context mcontext;
	protected List<T> mlist;
	protected LayoutInflater minflater;
	protected int layoutId;
	
	public BaseMyAdapter(Context context, List<T> list,int layoutId) {
		// TODO Auto-generated constructor stub
		this.mcontext=context;
		this.mlist=list;
		this.layoutId=layoutId;
		minflater=LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public T getItem(int arg0) {
		// TODO Auto-generated method stub
		return mlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2){
		ViewHolder viewHolder=ViewHolder.get(mcontext, arg1, arg2, layoutId, arg0);
		convertview(viewHolder, getItem(arg0));
		return viewHolder.getConvertView();
		
	}
	
	public abstract void convertview(ViewHolder viewHolder,T t);
	
	
}
