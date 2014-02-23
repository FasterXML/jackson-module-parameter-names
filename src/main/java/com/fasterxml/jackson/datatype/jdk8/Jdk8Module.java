package com.fasterxml.jackson.datatype.jdk8;

import java.nio.file.Paths;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class Jdk8Module extends SimpleModule
{
    private static final long serialVersionUID = 1L;

    public Jdk8Module()
    {
        super(PackageVersion.VERSION);

        // first deserializers
//        addDeserializer(Paths.class, new PathsDeserializer());

        // then serializers:
        final JsonSerializer<Object> stringSer = ToStringSerializer.instance;
        addSerializer(Paths.class, stringSer);

        // then key deserializers?
//       addKeyDeserializer(Paths.class, new PathsKeyDeserializer());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }
}
