package org.drecic.email.sparkmail.auth;

import org.drecic.email.sparkmail.Option;

import com.auth0.jwt.interfaces.DecodedJWT;

import spark.Request;

public class JWTAuthenticationFilter {
	
	private static String SECRET = "abc123";
	
	public static Option<DecodedJWT, String> process(Request request) {
		String rawToken = Utils.parseBearerToken(request.headers("Authorization"));
		DecodedJWT decodedToken = Utils.checkAndDecodeJWT(rawToken, SECRET);
		if (decodedToken == null) {
			return new Option<DecodedJWT, String>("invalid token", null);
		}

		return new Option<DecodedJWT, String>(decodedToken);
	}
}
