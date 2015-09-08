package com.example.frame;

import android.util.Log;


//���ܹ���
public class Chek {
	
	public static String checkisfromphone() {
		
		String str=Md5.stringToMD5(System.currentTimeMillis()+"");
		str=str.substring(8, 24);
		
		
		String str1=str.substring(0,2);	
		String str2=str.substring(2,4);
		String str3=str.substring(4,6);
		String str4=str.substring(6,10);
		String str5=str.substring(10,12);
		String str6=str.substring(12,14);
		String str7=str.substring(14,16);
		
//		Log.i("qqqqqqq", str);
		//str=str1+","+str2+","+str3+","+str4+","+str5+","+str6+","+str7;
		String xx="FH"+str5+"TTZ"+str1+"BL"+str7+"WJ"+str2+"YY"+str4+"GL"+str3+"GY"+str6;
//		Log.i("qqqqqqq", xx);
		
		String ss=Md5.stringToMD5(xx);
//		Log.i("qqqqqqq", ss);
		ss=ss.substring(16,32)+ss.substring(0,16);
//		Log.i("qqqqqqq", ss);
		ss=ss.substring(0, 8)+ss.substring(17,32)+ss.substring(8,17);
		
//		Log.i("qqqqqqq", ss);
		//Token=01234567890123456789012345678900&Seed=0123456789123456

		String s="Token="+ss+"&Seed="+str;
		//ss=ss+"\n"+yy;
		return s;
		
	}
}
