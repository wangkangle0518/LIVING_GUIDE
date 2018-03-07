package com.livingguide.common.pptx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hslf.usermodel.HSLFPictureData;
import org.apache.poi.hslf.usermodel.HSLFPictureShape;
import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFSlideShowImpl;
import org.apache.poi.sl.usermodel.PictureData;

public class Hello {

	public static void main(String args[]) throws IOException {
		HSLFSlideShow ppt = new HSLFSlideShow(new HSLFSlideShowImpl("slideshow.ppt"));

	    // extract all pictures contained in the presentation
	    int idx = 1;
	    for (HSLFPictureData pict : ppt.getPictureData()) {
	        // picture data
	        byte[] data = pict.getData();

	        PictureData.PictureType type = pict.getType();
	        String ext = type.extension;
	        FileOutputStream out = new FileOutputStream("pict_" + idx + ext);
	        out.write(data);
	        out.close();
	        idx++;
	    }

	    // add a new picture to this slideshow and insert it in a new slide
	    HSLFPictureData pd = ppt.addPicture(new File("clock.jpg"), PictureData.PictureType.JPEG);

	    HSLFPictureShape pictNew = new HSLFPictureShape(pd);

	    // set image position in the slide
	    pictNew.setAnchor(new java.awt.Rectangle(100, 100, 300, 200));

	    HSLFSlide slide = ppt.createSlide();
	    slide.addShape(pictNew);

	    // now retrieve pictures containes in the first slide and save them on disk
	    idx = 1;
	    slide = ppt.getSlides().get(0);
	    for (HSLFShape sh : slide.getShapes()) {
	        if (sh instanceof HSLFPictureShape) {
	            HSLFPictureShape pict = (HSLFPictureShape) sh;
	            HSLFPictureData pictData = pict.getPictureData();
	            byte[] data = pictData.getData();
	            PictureData.PictureType type = pictData.getType();
	            FileOutputStream out = new FileOutputStream("slide0_" + idx + type.extension);
	            out.write(data);
	            out.close();
	            idx++;
	        }
	    }

	    FileOutputStream out = new FileOutputStream("slideshow.ppt");
	    ppt.write(out);
	    out.close();
	}
}