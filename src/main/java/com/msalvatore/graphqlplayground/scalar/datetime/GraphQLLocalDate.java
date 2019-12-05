package com.msalvatore.graphqlplayground.scalar.datetime;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

import java.time.LocalDate;
import java.time.LocalDateTime;

// TODO: This could probably be simplified/re-written, but it's a proof of concept ¯\_(ツ)_/¯
// Taken from https://github.com/donbeave/graphql-java-datetime
public class GraphQLLocalDate extends GraphQLScalarType {

    private static final String DEFAULT_NAME = "LocalDate";

    public GraphQLLocalDate() {
        this(DEFAULT_NAME, false);
    }

    public GraphQLLocalDate(boolean zoneConversionEnabled) {
        this(DEFAULT_NAME, zoneConversionEnabled);
    }

    public GraphQLLocalDate(final String name, boolean zoneConversionEnabled) {
        super(name, "Local Date type", new Coercing<LocalDate, String>() {

            private LocalDateTimeConverter converter = new LocalDateTimeConverter(zoneConversionEnabled);

            private LocalDate convertImpl(Object input) {
                if (input instanceof String) {
                    LocalDateTime localDateTime = converter.parseDate((String) input);

                    if (localDateTime != null) {
                        return localDateTime.toLocalDate();
                    }
                }
                return null;
            }

            @Override
            public String serialize(Object input) {
                if (input instanceof LocalDate) {
                    return DateTimeHelper.toISOString((LocalDate) input);
                } else {
                    LocalDate result = convertImpl(input);
                    if (result == null) {
                        throw new CoercingSerializeException("Invalid value '" + input + "' for LocalDate");
                    }
                    return DateTimeHelper.toISOString(result);
                }
            }

            @Override
            public LocalDate parseValue(Object input) {
                LocalDate result = convertImpl(input);
                if (result == null) {
                    throw new CoercingParseValueException("Invalid value '" + input + "' for LocalDate");
                }
                return result;
            }

            @Override
            public LocalDate parseLiteral(Object input) {
                if (!(input instanceof StringValue)) return null;
                String value = ((StringValue) input).getValue();
                return convertImpl(value);
            }
        });
    }

}
