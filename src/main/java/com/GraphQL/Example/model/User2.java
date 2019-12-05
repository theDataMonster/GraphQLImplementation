package com.GraphQL.Example.model;

import java.util.UUID;

public class User2 {
	
	private final String id;
	private final String name;
	private final String email;
	private final String password;
	
	public User2(String name, String email, String password)
	{
		this(UUID.randomUUID().toString(),name,email,password);
	}
	
	public User2(String id, String name, String email, String password)
	{
		this.id=id;
		this.name=name;
		this.email=email;
		this.password=password;
	}
	

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}
