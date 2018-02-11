package sparkmail;

import org.junit.Assert;

import org.drecic.email.sparkmail.auth.Utils;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

public class ParseJsonTests {
	
	public static Faker faker = new Faker();
	public static ObjectMapper JSONMapper = new ObjectMapper();
	
	@Test
	public void it_succeeds_when_body_is_json_object() {
		String body = "{ \"key\": \"value\" }";
		JsonNode parsed = Utils.parseJson(body);
		Assert.assertNotNull(parsed);
	}
	
	@Test
	public void it_succeeds_when_body_is_json_array() {
		String body = "[ { \"key\": \"value\" }, { \"key2\": \"value2\" } ]";
		JsonNode parsed = Utils.parseJson(body);
		Assert.assertNotNull(parsed);
	}

	@Test
	public void it_fails_when_body_is_null() {
		String body = null;
		JsonNode parsed = Utils.parseJson(body);
		Assert.assertNull(parsed);
	}
	
	@Test
	public void it_fails_when_body_is_not_json() {
		String body = faker.lorem().sentence();
		JsonNode parsed = Utils.parseJson(body);
		Assert.assertNull(parsed);
	}
	
	@Test
	public void it_fails_when_body_is_invalid_json() {
		String body = "{ \"key\" \"value\" }";
		JsonNode parsed = Utils.parseJson(body);
		Assert.assertNull(parsed);
	}

}
