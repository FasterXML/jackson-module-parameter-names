package com.fasterxml.jackson.module.paramnames;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class SimplePersonTest {

    @Test
    public void shouldBeAbleToDeserializeSimplePerson() throws IOException {

        // given
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ParameterNamesModule());

        // when
        SimplePerson actual = objectMapper.readValue("{\"name\":\"joe\"}", SimplePerson.class);

        // then
        SimplePerson expected = new SimplePerson("joe");
        then(actual).isEqualToComparingFieldByField(expected);
    }
}
