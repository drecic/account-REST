package sparkmail;

import org.junit.Assert;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.drecic.email.sparkmail.auth.Utils;
import org.junit.Test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.javafaker.Faker;

public class CheckAndDecodeJWTTests {

	public static Faker faker = new Faker();
	
	@Test
	public void it_succeeds_when_the_signing_key_matches_and_the_token_isnt_expired() throws IllegalArgumentException, UnsupportedEncodingException {
		// create a date for 1 hour from now
		long future = new Date().getTime() + 3600000;
		Date date = new Date(future);
				
		Algorithm algorithm = Algorithm.HMAC512("secret");
		String jwt = JWT.create().withSubject("tester").withExpiresAt(date).sign(algorithm);				
		DecodedJWT decoded = Utils.checkAndDecodeJWT(jwt, "secret");
		Assert.assertNotNull(decoded);
	}
	
	@Test
	public void it_fails_when_the_signing_key_is_wrong() throws IllegalArgumentException, UnsupportedEncodingException {
		Algorithm algorithm = Algorithm.HMAC512("badkey");
		String jwt = JWT.create().withSubject("tester").sign(algorithm);
		DecodedJWT decoded = Utils.checkAndDecodeJWT(jwt, "goodkey");
		Assert.assertNull(decoded);
	}
	
	@Test
	public void it_fails_when_the_jwt_is_a_random_string() throws IllegalArgumentException, UnsupportedEncodingException {
		String malformed = faker.shakespeare().hamletQuote();
		DecodedJWT decoded = Utils.checkAndDecodeJWT(malformed, "goodkey");
		Assert.assertNull(decoded);
	}

	@Test
	public void it_fails_when_the_jwt_is_malformed_with_2_periods() throws IllegalArgumentException, UnsupportedEncodingException {
		StringBuilder malformed = new StringBuilder();
		malformed.append(faker.lorem().word());
		malformed.append('.');
		malformed.append(faker.lorem().word());
		malformed.append('.');
		malformed.append(faker.lorem().word());
		
		DecodedJWT decoded = Utils.checkAndDecodeJWT(malformed.toString(), "goodkey");
		Assert.assertNull(decoded);
	}
	
	@Test
	public void it_fails_when_the_jwt_is_expired() throws IllegalArgumentException, UnsupportedEncodingException {
		// create a date from 1 second ago
		long past = new Date().getTime() - 1000;
		Date date = new Date(past);
		
		Algorithm algorithm = Algorithm.HMAC512("secret");
		String jwt = JWT.create().withSubject("tester").withExpiresAt(date).sign(algorithm);				
		DecodedJWT decoded = Utils.checkAndDecodeJWT(jwt, "secret");
		Assert.assertNull(decoded);
	}
	
	@Test
	public void it_fails_when_the_jwt_has_no_expiration() throws IllegalArgumentException, UnsupportedEncodingException {
		Algorithm algorithm = Algorithm.HMAC512("secret");
		String jwt = JWT.create().withSubject("tester").sign(algorithm);				
		DecodedJWT decoded = Utils.checkAndDecodeJWT(jwt, "secret");
		Assert.assertNull(decoded);
	}
	
	@Test
	public void it_fails_when_the_jwt_is_null() throws IllegalArgumentException, UnsupportedEncodingException {
		String jwt = null;			
		DecodedJWT decoded = Utils.checkAndDecodeJWT(jwt, "secret");
		Assert.assertNull(decoded);
	}
}
