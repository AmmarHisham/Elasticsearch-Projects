package com.nbc.app.domain.convgdeal;

import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;

import com.nbc.app.domain.AbstractDomain;


@SuppressWarnings("serial")
@Document
public class ConvergenceDeal extends AbstractDomain<ConvergenceDeal> {

	private Agency agency;
	private Advertiser advertiser;
	private Demo demo;
	private Date modifiedOn;

	@Override
	@org.springframework.data.annotation.Id
	public Long getId() {
		return id;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public Advertiser getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(Advertiser advertiser) {
		this.advertiser = advertiser;
	}
	
	public Demo getDemo() {
		return demo;
	}

	public void setDemo(Demo demo) {
		this.demo = demo;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}		

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Override
	public ConvergenceDeal minify() {
		ConvergenceDeal deal = new ConvergenceDeal();
		deal.setId(this.getId());
		return deal;
	}

}
