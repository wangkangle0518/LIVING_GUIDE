package com.livingguide.common.pptx;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class ReadAndWriteWord {
	/**
	 * 读取Word文件
	 */
	public static String readDoc(String doc) {
	    String text = null;
	    try {
	        File file = new File(doc);
	        String fileName = file.getName();
	        int a  = fileName.lastIndexOf(".");
	        String name = fileName.substring(a,fileName.length());
	 
	        if(name.equals(".doc")){
	            // 创建输入流读取DOC文件
	            FileInputStream in = new FileInputStream(file);
	            // 创建WordExtractor
	            WordExtractor extractor = new WordExtractor(in);
	            // 对DOC文件进行提取
	            //text = extractor.extractText(in);
	            text = extractor.getTextFromPieces();
	            // text = extractor.getText();
	        }else if(name.equals(".docx")){
	            FileInputStream fis = new FileInputStream(file);
	            XWPFDocument xdoc = new XWPFDocument(fis);
	            XWPFWordExtractor wordExtractor = new XWPFWordExtractor(xdoc);
	            text = wordExtractor.getText();
	            //CoreProperties coreProps = wordExtractor.getCoreProperties();  
	            fis.close();
	        }else{
	            text = "文件格式不符";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return text;
	}
	 
	 /**
	  * 将内容写成一个doc文件，保存在磁盘上。
	  */
	public static boolean writeDoc(String path, String content) { 
	    ByteArrayInputStream bais = null;
	    FileOutputStream ostream = null;
	    boolean w = false; 
	    try {
	        content+="\r\n";
	        //byte b[] = content.getBytes("ISO-8859-1");
	        //byte b[] = content.getBytes("gbk");
	        //byte b[] = content.getBytes("GB2312");
	        //byte b[] = content.getBytes("UTF-8");
	        byte b[] = content.getBytes();
	 
	        bais = new ByteArrayInputStream(b);
	        POIFSFileSystem fs = new POIFSFileSystem(); 
	        fs.getRoot().createDocument("WordDocument", bais);
	 
	        ostream = new FileOutputStream(path);
	 
	        fs.writeFilesystem(ostream);
	    } catch (IOException e) { 
	        e.printStackTrace(); 
	    } finally {
	        if(bais!=null) try {bais.close(); } catch(Exception e) {e.printStackTrace();} 
	        if(ostream!=null) try {ostream.close(); } catch(Exception e) {e.printStackTrace();} 
	    }
	 
	    return w; 
	}
}
