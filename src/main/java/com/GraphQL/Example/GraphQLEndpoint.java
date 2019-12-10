package com.GraphQL.Example;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.GraphQL.Example.Auth.AuthContext;
import com.GraphQL.Example.Auth.IdTokenVerifierAndParser;
import com.GraphQL.Example.Exceptions.SanitizedError;
import com.GraphQL.Example.LinkQuery.Mutation;
import com.GraphQL.Example.LinkQuery.Query;
import com.GraphQL.Example.Resolver.LinkResolver;
import com.GraphQL.Example.Resolver.SigninResolver;
import com.GraphQL.Example.Resolver.VoteResolver;
import com.GraphQL.Example.model.LinkRepository;
import com.GraphQL.Example.model.User2;
import com.GraphQL.Example.model.UserRepository;
import com.GraphQL.Example.model.VoteRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
//import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLServlet;
import io.leangen.graphql.GraphQLSchemaGenerator;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String WEB_CLIENT_ID="826654866209-imkbppfamr8kdbd5eoavcacres9jktog.apps.googleusercontent.com";

	private static final LinkRepository linkRepository;
	private static final UserRepository userRepository;
	private static final VoteRepository voteRepository;
	
	protected static Logger logger=Logger.getLogger(GraphQLEndpoint.class.getName());

	static {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Link");
		PreparedQuery pq = datastore.prepare(q);

		List<Entity> allLinks = pq.asList(FetchOptions.Builder.withLimit(5));

		linkRepository = new LinkRepository(allLinks);

		com.google.appengine.api.datastore.Query q2 = new com.google.appengine.api.datastore.Query("User2");
		PreparedQuery pq2 = datastore.prepare(q2);

		List<Entity> allUsers = pq2.asList(FetchOptions.Builder.withLimit(5));

		userRepository = new UserRepository(allUsers);
		
		com.google.appengine.api.datastore.Query q3 = new com.google.appengine.api.datastore.Query("Vote");
		PreparedQuery pq3 = datastore.prepare(q3);

		List<Entity> allVotes = pq3.asList(FetchOptions.Builder.withLimit(5));
		
		voteRepository=new VoteRepository(allVotes);
	}

	

	public GraphQLEndpoint() {
		super(buildSchema());
	}

	public static GraphQLSchema buildSchema() {
		
		Query query=new Query(linkRepository);
		LinkResolver linkResolver=new LinkResolver(userRepository);
		SigninResolver signinResolver=new SigninResolver();
		VoteResolver voteResolver=new VoteResolver(linkRepository,userRepository);
		Mutation mutation=new Mutation(linkRepository, userRepository,voteRepository);
		
		return new GraphQLSchemaGenerator().withOperationsFromSingletons(query,linkResolver,mutation)
				.generate();
		
//			return SchemaParser
//					.newParser().file("schema.graphqls").resolvers(new Query(linkRepository),
//							new Mutation(linkRepository, userRepository,voteRepository), new SigninResolver(), new LinkResolver(userRepository),
//							new VoteResolver(linkRepository, userRepository))
//					.scalars(Scalars.dateTime)
//					.build().makeExecutableSchema();
//	
	}
	
	@Override
	protected GraphQLContext createContext(Optional<HttpServletRequest> request, Optional<HttpServletResponse> response)
	{
		//com.google.api.server.spi.auth.common.User googleUser = null;
//		UserService userService=UserServiceFactory.getUserService();
//		
//			User userserv=userService.getCurrentUser();
//			User2 user=userRepository.findByEmail(userserv.getEmail());
		
		
//		User2 user = null;
//		try {
//			user = userRepository.findByEmail(googleUser.getEmail());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//		}
		
		
//		User2 user=request.map(req -> req.getHeader("Authorization"))
//				.filter(id-> !id.isEmpty())
//				.map(id -> id.replace("Bearer ", ""))
//				.map(userRepository::findById)
//				.orElse(null);
		
		String token=request.map(req -> req.getHeader("Authorization"))
			.filter(id-> !id.isEmpty())
				.map(id -> id.replace("Bearer ", "")).orElse(null);
		
		GoogleIdToken.Payload payload=null;
		try {
			payload = IdTokenVerifierAndParser.getPayload(token);
		} catch (IOException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String name=(String) payload.get("name");
		String email= payload.getEmail();
		
		if(email==null)
		{
			logger.severe("email not found");
		}
		
			User2 user=null;
			try {
				user = userRepository.findByEmail(email);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
		
		return new AuthContext(user, request, response);
	}
	
	@Override
	protected List<GraphQLError> filterGraphQLErrors(List<GraphQLError> errors)
	{
//		return errors.stream()
//				.filter(e -> instanceof ExceptionWhileDataFetching || super.isClientError(e))
//				.map(e -> instanceof  ExceptionWhileDataFetching ? new SanitizedError((ExceptionWhileDataFetching) e) : e)
//				.collect(Collectors.toList());
		List<GraphQLError> finalErrors=new ArrayList<>();
		
		for(GraphQLError e: errors)
		{
			if((e instanceof ExceptionWhileDataFetching)|| super.isClientError(e))
			{
				if(e instanceof ExceptionWhileDataFetching)
				{
					finalErrors.add(new SanitizedError((ExceptionWhileDataFetching) e));
				}
				else
				{
					finalErrors.add(e);
				}
			}
		}
		
		return finalErrors;
	}

}
