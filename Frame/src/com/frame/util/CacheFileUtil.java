package com.frame.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;

/**
 * �����ļ�
 * 
 * @author
 *
 */
public class CacheFileUtil {

	private static CacheFileUtil instance;
	private Context mContext;

	private CacheFileUtil(Context context) {
		this.mContext = context;
	}

	public static CacheFileUtil getInstance(Context context) {
		if (instance == null) {
			synchronized (CacheFileUtil.class) {
				if (instance == null) {
					instance = new CacheFileUtil(context);
				}
			}
		}
		return instance;
	}

	/**
	 * �ж��ļ��Ƿ����
	 * 
	 * @param cachefile
	 * @return
	 */
	public synchronized boolean isExistDataCache(String cachefile) {
		boolean exist = false;
		File data = this.mContext.getFileStreamPath(cachefile);
		if (data.exists())
			exist = true;
		return exist;
	}

	/**
	 * �����ļ�
	 * 
	 * @param obj
	 * @param file
	 * @return
	 */
	public synchronized boolean saveObject(Object obj, String file) {

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {

			fos = this.mContext.openFileOutput(file, Context.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				oos.close();
			} catch (Exception e) {

			}
			try {
				fos.close();
			} catch (Exception e) {

			}
		}
	}

	/**
	 * ��ȡ�ļ�
	 * 
	 * @param file
	 * @return
	 */
	public Object readObject(String file) {
		if (!isExistDataCache(file))
			return null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = this.mContext.openFileInput(file);
			ois = new ObjectInputStream(fis);
			return ois.readObject();
		} catch (FileNotFoundException e) {
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof InvalidClassException) {
				File data = this.mContext.getFileStreamPath(file);
				data.delete();
			}
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
			}
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

	public synchronized long getLocalObject(String file) {
		File data = this.mContext.getFileStreamPath(file);
		return data.length();
	}

	public synchronized void deleteLocalObject(String file) {

		File data = this.mContext.getFileStreamPath(file);
		data.delete();

	}

}
