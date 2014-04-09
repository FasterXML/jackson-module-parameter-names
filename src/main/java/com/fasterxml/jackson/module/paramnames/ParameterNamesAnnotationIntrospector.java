package com.fasterxml.jackson.module.paramnames;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Introspector that uses parameter name information provided by the Java Reflection API additions in Java 8 to determine the parameter
 * name for methods and constructors.
 *
 * @author Lovro Pandzic
 * @see AnnotationIntrospector
 * @see Parameter
 */
class ParameterNamesAnnotationIntrospector extends NopAnnotationIntrospector {

    private static final long serialVersionUID = 1031867229003207750L;

    /**
     * Returns the parameter name, or {@code null} if it could not be determined.
     *
     * <p>{@code null} is returned in the following cases:
     * <ul>
     * <li>{@code annotated} is not of {@link AnnotatedParameter} type.</li>
     * <li>{@code annotated} doesn't contain owner of {@link AnnotatedConstructor} or {@link AnnotatedMethod} type.</li>
     * <li>{@code annotated} doesn't contain owner ({@link AnnotatedParameter#getOwner()} returns {@code null}).</li>
     * <li>Parameter name is not present.</li>
     * </ul>
     * </p>
     *
     * @see Parameter
     */
    @Override
    public PropertyName findNameForDeserialization(Annotated annotated) {

        if (!(annotated instanceof AnnotatedParameter)) {
            return null;
        }

        return findParameterName((AnnotatedParameter) annotated);
    }

    /**
     * Returns the parameter name, or {@code null} if it could not be determined.
     *
     * @param annotatedParameter containing constructor or method from which {@link Parameter} can be extracted
     *
     * @return {@link PropertyName parameter name} or {@code null} if parameter could not be determined
     */
    private PropertyName findParameterName(AnnotatedParameter annotatedParameter) {

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
     * @return {@link PropertyName parameter name} or {@code null} if parameter could not be determined
     */
    private PropertyName findParameterName(AnnotatedConstructor annotatedConstructor, int parameterIndex) {

        Constructor<?> constructor = annotatedConstructor.getAnnotated();

        if (constructor == null) {
            return null;
        }

        Parameter[] parameters = constructor.getParameters();

        return findParameterName(parameters, parameterIndex);
    }

    /**
     * Returns the parameter name, or {@code null} if it could not be determined.
     *
     * @param annotatedMethod method containing the parameter
     * @param parameterIndex  index of the parameter
     *
     * @return {@link PropertyName parameter name} or {@code null} if parameter could not be determined
     */
    private PropertyName findParameterName(AnnotatedMethod annotatedMethod, int parameterIndex) {

        Method method = annotatedMethod.getAnnotated();

        if (method == null) {
            return null;
        }

        Parameter[] parameters = method.getParameters();

        return findParameterName(parameters, parameterIndex);
    }

    /**
     * Returns the parameter name in the specified parameters array, or {@code null} if the parameter name is not present.
     *
     * @param parameters     array containing the target parameter
     * @param parameterIndex index of the parameter
     *
     * @return {@link PropertyName parameter name} or null if {@link Parameter#isNamePresent()}  parameter is not present}
     */
    private PropertyName findParameterName(Parameter[] parameters, int parameterIndex) {

        Parameter parameter = parameters[parameterIndex];

        if (!parameter.isNamePresent()) {
            return null;
        }

        return new PropertyName(parameter.getName());
    }
}
