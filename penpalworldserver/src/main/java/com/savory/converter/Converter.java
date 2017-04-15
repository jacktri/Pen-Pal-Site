package com.savory.converter;

public interface Converter {
    <T,U> U convert(final T source, Class<U> destinationType);
}
