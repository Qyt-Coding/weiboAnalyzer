package com.bean;

public class User {
	
	String id;	//id
	String screen_name;	//用户名	
	String verified_reason;	//个人签名
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScreen_name() {
		return screen_name;
	}
	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}
	public String getVerified_reason() {
		return verified_reason;
	}
	public void setVerified_reason(String verified_reason) {
		this.verified_reason = verified_reason;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", screen_name=" + screen_name + ", verified_reason=" + verified_reason + ", getId()="
				+ getId() + ", getScreen_name()=" + getScreen_name() + ", getVerified_reason()=" + getVerified_reason()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
}
