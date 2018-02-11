package org.bgalusha.email.sparkmail.requests;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import spark.Request;
import spark.Response;
import spark.Route;

public class LoginRequest implements Route {
	public String username;
	public String password;

	public boolean isValid() {
		if (this.username == null || this.password == null) {
			return false;
		}
		
		this.password.trim();
		this.username.trim();
		
		return true;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		LoginRequest loginRequest = null;
		try {
			loginRequest = mapper.readValue(request.body(), LoginRequest.class);
		} catch (IOException e) {
			return "Parse Error! Very invalid!";
		}
		
		return "Who knows";
	}
}
