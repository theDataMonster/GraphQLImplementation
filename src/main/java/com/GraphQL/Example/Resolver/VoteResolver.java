package com.GraphQL.Example.Resolver;

import com.GraphQL.Example.model.Link;
import com.GraphQL.Example.model.LinkRepository;
import com.GraphQL.Example.model.User2;
import com.GraphQL.Example.model.UserRepository;
import com.GraphQL.Example.model.Vote;
import com.google.appengine.api.datastore.EntityNotFoundException;

import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

public class VoteResolver{
	
	private final LinkRepository linkRepository;
	private final UserRepository userRepository;
	
	public VoteResolver(LinkRepository linkRepository, UserRepository userRepository)
	{
		this.linkRepository=linkRepository;
		this.userRepository=userRepository;
	}
	
	@GraphQLQuery
	public User2 user(@GraphQLContext Vote vote)
	{
		return userRepository.findById(vote.getUserId());
	}
	
	@GraphQLQuery
	public Link link(@GraphQLContext Vote vote)
	{
		try {
			return linkRepository.findById(vote.getLinkId());
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
