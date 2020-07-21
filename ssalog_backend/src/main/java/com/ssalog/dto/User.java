package com.ssalog.dto;

public class User {
	private String id;
	private String password;
	private String userName;
	private String email;
	private String birthDay;	
	public User() {}
	
	public User(String id, String password, String userName, String email, String birthDay) {
		super();
		this.id = id;
		this.password = password;
		this.userName = userName;
		this.email = email;
		this.birthDay = birthDay;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", userName=" + userName + ", email=" + email
				+ ", birthDay=" + birthDay + "]";
	}
	
	
	
}
