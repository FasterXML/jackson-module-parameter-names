package com.fasterxml.jackson.module.paramnames;

import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

/**
 * @author Lovro Pandzic
 */
public class ParameterNamesAnnotationIntrospectorTest {

    private ParameterNamesAnnotationIntrospector parameterNamesAnnotationIntrospector = new ParameterNamesAnnotationIntrospector();
    private AnnotatedParameter annotatedParameter;
    private String propertyName;

    @Test
    public void shouldFindParameterNameFromConstructorForLegalIndex() throws NoSuchMethodException {

        givenAnnotatedWithParams(ImmutableBean.class.getConstructor(String.class, Integer.class), 0);

        whenFindImplicitName();

        thenShouldFindParameterName("name");
    }

    @Test
    public void shouldFindParameterNameFromMethodForLegalIndex() throws NoSuchMethodException {

        givenAnnotatedWithParams(ImmutableBeanWithStaticFactory.class.getMethod("of", String.class, Integer.class), 0);

        whenFindImplicitName();

        thenShouldFindParameterName("name");
    }

    private void givenAnnotatedWithParams(Constructor<ImmutableBean> constructor, int index) throws NoSuchMethodException {

        AnnotatedConstructor owner = new AnnotatedConstructor(constructor, null, null);
        annotatedParameter = new AnnotatedParameter(owner, null, null, index);
    }

    private void givenAnnotatedWithParams(Method method, int index) throws NoSuchMethodException {

        AnnotatedMethod owner = new AnnotatedMethod(method, null, null);
        annotatedParameter = new AnnotatedParameter(owner, null, null, index);
    }

    private void whenFindImplicitName() {

        propertyName = parameterNamesAnnotationIntrospector.findImplicitPropertyName(annotatedParameter);
    }

    private void thenShouldFindParameterName(String name) {

        assertEquals(name, propertyName);
    }
}
