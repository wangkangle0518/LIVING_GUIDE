package com.livingguide.common.office.word;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class Questions {
	public Questions() {
		super();
	}

	public String split(String question) {
		String[] temp = question.split("[\\pP‘’“”]| |\t");
		Pattern pattern = Pattern.compile("^[0-9]*$");
		int k = 0;
		int i = temp.length;
		for (int j = 0; j < i; j++) {
			if (!pattern.matcher(temp[j].trim()).matches()) {
				break;
			}
			k += temp[j].length() + 1;
		}
        return question.substring(k).trim();
	}
	
	public Questions(String question, String answer, String analysis) throws Exception {
		super();
		this.answer = answer;
		this.analysis = analysis;
		
		this.question = replaceAll(question, "  ", "");
		this.question = replaceAll(question, "\t", "");
		this.question = split(this.question);
	}
	
	private String replaceAll(String str, String regex, String replacement) {
		if (str.indexOf(regex) > 0) {
			return str.replaceAll(regex, replacement);
		}
		return str;
	}

	/**
	 * 问题
	 */
	private String question;
	
	/**
	 * 答案
	 */
	private String answer;
	
	/**
	 * 试题解析
	 */
	private String analysis;

	public String getQuestion() {
		return question;
	}
	
	public String getQuestionIsNoInterpunction() {
		return question.replaceAll(" |[\\pP‘’“”]|\t", "");
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("Questions: [\n问题:\n");
		sb.append(question);
		sb.append("\n答案:\n");
		sb.append(answer);
		if (!StringUtils.isBlank(analysis)) {
			sb.append("\n解析:\n");
			sb.append(analysis);
		}
		sb.append("\n]");
		return sb.toString();
	}
}
