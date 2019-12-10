package com.GraphQL.Example.model;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class UserRepository {
	
	private final List<Entity> users;
	
	protected static DatastoreService datastore=DatastoreServiceFactory.getDatastoreService();
	
	public UserRepository(List<Entity> users)
	{
		this.users=users;
	}
	
	public User2 findByEmail(String email)
	{
		Query q=new Query("User2").addFilter("email", FilterOperator.EQUAL, email);
		PreparedQuery pq=datastore.prepare(q);
		Entity user2=pq.asSingleEntity();
		
		return user2(user2);
	}
	
	public User2 findById(String id)
	{
		Query q=new Query("User2").addFilter("id", FilterOperator.EQUAL, id);
		PreparedQuery pq=datastore.prepare(q);
		Entity user2=pq.asSingleEntity();
		
		return user2(user2);
	}
	
	public User2 saveUser(User2 user)
	{
		Entity e=new Entity("User2");
		e.setIndexedProperty("id",  user.getId());
		e.setIndexedProperty("name", user.getName());
		e.setIndexedProperty("email", user.getEmail());
//		e.setIndexedProperty("password",user.getPassword());
		
		datastore.put(e);
		
		return user;
	}
	
	private User2 user2(Entity e)
	{
		if(e==null)
		{
			return null;
		}
		
		return new User2(e.getProperty("id").toString(), e.getProperty("name").toString(), e.getProperty("email").toString());
	}

}
