package com.nbc.app.domain.response;

import java.util.ArrayList;
import java.util.List;

public class ResponseObject {

	private int count;

	private int totalCount;

	private int totalPage;

	private boolean success;

	private List<? extends Object> result;

	/**
	 * @return the totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * @param totalPage
	 *            the totalPage to set
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public ResponseObject(boolean success, int totalCount, int count, List<? extends Object> objList, String response,
			int totalPage) {
		this.success = success;
		this.totalCount = totalCount;
		this.count = count;
		this.result = objList;
		this.totalPage = totalPage;
	}

	public ResponseObject(String errorMsg) {
		List<String> errorList = new ArrayList<String>();
		errorList.add(errorMsg);
		this.result = errorList;
	}

	public ResponseObject() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the result
	 */
	public List<? extends Object> getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(List<? extends Object> result) {
		this.result = result;
	}

}
