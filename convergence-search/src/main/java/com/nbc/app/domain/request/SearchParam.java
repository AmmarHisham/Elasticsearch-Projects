package com.nbc.app.domain.request;

import java.util.List;

public class SearchParam {

	private List<String> resultFields;
	private MultiMatch multiMatch;

	/**
	 * @return the multiMatch
	 */
	public MultiMatch getMultiMatch() {
		return multiMatch;
	}

	/**
	 * @param multiMatch
	 *            the multiMatch to set
	 */
	public void setMultiMatch(MultiMatch multiMatch) {
		this.multiMatch = multiMatch;
	}

	/**
	 * @return the resultFields
	 */
	public List<String> getResultFields() {
		return resultFields;
	}

	/**
	 * @param resultFields
	 *            the resultFields to set
	 */
	public void setResultFields(List<String> resultFields) {
		this.resultFields = resultFields;
	}

}
