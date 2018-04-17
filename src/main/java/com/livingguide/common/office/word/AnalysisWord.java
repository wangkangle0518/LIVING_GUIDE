package com.livingguide.common.office.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class AnalysisWord {

	private File dir;

	private static final String[] EXTENSIONS = new String[] { "doc", "docx" };
	
	private static Map<String, Questions> map = new LinkedHashMap<String, Questions>();
	
	private FileInputStream in;
	
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

	public AnalysisWord(String inputDir) throws Exception {
		this.dir = new File(inputDir);
		// make sure it's a directory
		if (!dir.exists() || !dir.isDirectory()) {
			throw new IOException("文件夹不存在");
		}
	}

	public Map<String, Questions> getWordMap() throws Exception {

		for (File f : dir.listFiles(IMAGE_FILTER)) {
			in = new FileInputStream(f);

			InputStream is = FileMagic.prepareToCheckMagic(in);
			FileMagic fm = FileMagic.valueOf(is);
			String path = f.getPath();
			System.out.println(path);
			if (fm == FileMagic.OLE2 && path.substring(path.length() - 3).equalsIgnoreCase("doc")) {
				analysisDoc(path);
			} else if (fm == FileMagic.OOXML && path.substring(path.length() - 4).equalsIgnoreCase("docx")) {
				analysisDocx(path);
			} else {
				System.out.println("=========== 暂时无法解析 =====" + fm + "=====" + path);
			}
			in.close();
		}
		
		return map;
	}

	private void analysisDoc(String fileName) throws Exception {
		in = new FileInputStream(new File(fileName));
		HWPFDocument doc = new HWPFDocument(in);
		Range range = doc.getRange();
		int num = range.numParagraphs();
		String[] question = new String[] {"","",""};
		String tempStr;
		for (int i = 0; i < num; i++) {
			Paragraph para = range.getParagraph(i);
			tempStr = para.text().trim();
			if (StringUtils.isBlank(tempStr)) {
				continue;
			}
			if (para.getCharacterRun(0).isBold()) {
				getQuestions(question, tempStr);
			} else if (para.getCharacterRun(0).isItalic()) {
				question[2] = getTemp(question[2], tempStr);
			} else {
				question[1] = getTemp(question[1], tempStr);
			}
		}
		doc.close();
		close();
	}

	private void analysisDocx(String fileName) throws Exception {
		in = new FileInputStream(new File(fileName));
		XWPFDocument doc = new XWPFDocument(in);
		List<XWPFParagraph> paras = doc.getParagraphs();
		String[] question = new String[] {"","",""};
		String tempStr;
		for (XWPFParagraph para : paras) {
			if (para.isEmpty()) {
				continue;
			}
			tempStr = para.getText().trim();
			if (StringUtils.isBlank(tempStr)) {
				continue;
			}
			if (para.getRuns().get(0).isBold()) {
				getQuestions(question, tempStr);
			} else if (para.getRuns().get(0).isItalic()) {
				question[2] = getTemp(question[2], tempStr);
			} else {
				question[1] = getTemp(question[1], tempStr);
			}
		}
		doc.close();
		close();
	}
	
	private String getTemp(String question, String tempStr) {
		if (question.length() > 0) {
			return question + "\r\n" + tempStr;
		} 
		return tempStr;
	}

	private void getQuestions(String[] question, String tempStr) throws Exception {
		if (StringUtils.isBlank(question[0]) || StringUtils.isBlank(question[1])) {
			question[0] = tempStr;
			question[2] = "";
		}
		if (question[0] != tempStr) {
			Questions questions = new Questions(question[0], question[1], question[2]);
			map.put(question[0], questions);
			question[0] = tempStr;
			question[1] = "";
			question[2] = "";
		}
	}
	
	private void close() {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}