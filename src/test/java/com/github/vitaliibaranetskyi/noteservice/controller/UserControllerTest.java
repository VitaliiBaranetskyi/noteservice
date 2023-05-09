package com.github.vitaliibaranetskyi.noteservice.controller;

import com.github.vitaliibaranetskyi.noteservice.dto.UserDTO;
import com.github.vitaliibaranetskyi.noteservice.model.Role;
import com.github.vitaliibaranetskyi.noteservice.model.User;
import com.github.vitaliibaranetskyi.noteservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private UserDTO userDTO;
    private List<UserDTO> userListDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        Role role = new Role("USER");
        role.setId("1");

        userDTO = new UserDTO();
        userDTO.setId("1");
        userDTO.setUsername("user1");
        userDTO.setPassword("password1");
        userDTO.setRole(role);

        userListDTO = new ArrayList<>();
        userListDTO.add(userDTO);
    }

    @Test
    @DisplayName("Test Get All Users - Should return OK")
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(userListDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(userDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value(userDTO.getUsername()));

    }

    @Test
    @DisplayName("Test Get User By Id - Should return OK")
    void testGetUserById() throws Exception {
        when(userService.getUserById("1")).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userDTO.getUsername()));
    }


    @Test
    @DisplayName("Test Create User - Should return CREATED")
    void testCreateUser() throws Exception {
        when(userService.createUser(any())).thenReturn(userDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"user1\", \"password\": \"password1\"}"))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userDTO.getUsername()));
    }


    @Test
    @DisplayName("Test Update User - Should return OK")
    void testUpdateUser() throws Exception {
        Role newRole = new Role("ADMIN");
        newRole.setId("2");
        UserDTO updatedUserDTO = new UserDTO();
        updatedUserDTO.setId("1");
        updatedUserDTO.setUsername("updatedUser1");
        updatedUserDTO.setPassword("updatedPassword1");
        updatedUserDTO.setRole(newRole);

        User currentUser = new User();
        currentUser.setId("2");
        currentUser.setUsername("admin");
        currentUser.setPassword("adminPassword");
        currentUser.setRole(newRole);

        when(userService.updateUser(anyString(), any(UserDTO.class), any(User.class))).thenReturn(updatedUserDTO);

        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser.getUsername(), currentUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"updatedUser1\", \"password\": \"updatedPassword1\", \"role\": {\"name\": \"ADMIN\"}}"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(updatedUserDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(updatedUserDTO.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(updatedUserDTO.getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role.name").value(updatedUserDTO.getRole().getName()));
    }

    @Test
    @DisplayName("Test Delete User - Should return NO CONTENT")
    void testDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/user/1"))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Test Create User With Invalid Input - Should return BAD REQUEST")
    void testCreateUserWithInvalidInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"\", \"password\": \"password1\"}"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
}
