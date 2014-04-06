package com.fasterxml.jackson.datatype.jdk8.introspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.Parameters;

/**
 * @author Lovro Pandzic
 */
@RunWith(Parameterized.class)
public class ParameterNamesResolverSerializationTest {

    @Parameters(name = "{index} serializing {1} should produce {0}")
    public static Iterable<Object[]> data() {

        return Arrays.asList(new Object[][]{{"{\"name\":\"abc\",\"value\":1}", new ImmutableBean("abc", 1)},
                                            {"{\"name\":null,\"value\":1}", new ImmutableBean(null, 1)},
                                            {"{\"name\":\"abc\",\"value\":null}", new ImmutableBean("abc", null)},
                                            {"{\"name\":null,\"value\":null}", new ImmutableBean(null, null)},
                                            {"{\"name\":\"abc\",\"value\":1}", ImmutableBeanWithStaticFactory.of("abc", 1)},
                                            {"{\"name\":null,\"value\":1}", ImmutableBeanWithStaticFactory.of(null, 1)},
                                            {"{\"name\":\"abc\",\"value\":null}", ImmutableBeanWithStaticFactory.of("abc", null)},
                                            {"{\"name\":null,\"value\":null}", ImmutableBeanWithStaticFactory.of(null, null)}});
    }

    private final String expected;
    private final Object immutableBean;
    private final ObjectMapper objectMapper;

    public ParameterNamesResolverSerializationTest(String expected, Object immutableBean) {

        this.expected = expected;
        this.immutableBean = immutableBean;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ParameterNamesResolver());
    }

    @Test
    public void test() throws JsonProcessingException {

        assertEquals(expected, objectMapper.writeValueAsString(immutableBean));
    }
}
