package com.fasterxml.jackson.module.paramnames;

import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class ParameterNamesModule extends SimpleModule
{
    private static final long serialVersionUID = 1L;

    public ParameterNamesModule()
    {
        super(PackageVersion.VERSION);
    }

    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);
        context.insertAnnotationIntrospector(new ParameterNamesAnnotationIntrospector());
    }
    
    @Override
    public int hashCode() { return getClass().hashCode(); }

    @Override
    public boolean equals(Object o) { return this == o; }
}
