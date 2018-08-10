package com.nbc.app.domain.index;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The persistent class for the PLAN database table.
 * 
 */
@SuppressWarnings("serial")
//@JsonInclude(Include.NON_NULL)
public class ConvergenceIndex {

	private Long id;
	private String name;

	private Long agencyId;
	private String agencySFId;
	private String agencyName;

	private Long advertiserId;
	private String advertiserSFId;
	private String advertiserName;

	private Long demoId;
	private String demo;
	private Boolean enabled;
	
	
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
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Long getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}
	public String getAgencySFId() {
		return agencySFId;
	}
	public void setAgencySFId(String agencySFId) {
		this.agencySFId = agencySFId;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public Long getAdvertiserId() {
		return advertiserId;
	}
	public void setAdvertiserId(Long advertiserId) {
		this.advertiserId = advertiserId;
	}
	public String getAdvertiserSFId() {
		return advertiserSFId;
	}
	public void setAdvertiserSFId(String advertiserSFId) {
		this.advertiserSFId = advertiserSFId;
	}
	public String getAdvertiserName() {
		return advertiserName;
	}
	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}
	public Long getDemoId() {
		return demoId;
	}
	public void setDemoId(Long demoId) {
		this.demoId = demoId;
	}
	public String getDemo() {
		return demo;
	}
	public void setDemo(String demo) {
		this.demo = demo;
	}




}