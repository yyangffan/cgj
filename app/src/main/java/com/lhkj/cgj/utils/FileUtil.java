package com.lhkj.cgj.utils;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;

public class FileUtil {
	/**
	 * 文件路径转Byte数组
	 * @param filePath
	 * @return byte数组
	 */
	public static byte[] getBytes(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	
	/**
	 * 创建文件夹
	 * @param folerPath
	 * @return
	 */
	public static File creatFolder(String folerPath)
	{
		File file=new File(folerPath);
		if(file.exists())
		{
			file.mkdir();
		}
		return file;
	}
	/**
	 * 创建文件
	 * @param filePath
	 * @return
	 */
	public static File creatFiles(String filePath)
	{
		File file=new File(filePath);
		if(file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return file;
	}
	/**
	 * 文件最后修改日期
	 * 
	 * @param context
	 * @param time
	 * @return
	 */
	public static String formatDateString(Context context, long time) {
		DateFormat dateFormat = android.text.format.DateFormat
				.getDateFormat(context);
		DateFormat timeFormat = android.text.format.DateFormat
				.getTimeFormat(context);
		Date date = new Date(time);
		return dateFormat.format(date) + " " + timeFormat.format(date);
	}

	/**
	 * 转换文件大小
	 * 
	 * @param size
	 * @return
	 */
	public static String convertStorage(long size) {
		long kb = 1024;
		long mb = kb * 1024;
		long gb = mb * 1024;

		if (size >= gb) {
			return String.format("%.1f GB", (float) size / gb);
		} else if (size >= mb) {
			float f = (float) size / mb;
			return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
		} else if (size >= kb) {
			float f = (float) size / kb;
			return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
		} else
			return String.format("%d B", size);
	}

	/**
	 * 取扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtFromFilename(String filename) {
		int dotPosition = filename.lastIndexOf('.');
		if (dotPosition != -1) {
			return filename.substring(dotPosition + 1, filename.length());
		}
		return "";
	}

	public static String getNameFromFilename(String filename) {
		int dotPosition = filename.lastIndexOf('.');
		if (dotPosition != -1) {
			return filename.substring(0, dotPosition);
		}
		return "";
	}

	/**
	 * /data/data/xxx.xxx.xxx/databases/xxx.db
	 * 
	 * @param fromfiles
	 * @param tofiles
	 */
	public static void copyFile(String fromfiles, String tofiles) {
		InputStream myInput;
		OutputStream myOutput;
		File file=new File(tofiles);
		try {
			if (!file.exists()){
				file.createNewFile();
			}
			myInput = new FileInputStream(fromfiles);
			myOutput = new FileOutputStream(tofiles);
			// transfer bytes from the inputfile to the outputfile
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
			// Close the streams
			myOutput.flush();
			myOutput.close();
			myInput.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
