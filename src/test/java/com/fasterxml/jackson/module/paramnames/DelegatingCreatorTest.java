package com.fasterxml.jackson.module.paramnames;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

public class DelegatingCreatorTest {

	@Test
	public void shouldNotOverrideJsonCreatorAnnotationWithSpecifiedMode() throws IOException {

		// given
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));

		// when
		ClassWithDelegatingCreator actual = objectMapper.readValue("{\"value\":\"aValue\"}", ClassWithDelegatingCreator.class);

		// then
		Map<String, String> props = new HashMap<>();
		props.put("value", "aValue");
		ClassWithDelegatingCreator expected = new ClassWithDelegatingCreator(props);
		then(actual).isEqualToComparingFieldByField(expected);
	}

	static class ClassWithDelegatingCreator {

		private final String value;

		@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
		ClassWithDelegatingCreator(Map<String, String> props) {
			this.value = props.get("value");
		}

		String getValue() {
			return value;
		}
	}
}
