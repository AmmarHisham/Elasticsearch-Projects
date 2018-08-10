package com.nbc.app.domain;

import java.io.Serializable;

import org.springframework.data.domain.Example;

@SuppressWarnings("serial")
public abstract class AbstractDomain<T> implements Serializable {
	protected Long id;
	protected String name;
	protected Boolean enabled;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public T getFields(Object... objects) {

		return null;
	}

/*	
	public Boolean getEnabled() {
		return enabled;
	}*/

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public abstract T minify();

	public Example listActive() {
		this.setEnabled(true);
		Example ex = Example.of(this);
		return ex;
	}
}
