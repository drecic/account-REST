package org.drecic.email.sparkmail.auth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Utils {
	
	public static ObjectMapper JsonMapper = new ObjectMapper();
	
	public static String parseBearerToken(String headerValue) {
		if (headerValue == null) {
			return null;
		}
		
		String[] split = headerValue.trim().split("\\s+");
		if (split.length != 2) {
			return null;
		}
		
		String type = split[0];
		String token = split[1];
		if (type.compareTo("Bearer") == 0) {
			return token;
		} else {
			return null;
		}
	}
	
	public static JsonNode parseJson(String bodyValue) {
		if (bodyValue == null) {
			return null;
		}
		
		JsonNode parsedBody;
		try {
			parsedBody = JsonMapper.readTree(bodyValue);
		} catch (IOException e) {
			return null;
		}
		
		return parsedBody;
	}
	
	public static <T> T unmarshallJson(JsonNode json, Class<T> objectClass) {
		if (json == null) {
			return null;
		}
		
		T object = null;
		try {
			object = JsonMapper.treeToValue(json, objectClass);
		} catch (IOException e) {
			return null;
		}
		
		return object;
	}
	
	public static DecodedJWT checkAndDecodeJWT(String token, String signingSecret) {
		if (token == null) {
			return null;
		}
		
		Algorithm algorithm;
		try {
			algorithm = Algorithm.HMAC512(signingSecret);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		
		JWTVerifier verifier = JWT.require(algorithm).build();
		
		DecodedJWT decoded = null;
		try {
			decoded = verifier.verify(token);
		} catch (JWTVerificationException | IllegalArgumentException e) {
			// the signature didn't check out
			return null;
		}
		
		if (decoded.getExpiresAt() == null) {
			return null;
		}
		
		return decoded;
	}
	
	public static String buildJsonErrorMessage(String message) {
		
		if (message == null) {
			return null;
		}
		
		String errorMessage = null;
		ObjectNode root = JsonMapper.createObjectNode();
		root.put("error", message);
		
		try {
			errorMessage = JsonMapper.writeValueAsString(root);
		} catch (JsonProcessingException e) {
			// this should always succeed, so if it doesn't, we have a problem
			throw new RuntimeException(e);
		}
		return errorMessage;
	}
	
	public static String buildJsonErrorMessage(List<String> messages) {
		
		if (messages == null) {
			return null;
		}
		
		String errorMessage = null;
		ObjectNode root = JsonMapper.createObjectNode();		
		root.set("errors", JsonMapper.valueToTree(messages));
		
		try {
			errorMessage = JsonMapper.writeValueAsString(root);
		} catch (JsonProcessingException e) {
			// this should always succeed, so if it doesn't, we have a problem
			throw new RuntimeException(e);
		}
		return errorMessage;
	}
}
