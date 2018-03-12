package com.livingguide.common.pptx;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;

public class ImagesBatchMergeToPPTX {
	
	// File representing the folder that you select using a FileChooser
	private File dir;
	
	private File file;

	// array of supported extensions (use a List if you prefer)
	private static final String[] EXTENSIONS = new String[] { "jpg", "png", "bmp" };// and other formats you need
	
	public ImagesBatchMergeToPPTX(String imagesUrl) throws IOException {
		this.dir = new File(imagesUrl);
		// make sure it's a directory
		if (!dir.exists() || !dir.isDirectory()) {
			throw new IOException("文件夹不存在");
		}
		this.file = new File(imagesUrl + "\\" + imagesUrl.substring(imagesUrl.lastIndexOf("\\")) + ".pptx");
		if (file.exists()) {
			file.delete();
		}
		mergeToPptx();
	}

	// filter to identify images based on their extensions
	private static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

		// @Override
		public boolean accept(final File dir, final String name) {
			for (final String ext : EXTENSIONS) {
				if (name.endsWith("." + ext)) {
					return (true);
				}
			}
			return (false);
		}
	};
	
	@SuppressWarnings({ "resource", "unused" })
	private void mergeToPptx() throws IOException {
		XMLSlideShow ppt = new XMLSlideShow();
		FileOutputStream out = new FileOutputStream(this.file);
		ppt.setPageSize(new Dimension(1920, 1080));
		for (final File f : dir.listFiles(IMAGE_FILTER)) {
			XSLFSlide slide = ppt.createSlide();
			// converting it into a byte array
			byte[] picture = IOUtils.toByteArray(new FileInputStream(f));
			
			// adding the image to the presentation
			PictureData idx = ppt.addPicture(picture, PictureData.PictureType.JPEG);
			
			XSLFPictureShape pic = slide.createPicture(idx);
			Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");
			ImageReader reader = (ImageReader) readers.next();
			ImageInputStream iis = ImageIO.createImageInputStream(f);
			reader.setInput(iis, true);
			
			System.out.println("====================导入图片"+ f.getName() + "============================");
		}
		ppt.write(out);
		System.out.println("========================导入图片完成==============================");
		out.close();
	}
	
	public static void main(String[] args) {
		try {
			new ImagesBatchMergeToPPTX("C:\\Users\\Lele\\Desktop\\Camera");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}