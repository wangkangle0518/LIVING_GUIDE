package com.livingguide.common.pptx;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.poi.hslf.usermodel.HSLFPictureData;
import org.apache.poi.hslf.usermodel.HSLFPictureShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.sl.usermodel.PictureData;

public class ImageToPPT {

	// File representing the folder that you select using a FileChooser
	private File dir;

	private File file;

	// array of supported extensions (use a List if you prefer)
	private static final String[] EXTENSIONS = new String[] { "jpg", "png", "bmp" };// and other formats you need

	public ImageToPPT(String imagesUrl) throws IOException {
		this.dir = new File(imagesUrl);
		// make sure it's a directory
		if (!dir.exists() || !dir.isDirectory()) {
			throw new IOException("文件夹不存在");
		}
		this.file = new File(imagesUrl + "\\" + imagesUrl.substring(imagesUrl.lastIndexOf("\\")) + ".ppt");
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

	@SuppressWarnings("resource")
	public void mergeToPptx() throws IOException {
		HSLFSlideShow ppt = new HSLFSlideShow();
		ppt.setPageSize(new Dimension(1920, 1080));
		File[] fs = dir.listFiles(IMAGE_FILTER);
		for (File f : fs) {
			// adding the image to the presentation
			HSLFPictureData pd1 = ppt.addPicture(f, PictureData.PictureType.JPEG);
			// get a new shape
			HSLFPictureShape pictNew1 = new HSLFPictureShape(pd1);
			// set the background anchor in the ppt 
			pictNew1.setAnchor(new java.awt.Rectangle(0, 0, 1920, 1080));
			// create a new blank slide
			HSLFSlide slide = ppt.createSlide();
			// add thd background to the slide
			slide.addShape(pictNew1);
			// adding the image to the presentation
			HSLFPictureData pd = ppt.addPicture(f, PictureData.PictureType.JPEG);
			// read the picture
			BufferedImage image = ImageIO.read(f);
			// get width and height of the picture
			double width = image.getWidth();
			double height = image.getHeight();
			// get a new shape
			HSLFPictureShape pictNew = new HSLFPictureShape(pd);
			// set the picture anchor in the ppt 
			if (width > height) {
				pictNew.setAnchor(new java.awt.Rectangle(420, (int) ((1080 - (height / (width / 1080))) / 1.618 * 0.618), 1080, (int) (height / (width / 1080))));
			} else {
				pictNew.setAnchor(new java.awt.Rectangle((int) ((1920 - (width / (height / 1080))) / 2), 0, (int) (width / (height / 1080)), 1080));
			}
			// add the picture to the slide
			slide.addShape(pictNew);

			System.out.println("====================导入图片" + f.getName() + "============================");
		}

		FileOutputStream out = new FileOutputStream(this.file);
		ppt.write(out);
		System.out.println("========================导入图片完成==============================");
		out.close();
	}
	
	public static void main(String[] args) {
		try {
			new ImageToPPT("D:\\Camera");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}