package com.fasterxml.jackson.module.paramnames;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.*;

import java.lang.reflect.*;

/**
 * Introspector that uses parameter name information provided by the Java Reflection API additions in Java 8 to determine the parameter
 * name for methods and constructors.
 *
 * @author Lovro Pandzic
 * @see AnnotationIntrospector
 * @see Parameter
 */
class ParameterNamesAnnotationIntrospector extends NopAnnotationIntrospector {
    // stateless, can just use static id
    private static final long serialVersionUID = 1L;

    /**
     * Since Jackson 2.4, there has been distinction between annotation-specified
     * explicit names; and implicit name that comes from source. With this module
     * we only introspect implicit names, so this method needs to return null.
     */
    @Override
    public PropertyName findNameForDeserialization(Annotated annotated) {
        return null;
    }

    // since 2.4
    @Override
    public String findParameterSourceName(AnnotatedParameter param) {
        return findParameterName(param);
    }

    /**
     * Returns the parameter name, or {@code null} if it could not be determined.
     *
     * @param annotatedParameter containing constructor or method from which {@link Parameter} can be extracted
     *
     * @return name or {@code null} if parameter could not be determined
     */
    private String findParameterName(AnnotatedParameter annotatedParameter) {

        AnnotatedWithParams owner = annotatedParameter.getOwner();

        if (owner instanceof AnnotatedConstructor) {
            return findParameterName((AnnotatedConstructor) owner, annotatedParameter.getIndex());
        }

        if (owner instanceof AnnotatedMethod) {
            return findParameterName((AnnotatedMethod) owner, annotatedParameter.getIndex());
        }

        return null;
    }

    /**
     * Returns the parameter name, or {@code null} if it could not be determined.
     *
     * @param annotatedConstructor constructor containing the parameter
     * @param parameterIndex       index of the parameter
     *
     * @return name or {@code null} if parameter could not be determined
     */
    private String findParameterName(AnnotatedConstructor annotatedConstructor, int parameterIndex) {
        Constructor<?> constructor = annotatedConstructor.getAnnotated();
        return findParameterName(constructor.getParameters(), parameterIndex);
    }

    /**
     * Returns the parameter name, or {@code null} if it could not be determined.
     *
     * @param annotatedMethod method containing the parameter
     * @param parameterIndex  index of the parameter
     *
     * @return name or {@code null} if parameter could not be determined
     */
    private String findParameterName(AnnotatedMethod annotatedMethod, int parameterIndex) {
        Method method = annotatedMethod.getAnnotated();
        return findParameterName(method.getParameters(), parameterIndex);
    }

    /**
     * Returns the parameter name in the specified parameters array, or {@code null} if the parameter name is not present.
     *
     * @param parameters     array containing the target parameter
     * @param parameterIndex index of the parameter
     *
     * @return name or null if {@link Parameter#isNamePresent()}  parameter is not present}
     */
    private String findParameterName(Parameter[] parameters, int parameterIndex) {
        Parameter parameter = parameters[parameterIndex];
        return parameter.isNamePresent() ? parameter.getName() : null;
    }
}
