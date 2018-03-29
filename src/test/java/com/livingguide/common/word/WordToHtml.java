package com.livingguide.common.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class WordToHtml {
	
	private File dir;
	
	private File file;

	private static final String[] EXTENSIONS = new String[] { "doc", "docx" };// and other formats you need

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
	
	public WordToHtml(String inputDir) throws Exception {
		this.dir = new File(inputDir);
		// make sure it's a directory
		if (!dir.exists() || !dir.isDirectory()) {
			throw new IOException("文件夹不存在");
		}
		this.file = new File(inputDir + "\\" + inputDir.substring(inputDir.lastIndexOf("\\")) + ".pptx");
		if (file.exists()) {
			file.delete();
		}
		getWordAndStyle();
	}

	public static void main(String argv[]) {
		String inputFile = "E:\\文档\\资料\\doc\\面试笔试题";
		try {
			new WordToHtml(inputFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取每个文字样式
	 * 
	 * @param fileName
	 * @throws Exception
	 */

	private void getWordAndStyle() throws Exception {

		int  i = 0;
		for (final File f : dir.listFiles(IMAGE_FILTER)) {
			i++;
			FileInputStream in = new FileInputStream(f);
			
			InputStream is = FileMagic.prepareToCheckMagic(in);
			FileMagic fm = FileMagic.valueOf(is);
			String name = f.getName();
			if (fm == FileMagic.OLE2 && name.substring(name.length()-3).equalsIgnoreCase("doc")) {
//				analysisDoc(fileName);
			} else if (fm == FileMagic.OOXML && name.substring(name.length()-4).equalsIgnoreCase("docx")) {
//				analysisDocx(fileName);
			} else {
				System.out.println("=========== 暂时无法解析 =====" + fm + "=====" + name);
			}
		}
		System.out.println(i);
	}
	
	private Map<String, Questions> analysisDoc(String fileName) throws IOException {
		FileInputStream in = new FileInputStream(new File(fileName));

		HWPFDocument doc = new HWPFDocument(in);

		// 取得文档中字符的总数
		int length = doc.characterLength();
		// 试题集容器
		Map<String, Questions> map = new LinkedHashMap<String, Questions>();

		String question = null;
		String answer = null;
		String analysis = null;
		String tempStr = "";
		for (int i = 0; i < length - 1; i++) {
			// 整篇文章的字符通过一个个字符的来判断,range为得到文档的范围
			Range range1 = new Range(i, i + 1, doc);
			Range range2 = new Range(i + 1, i + 2, doc);

			CharacterRun cr = range1.getCharacterRun(0);

			// 第二个字符
			CharacterRun cr2 = range2.getCharacterRun(0);

			// 比较前后2个字符是否具有相同的格式
			boolean flag = compareCharStyle(cr, cr2);
			if (flag) {
				tempStr += cr.text();
			} else {
				boolean isItalic = cr.isItalic();
				boolean isBold = cr.isBold();
				if (isBold) {
					if (question == null || question.length() ==0) {
						question = tempStr;
						tempStr = "";
						continue;
					}
					if (question != tempStr) {
						Questions questions = new Questions(question, answer, analysis);
						map.put(question, questions);
						question = tempStr;
						tempStr = "";
					}
				} else if (isItalic){
					analysis = tempStr;
					tempStr = "";
				} else if (!isBold && !isItalic){
					answer = tempStr;
					tempStr = "";
				}
			}
		}
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (String ie : map.keySet()) {
			System.out.println(map.get(ie).toString());
		}
		return map;
	}

	private boolean compareCharStyle(CharacterRun cr1, CharacterRun cr2) {
		boolean flag = false;
		if (cr1.isBold() == cr2.isBold() && cr1.isItalic() == cr2.isItalic()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 通过XWPFDocument对内容进行访问。对于XWPF文档而言，用这种方式进行读操作更佳。
	 * 
	 * @throws Exception
	 */
	private void analysisDocx(String fileName) throws Exception {
		FileInputStream in = new FileInputStream(new File(fileName));
		@SuppressWarnings("resource")
		XWPFDocument doc = new XWPFDocument(in);
		List<XWPFParagraph> paras = doc.getParagraphs();
		for (XWPFParagraph para : paras) {
			if (para.isEmpty()) {
				continue;
			}
			para.getRuns().get(0).isBold();
			// 当前段落的属性
			// CTPPr pr = para.getCTP().getPPr();
			System.out.println(para.getText());
		}
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}