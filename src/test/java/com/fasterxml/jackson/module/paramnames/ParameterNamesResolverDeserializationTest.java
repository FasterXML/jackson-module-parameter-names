package com.fasterxml.jackson.module.paramnames;

import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.Parameters;

/**
 * @author Lovro Pandzic
 */
@RunWith(Parameterized.class)
public class ParameterNamesResolverDeserializationTest {

    @Parameters(name = "{index} deserializing {0} should produce {1}")
    public static Iterable<Object[]> data() {

        return Arrays.asList(new Object[][]{{"{\"name\":\"abc\",\"value\":1}", new ImmutableBean("abc", 1)},
                                            {"{\"name\":null,\"value\":1}", new ImmutableBean(null, 1)},
                                            {"{\"name\":\"abc\",\"value\":null}", new ImmutableBean("abc", null)},
                                            {"{\"name\":null,\"value\":null}", new ImmutableBean(null, null)},
                                            {"{\"value\":1,\"name\":\"abc\"}", new ImmutableBean("abc", 1)},
                                            {"{\"value\":1,\"name\":null}", new ImmutableBean(null, 1)},
                                            {"{\"value\":null,\"name\":\"abc\"}", new ImmutableBean("abc", null)},
                                            {"{\"value\":null,\"name\":null}", new ImmutableBean(null, null)},
                                            {"{\"name\":\"abc\",\"value\":1}", ImmutableBeanWithStaticFactory.of("abc", 1)},
                                            {"{\"name\":null,\"value\":1}", ImmutableBeanWithStaticFactory.of(null, 1)},
                                            {"{\"name\":\"abc\",\"value\":null}", ImmutableBeanWithStaticFactory.of("abc", null)},
                                            {"{\"name\":null,\"value\":null}", ImmutableBeanWithStaticFactory.of(null, null)},
                                            {"{\"value\":1,\"name\":\"abc\"}", ImmutableBeanWithStaticFactory.of("abc", 1)},
                                            {"{\"value\":1,\"name\":null}", ImmutableBeanWithStaticFactory.of(null, 1)},
                                            {"{\"value\":null,\"name\":\"abc\"}", ImmutableBeanWithStaticFactory.of("abc", null)},
                                            {"{\"value\":null,\"name\":null}", ImmutableBeanWithStaticFactory.of(null, null)}});
    }

    private final String json;
    private final Object expected;
    private final ObjectMapper objectMapper;

    public ParameterNamesResolverDeserializationTest(String json, Object expected) {
        this.json = json;
        this.expected = expected;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ParameterNamesModule());
    }

    @Test
    public void test() throws IOException {
        assertEquals(expected, objectMapper.readValue(json, expected.getClass()));
    }
}
