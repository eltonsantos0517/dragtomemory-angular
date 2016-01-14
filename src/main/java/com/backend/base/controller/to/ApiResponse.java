/**
 *  Copyright 2015 SmartBear Software
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.backend.base.controller.to;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ApiResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer httpStatusCode;
	private String errorMessage;
	private String httpStatusMessage;
	private Integer totalCount;
	private Integer resultCount;
	private String cursor;
	private Object data;

	public ApiResponse() {
	}

	public ApiResponse(String errorMessage, Integer httpStatusCode, String httpStatusMessage, Integer totalCount,
			Integer resultCount, String cursor, Object data) {
		this.httpStatusCode = httpStatusCode;
		this.httpStatusMessage = httpStatusMessage;
		this.totalCount = totalCount;
		this.resultCount = resultCount;
		this.errorMessage = errorMessage;
		this.cursor = cursor;
		this.data = data;
	}

	public Integer getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(Integer httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getHttpStatusMessage() {
		return httpStatusMessage;
	}

	public void setHttpStatusMessage(String httpStatusMessage) {
		this.httpStatusMessage = httpStatusMessage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getResultCount() {
		return resultCount;
	}

	public void setResultCount(Integer resultCount) {
		this.resultCount = resultCount;
	}

	public String getCursor() {
		return cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

}
