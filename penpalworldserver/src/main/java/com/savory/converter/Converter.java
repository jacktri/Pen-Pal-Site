package com.savory.converter;

@FunctionalInterface
public interface Converter {
    <T,U> U convert(final T source, Class<U> destinationType);
}
