package com.github.vitaliibaranetskyi.noteservice.dto;

import com.github.vitaliibaranetskyi.noteservice.model.Role;
import lombok.*;

@Data
public class UserDTO {
    private String id;
    private String username;
    private String password;
    private Role role;

}
