package com.nbc.app.domain.request;

public abstract class AbstractRequestParam {

	private Long userId;
	//private Long sessionId;
	private Long tibcoJobId;
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the sessionId
	 */
/*	public Long getSessionId() {
		return sessionId;
	}
	*//**
	 * @param sessionId the sessionId to set
	 *//*
	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}*/
	public Long getTibcoJobId() {
		return tibcoJobId;
	}
	public void setTibcoJobId(Long tibcoJobId) {
		this.tibcoJobId = tibcoJobId;
	}
}
