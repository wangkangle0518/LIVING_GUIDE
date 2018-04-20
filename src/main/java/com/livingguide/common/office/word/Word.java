package com.livingguide.common.office.word;

import java.util.Map;

public class Word {

	public static void main(String[] args) throws Exception {
		String inputFile = "E:\\文档\\资料\\doc\\新建文件夹";
		try {
			AnalysisWord analysisWord = new AnalysisWord(inputFile);
			Map<String, Questions> map = analysisWord.getWordMap();
			int i = 0;
			int j = 0;
			ExportWord exportWord = new ExportWord("面试题总集" + ++i);
			for (String str : map.keySet()) {
				exportWord.createNewParagraph();
				exportWord.addText(map.get(str).getQuestion(), true, 14);
				String answers = map.get(str).getAnswer();
				for (String answer : answers.split("\r\n")) {
					exportWord.createNewParagraph();
					exportWord.addText(answer, false, 12);
				}
				j++;
				if (j % 2000 == 0) {
					System.out.println("=====================================k=" + j + "=============================================");
					exportWord.close();
					exportWord = new ExportWord("面试题总集" + ++i);
				}
			}
			exportWord.close();
			System.out.println("==============================================" + map.size() + "==============================================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
