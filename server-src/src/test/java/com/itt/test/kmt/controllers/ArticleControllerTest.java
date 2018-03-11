package com.itt.test.kmt.controllers;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itt.kmt.models.Approve;
import com.itt.kmt.models.Article;
import com.itt.kmt.models.ArticleType;
import com.itt.kmt.response.models.ResponseMsg;
import com.itt.kmt.services.ArticleService;
import com.itt.test_data.ArticleTestDataRepository;
import com.itt.test_data.ArticleTypeTestDataRepository;
import com.itt.utility.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;

import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Class ArticleControllerTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebMvc

/** The Constant log. */
@Slf4j
public class ArticleControllerTest extends AbstractShiroTest {

    /** The mock mvc. */
    @Autowired
    private MockMvc mockMvc;


    /** The article type test data repository. */
    @Autowired
    private ArticleTypeTestDataRepository articleTypeTestDataRepository;

    /** The article test data repository. */
    @Autowired
    private ArticleTestDataRepository articleTestDataRepository;

    /** The role test data repository. */
    @MockBean
    private ArticleService articleService;

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
        DefaultWebSecurityManager securityManger = Mockito.mock(DefaultWebSecurityManager.class, RETURNS_DEEP_STUBS);
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
     * Adds the.
     *
     * @throws Exception the exception
     */
    @Test
    public void add()
            throws Exception {

        // Arrange
        Article article = articleTestDataRepository.getArticles()
                .get("article-1");
        ResponseMsg postResponseMsg = new ResponseMsg(true, Constants.ARTICLE_CREATED_MESSAGE);

        when(articleService.save(article)).thenReturn(article);
        HashMap<String, Article> map = new HashMap<String, Article>();
        map.put("article", article);

        String content = new ObjectMapper().writeValueAsString(map);
        // Act
        ResultActions resultActions = null;

        resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        // Assert
        resultActions.andExpect(status().isOk())
        .andExpect(
                content().contentType(new MediaType("application", "json", Charset.forName("UTF-8"))))
        .andExpect(jsonPath("$.success.message", is(postResponseMsg.getMessage())))
        .andExpect(jsonPath("$.success.status", is(postResponseMsg.getStatus())));
    }

    @Test
    public void getAllArticleTypes() throws Exception {
        // Arrange
        ArticleType articleType1 = articleTypeTestDataRepository.getTypes()
                .get("articleType-1");
        ArticleType articleType2 = articleTypeTestDataRepository.getTypes()
                .get("articleType-2");
        List<ArticleType> types = new ArrayList<ArticleType>();

        types.add(articleType1);
        types.add(articleType2);

        when(articleService.getArticleTypes()).thenReturn(types);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/articles/types")
                .accept(MediaType.APPLICATION_JSON);
        ResultActions resultActions = null;

        resultActions = mockMvc.perform(requestBuilder);

        resultActions.andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.types", Matchers.hasSize(2)))
        .andExpect(jsonPath("$.types[0].id", is(articleType1.getId())))
        .andExpect(jsonPath("$.types[0].type", is(articleType1.getType())))
        .andExpect(jsonPath("$.types[1].id", is(articleType2.getId())))
        .andExpect(jsonPath("$.types[1].type", is(articleType2.getType())));
    }

    @Test
    public void updateArticleTest() throws Exception {

        // Arrange
        Article article = articleTestDataRepository.getArticles()
                .get("article-3");
        article.setVersion(article.getVersion() + 1);
        ResponseMsg putResponseMsg = new ResponseMsg(true, "Modifications have been saved successfully");

        when(articleService.updateArticle(article.getId(), article)).thenReturn(article);
        HashMap<String, Article> map = new HashMap<String, Article>();
        map.put("article", article);

        // Act
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/articles/" + article.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(map)));

        // Assert
        resultActions.andExpect(status().isOk())
        .andExpect(
                content().contentType(new MediaType("application", "json", Charset.forName("UTF-8"))))
        .andExpect(jsonPath("$.success.message", is(putResponseMsg.getMessage())))
        .andExpect(jsonPath("$.success.status", is(putResponseMsg.getStatus())));
        verify(articleService, times(1)).updateArticle(article.getId(), article);
    }


