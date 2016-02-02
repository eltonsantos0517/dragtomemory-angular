package com.backend.base.controller.to;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.JsonObject;

@XmlRootElement
public class PushTO {

	private String message;
	private String to;
	private String collapse_key;
	private String priority;
	private Boolean content_available;
	private Boolean delay_while_idle;
	private Long time_to_live;
	private String restricted_package_name;
	private Boolean dry_run;
	private JsonObject data;
	private JsonObject notification;
	private String[] registration_ids;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCollapse_key() {
		return collapse_key;
	}

	public void setCollapse_key(String collapse_key) {
		this.collapse_key = collapse_key;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Boolean getContent_available() {
		return content_available;
	}

	public void setContent_available(Boolean content_available) {
		this.content_available = content_available;
	}

	public Boolean getDelay_while_idle() {
		return delay_while_idle;
	}

	public void setDelay_while_idle(Boolean delay_while_idle) {
		this.delay_while_idle = delay_while_idle;
	}

	public Long getTime_to_live() {
		return time_to_live;
	}

	public void setTime_to_live(Long time_to_live) {
		this.time_to_live = time_to_live;
	}

	public String getRestricted_package_name() {
		return restricted_package_name;
	}

	public void setRestricted_package_name(String restricted_package_name) {
		this.restricted_package_name = restricted_package_name;
	}

	public Boolean getDry_run() {
		return dry_run;
	}

	public void setDry_run(Boolean dry_run) {
		this.dry_run = dry_run;
	}

	public JsonObject getData() {
		return data;
	}

	public void setData(JsonObject data) {
		this.data = data;
	}

	public JsonObject getNotification() {
		return notification;
	}

	public void setNotification(JsonObject notification) {
		this.notification = notification;
	}

	public String[] getRegistration_ids() {
		return registration_ids;
	}

	public void setRegistration_ids(String[] registration_ids) {
		this.registration_ids = registration_ids;
	}

}
