package com.GraphQL.Example.Exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;

import graphql.ExceptionWhileDataFetching;

public class SanitizedError extends ExceptionWhileDataFetching{

	public SanitizedError(ExceptionWhileDataFetching inner) {
		super(inner.getException());
		// TODO Auto-generated constructor stub
	}
	
	@Override
	//@JsonIgnore
	public Throwable getException()
	{
		return super.getException();
	}

}
