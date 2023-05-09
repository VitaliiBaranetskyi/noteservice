package com.github.vitaliibaranetskyi.noteservice.dto;

import com.github.vitaliibaranetskyi.noteservice.model.Role;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserDTO {
    private String id;
    @NotNull
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9_]+", message = "Invalid username")
    private String username;
    @NotBlank
    @Size(min=8, max=30)
    private String password;
    private Role role;

}
