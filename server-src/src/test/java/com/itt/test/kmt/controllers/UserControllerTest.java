
package com.itt.test.kmt.controllers;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itt.kmt.models.Role;
import com.itt.kmt.models.User;
import com.itt.kmt.repositories.UserRepository;
import com.itt.kmt.response.models.ResponseMsg;
import com.itt.kmt.services.UserService;
import com.itt.test_data.RoleTestDataRepository;
import com.itt.test_data.TestDataRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class UserControllerTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebMvc

/** The Constant log. */
@Slf4j
public class UserControllerTest extends AbstractShiroTest {

    /** The mock mvc. */
    @Autowired
    private MockMvc mockMvc;

    /** The user service. */
    @MockBean
    private UserService userService;

    /** The user repository. */
    @MockBean
    private UserRepository userRepository;

    /** The test data repository. */
    @Autowired
    private TestDataRepository testDataRepository;

    /** The role test data repository. */
    @Autowired
    private RoleTestDataRepository roleTestDataRepository;

    /** The content type. */
    private MediaType contentType = new MediaType("application", "json", Charset.forName("UTF-8"));

    /** The ctx. */
    @Autowired
    private WebApplicationContext ctx;

    /** The subject under test. */
    private Subject subjectUnderTest;

    /** The mock session. */
    private MockHttpSession mockSession;

    /** The wac. */
    @Autowired
    private WebApplicationContext wac;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp()
        throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                                 .build();

        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                                      .build();
        DefaultWebSecurityManager securityManger = mock(DefaultWebSecurityManager.class, RETURNS_DEEP_STUBS);
        ThreadContext.bind(securityManger);
        // 1. Create a mock authenticated Subject instance for the test to run:
        subjectUnderTest = new Subject.Builder((DefaultWebSecurityManager) getSecurityManager()).buildSubject();

