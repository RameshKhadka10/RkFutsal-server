package com.futsal.payload.response;

import java.util.List;

public class JwtResponse {
	
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private Long phone;
	private List<String> roles;
	
	public JwtResponse(String accessToken, Long id, String username, Long phone, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.phone = phone;
		this.roles = roles;
	}
	public String getAccessToken() {
		return token;
	}
	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}
	public String getTokenType() {
		return type;
	}
	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	public List<String> getRoles() {
		return roles;
	}
	

}
