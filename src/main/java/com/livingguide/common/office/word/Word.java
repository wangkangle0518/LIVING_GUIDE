package com.livingguide.common.office.word;

import java.util.Map;

public class Word {

	public static void main(String[] args) throws Exception {
		String inputFile = "D:\\";
		try {
			AnalysisWord analysisWord = new AnalysisWord(inputFile);
			Map<String, Questions> map = analysisWord.getWordMap();
			int i = 0;
			int j = 0;
			ExportWord exportWord = new ExportWord("面试题总集" + ++i);
			for (String str : map.keySet()) {
				j++;
				exportWord.createNewParagraph(1, j + "、" + map.get(str).getQuestion(), true, 12);
				String answers = map.get(str).getAnswer();
				for (String answer : answers.split("\r\n")) {
					exportWord.createNewParagraph(0, answer, false, 10);
				}
				if (j % 2000 == 0) {
					System.out.println("=====================================k=" + j + "=============================================");
					exportWord.close();
					exportWord = new ExportWord("面试题总集" + ++i);
				}
			}
			exportWord.createNewParagraph(0, "END", true, 12);
			exportWord.close();
			System.out.println("==============================================" + map.size() + "==============================================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
