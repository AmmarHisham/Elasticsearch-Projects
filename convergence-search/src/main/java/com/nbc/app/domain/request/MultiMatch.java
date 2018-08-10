package com.nbc.app.domain.request;

import java.util.List;

public class MultiMatch {

	private String input;

	private List<String> fields;

	/**
	 * @return the input
	 */
	public String getInput() {
		return input;
	}

	/**
	 * @param input
	 *            the input to set
	 */
	public void setInput(String input) {
		this.input = input;
	}

	/**
	 * @return the fields
	 */
	public List<String> getFields() {
		return fields;
	}

	/**
	 * @param fields
	 *            the fields to set
	 */
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
}
