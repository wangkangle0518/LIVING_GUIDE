package com.livingguide.commom;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

public class ZipDemo {

	/**
	 * 压缩uri路径下文件到zipFileName
	 * 
	 * @param zipFileName
	 * @param uri
	 * @throws Exception
	 */
	public static void compressFile(String zipFileName, String uri) throws Exception {
		// 文件目录
		File srcdir = new File(uri);
		// 文件目录是否存在
		if (!srcdir.exists()) {
			throw new Exception("目录" + uri + "不存在");
		}
		// 创建压缩包
		createZip(zipFileName, srcdir);
	}

	/**
	 * 设置要打包的文件路径
	 * 
	 * @param srcdir
	 * @param prj
	 * @return
	 */
	private static FileSet setFile(File srcdir, Project prj) {
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		// 如果路径是文件目录
		if (srcdir.isDirectory()) {
			// 设置目录
			fileSet.setDir(srcdir);
		} else { // 否则，设置为文件
			fileSet.setFile(srcdir);
		}
		// 返回fileSet
		return fileSet;
	}

	/**
	 * 创建压缩包
	 * 
	 * @param zipFile
	 * @param prj
	 * @param fileSet
	 */
	private static void createZip(String zipFileName, File srcdir) {
		// 创建压缩文件
		Zip zip = new Zip();
		// 创建项目
		Project prj = new Project();
		// 设置目录
		zip.addFileset(setFile(srcdir, prj));
		// 设置文件编码
		zip.setEncoding("utf-8");
		// 设置项目
		zip.setProject(prj);
		// 压缩文件 (zipFileName为文件完整路径)
		zip.setDestFile(new File(zipFileName));
		// 执行压缩
		zip.execute();
	}

	public static void main(String[] args) {
		// 设置要压缩文件的目录和文件完整名（文件目录+文件名.文件格式）
		try {
			compressFile("C:\\Users\\Lele\\Desktop\\exda.zip", "C:\\Users\\Lele\\Desktop\\admim");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
				String utfPath = zipEnt.getName();
				String strtemp = fileSavePath + File.separator + utfPath;
				if (zipEnt.isDirectory()) { // 目录
					File dir = new File(strtemp);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					continue;
				} else {
					// 读写文件
					InputStream is = zipFile.getInputStream(zipEnt);
					BufferedInputStream bis = new BufferedInputStream(is);
					// 建目录
					String strsubdir = utfPath;
					for (int i = 0; i < strsubdir.length(); i++) {
						if (strsubdir.substring(i, i + 1).equalsIgnoreCase("/")) {
							String temp = fileSavePath + File.separator + strsubdir.substring(0, i);
							File subdir = new File(temp);
							if (!subdir.exists())
								subdir.mkdir();
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
}
