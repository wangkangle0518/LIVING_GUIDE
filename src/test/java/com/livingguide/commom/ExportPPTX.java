package com.livingguide.commom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;

public class ExportPPTX {

	public static void main(String args[]) throws IOException {

		// 创建幻灯片
		XMLSlideShow ppt = new XMLSlideShow();

		// 创建空白演示文稿
		XSLFSlide slide = ppt.createSlide();

		// 读取图片
		File image = new File("C://POIPPT//boy.jpg");

		// 图片转码
		byte[] picture = IOUtils.toByteArray(new FileInputStream(image));

		// 添加图片到PPT
		PictureData idx = ppt.addPicture(picture, PictureData.PictureType.JPEG);

		// 在演示文稿中嵌入图片
		XSLFPictureShape pic = slide.createPicture(idx);

		// 创建ppt文件
		File file = new File("addingimage.pptx");
		FileOutputStream out = new FileOutputStream(file);

		// 写入演示文稿
		ppt.write(out);
		System.out.println("image added successfully");
		out.close();
	}
}