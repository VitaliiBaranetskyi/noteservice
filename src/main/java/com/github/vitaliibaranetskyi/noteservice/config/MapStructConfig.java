package com.github.vitaliibaranetskyi.noteservice.config;

import com.github.vitaliibaranetskyi.noteservice.mapper.NoteMapper;
import com.github.vitaliibaranetskyi.noteservice.mapper.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapStructConfig {
    @Bean
    public UserMapper userMapper() {
        return Mappers.getMapper(UserMapper.class);
    }

    @Bean
    public NoteMapper noteMapper() {
        return Mappers.getMapper(NoteMapper.class);
    }
}
