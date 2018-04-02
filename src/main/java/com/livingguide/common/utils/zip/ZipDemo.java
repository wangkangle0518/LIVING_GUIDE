package com.livingguide.common.utils.zip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

public class ZipDemo implements IPackage {

	/**
	 * 压缩uri路径下文件到zipFileName
	 * 
	 * @param zipFileName
	 * @param uri
	 * @throws Exception
	 */
	public void compressFile(String zipFileName, String uri) throws Exception {
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
	 * @throws IOException 
	 */
	private FileSet setFile(File srcdir, Project prj) throws IOException {
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		// 如果路径是文件目录
		if (srcdir.isDirectory()) {
			// 设置目录
			fileSet.setDir(srcdir);
			// 设置要压缩的文件类型,类型之间“,”分割
			if (compressImage()) {
				fileSet.setIncludes("*.java,*.html,*.txt,image\\*");
			} else {
				fileSet.setIncludes("*.java,*.html,*.txt,image");
			}
			// 设置要排除的文件类型,类型之间“,”分割
			fileSet.setExcludes("backup,read.properties");
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
	 * @throws IOException 
	 */
	private void createZip(String zipFileName, File srcdir) throws IOException {
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

	private boolean compressImage() throws IOException {
		// 创建peoperties对象
		Properties properties = new Properties();
		// 读取文件
		InputStream in = new BufferedInputStream (new FileInputStream("C:\\Users\\Lele\\Desktop\\预习作业\\ML09预习作业\\zipdemo\\read.properties"));
		// 解析文件
		properties.load(in);
		// 获取zipImage
		String zipImage = properties.getProperty("zipImage");
		System.out.println(zipImage);
		// 返回是否压缩文件夹内的文件
		if ("yes".equalsIgnoreCase(zipImage)) {
			return true;
		} else {
			return false;
		}
	}
}
