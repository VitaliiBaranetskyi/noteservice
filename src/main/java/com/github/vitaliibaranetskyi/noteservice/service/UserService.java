package com.github.vitaliibaranetskyi.noteservice.service;

import com.github.vitaliibaranetskyi.noteservice.dto.UserDTO;
import com.github.vitaliibaranetskyi.noteservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(String id);
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(String id, UserDTO userDTO, User currentUser);
    void deleteUser(String id);
    Optional<User> findByUsername(String username);
}
