package com.gold.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityMapper {

    private ModelMapper mapper;

    @Autowired
    public EntityMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public <T, S> S convertToDto(T source, Class<S> destination) {
        return mapper.map(source, destination);
    }

    public <T, S> S convertToEntity(T source, Class<S> destination) {
        return mapper.map(source, destination);
    }

    public <T, S> void convertToEntity(T source, S destination) {
        mapper.map(source, destination);
    }

    public <T, S> List<S> convertToDto(List<T> entities, Class<S> destination) {
        return entities.stream()
                .map(entity -> convertToDto(entity, destination))
                .collect(Collectors.toList());
    }
}
