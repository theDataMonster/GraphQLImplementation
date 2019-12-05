package com.GraphQL.Example.model;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.GraphQL.Example.Scalars.Scalars;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class VoteRepository {
	
	private final List<Entity> votes;
	protected static DatastoreService datastore=DatastoreServiceFactory.getDatastoreService();
	
	public VoteRepository(List<Entity> votes)
	{
		this.votes=votes;
	}
	
	public List<Vote> findByUserId(String userId)
	{
		List<Vote> list=new ArrayList<>();
		for(Entity vote: votes)
		{
			if(vote.getProperty("userId").toString().equals(userId))
			{
				list.add(vote(vote));
			}
		}
		return list;
	}
	
	public List<Vote> findByLinkId(String linkId)
	{
		List<Vote> list=new ArrayList<>();
		for(Entity vote: votes)
		{
			if(vote.getProperty("linkId").toString().equals(linkId));
			{
				list.add(vote(vote));
			}
		}
		
		
		return list;
	}
	
	public Vote saveVote(Vote vote)
	{
		Entity e=new Entity("Vote");
		e.setIndexedProperty("userId", vote.getUserId());
		e.setIndexedProperty("linkId", vote.getLinkId());
		e.setIndexedProperty("createdAt", Scalars.dateTime.getCoercing().serialize(vote.getCreatedAt()));
		datastore.put(e);
		
		return new Vote(ZonedDateTime.parse(e.getProperty("createdAt").toString()), e.getProperty("userId").toString(),e.getProperty("linkId").toString());
	}
	
	private Vote vote(Entity vote)
	{
		return new Vote(
				ZonedDateTime.parse(vote.getProperty("createdAt").toString()), vote.getProperty("userId").toString(), vote.getProperty("linkId").toString());
	}

}
