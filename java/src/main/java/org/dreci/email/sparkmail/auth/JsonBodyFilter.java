package org.drecic.email.sparkmail.auth;

import org.drecic.email.sparkmail.Option;

import com.fasterxml.jackson.databind.JsonNode;

import spark.Request;

public class JsonBodyFilter {
	
	public static <T> Option<T,String> process(Request request, Class<T> objectClass) {
		
		// verify that the body contains valid JSON data
		JsonNode bodyJSON = Utils.parseJson(request.body());
		if (bodyJSON == null) {
			return new Option<T, String>("failed to read request body", null);
		}
		
		// JSON data unmarshalling
		T object = Utils.unmarshallJson(bodyJSON, objectClass);
		if (object == null) {
			return new Option<T, String>("Unable to unmarshall JSON", null);
		}
		
		return new Option<T, String>(object);
	}
}
