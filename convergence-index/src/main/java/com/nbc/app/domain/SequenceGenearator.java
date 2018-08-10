package com.nbc.app.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SequenceGenearator {

	private String id;
	private long value;

	@org.springframework.data.annotation.Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = "id";
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

}
