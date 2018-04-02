package com.livingguide.common.office.word;

public class Questions {

	public Questions() {
		super();
	}

	public Questions(String question, String answer, String analysis) {
		super();
		this.question = question;
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
		return "Questions [\nquestion=" + question + ",\nanswer=" + answer + ",\nanalysis=" + analysis + "]";
	}
}
