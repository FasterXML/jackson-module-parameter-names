package com.fasterxml.jackson.module.paramnames;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

public class NoDefaultConstructorDeserializationTest {

    private ObjectMapper objectMapper;
    private Object readValue;

    @Before
    public void setUp() {

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ParameterNamesModule());
    }

    @Test
    public void shouldDeserializeClassWithMandatoryFields() throws IOException {

        whenReadValue("{\"firstMandatoryField\":\"abc\"," +
                      "\"secondMandatoryField\":true}",
                      ClassWithMandatoryFields.class);

        then(readValue).isEqualToComparingFieldByField(new ClassWithMandatoryFields("abc",
                                                                                    true));
    }

    @Test
    public void shouldDeserializeClassWithMandatoryAndOptionalFields() throws IOException {

        whenReadValue("{\"firstMandatoryField\":\"abc\"," +
                      "\"secondMandatoryField\":true," +
                      "\"firstOptionalField\":10," +
                      "\"secondOptionalField\":2.3}",
                      ClassWithMandatoryAndOptionalFields.class);

        ClassWithMandatoryAndOptionalFields expected = new ClassWithMandatoryAndOptionalFields("abc", true);
        expected.setFirstOptionalField(10);
        expected.setSecondOptionalField(new BigDecimal("2.3"));
        then(readValue).isEqualToComparingFieldByField(expected);
    }

    private void whenReadValue(String json, Class<?> valueType) throws IOException {

        readValue = objectMapper.readValue(json, valueType);
    }

    public static class ClassWithMandatoryFields {

        private final String firstMandatoryField;
        private final Boolean secondMandatoryField;

        public ClassWithMandatoryFields(String firstMandatoryField, Boolean secondMandatoryField) {

            this.firstMandatoryField = firstMandatoryField;
            this.secondMandatoryField = secondMandatoryField;
        }

        public String getFirstMandatoryField() {

            return firstMandatoryField;
        }

        public Boolean getSecondMandatoryField() {

            return secondMandatoryField;
        }
    }

    public static class ClassWithMandatoryAndOptionalFields {

        private final String firstMandatoryField;
        private final Boolean secondMandatoryField;
        private Integer firstOptionalField;
        private BigDecimal secondOptionalField;

        public ClassWithMandatoryAndOptionalFields(String firstMandatoryField, Boolean secondMandatoryField) {

            this.firstMandatoryField = firstMandatoryField;
            this.secondMandatoryField = secondMandatoryField;
        }

        public String getFirstMandatoryField() {

            return firstMandatoryField;
        }

        public Boolean getSecondMandatoryField() {

            return secondMandatoryField;
        }

        public Integer getFirstOptionalField() {

            return firstOptionalField;
        }

        public BigDecimal getSecondOptionalField() {

            return secondOptionalField;
        }

        public void setFirstOptionalField(Integer firstOptionalField) {

            this.firstOptionalField = firstOptionalField;
        }

        public void setSecondOptionalField(BigDecimal secondOptionalField) {

            this.secondOptionalField = secondOptionalField;
        }
    }
}
