package com.GraphQL.Example.model;

import java.util.ArrayList;
import java.util.List;

import com.GraphQL.Example.Filter.LinkFilter;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class LinkRepository {
	
	private final List<Entity> links;
	
	protected static DatastoreService datastore= DatastoreServiceFactory.getDatastoreService();
	
	public LinkRepository(List<Entity> links)
	{
		this.links=links;
	}
	
	public Link findById(String id) throws EntityNotFoundException
	{
		Entity eLink=datastore.get(KeyFactory.createKey("Link", id));
		return link(eLink);
	}
	
	private Link link(Entity e)
	{
		return new Link(e.getProperty("id").toString(),e.getProperty("url").toString(),e.getProperty("description").toString(), e.getProperty("postedBy").toString());
	}
	
	public void saveLink(Link link)
	{
		Entity entity=new Entity("Link",link.getId());
		entity.setIndexedProperty("id", link.getId());
		entity.setIndexedProperty("url", link.getUrl());
		entity.setIndexedProperty("description", link.getDescription());
		entity.setIndexedProperty("postedBy",link.getUserId());
		datastore.put(entity);
	}
	
//	public LinkRepository(List<Link>)
//	{
//		links=new ArrayList<>();
//		links.add(new Link("http://howtographql.com","Your favorite graphql page"));
//		links.add(new Link("http://graphql.org/learn","The official docs"));
//	}
	
	public List<Link> getAllLinks(LinkFilter filter)
	{
		List<Link> allLinks=new ArrayList<>();
		Filter urlFilter=new FilterPredicate("url",FilterOperator.EQUAL, filter.getUrlContains());
		Filter desFilter=new FilterPredicate("description", FilterOperator.EQUAL, filter.getDescriptionContains());
		
		CompositeFilter combFilter=CompositeFilterOperator.and(urlFilter,desFilter);
		Query q=new Query("Link").setFilter(combFilter);
		PreparedQuery pq=datastore.prepare(q);
		
		List<Entity> allEntityLinks=pq.asList(FetchOptions.Builder.withLimit(5));
		
		for(Entity e:allEntityLinks)
		{
			allLinks.add(link(e));
		}
		//List<Link> allLinks=new ArrayList<Link>();
		
		return allLinks;
	}
	
	public List<Link> getAllLinks()
	{
		List<Link> allLinks=new ArrayList<>();
		Query q=new Query("Link");
		PreparedQuery pq=datastore.prepare(q);
		
		List<Entity> allEntityLinks=pq.asList(FetchOptions.Builder.withLimit(5));
		
		for(Entity e:allEntityLinks)
		{
			allLinks.add(link(e));
		}
		
		return allLinks;
	}
	
//	public void saveLink(Link link)
//	{
//		links.add(link);
//	}

}
