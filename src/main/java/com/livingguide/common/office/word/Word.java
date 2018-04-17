package com.livingguide.common.office.word;

import java.util.Map;

public class Word {

	public static void main(String[] args) throws Exception {
		String inputFile = "E:\\文档\\资料\\doc\\已处理";
		try {
			AnalysisWord analysisWord = new AnalysisWord(inputFile);
			Map<String, Questions> map = analysisWord.getWordMap();
			int i = 0;
			int j = 0;
			int k = 0;
			ExportWord exportWord = new ExportWord("面试题总集" + ++j);
			for (String str : map.keySet()) {
				exportWord.createNewParagraph(++i);
				exportWord.addText(map.get(str).getQuestion(), true, 14, 0);
				exportWord.createNewParagraph(++i);
				exportWord.addText(map.get(str).getAnswer(), false, 12, 0);
				k++;
				if (k % 1000 == 0) {
					exportWord.close();
					exportWord = new ExportWord("面试题总集" + ++j);
				}
			}
			exportWord.close();
			System.out.println("==============================================" + map.size() + "==============================================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