    @Test
    public void getArticleByIdTest() throws Exception {
        // Arrange
        Article article = articleTestDataRepository.getArticles()
                .get("article-1");

        String jwtToken = "testToken";
        when(articleService.getArticleById(article.getId(), jwtToken)).thenReturn(article);

        // Act
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/articles/" + article.getId())
                .header(Constants.AUTHORIZATION, jwtToken)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andExpect(status().isOk())
        .andExpect(
                content().contentType(new MediaType("application", "json", Charset.forName("UTF-8"))))
        .andExpect(jsonPath("$.article.id", is(article.getId())));
    }
    
    @Test
    public void getArticlesTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/articles").param("size", "10")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
    }

    @Test
    public void deleteArticleTest() throws Exception {
        // Arrange
        Article article = articleTestDataRepository.getArticles()
                .get("article-4");
        String jwtToken = "testToken";
        ResponseMsg deleteResponseMsg = new ResponseMsg(true, Constants.ARTICLE_DELETED_MESSAGE);

        doNothing().when(articleService).delete(article.getId(), jwtToken);

        // Act
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/articles/" + article.getId())
                        .header(Constants.AUTHORIZATION, jwtToken)
                        .contentType(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andExpect(status().isOk())
                .andExpect(
                        content().contentType(new MediaType("application", "json", Charset.forName("UTF-8"))))
                .andExpect(jsonPath("$.success.message", is(deleteResponseMsg.getMessage())))
                .andExpect(jsonPath("$.success.status", is(deleteResponseMsg.getStatus())));
        verify(articleService, times(1)).delete(article.getId(), jwtToken);
    }

    @Test
    public void approveArticleTest() throws Exception {
        // Arrange
        Article article = articleTestDataRepository.getArticles()
                .get("article-3");
        String jwtToken = "testToken";
        ResponseMsg putResponseMsg = new ResponseMsg(true, Constants.ARTICLE_APPROVED_MESSAGE);
        Approve approve = new Approve();
        approve.setApproved(true);
        approve.setComment("ap comment");

        when(articleService.articleApproval(approve, article.getId(), jwtToken)).thenReturn(true);

        HashMap<String, Approve> map = new HashMap<String, Approve>();
        map.put("approve", approve);

        String content = new ObjectMapper().writeValueAsString(map);

        // Act
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/articles/approve/" + article.getId())
                        .header(Constants.AUTHORIZATION, jwtToken)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andExpect(status().isOk())
                .andExpect(
                        content().contentType(new MediaType("application", "json", Charset.forName("UTF-8"))))
                .andExpect(jsonPath("$.success.message", is(putResponseMsg.getMessage())))
                .andExpect(jsonPath("$.success.status", is(putResponseMsg.getStatus())));
        verify(articleService, times(1)).articleApproval(approve, article.getId(), jwtToken);
    }

    @Test
    public void commentArticleTest() throws Exception {
        // Arrange
        Article article = articleTestDataRepository.getArticles()
                .get("article-3");
        String jwtToken = "testToken";
        ResponseMsg putResponseMsg = new ResponseMsg(true, Constants.ARTICLE_POSTED_COMMENT);
        Approve approve = new Approve();
        approve.setApproved(false);
        approve.setComment("test comment");

        when(articleService.articleApproval(approve, article.getId(), jwtToken)).thenReturn(false);

        HashMap<String, Approve> map = new HashMap<String, Approve>();
        map.put("approve", approve);

        String content = new ObjectMapper().writeValueAsString(map);

        // Act
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/articles/approve/" + article.getId())
                        .header(Constants.AUTHORIZATION, jwtToken)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andExpect(status().isOk())
                .andExpect(
                        content().contentType(new MediaType("application", "json", Charset.forName("UTF-8"))))
                .andExpect(jsonPath("$.success.message", is(putResponseMsg.getMessage())))
                .andExpect(jsonPath("$.success.status", is(putResponseMsg.getStatus())));
        verify(articleService, times(1)).articleApproval(approve, article.getId(), jwtToken);
    }
    /**
     * Tear down.
     */
    @After
    public void tearDown() {

    }
}
