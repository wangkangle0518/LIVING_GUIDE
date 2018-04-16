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

public class WordToHtml {

	private File dir;

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
		getWordAndStyle();
	}

	public static void main(String argv[]) {
		String inputFile = "E:\\文档\\资料\\doc";
		try {
			new WordToHtml(inputFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getWordAndStyle() throws Exception {

		for (final File f : dir.listFiles(IMAGE_FILTER)) {
			FileInputStream in = new FileInputStream(f);

			InputStream is = FileMagic.prepareToCheckMagic(in);
			FileMagic fm = FileMagic.valueOf(is);
			String name = f.getName();
			String path = f.getPath();
			System.out.println(path);
			if (fm == FileMagic.OLE2 && name.substring(name.length() - 3).equalsIgnoreCase("doc")) {
				analysisDoc(path);
			} else if (fm == FileMagic.OOXML && name.substring(name.length() - 4).equalsIgnoreCase("docx")) {
				analysisDocx(path);
			} else {
				System.out.println("=========== 暂时无法解析 =====" + fm + "=====" + name);
				if (f.exists()) {
					f.createNewFile();
					System.out.println(f.delete());
				}
			}
			break;
		}
	}

	private Map<String, Questions> analysisDoc(String fileName) throws IOException {
		System.out.println(fileName);
		FileInputStream in = new FileInputStream(new File(fileName));

		HWPFDocument doc = new HWPFDocument(in);
		Range range = doc.getRange();
		int num = range.numParagraphs();
		String question = "";
		String answer = "";
		String analysis = "";
		String tempStr = "";
		Map<String, Questions> map = new LinkedHashMap<String, Questions>();
		for (int i = 0; i < num; i++) {
			Paragraph para = range.getParagraph(i);
			tempStr = para.text().trim();
			if (StringUtils.isBlank(tempStr)) {
				continue;
			}
			if (para.getCharacterRun(0).isBold()) {
				if (StringUtils.isBlank(question) || StringUtils.isBlank(answer)) {
					question = tempStr;
					tempStr = "";
					analysis = "";
					continue;
				}
				if (question != tempStr) {
					Questions questions = new Questions(question, answer, analysis);
					map.put(question, questions);
					question = tempStr;
					tempStr = "";
					analysis = "";
					answer = "";
					continue;
				}
			} else if (para.getCharacterRun(0).isItalic()) {
				analysis += tempStr;
				continue;
			} else {
				answer += tempStr;
				continue;
			}
		}
		doc.close();
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

	private Map<String, Questions> analysisDocx(String fileName) throws Exception {
		FileInputStream in = new FileInputStream(new File(fileName));
		XWPFDocument doc = new XWPFDocument(in);
		List<XWPFParagraph> paras = doc.getParagraphs();
		String question = "";
		String answer = "";
		String analysis = "";
		String tempStr = "";
		Map<String, Questions> map = new LinkedHashMap<String, Questions>();
		for (XWPFParagraph para : paras) {
			if (para.isEmpty()) {
				continue;
			}
			tempStr = para.getText().trim();
			if (StringUtils.isBlank(tempStr)) {
				continue;
			}
			if (para.getRuns().get(0).isBold()) {
				if (StringUtils.isBlank(question) || StringUtils.isBlank(answer)) {
					question = tempStr;
					tempStr = "";
					analysis = "";
					continue;
				}
				if (question != tempStr) {
					Questions questions = new Questions(question, answer, analysis);
					map.put(question, questions);
					question = tempStr;
					tempStr = "";
					analysis = "";
					answer = "";
					continue;
				}
			} else if (para.getRuns().get(0).isItalic()) {
				analysis += tempStr;
				continue;
			} else {
				answer += tempStr;
				continue;
			}
		}
		doc.close();
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

}