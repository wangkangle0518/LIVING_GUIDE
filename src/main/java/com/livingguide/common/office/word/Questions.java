package com.livingguide.common.office.word;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class Questions {
	private final String split1 = "、";
	private final String split2 = ".";
	private final String split3 = "、";

	public Questions() {
		super();
	}

	public String split(String question, String split) {
		String str = question.substring(0, question.indexOf(split));
        Pattern pattern = Pattern.compile("^[0-9]*$");  
        if (pattern.matcher(str).matches()) {
			question = question.substring(question.indexOf(split) + 1);
		}
        return question;
	}
	
	public Questions(String question, String answer, String analysis) throws Exception {
		super();
		if (StringUtils.isBlank(question)) {
			throw new Exception("问题不能为空");
		}
		this.question = question;
		if (this.question.indexOf(split1) > 0) {
			this.question = split(this.question, split1);
		}
		if (this.question.indexOf(split2) > 0) {
			this.question = split(this.question, split2);
		}
		if (this.question.indexOf(split3) > 0) {
			this.question = split(this.question, split3);
		}
		if (this.question.indexOf(" ") > 0) {
			this.question = this.question.replaceAll(" ", "");
		}
		this.answer = answer;
		this.analysis = analysis;
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
