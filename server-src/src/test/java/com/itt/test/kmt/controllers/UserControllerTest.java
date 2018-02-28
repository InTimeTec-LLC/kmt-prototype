package com.itt.test.kmt.controllers;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.itt.kmt.models.Role;
import com.itt.test_data.RoleTestDataRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itt.kmt.models.User;
import com.itt.kmt.response.models.ResponseMsg;
import com.itt.kmt.services.UserService;
import com.itt.test_data.TestDataRepository;

import lombok.extern.slf4j.Slf4j;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebMvc
@Slf4j
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private TestDataRepository testDataRepository;

    @Autowired
    private RoleTestDataRepository roleTestDataRepository;

    private MediaType contentType = new MediaType("application", "json", Charset.forName("UTF-8"));

    @Test
    public void getUser() throws Exception {
        // Arrange
        User user = testDataRepository.getUsers().get("user-1");
        when(userService.getUserById(user.getId())).thenReturn(user);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/" + user.getId())
                .accept(MediaType.APPLICATION_JSON);

        // Act
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        // Assert
        resultActions.andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.user.firstName", is(user.getFirstName())))
        .andExpect(jsonPath("$.user.lastName", is(user.getLastName())))
        .andExpect(jsonPath("$.user.email", is(user.getEmail())))
        .andExpect(jsonPath("$.user.userRole", is(user.getUserRole())));

        verify(userService, times(1)).getUserById(user.getId());
   }

    @Test
    public void getAllUsers() throws Exception {
        User userOne = testDataRepository.getUsers()
                .get("user-1");
        User userTwo = testDataRepository.getUsers()
                .get("user-2");

        ArrayList<User> users = new ArrayList<User>();
        users.add(userOne);
        users.add(userTwo);

        when(userService.getAllUsers()).thenReturn(users);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users")
                .accept(MediaType.APPLICATION_JSON);

        // Act
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        resultActions
        .andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.users", Matchers.hasSize(2)))
        .andExpect(jsonPath("$.users[0].id", is(userOne.getId())))
        .andExpect(jsonPath("$.users[0].firstName", is(userOne.getFirstName())))
        .andExpect(jsonPath("$.users[0].lastName", is(userOne.getLastName())))
        .andExpect(jsonPath("$.users[0].email", is(userOne.getEmail())))
        .andExpect(jsonPath("$.users[0].userRole", is(userOne.getUserRole())))
        .andExpect(jsonPath("$.users[1].id", is(userTwo.getId())))
        .andExpect(jsonPath("$.users[1].firstName", is(userTwo.getFirstName())))
        .andExpect(jsonPath("$.users[1].lastName", is(userTwo.getLastName())))
        .andExpect(jsonPath("$.users[1].email", is(userTwo.getEmail())))
        .andExpect(jsonPath("$.users[1].userRole", is(userTwo.getUserRole())));
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void add() throws Exception {
        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-1");
        ResponseMsg postResponseMsg = new ResponseMsg(true, "user added successfully");

        when(userService.save(user)).thenReturn(user);

        String content = new ObjectMapper().writeValueAsString(user);
        // Act
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/users").
                   contentType(MediaType.APPLICATION_JSON).content(content));

        // Assert
        resultActions.andExpect(status().isOk())
        .andExpect(content().contentType(new MediaType("application", "json", Charset.forName("UTF-8"))))
        .andExpect(jsonPath("$.success.message", is(postResponseMsg.getMessage())))
        .andExpect(jsonPath("$.success.status", is(postResponseMsg.getStatus())));

        verify(userService, times(1)).save(user);
    }

    @Test
    public void updateUser() throws Exception {
        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-1");
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        user.setFirstName(firstName);
        user.setLastName(lastName);

        when(userService.updateUser(user, user.getId())).thenReturn(user);

        String content = new ObjectMapper().writeValueAsString(user);
        // Act
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/users/" + user.getId()).
                   contentType(MediaType.APPLICATION_JSON).content(content));

        ResponseMsg updateResponseMsg = new ResponseMsg(true, "user updated successfully");
        // Assert
        resultActions.andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.success.message", is(updateResponseMsg.getMessage())))
        .andExpect(jsonPath("$.success.status", is(updateResponseMsg.getStatus())));

        verify(userService, times(1)).updateUser(user, user.getId());
    }

    @Test
    public void deleteUser() throws Exception {
        User user = testDataRepository.getUsers().get("user-1");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + user.getId()).
                   contentType(MediaType.APPLICATION_JSON));
        ResponseMsg deleteResponseMsg = new ResponseMsg(true, "user deleted successfully");

        resultActions.andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.success.message", is(deleteResponseMsg.getMessage())))
        .andExpect(jsonPath("$.success.status", is(deleteResponseMsg.getStatus())));

        verify(userService, times(1)).deleteUserById(user.getId());
    }

    @Test
    public void getAllRoles() throws Exception {
        // Arrange
        Role role1 = roleTestDataRepository.getRoles()
                .get("role-1");
        Role role2 = roleTestDataRepository.getRoles()
                .get("role-1");
        List<Role> roles = new ArrayList<Role>();

        roles.add(role1);
        roles.add(role2);

        when(userService.getUserRoles()).thenReturn(roles);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/roles")
                .accept(MediaType.APPLICATION_JSON);

        // Act
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.roles", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.roles[0].id", is(role1.getId())))
                .andExpect(jsonPath("$.roles[0].role", is(role1.getRole())))
                .andExpect(jsonPath("$.roles[1].id", is(role2.getId())))
                .andExpect(jsonPath("$.roles[1].role", is(role2.getRole())));
        verify(userService, times(1)).getUserRoles();
    }
    
    @After
    public void tearDown() {

    }
}