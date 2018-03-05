
package com.itt.test.kmt.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itt.kmt.models.User;
import com.itt.kmt.repositories.UserRepository;
import com.itt.kmt.response.models.LoginResponseMsg;
import com.itt.kmt.services.UserService;
import com.itt.test_data.RoleTestDataRepository;
import com.itt.test_data.TestDataRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class LoginControllerTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebMvc

/** The Constant log. */

/** The Constant log. */
@Slf4j
public class LoginControllerTest extends AbstractShiroTest {

    /** The wac. */
    @Autowired
    private WebApplicationContext wac;

    /** The mock mvc. */
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

    /** The subject under test. */
    private Subject subjectUnderTest;

    /** The mock session. */
    private MockHttpSession mockSession;

    /** The ctx. */
    @Autowired
    private WebApplicationContext ctx;

    /** The content type. */
    private MediaType contentType = new MediaType("application", "json", Charset.forName("UTF-8"));

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
        // final Authenticate bean = (Authenticate)ctx.getBean("authenticate");
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
     * Login.
     *
     * @throws Exception the exception
     */
    @Test
    public void login()
        throws Exception {

        User user = testDataRepository.getUsers()
                                      .get("user-1");
        LoginResponseMsg loginResponseMsg = new LoginResponseMsg(
            true, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MTk4NDY4NzAsImVtYWlsIjoiYWFhMUBnb" 
                + "WFpbC5jb20ifQ.MY63G1AgD5LOE8loGIGYA_K9atPcUtF5R2DRZwkbdj4");
        loginResponseMsg.setUser(user);

        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);

        String content = new ObjectMapper().writeValueAsString(user);
        ResultActions resultActions = null;

        // Act
        MockHttpServletRequestBuilder cc = MockMvcRequestBuilders.post("/login")
                                                                 .contentType(MediaType.APPLICATION_JSON)
                                                                 .content(content);

        resultActions = mockMvc.perform(cc);

        // to-do: Need to assert with status and response
        resultActions.andExpect(
            MockMvcResultMatchers.content()
                                 .contentType(new MediaType("application", "json", Charset.forName("UTF-8"))))
                     .andExpect(status().isBadRequest());
    }

    /**
     * unauthorizedLogin.
     *
     * @throws Exception the exception
     */
    @Test
    public void invalidEmail()
        throws Exception {

        User user = testDataRepository.getUsers()
                                      .get("user-1");

        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);

        user.setEmail("invalid@email.com");
        String content = new ObjectMapper().writeValueAsString(user);
        ResultActions resultActions = null;

        // Act
        MockHttpServletRequestBuilder cc = MockMvcRequestBuilders.post("/login")
                                                                 .contentType(MediaType.APPLICATION_JSON)
                                                                 .content(content);

        resultActions = mockMvc.perform(cc);

        // to-do: Need to assert with status and response
        resultActions.andExpect(
            MockMvcResultMatchers.content()
                                 .contentType(new MediaType("application", "json", Charset.forName("UTF-8"))));
    }

    /**
     * unauthorizedLogin.
     *
     * @throws Exception the exception
     */
    @Test
    public void invalidPassword()
        throws Exception {

        User user = testDataRepository.getUsers()
                                      .get("user-1");

        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);

        user.setPassword("invalidpassword");
        String content = new ObjectMapper().writeValueAsString(user);
        ResultActions resultActions = null;

        // Act
        MockHttpServletRequestBuilder cc = MockMvcRequestBuilders.post("/login")
                                                                 .contentType(MediaType.APPLICATION_JSON)
                                                                 .content(content);

        resultActions = mockMvc.perform(cc);

        // to-do: Need to assert with status and response
        resultActions.andExpect(
            MockMvcResultMatchers.content()
                                 .contentType(new MediaType("application", "json", Charset.forName("UTF-8"))));
    }

    /**
     * Unauthorized.
     *
     * @throws Exception the exception
     */
    @Test
    public void unauthorized()
        throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/401")
                                                              .accept(MediaType.APPLICATION_JSON);

        // Act
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        resultActions.andExpect(status().isUnauthorized())
                     .andExpect(content().contentType(contentType));

    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {

    }
}
