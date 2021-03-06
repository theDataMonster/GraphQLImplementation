package com.GraphQL.Example.Auth;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

public class IdTokenVerifierAndParser {
	
	private static final String GOOGLE_CLIENT_ID="826654866209-imkbppfamr8kdbd5eoavcacres9jktog.apps.googleusercontent.com";
	
	public static GoogleIdToken.Payload getPayload(String tokenString) throws IOException, GeneralSecurityException{
		
		JacksonFactory jacksonFactory=new JacksonFactory();
		
		GoogleIdTokenVerifier googleIdTokenVerifier= new GoogleIdTokenVerifier(new NetHttpTransport(), jacksonFactory);
		GoogleIdToken token=GoogleIdToken.parse(jacksonFactory, tokenString);
		
		if(googleIdTokenVerifier.verify(token))
		{
			GoogleIdToken.Payload payload=token.getPayload();
			
			if(!GOOGLE_CLIENT_ID.equals(payload.getAudience()))
			{
				throw new IllegalArgumentException("Audience mismatch");
			}
			else if(!GOOGLE_CLIENT_ID.equals(payload.getAuthorizedParty())) {
				
				throw new IllegalArgumentException("Client ID mismatch");
			}
			return payload;
		}
		else {
			
			throw new IllegalArgumentException("Token cannot be verified");
		}
	}

}
