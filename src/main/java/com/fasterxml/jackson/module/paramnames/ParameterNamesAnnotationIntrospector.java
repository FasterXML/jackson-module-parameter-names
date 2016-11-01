package com.fasterxml.jackson.module.paramnames;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.MalformedParametersException;
import java.lang.reflect.Parameter;

/**
 * Introspector that uses parameter name information provided by the Java Reflection API additions in Java 8 to
 * determine the parameter name for methods and constructors.
 *
 * @author Lovro Pandzic
 * @see AnnotationIntrospector
 * @see Parameter
 */
class ParameterNamesAnnotationIntrospector extends NopAnnotationIntrospector {
    private static final long serialVersionUID = 1L;

    private final JsonCreator.Mode creatorBinding;
    private final ParameterExtractor parameterExtractor;

    ParameterNamesAnnotationIntrospector(JsonCreator.Mode creatorBinding, ParameterExtractor parameterExtractor) {

        this.creatorBinding = creatorBinding;
        this.parameterExtractor = parameterExtractor;
    }

    @Override
    public String findImplicitPropertyName(AnnotatedMember m) {
        if (m instanceof AnnotatedParameter) {
            return findParameterName((AnnotatedParameter) m);
        }
        return null;
    }

    @Deprecated // since 2.9
    @Override
    public JsonCreator.Mode findCreatorBinding(Annotated a) {

        JsonCreator ann = a.getAnnotation(JsonCreator.class);
        if (ann != null) {
            return ann.mode();
        }

        return creatorBinding;
    }

    @Deprecated // since 2.9
    @Override
    public boolean hasCreatorAnnotation(Annotated a) {
        if (a instanceof AnnotatedConstructor) {
            Constructor<?>[] constructors = ((AnnotatedConstructor) a).getDeclaringClass().getConstructors();
            if (constructors.length == 1 && constructors[0].equals(a.getAnnotated())) {
                return true;
            }
        }
        return false;
    }

    private String findParameterName(AnnotatedParameter annotatedParameter) {

        Parameter[] params;
        try {
            params = getParameters(annotatedParameter.getOwner());
        } catch (MalformedParametersException e) {
            return null;
        }

        Parameter p = params[annotatedParameter.getIndex()];
        return p.isNamePresent() ? p.getName() : null;
    }

    private Parameter[] getParameters(AnnotatedWithParams owner) {
        if (owner instanceof AnnotatedConstructor) {
            return parameterExtractor.getParameters(((AnnotatedConstructor) owner).getAnnotated());
        }

        if (owner instanceof AnnotatedMethod) {
            return parameterExtractor.getParameters(((AnnotatedMethod) owner).getAnnotated());
        }

        return null;
    }
}
