package com.GraphQL.Example.OAuthIntegration;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.GraphQL.Example.Auth.IdTokenVerifierAndParser;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException
	{
		resp.setContentType("text/html");
		
		
		try {
			String idToken=req.getParameter("id_token");
			GoogleIdToken.Payload payload=IdTokenVerifierAndParser.getPayload(idToken);
			String name=(String) payload.get("name");
			String email=payload.getEmail();
			
			System.out.println("Email="+email);
			System.out.println("Name="+name);
			
			HttpSession session=req.getSession(true);
			session.setAttribute("userName", name);
			session.setAttribute("token", idToken);
			req.getServletContext().getRequestDispatcher("/queryGQL.html").forward(req, resp);
		} catch (IOException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	

}
