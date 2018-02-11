package sparkmail;

import org.drecic.email.sparkmail.auth.Utils;
import org.junit.Assert;

import org.junit.Test;

public class ParseBearerTokenTests {

	@Test
	public void it_succeeds_if_the_token_is_valid() {
		String headerValue = "Bearer abc123";
		String bearerToken = Utils.parseBearerToken(headerValue);
		Assert.assertEquals("abc123", bearerToken);
	}
	
	@Test
	public void it_succeeds_if_the_token_is_valid_and_has_arbitrary_whitespace() {
		String headerValue = "  	Bearer 	 	 asdf 		";
		String bearerToken = Utils.parseBearerToken(headerValue);
		Assert.assertEquals("asdf", bearerToken);
	}
	
	@Test
	public void it_fails_if_the_header_contains_extra_fields() {
		String headerValue = "Bearer abc123  extra	fields";
		String bearerToken = Utils.parseBearerToken(headerValue);
		Assert.assertNull(bearerToken);
	}
	
	@Test
	public void it_fails_if_authentication_isnt_a_bearer_token() {
		String headerValue = "Basic abc123";
		String bearerToken = Utils.parseBearerToken(headerValue);
		Assert.assertNull(bearerToken);
	}
	
	@Test
	public void it_fails_if_no_token_is_present() {
		String headerValue = "Bearer ";
		String bearerToken = Utils.parseBearerToken(headerValue);
		Assert.assertNull(bearerToken);
	}
	
	@Test
	public void it_fails_if_the_header_is_null() {
		String headerValue = null;
		String bearerToken = Utils.parseBearerToken(headerValue);
		Assert.assertNull(bearerToken);
	}
}
