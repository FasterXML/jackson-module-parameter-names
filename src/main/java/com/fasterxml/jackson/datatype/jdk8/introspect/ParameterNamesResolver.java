package com.fasterxml.jackson.datatype.jdk8.introspect;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.lang.reflect.Parameter;

/**
 * {@link SimpleModule Extension} for {@link ObjectMapper} that uses <a href="http://openjdk.java.net/jeps/118">JEP-118</a> to enable
 * conversion of classes with no default (no-arg) constructor and without using annotations.
 *
 * <p>Following preconditions must be satisfied to use this extension:
 * <ul>
 *     <li>Converted classes must be compiled with Java 8 compliant compiler.</li>
 *     <li>{@code -parameters} option must be passed to the compiler.</li>
 * </ul>
 * </p>
 *
 * <p>To enable {@link ParameterNamesResolver} simply register it with {@link ObjectMapper}:
 * <pre>
 *      ObjectMapper objectMapper = new ObjectMapper();
 *      objectMapper.registerModule(new ParameterNamesResolver());
 *
 * </pre></p>
 *
 * <p>Example class that can be converted using this extension:
 * <pre>
 * public static final class ImmutableBean {
 *
 *     private final String name;
 *     private final Integer value;
 *
 *     public ImmutableBean(String name, Integer value) {
 *         this.name = name;
 *         this.value = value;
 *     }
 *
 *     public String getName() {
 *
 *         return name;
 *     }
 *
 *     public Integer getValue() {
 *
 *         return value;
 *     }
 * </pre></p>
 *
 * @author Lovro Pandzic
 * @see Parameter
 * @see ObjectMapper
 * @see JsonCreator
 */
public final class ParameterNamesResolver extends SimpleModule {

    public static ParameterNamesResolver getDefault() {

        return new ParameterNamesResolver();
    }

    private static final long serialVersionUID = -6784516575056317310L;

    ParameterNamesResolver() {

    }

    @Override
    public void setupModule(SetupContext context) {

        super.setupModule(context);
        context.insertAnnotationIntrospector(new ParameterNamesAnnotationIntrospector());
    }
}
