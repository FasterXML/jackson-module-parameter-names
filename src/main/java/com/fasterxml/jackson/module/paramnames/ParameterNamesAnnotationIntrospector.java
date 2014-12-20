package com.fasterxml.jackson.module.paramnames;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
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
class ParameterNamesAnnotationIntrospector extends NopAnnotationIntrospector
{
    // stateless, can just use static id
    private static final long serialVersionUID = 1L;

    /**
     * Since Jackson 2.4, there has been distinction between annotation-specified
     * explicit names; and implicit name that comes from source.
     */
    /*
    @Override
    public PropertyName findNameForDeserialization(Annotated annotated) {
        return null;
    }
    */

    // since 2.4
    @Override
    public String findImplicitPropertyName(AnnotatedMember m) {
        if (m instanceof AnnotatedParameter) {
            return findParameterName((AnnotatedParameter) m);
        }
        return null;
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
        Parameter[] params;
        
        if (owner instanceof AnnotatedConstructor) {
            params = ((AnnotatedConstructor) owner).getAnnotated().getParameters();
        } else if (owner instanceof AnnotatedMethod) {
            params = ((AnnotatedMethod) owner).getAnnotated().getParameters();
        } else {
            return null;
        }
        Parameter p = params[annotatedParameter.getIndex()];
        return p.isNamePresent() ? p.getName() : null;
    }
}
