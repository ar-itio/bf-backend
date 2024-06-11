package com.onlinebankingsystem.dto;

public class UserLoginRequest {

	private int userId; // for reset password

	private String emailId;

	private String password;
	private String confirmpassword;

	private String role;
	private String twoFactorCode;
	private boolean twoFactorRequired; // Add this field

	public String getTwoFactorCode() {
		return twoFactorCode;
	}

	public void setTwoFactorCode(String twoFactorCode) {
		this.twoFactorCode = twoFactorCode;
	}

	public boolean isTwoFactorRequired() {
		return twoFactorRequired;
	}

	public void setTwoFactorRequired(boolean twoFactorRequired) {
		this.twoFactorRequired = twoFactorRequired;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

}
