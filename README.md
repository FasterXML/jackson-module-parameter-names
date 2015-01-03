[Jackson](../../../jackson) module
that adds support for accessing parameter names; a feature added in JDK 8.

## Status

This is a new, experimental module; 2.4 is the first official release.

[![Build Status](https://fasterxml.ci.cloudbees.com/job/jackson-module-parameter-names-master/badge/icon)](https://fasterxml.ci.cloudbees.com/job/jackson-module-parameter-names-master/)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.fasterxml.jackson.module/jackson-module-parameter-names/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.fasterxml.jackson.module/jackson-module-parameter-names)


## Usage

### Maven dependency

For maven dependency definition, click on the second badge in the previous, Status section.

### Registering module

Like all standard Jackson modules (libraries that implement Module interface), registration is done as follows:

```java
ObjectMapper mapper = new ObjectMapper();
mapper.registerModule(new ParameterNamesModule());
```

after which functionality is available for all normal Jackson operations.

### Usage example

Java 8 API adds support for accessing parameter names at runtime in order to enable clients to abandon the JavaBeans standard if they want to without forcing them to use annotations (such as [JsonProperty][1]).

So, after registering the module as described above, you will be able to use data binding with a class like

```java
class Person {

    private final String name;
    private final String surname;

    @JsonCreator // important!
    public Person(String name, String surname) {

        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
```
class Person must be compiled with Java 8 compliant compiler with the enabled option to store formal parameter names (`-parameters` option). For more information about Java 8 API for accessing parameter names at runtime see [this][2].


## More

See [Wiki](../../wiki) for more information (javadocs, downloads).

[1]: http://jackson.codehaus.org/1.1.2/javadoc/org/codehaus/jackson/annotate/JsonProperty.html
[2]: http://docs.oracle.com/javase/tutorial/reflect/member/methodparameterreflection.html
