package sparkmail;

import java.util.List;
import java.util.Map;

import org.drecic.email.sparkmail.auth.Utils;
import org.junit.Assert;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

public class UnmarshallJsonTests {

	public static class TestData {
		public String stringData;
		public Integer integerData;
		public Float floatData;
		public Map<String, Object> objectData;
		public List<Integer> arrayData;
	}

	@Test
	public void it_succeeds_when_the_json_is_a_valid_object() {
		String jsonString = "{\"stringData\":\"string\",\"integerData\":1,\"floatData\":1.01,\"objectData\":{\"key\":\"value\"},\"arrayData\":[1,2,3]}";
		JsonNode json = Utils.parseJson(jsonString);
		Assert.assertNotNull(json);

		TestData data = Utils.unmarshallJson(json, TestData.class);
		Assert.assertNotNull(data);
		Assert.assertEquals("string", data.stringData);
		Assert.assertEquals(1, data.integerData, 0);
		Assert.assertEquals(1.01, data.floatData, 0.01);
		Assert.assertEquals("value", data.objectData.get("key"));
		Assert.assertEquals(2, data.arrayData.get(1), 0);
	}

	@Test
	public void it_succeeds_when_the_json_is_a_valid_object_with_no_fields() {
		JsonNode json = Utils.parseJson("{ }");
		Assert.assertNotNull(json);

		TestData data = Utils.unmarshallJson(json, TestData.class);
		Assert.assertNotNull(data);
		Assert.assertEquals(null, data.stringData);
		Assert.assertEquals(null, data.integerData);
		Assert.assertEquals(null, data.floatData);
		Assert.assertEquals(null, data.objectData);
		Assert.assertEquals(null, data.arrayData);
	}

	@Test
	public void it_fails_when_the_json_has_mismatched_types_1() {
		JsonNode json = Utils.parseJson("{\"integerData\":\"string\"}");
		Assert.assertNotNull(json);
		TestData data = Utils.unmarshallJson(json, TestData.class);
		Assert.assertNull(data);
	}
	
	@Test
	public void it_fails_when_the_json_has_mismatched_types_2() {
		JsonNode json = Utils.parseJson("{\"arrayData\": [ \"string\" ]}");
		Assert.assertNotNull(json);
		TestData data = Utils.unmarshallJson(json, TestData.class);
		Assert.assertNull(data);
	}
	
	@Test
	public void it_fails_when_the_json_is_null() {
		JsonNode json = null;
		TestData data = Utils.unmarshallJson(json, TestData.class);
		Assert.assertNull(data);
	}
}
