package com.GraphQL.Example.model;

public class SigninPayload {
	
	private final String token;
	private final User2 user;
	
	public SigninPayload(String token, User2 user) {
		this.token = token;
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public User2 getUser() {
		return user;
	}
	

}
