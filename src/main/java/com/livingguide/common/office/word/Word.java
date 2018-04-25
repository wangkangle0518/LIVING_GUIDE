package com.livingguide.common.office.word;

import java.util.Map;

public class Word {

	public static void main(String[] args) throws Exception {
		String inputFile = "D:\\";
		try {
			AnalysisWord analysisWord = new AnalysisWord(inputFile);
			int i = 0;
			int j = 0;
			Map<String, Questions> map = analysisWord.getWordMap();
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
//			analysisWord.getWordMap();
//			List<Entry<String, Questions>> list = analysisWord.sort();
//			ExportWord exportWord = new ExportWord("面试题总集" + ++i);
//			for (Entry<String, Questions> entry : list) {
//				Questions questions = entry.getValue();
//				j++;
//				exportWord.createNewParagraph(1, j + "、" + questions.getQuestion(), true, 12);
//				String answers = questions.getAnswer();
//				for (String answer : answers.split("\r\n")) {
//					exportWord.createNewParagraph(0, answer, false, 10);
//				}
//				if (j % 2000 == 0) {
//					System.out.println("=====================================k=" + j + "=============================================");
//					exportWord.close();
//					exportWord = new ExportWord("面试题总集" + ++i);
//				}
//			}
			exportWord.createNewParagraph(0, "END", true, 12);
			exportWord.close();
//			System.out.println("==============================================" + list.size() + "==============================================");
			System.out.println("==============================================" + map.size() + "==============================================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
