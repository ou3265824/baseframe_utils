package com.frame.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment{
	
	private BaseActivity baseActivity;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		baseActivity=(BaseActivity) getActivity();
		
	}
	
}
