package com.GraphQL.Example.Resolver;

import com.GraphQL.Example.model.Link;
import com.GraphQL.Example.model.User2;
import com.GraphQL.Example.model.UserRepository;

import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

public class LinkResolver{
	
	private final UserRepository userRepository;
	
	public LinkResolver(UserRepository userRepository)
	{
		this.userRepository=userRepository;
	}
	
	@GraphQLQuery
	public User2 postedBy(@GraphQLContext Link link)
	{
		if(link.getUserId()==null)
		{
			return null;
		}
		
		return userRepository.findById(link.getUserId());
	}
}
