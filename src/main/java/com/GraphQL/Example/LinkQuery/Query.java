package com.GraphQL.Example.LinkQuery;

import java.util.List;

import com.GraphQL.Example.Filter.LinkFilter;
import com.GraphQL.Example.model.Link;
import com.GraphQL.Example.model.LinkRepository;

import io.leangen.graphql.annotations.GraphQLQuery;

public class Query {
	
	private final LinkRepository linkRepository;
	
	public Query(LinkRepository linkRepository)
	{
		this.linkRepository=linkRepository;
	}
	@GraphQLQuery
	public List<Link> allLinks(LinkFilter filter)
	{
		return linkRepository.getAllLinks(filter);
	}
	@GraphQLQuery
	public List<Link> allLinks()
	{
		return linkRepository.getAllLinks();
	}
	
	

}
