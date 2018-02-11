package sparkmail;

import java.util.LinkedList;

import org.drecic.email.sparkmail.auth.Utils;
import org.junit.Assert;

import org.junit.Test;

public class BuildJsonErrorMessageTests {

	@Test
	public void it_succeeds_when_the_message_is_a_nonempty_string() {
		String jsonError = Utils.buildJsonErrorMessage("message");
		Assert.assertEquals("{\"error\":\"message\"}", jsonError);
	}
	
	@Test
	public void it_succeeds_when_the_message_is_an_empty_string() {
		String jsonError = Utils.buildJsonErrorMessage("");
		Assert.assertEquals("{\"error\":\"\"}", jsonError);
	}
	
	@Test
	public void it_fails_when_message_is_a_null_string() {
		String jsonError = Utils.buildJsonErrorMessage((String) null);
		Assert.assertNull(jsonError);
	}
	
	@Test
	public void it_succeeds_when_the_message_is_a_list_of_nonempty_strings() {
		LinkedList<String> messages = new LinkedList<String>();
		messages.add("message1");
		messages.add("message2");
		String jsonError = Utils.buildJsonErrorMessage(messages);
		Assert.assertEquals("{\"errors\":[\"message1\",\"message2\"]}", jsonError);
	}
	
	@Test
	public void it_succeeds_when_the_message_is_a_list_of_empty_strings() {
		LinkedList<String> messages = new LinkedList<String>();
		messages.add("");
		messages.add("");
		String jsonError = Utils.buildJsonErrorMessage(messages);
		Assert.assertEquals("{\"errors\":[\"\",\"\"]}", jsonError);
	}
	
	@Test
	public void it_fails_when_message_is_a_null_list() {
		String jsonError = Utils.buildJsonErrorMessage((LinkedList<String>) null);
		Assert.assertNull(jsonError);
	}
}
