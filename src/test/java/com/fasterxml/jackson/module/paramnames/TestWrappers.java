package com.fasterxml.jackson.module.paramnames;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class TestWrappers {

    public static class IntWrapper
    {
        private final int value;

        @JsonCreator
        public IntWrapper(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static class GenericWrapper<T>
    {
        private final T value;

        @JsonCreator
        public GenericWrapper(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }
    }

    @Test
    @Ignore
    public void testWrapper() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(new ParameterNamesModule());

        IntWrapper result = mapper.readValue
                ("{\"value\":13}", IntWrapper.class);
        assertNotNull(result);
    }

    @Test
    @Ignore
    public void testGenericWrapper() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(new ParameterNamesModule());

        GenericWrapper<Integer> result = mapper.readValue
            ("{\"value\":13}", new TypeReference<GenericWrapper<Integer>>() { });
        assertNotNull(result);
    }
}
