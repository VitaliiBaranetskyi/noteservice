package com.github.vitaliibaranetskyi.noteservice.service;

import com.github.vitaliibaranetskyi.noteservice.dto.UserDTO;
import com.github.vitaliibaranetskyi.noteservice.exception.UserAlreadyExistsException;
import com.github.vitaliibaranetskyi.noteservice.exception.UserNotFoundException;
import com.github.vitaliibaranetskyi.noteservice.mapper.UserMapper;
import com.github.vitaliibaranetskyi.noteservice.model.Role;
import com.github.vitaliibaranetskyi.noteservice.model.User;
import com.github.vitaliibaranetskyi.noteservice.repository.RoleRepository;
import com.github.vitaliibaranetskyi.noteservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return userMapper.toDto(user.get());
        } else {
            throw new UserNotFoundException("User with id " + id + " not found.");
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user;
        } else {
            throw new UserNotFoundException("User with username " + username + " not found.");
        }
    }

    @Override
    public UserDTO createUser(UserDTO userDto) {
        Optional<User> existingUser = userRepository.findByUsername(userDto.getUsername());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User with username " + userDto.getUsername() + " already exists.");
        } else {
            String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
            User user = userMapper.toEntity(userDto);
            user.setPassword(encryptedPassword);
            Role userRole = roleRepository.findByName("USER");
            if (userRole == null) {
                userRole = new Role();
                userRole.setName("USER");
                roleRepository.save(userRole);
            }
            user.setRole(userRole);
            User savedUser = userRepository.save(user);
            return userMapper.toDto(savedUser);
        }
    }

    @Override
    public UserDTO updateUser(String id, UserDTO userDto, User currentUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            if (userDto.getUsername() != null) {
                updatedUser.setUsername(userDto.getUsername());
            }
            if (userDto.getPassword() != null) {
                updatedUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
            if (currentUser != null && currentUser.getRole().getName().equals("ADMIN")) {
                if(userDto.getRole() != null) {
                    Role role = roleRepository.findByName(userDto.getRole().getName());
                    if (role != null) {
                        updatedUser.setRole(role);
                    }
                }
            } else {
                userDto.setRole(updatedUser.getRole());
            }
            User savedUser = userRepository.save(updatedUser);
            return userMapper.toDto(savedUser);
        } else {
            throw new UserNotFoundException("User with id " + id + " not found.");
        }
    }


    @Override
    public void deleteUser(String id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            userRepository.delete(existingUser.get());
        } else {
            throw new UserNotFoundException("User with id " + id + " not found.");
        }
    }
}

