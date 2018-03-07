package com.livingguide.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class CompressEntry {
	private static String pattern = "YYYY_MM_DD_HH_mm_ss";

	/**
	 * 参数输入tar 自动打包成.tar类型的压缩包，输入.zip自动打包成.zip类型的压缩包
	 * @throws Exception 
	 */
	public void myCompress(String zipOrTar, String zipFileName, String uri) throws Exception {
		System.out.println("开始压缩");
		// 如果是zip，则压缩为zip文件
		if (".zip".equalsIgnoreCase(zipOrTar)) {
			new ZipDemo().compressFile(zipFileName, uri);
		} else if (".tar".equalsIgnoreCase(zipOrTar)) {
			// 压缩为tar文件
			new DemoTar().compressFile(uri, zipFileName);
		} else {
			throw new Exception("未实现的压缩类型");
		}
		System.out.println("压缩完成");
	}
	
	/**
	 * 1).打包前需要将目录中已有的打包文件.zip文件A拷贝到backup文件夹中（8分）
	 * 2).删除根目录文件夹中已有的zip文件A（7分）
	 * @throws IOException 
	 */
	public void runCompress(String fileUrl, String newFileUri) throws IOException {
		// 拷贝文件
		FileUtils.copyFileToDirectory(new File(fileUrl), new File(newFileUri));
		System.out.println("拷贝完成");
		// 删除文件
		if (FileUtils.deleteQuietly(new File(fileUrl))) {
			System.out.println("删除成功");
		} else {
			System.out.println("删除失败");
		}
	}
	
	public static void main(String[] args) {
		CompressEntry ce = new CompressEntry();
		try {
			String name = formatDate();
			System.out.println(name);
			ce.myCompress(".zip", "C:\\Users\\Lele\\Desktop\\预习作业\\ML09预习作业\\Test_"+name+".zip", "C:\\Users\\Lele\\Desktop\\预习作业\\ML09预习作业\\zipdemo");
			ce.runCompress("C:\\Users\\Lele\\Desktop\\预习作业\\ML09预习作业\\Test_"+name+".zip", "C:\\Users\\Lele\\Desktop\\预习作业\\ML09预习作业\\zipdemo\\backup");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 格式化当前日期
	 * @return
	 */
	public static String formatDate() {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date());
	}
}
