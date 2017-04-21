package com.savory.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ConverterImpl implements Converter{

    private ModelMapper modelMapper;

    public ConverterImpl(){
        modelMapper = new ModelMapper();
        modelMapper.addMappings(new ProfileMap());
        modelMapper.addMappings(new ProfileDtoMap());
        modelMapper.addMappings(new UserMap());
    }

    public <T,U> U convert(final T source, Class<U> destinationType) {
        return modelMapper.map(source,destinationType);
    }
}
