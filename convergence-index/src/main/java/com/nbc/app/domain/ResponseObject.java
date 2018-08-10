package com.nbc.app.domain;

import java.util.HashSet;
import java.util.Set;

public class ResponseObject { 
		private boolean success = false;
		private Set<? extends Object> data;
   
		public ResponseObject(Set<? extends Object> objList,boolean success){
		  this.success = success;
		  this.data = objList;
		}
	  
		public ResponseObject(String errorMsg){
		  Set<String> errorList = new HashSet<String>();
		  errorList.add(errorMsg);
		  this.success = success;
		  this.data = errorList; 
		}
  
		public ResponseObject() {
		}

		/**
		 * @return the success
		 */
		public boolean isSuccess() {
			return success;
		}

		/**
		 * @param success the success to set
		 */
		public void setSuccess(boolean success) {
			this.success = success;
		}

		/**
		 * @return the data
		 */
		public Set<? extends Object> getData() {
			return data;
		}

		/**
		 * @param data the data to set
		 */
		public void setData(Set<? extends Object> data) {
			this.data = data;
		}
}
