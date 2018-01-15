package com.livingguide.commom;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipException;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

public class Uncompress {

	/**
	 * 解压文件到指定目录
	 * 
	 * @param zipFile
	 *            目标文件
	 * @param descDir
	 *            解压目录
	 * @author isDelete 是否删除目标文件
	 */
	@SuppressWarnings("unchecked")
	public static void unZipFiles(String zipFilePath, String fileSavePath, boolean isDelete) throws Exception {
		try {
			File file = new File(zipFilePath);
			if (!file.exists() && file.length() <= 0) {
				throw new Exception("要解压的文件不存在!");
			}
			// 一定要加上编码，之前解压另外一个文件，没有加上编码导致不能解压
			ZipFile zipFile = new ZipFile(file, "utf-8");
			Enumeration<ZipEntry> enumeration = zipFile.getEntries();
			while (enumeration.hasMoreElements()) {
				ZipEntry zipEnt = enumeration.nextElement();
				String name = zipEnt.getName();
				// separator：分隔符
				String strtemp = fileSavePath + File.separator + name;
				if (zipEnt.isDirectory()) { // 目录
					createDirs(strtemp);
				} else {
					writeFiles(zipFile, zipEnt, name, fileSavePath, strtemp);
				}
			}
			zipFile.close();
		} catch (Exception e) {
			throw e;
		}
		/**
		 * 文件不能删除的原因： 1.看看是否被别的进程引用，手工删除试试(删除不了就是被别的进程占用) 2.file是文件夹 并且不为空，有别的文件夹或文件，
		 * 3.极有可能有可能自己前面没有关闭此文件的流(我遇到的情况)
		 */
		if (isDelete) {
			new File(zipFilePath).delete();
		}
	}
	
	private static void createDirs(String strtemp) {
		File dir = new File(strtemp);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}
	
	private static void writeFiles(ZipFile zipFile, ZipEntry zipEnt, String name, String fileSavePath, String strtemp) throws ZipException, IOException {
		// 读写文件
		InputStream is = zipFile.getInputStream(zipEnt);
		BufferedInputStream bis = new BufferedInputStream(is);
		// 建目录
		for (int i = 0; i < name.length(); i++) {
			if (name.substring(i, i + 1).equals("/")) {
				String temp = fileSavePath + File.separator + name.substring(0, i);
				File subdir = new File(temp);
				if (!subdir.exists()) {
					subdir.mkdir();
				}
			}
		}
		FileOutputStream fos = new FileOutputStream(strtemp);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		int len;
		byte[] buff = new byte[1024];
		while ((len = bis.read(buff)) != -1) {
			bos.write(buff, 0, len);
		}
		bos.close();
		fos.close();
	}
}