        mockSession = new MockHttpSession(
            ctx.getServletContext(), subjectUnderTest.getSession()
                                                     .getId()
                                                     .toString());
        // 2. Bind the subject to the current thread:
        setSubject(subjectUnderTest);
    }
    /**
     * Gets the user.
     *
     * @return the user
     * @throws Exception the exception
     */
    @Test
    public void getUser()
        throws Exception {

        // Arrange
        User user = testDataRepository.getUsers()
                                      .get("user-1");
        when(userRepository.findOne(user.getId())).thenReturn(user);
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

        verify(userRepository, times(1)).findOne(user.getId());
    }

    /**
     * Gets the all users.
     *
     * @return the all users
     * @throws Exception the exception
     */
    @Test
    public void getAllUsers()
        throws Exception {

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

        resultActions.andExpect(status().isOk())
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

    /**
     * Gets the all users.
     *
     * @return the all users
     * @throws Exception the exception
     */
    @Test
    public void getAllApprovers()
        throws Exception {

        User userOne = testDataRepository.getUsers()
                                         .get("user-1");
        User userTwo = testDataRepository.getUsers()
                                         .get("user-2");

        ArrayList<User> admins = new ArrayList<User>();
        admins.add(userOne);
        ArrayList<User> managers = new ArrayList<User>();
        managers.add(userTwo);
        ArrayList<User> adminsAndManagers = new ArrayList<User>();
        adminsAndManagers.addAll(admins);
        adminsAndManagers.addAll(managers);
        ArrayList<String> roles = new ArrayList<String>();
        roles.add("admin");
        roles.add("manager");

        when(userService.getAllActiveUsersByRoles(roles)).thenReturn(adminsAndManagers);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/approvers")
                                                              .accept(MediaType.APPLICATION_JSON);

        // Act
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        resultActions.andExpect(status().isOk())
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
        verify(userService, times(1)).getAllActiveUsersByRoles(roles);
    }

    /**
     * Adds the.
     *
     * @throws Exception the exception
     */
    @Test
    public void add()
        throws Exception {

        // Arrange
        User user = testDataRepository.getUsers()
                                      .get("user-1");
        ResponseMsg postResponseMsg = new ResponseMsg(true, "added successfully");

        when(userService.save(user)).thenReturn(user);
        HashMap<String, User> map = new HashMap<String, User>();
        map.put("user", user);

        String content = new ObjectMapper().writeValueAsString(map);
        // Act
        ResultActions resultActions = null;

        resultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(content));

        // Assert
        resultActions.andExpect(status().isOk())
                     .andExpect(
                         content().contentType(new MediaType("application", "json", Charset.forName("UTF-8"))))
                     .andExpect(jsonPath("$.success.message", is(postResponseMsg.getMessage())))
                     .andExpect(jsonPath("$.success.status", is(postResponseMsg.getStatus())));

        verify(userService, times(1)).save(user);
    }

    /**
     * Update user.
     *
     * @throws Exception the exception
     */
    @Test
    public void updateUser()
        throws Exception {

        // Arrange
        User user = testDataRepository.getUsers()
                                      .get("user-1");
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        user.setFirstName(firstName);
        user.setLastName(lastName);

        when(userService.updateUser(user, user.getId())).thenReturn(user);
        HashMap<String, User> map = new HashMap<String, User>();
        map.put("user", user);

        String content = new ObjectMapper().writeValueAsString(map);
        // Act
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.put("/users/" + user.getId())
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(content));

        ResponseMsg updateResponseMsg = new ResponseMsg(true, "updated successfully");
        // Assert
        resultActions.andExpect(status().isOk())
                     .andExpect(content().contentType(contentType))
                     .andExpect(jsonPath("$.success.message", is(updateResponseMsg.getMessage())))
                     .andExpect(jsonPath("$.success.status", is(updateResponseMsg.getStatus())));

        verify(userService, times(1)).updateUser(user, user.getId());
    }
    /**
     * Update status of the user.
     *
     * @throws Exception the exception
     */
    @Test
    public void changeUserStatus() throws Exception {
        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-1");

        user.setActive(true);

        when(userService.changeUserStatus(user.getId(), user.isActive())).thenReturn(user);

        String content = new ObjectMapper().writeValueAsString(null);
        // Act
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/users/state/" + user.getId() 
                    + "/" + user.isActive()).
                   contentType(MediaType.APPLICATION_JSON).content(content));

        ResponseMsg activateResponseMsg = new ResponseMsg(true, "activated successfully");
        // Assert
        resultActions.andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.success.message", is(activateResponseMsg.getMessage())))
        .andExpect(jsonPath("$.success.status", is(activateResponseMsg.getStatus())));

        verify(userService, times(1)).changeUserStatus(user.getId(), true);
    }
    /**
     * Update status of the user.
     *
     * @throws Exception the exception
     */
    @Test
    public void activateActiveUser() throws Exception {
        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-1");

        user.setActive(true);

        when(userService.changeUserStatus(user.getId(), user.isActive()))
            .thenThrow(new RuntimeException("Operation not permitted"));

        String content = new ObjectMapper().writeValueAsString(null);
        // Act
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/users/state/" + user.getId() 
                    + "/" + user.isActive()).
                   contentType(MediaType.APPLICATION_JSON).content(content));

        ResponseMsg activateResponseMsg = new ResponseMsg(false, "Bad Request");
        // Assert
        resultActions.andExpect(status().isBadRequest())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.success.message", is(activateResponseMsg.getMessage())))
        .andExpect(jsonPath("$.success.status", is(activateResponseMsg.getStatus())));

        verify(userService, times(1)).changeUserStatus(user.getId(), true);
    }
    /**
     * Update status of the user.
     *
     * @throws Exception the exception
     */
    @Test
    public void changeUserStatusToDeactivate() throws Exception {
        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-3");

        user.setActive(false);

        when(userService.changeUserStatus(user.getId(), false)).thenReturn(user);

        String content = new ObjectMapper().writeValueAsString(null);
        // Act
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/users/state/" + user.getId()
                    + "/" + user.isActive()).
                   contentType(MediaType.APPLICATION_JSON).content(content));

        ResponseMsg activateResponseMsg = new ResponseMsg(true, "deactivated successfully");
        // Assert
        resultActions.andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.success.message", is(activateResponseMsg.getMessage())))
        .andExpect(jsonPath("$.success.status", is(activateResponseMsg.getStatus())));

        verify(userService, times(1)).changeUserStatus(user.getId(), user.isActive());
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
        ResultActions resultActions = null;

        resultActions = mockMvc.perform(requestBuilder);

        resultActions.andExpect(status().isOk())
                     .andExpect(content().contentType(contentType))
                     .andExpect(jsonPath("$.roles", Matchers.hasSize(2)))
                     .andExpect(jsonPath("$.roles[0].id", is(role1.getId())))
                     .andExpect(jsonPath("$.roles[0].role", is(role1.getRole())))
                     .andExpect(jsonPath("$.roles[1].id", is(role2.getId())))
                     .andExpect(jsonPath("$.roles[1].role", is(role2.getRole())));
        verify(userService, times(1)).getUserRoles();
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {

    }
}
