package com.GraphQL.Example.Resolver;

import com.GraphQL.Example.model.SigninPayload;
import com.GraphQL.Example.model.User2;

import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

public class SigninResolver{
	
	@GraphQLQuery
	public User2 user(@GraphQLContext SigninPayload payload)
	{
		return payload.getUser();
	}

}
