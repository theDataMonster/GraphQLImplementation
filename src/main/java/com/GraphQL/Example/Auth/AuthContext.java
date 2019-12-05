package com.GraphQL.Example.Auth;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.GraphQL.Example.model.User2;

import graphql.servlet.GraphQLContext;

public class AuthContext extends GraphQLContext{
	
	private final User2 user;

	public AuthContext(User2 user, Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
		super(request, response);
		// TODO Auto-generated constructor stub
		this.user=user;
	}

	public User2 getUser() {
		return user;
	}
	
	
	

}
