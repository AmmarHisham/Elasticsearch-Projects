package com.nbc.app.domain.jobs;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * The persistent class for the PLAN database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(schema = "NBC_CUST", name = "LAST_RUN_JOB")
@IdClass(IndexRunDateKey.class)
public class IndexRunDate  implements Serializable{
	
	private String machineName;
	private Timestamp runDate;
	private String indexName;
	
	
	
	
	/**
	 * @return the machineName
	 */
	@Id
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
	 * @return the runDate
	 */
	@Column(name = "LAST_RUN_DATE")
	public Timestamp getRunDate() {
		return runDate;
	}

	/**
	 * @param runDate the runDate to set
	 */
	public void setRunDate(Timestamp runDate) {
		this.runDate = runDate;
	}

	/**
	 * @return the indexName
	 */
	@Id
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
