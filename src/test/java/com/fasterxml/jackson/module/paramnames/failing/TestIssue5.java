package com.fasterxml.jackson.module.paramnames.failing;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

// Not actually failing; but since [Issue#5] open, leave here for now.
public class TestIssue5
{
    static final class SimpleConstructor {
        private final String value;

        public SimpleConstructor(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }    

    private static final TypeReference<List<SimpleConstructor>> SIMPLE_CONSTRUCTOR_REF 
        = new TypeReference<List<SimpleConstructor>>() {};    

    @Test
    public void testSimpleConstructorWithParametersModule() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(new ParameterNamesModule());

        final List<SimpleConstructor> value = mapper.readValue("[\"y\"]", SIMPLE_CONSTRUCTOR_REF);
        Assert.assertNotNull(value);
        Assert.assertEquals(1, value.size());
        Assert.assertEquals("y", value.get(0).getValue());
    }

    @Test
    public void testSimpleConstructorWithoutParametersModule() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();

        final List<SimpleConstructor> value = mapper.readValue("[\"x\"]", SIMPLE_CONSTRUCTOR_REF);
        Assert.assertNotNull(value);
        Assert.assertEquals(1, value.size());
        Assert.assertEquals("x", value.get(0).getValue());
    }
}
