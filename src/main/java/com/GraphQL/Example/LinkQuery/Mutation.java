package com.GraphQL.Example.LinkQuery;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import com.GraphQL.Example.Auth.AuthContext;
import com.GraphQL.Example.model.AuthData;
import com.GraphQL.Example.model.Link;
import com.GraphQL.Example.model.LinkRepository;
import com.GraphQL.Example.model.SigninPayload;
import com.GraphQL.Example.model.User2;
import com.GraphQL.Example.model.UserRepository;
import com.GraphQL.Example.model.Vote;
import com.GraphQL.Example.model.VoteRepository;
import com.coxautodev.graphql.tools.GraphQLRootResolver;

import graphql.GraphQLException;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLRootContext;

public class Mutation implements GraphQLRootResolver{
	
	private final LinkRepository linkRepository;
	private final UserRepository userRepository;
	private final VoteRepository voteRepository;
	
	public Mutation(LinkRepository linkRepository, UserRepository userRepository, VoteRepository voteRepository)
	{
		this.linkRepository=linkRepository;
		this.userRepository=userRepository;
		this.voteRepository=voteRepository;
	}
	
//	public Link createLink(String id,String url, String description)
//	{
//		Link newLink=new Link(id,url,description);
//		linkRepository.saveLink(newLink);
//		return newLink;
//	}
	@GraphQLMutation
	public Link createLink(@GraphQLNonNull String url, @GraphQLNonNull  String description, @GraphQLRootContext AuthContext context)
	{
		Link newLink=new Link(url, description, context.getUser().getId());
		linkRepository.saveLink(newLink);
		return newLink;
	}
	
	@GraphQLMutation
	public User2 createUser(String name, AuthData auth)
	{
		User2 newUser=new User2(name, auth.getEmail(), auth.getPassword());
		userRepository.saveUser(newUser);
		return newUser;
	}
	
	@GraphQLMutation
	public SigninPayload signinUser(AuthData auth)
	{
		User2 user=userRepository.findByEmail(auth.getEmail());
		if(user.getPassword().equals(auth.getPassword()))
		{
			return new SigninPayload(user.getId(), user);
		}
		throw new GraphQLException("Invalid Credentials");
	}
	
	@GraphQLMutation
	public Vote createVote(String linkId, String userId)
	{
		ZonedDateTime now=Instant.now().atZone(ZoneOffset.UTC);
		return voteRepository.saveVote(new Vote(now, userId, linkId));
	}

}
