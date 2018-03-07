package com.livingguide.common.pptx;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.SlideLayout;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlideLayout;
import org.apache.poi.xslf.usermodel.XSLFSlideMaster;

public class Exporter {

	private BufferedImage cropImage(BufferedImage src, Rectangle rect) {
		BufferedImage dest = src.getSubimage(0, 0, rect.width, rect.height);
		return dest;
	}

	public static void main(String args[]) throws IOException {

		int ppt2013Width = 1368;
		int ppt2013Height = 768;
		int pageWidth = 1000;
		int pageHeight = 850;
		int TitleArea = 0;
		int ExtendedWidth = 1054;
		int ExtendedWidthHeightLessThanWidth = 994;

		String sourceFolder = "G://dinesh//Source Images";
		XMLSlideShow ppt = new XMLSlideShow();

		File image2 = new File("G://dinesh//Images//Right.jpg");

		File image3 = new File("G://dinesh//Images//left3.jpg");

		File image4 = new File("G://dinesh//Source Images//15.Legend1 (legend).png");
		File image5 = new File("G://dinesh//Source Images//16.Legend2 (legend).png");

		// converting it into a byte array

		byte[] picture2 = IOUtils.toByteArray(new FileInputStream(image2));
		byte[] picture3 = IOUtils.toByteArray(new FileInputStream(image3));

		byte[] picture4 = IOUtils.toByteArray(new FileInputStream(image4));
		byte[] picture5 = IOUtils.toByteArray(new FileInputStream(image5));

		// adding the image to the presentation

		XSLFPictureData idx1 = ppt.addPicture(picture2, PictureData.PictureType.JPEG);

		XSLFPictureData idx2 = ppt.addPicture(picture3, PictureData.PictureType.JPEG);
		// int idx3 = ppt.addPicture(picture4, XSLFPictureData.PICTURE_TYPE_PNG);
		// int idx4 = ppt.addPicture(picture5, XSLFPictureData.PICTURE_TYPE_PNG);

		// creating a slide with given picture on it

		// XSLFPictureShape pic2 = slide2.createPicture(idx1);

		File folder = new File(sourceFolder);

		File[] listOfFiles = folder.listFiles();

		for (File image : listOfFiles) {
			if (image.isFile() && image.getName().endsWith("png") && (image.getName().indexOf("Legend") == -1)) {
				BufferedImage img = null;

				try {
					img = ImageIO.read(image);

					int width = img.getWidth();
					int height = img.getHeight();

					BufferedImage img1 = img.getSubimage(20, 0, 1000, 500);

					List<XSLFSlideMaster> slideMaster = ppt.getSlideMasters();

					// get the desired slide layout
					XSLFSlideLayout titleLayout = slideMaster.get(0).getLayout(SlideLayout.TITLE_ONLY);
					// titleLayout.createAutoShape();

					XSLFSlide slide = ppt.createSlide();

					slide.setFollowMasterGraphics(true);
					// slide.setBackground(new java.awt.Color(0,0,255));

					// XSLFTextShape title1 = slide.getPlaceholder(0);
					// XSLFTextShape body = slide.getPlaceholder(1);

					// clear the existing text in the slide
					// body.clearText();

					// adding new paragraph
					// body.addNewTextParagraph().addNewTextRun().setText("this is my first slide
					// body");

					// XSLFTextShape body = slide.getPlaceholder(1);

					// clear the existing text in the slide
					// body.clearText();

					// int type=img.getType();

					// following expression is currently unused
					if (width > pageWidth) {
						double divider = width / pageWidth;
						double multiplier = height / width;
						width /= divider;
						// width = Math.floor(width);
						// height = Math.floor(width*multiplier);
					}
					if (height > pageHeight) {
						double divider = height / width;
						// width = Math.floor(pageHeight/divider);
						height = pageHeight;
					}

					// int ee=img.getRGB(IMG_HEIGHT, IMG_WIDTH);

					// BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);

					// adding new paragraph
					// body.addNewTextParagraph().addNewTextRun().setText("this is my first slide
					// body");

					// setting the title init
					java.awt.Dimension pgsize = ppt.getPageSize();

					int pgw = pgsize.width; // slide width in points
					int pgh = pgsize.height; // slide height in points
					System.out.println("current page size of the PPT is:");
					System.out.println("width :" + pgw);
					System.out.println("height :" + pgh);

					String a = image.getName().replace(".png", "");

					if (image.getName().indexOf('(') > -1) {
						a = image.getName().substring(0, image.getName().indexOf('('));
					}
					a = a.substring(a.indexOf('.') + 1);

					/*
					 * title1.setText(a.replaceAll("[(1-9)]+","" )); title1.setAnchor(new
					 * java.awt.Rectangle(251,45,700,15));
					 * title1.setFillColor(java.awt.Color.green); title1.setWordWrap(true);
					 */

					// XSLFTextParagraph paragraph=title1.addNewTextParagraph();
					// XSLFTextRun run = paragraph.addNewTextRun();
					// run.setFontColor(java.awt.Color.red);
					// run.setFontSize(24);
					// byte[] picture = IOUtils.toByteArray(new FileInputStream(image));

					// BufferedImage dest = src.getSubimage(0, 0, rect.width, rect.height);

					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(img1, "png", baos);

					byte[] picture = baos.toByteArray();

					XSLFPictureData idx = ppt.addPicture(picture, PictureData.PictureType.JPEG);

					XSLFPictureShape pic = slide.createPicture(idx);
					// pic.resize();
					// pic.setLineColor(java.awt.Color.red);
					// pic.getDyaCropTop();
					// pic.getDxaGoal();

					// pic.setAnchor(anchor);
					// pic.setFlipHorizontal(flip);

					pic.getFillColor();

					XSLFPictureShape pic2 = slide.createPicture(idx1);
					XSLFPictureShape pic3 = slide.createPicture(idx2);

					// String ch=image.getName();
					// if (ch.contains("Timeliness"))
					// {

					// XSLFPictureShape pic4 = slide.createPicture(idx3);
					// pic4.setAnchor(new java.awt.Rectangle(300,628,300,140));
					// }
					// else
					// {
					// XSLFPictureShape pic5 = slide.createPicture(idx4);
					// pic5.setAnchor(new java.awt.Rectangle(300,628,300,140));
					// }

					/*
					 * if(image.getName().contains("map"))
					 * if(image.getName().contains("Timeliness")){ XSLFPictureShape pic4 =
					 * slide.createPicture(idx3); pic4.setAnchor(new
					 * java.awt.Rectangle(190,500,250,140)); }else{ XSLFPictureShape pic5 =
					 * slide.createPicture(idx4); pic5.setAnchor(new
					 * java.awt.Rectangle(190,500,250,140)); }
					 */

					if (height > (ppt2013Height - 150))

					{
						if (height > width) {
							/*
							 * if(width>1026) { ExtendedWidth=1000; }
							 */

							int heightChanged = height + 100;
							if (heightChanged < (ppt2013Height - TitleArea)) {

								// pic.setAnchor(new
								// java.awt.Rectangle((((ppt2013Width-ppt2013Width/8)-ExtendedWidth)/2),TitleArea,ExtendedWidth,heightChanged));
								pic.setAnchor(new java.awt.Rectangle(
										(((ppt2013Width - ppt2013Width / 8) - ExtendedWidth) / 2), TitleArea,
										ExtendedWidth, heightChanged));
							} else {
								pic.setAnchor(new java.awt.Rectangle(
										(((ppt2013Width - ppt2013Width / 8) - ExtendedWidth) / 2), TitleArea,
										ExtendedWidth, (ppt2013Height - TitleArea)));
							}

						} else {
							pic.setAnchor(new java.awt.Rectangle(
									(((ppt2013Width - ppt2013Width / 8) - ExtendedWidthHeightLessThanWidth) / 2),
									TitleArea, ExtendedWidthHeightLessThanWidth, 508));
						}
					}
					// else if(width<500||height<400)
					// {

					// if(width>height){

					// pic.setAnchor(new java.awt.Rectangle(181,100,504,400));
					// }
					// else{
					// pic.setAnchor(new java.awt.Rectangle(181,100,400,500));
					// }
					// }
					else {
						int heightChanged = height + 100;
						if (heightChanged < (ppt2013Height - 150)) {
							pic.setAnchor(
									new java.awt.Rectangle((((ppt2013Width - ppt2013Width / 16) - ExtendedWidth) / 2),
											TitleArea, ExtendedWidth, heightChanged + 100));
						} else {
							pic.setAnchor(
									new java.awt.Rectangle((((ppt2013Width - ppt2013Width / 16) - ExtendedWidth) / 2),
											TitleArea, ExtendedWidth, height + 100));

						}
					}

					pic2.setAnchor(new java.awt.Rectangle((ppt2013Width - ppt2013Width / 8), 0, (ppt2013Width / 8),
							ppt2013Height));

					pic3.setAnchor(new java.awt.Rectangle(0, 0, (ppt2013Width / 16), ppt2013Height));

					// pic.getPictureData();

					// pic.resize();
					// java.awt.Dimension imgsize = pic.get

					// pic.drawContent(g)
					System.out.println(image.getName());
					System.out.println("path:" + image.getPath());
					System.out.println(" width : " + img.getWidth());
					System.out.println(" height: " + img.getHeight());
					System.out.println(" size  : " + image.length());
					System.out.println("" + image.getParent());
					System.out.println("" + image.getCanonicalPath());
					System.out.println("" + image.getAbsolutePath());
				} catch (final IOException e) {
					// handle errors here
				}

			}
		}
		// set new page size
		ppt.setPageSize(new java.awt.Dimension(ppt2013Width, ppt2013Height));

		File file = new File(sourceFolder + "\\Export7.pptx");
		FileOutputStream out = new FileOutputStream(file);

		ppt.write(out);
		System.out.println("Presentation created successfully");
		out.close();
	}
}