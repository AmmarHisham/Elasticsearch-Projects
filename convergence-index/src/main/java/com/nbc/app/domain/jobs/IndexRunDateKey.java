package com.nbc.app.domain.jobs;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * The persistent class for the PLAN database table.
 * 
 */
@SuppressWarnings("serial")
public class IndexRunDateKey implements Serializable {

	private String machineName;
	private String indexName;
	/**
	 * @return the machineName
	 */
	@Column(name = "MACHINE_NAME")
	public String getMachineName() {
		return machineName;
	}
	/**
	 * @param machineName the machineName to set
	 */
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	/**
	 * @return the indexName
	 */
	@Column(name = "NAME")
	public String getIndexName() {
		return indexName;
	}
	/**
	 * @param indexName the indexName to set
	 */
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
}