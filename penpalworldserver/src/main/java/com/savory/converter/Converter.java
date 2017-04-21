package com.savory.converter;

import java.util.function.Function;

@FunctionalInterface
public interface Converter {
    <T,U> U convert(final T source, Class<U> destinationType);

    default <T,U> Function<T,U> convert(Class<U> destinationType){
        return source -> convert(source,destinationType);
    }
}
