package com.fasterxml.jackson.module.paramnames;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Basic sanity check test to ensure that build environment actually enables
 * storage of parameter names
 */
public class TestJDKSettings
{
    static class Bean {
        public Bean(String beanName1) { }

        public static void factory(String name, Integer foobar) { }
    }

    @Test
    public void testMethodParameterNames() throws Exception {
        Constructor<?> ctor = Bean.class.getDeclaredConstructor(String.class);
        assertNotNull(ctor);
        assertEquals("beanName1", ctor.getParameters()[0].getName());
        Method m = Bean.class.getDeclaredMethod("factory", String.class, Integer.class);
        assertEquals("foobar", m.getParameters()[1].getName());
    }
}
