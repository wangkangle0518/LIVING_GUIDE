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
	static final File dir = new File("C:\\Users\\Lele\\Desktop\\Camera");

	// array of supported extensions (use a List if you prefer)
	static final String[] EXTENSIONS = new String[] { "jpg", "png", "bmp" };// and other formats you need

	// filter to identify images based on their extensions
	static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

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

	public static void main(String[] args) throws IOException {

		if (dir.isDirectory()) { // make sure it's a directory
			XMLSlideShow ppt = new XMLSlideShow();
			File file = new File("C:\\Users\\Lele\\Desktop\\小可心.pptx");
			FileOutputStream out = new FileOutputStream(file);
			for (final File f : dir.listFiles(IMAGE_FILTER)) {

				XSLFSlide slide = ppt.createSlide();

				// converting it into a byte array
				byte[] picture = IOUtils.toByteArray(new FileInputStream(f));

				// adding the image to the presentation
				PictureData idx = ppt.addPicture(picture, PictureData.PictureType.JPEG);

				// creating a slide with given picture on it
				XSLFPictureShape pic = slide.createPicture(idx);
				Iterator readers = ImageIO.getImageReadersByFormatName("jpg");
				ImageReader reader = (ImageReader) readers.next();
				ImageInputStream iis = ImageIO.createImageInputStream(f);
				reader.setInput(iis, true);
//				ppt.setPageSize(new Dimension(reader.getWidth(0), reader.getHeight(0)));
				ppt.setPageSize(new Dimension(1920, 1920));
				System.out.println("============" + f.getName() + "============" + ppt.getPageSize().getWidth() + ";" + ppt.getPageSize().getHeight());

			}
			ppt.write(out);
			System.out.println("Presentation created successfully");
		}

	}
}