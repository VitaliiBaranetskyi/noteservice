package com.github.vitaliibaranetskyi.noteservice.mapper;

import com.github.vitaliibaranetskyi.noteservice.dto.UserDTO;
import com.github.vitaliibaranetskyi.noteservice.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User user);

    User toEntity(UserDTO userDTO);

}
