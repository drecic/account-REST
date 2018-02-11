package org.drecic.email.sparkmail;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bgalusha.email.sparkmail.auth.JWTAuthenticationFilter;
import org.bgalusha.email.sparkmail.auth.JsonBodyFilter;
import org.bgalusha.email.sparkmail.auth.Utils;
import org.bgalusha.email.sparkmail.controllers.RequestData;
import org.bgalusha.email.sparkmail.dao.User;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import spark.Response;
import spark.Spark;

public class Routes {
	
	public static ObjectMapper mapper = new ObjectMapper();
	
	public static class ErrorResponse {
		public String message;
		
		public ErrorResponse(String message) {
			this.message = message;
		}
	}
	
	public static class NewUsernameRequest implements RequestData {
		public String newUsername;

		@Override
		public List<String> verifySelf() {
			List<String> errors = new LinkedList<String>();
			if (this.newUsername == null) {
				errors.add("missing required field: newUsername");
			}  else if (this.newUsername.length() > 10) {
				errors.add("field newUsername exceeds maximum length");
			}
			
			if (errors.size() == 0) {
				return null;
			} else {
				return errors;
			}
		}
	}
	
	public static void setupRoutes() {
		Spark.post("/users/:username", (request, response) -> {
			
			// ---------------------------
			// AUTHENTICATED REQUESTS ONLY
			// ---------------------------
			
			Option<DecodedJWT, String> tokenOption = JWTAuthenticationFilter.process(request);
			if (!tokenOption.success()) {
				response.status(401);
				return tokenOption.negative();
			}
			DecodedJWT decodedToken = tokenOption.affirmative();
			
			
			// --------------------------
			// PUT AND POST REQUESTS ONLY
			// --------------------------
			
			Option<NewUsernameRequest, String> jsonOption = JsonBodyFilter.process(request, NewUsernameRequest.class);
			if (!jsonOption.success()) {
				response.status(422);
				return jsonOption.negative();
			}
			NewUsernameRequest newUsernameRequest = jsonOption.affirmative();
			
			
			// This should return a list of items instead of just an error message
			List<String> requestJsonErrors = newUsernameRequest.verifySelf();
			if (requestJsonErrors != null) {
				response.status(422);
				return Utils.buildJsonErrorMessage(requestJsonErrors);
			}
			
			// -----------------------------------
			// REQUESTS THAT REQUIRE DATABASE ONLY
			// -----------------------------------
			
			// database access verification (for this case, it will always succeed)
			boolean databaseSuccess = true;
			if (!databaseSuccess) {
				response.status(500);
				response.body(mapper.writeValueAsString(new ErrorResponse("database unavailable")));
				return response.body();
			}
						
			// database existence verification [there is only one user ('ben') in our system]
			String username = request.params("username");
			if (username.compareTo("ben") != 0) {
				response.status(404);
				response.body(mapper.writeValueAsString(new ErrorResponse("couldn't find user '" + username + "'")));
				return response.body();
			}
			
			// database update verification (for this case, it will always succeed)
			if (newUsernameRequest.newUsername.compareTo("oreocat") == 0) {
				response.status(422);
				response.body(mapper.writeValueAsString(new ErrorResponse("username is in use")));
				return response.body();
			}
			
			// ---------------------------------
			// NOT ALL REQUESTS WILL HAVE A BODY
			// ---------------------------------			
			
			
			// finally, we can return the updated user
			User user = new User();
			user.setUsername(newUsernameRequest.newUsername);
			response.status(200);
			response.body(mapper.writeValueAsString(user));
			return response.body();
		});
	}
}
