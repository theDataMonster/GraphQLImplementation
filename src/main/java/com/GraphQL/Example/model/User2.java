package com.GraphQL.Example.model;

import java.util.UUID;

public class User2 {
	
	private final String id;
	private final String name;
	private final String email;
	
	public User2(String name, String email)
	{
		this(UUID.randomUUID().toString(),name,email);
	}
	
	public User2(String id, String name, String email)
	{
		this.id=id;
		this.name=name;
		this.email=email;
		
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

	
}
