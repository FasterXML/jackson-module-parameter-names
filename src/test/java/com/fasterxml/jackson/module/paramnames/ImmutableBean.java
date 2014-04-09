package com.fasterxml.jackson.module.paramnames;

/**
* @author Lovro Pandzic
*/
class ImmutableBean {

    private final String name;
    private final Integer value;

    public ImmutableBean(String name, Integer value) {

        this.name = name;
        this.value = value;
    }

    public String getName() {

        return name;
    }

    public Integer getValue() {

        return value;
    }

    @Override
    public int hashCode() {

        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ImmutableBean that = (ImmutableBean) o;

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (value != null ? !value.equals(that.value) : that.value != null) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {

        return "ImmutableBean{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
