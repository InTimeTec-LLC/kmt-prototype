package com.itt.kmt.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.HashMap;

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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itt.kmt.model.Article;
import com.itt.kmt.service.ArticleService;
import com.itt.test_data.TestDataRepository;

import lombok.extern.slf4j.Slf4j;
/**
 * This class is responsible for exposing REST APis for Article.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebMvc
@Slf4j
public class ArticleControllerTest {

    @Autowired
    private MockMvc mvc;
    /**
     * Service implementation for DB entity that provides retrieval methods.
     */
    @MockBean
    private ArticleService articleService;

    @Autowired
    private TestDataRepository testDataRepository;

    private MediaType contentType = new MediaType("application", "json", Charset.forName("UTF-8"));
    
    private final static String URL = "/articles";
    /**
     * TEST for Article retrieval by id.
     * @throws Exception 
     *
     */
    @Test
    public void getArticleByIdTest() throws Exception{
        // Arrange
        HashMap<String, Article> articles = testDataRepository.getArticles();
        Article article1 = testDataRepository.getArticles().get("article-1");
        when(articleService.getArticleById(article1.getId())).thenReturn(article1);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/articles/1").accept(MediaType.APPLICATION_JSON);

     // Act
        ResultActions resultActions = mvc.perform(requestBuilder);
        assertEquals(resultActions.andReturn().getResponse().getContentAsString(), "Test");
     // Assert
        resultActions.andExpect(status().isOk())
                     .andExpect(content().contentType(new MediaType("application", "json", Charset.forName("UTF-8"))))
                     .andExpect(jsonPath("$.userId", is(article1.getUserId())));
        verify(articleService,times(1)).getArticleById(article1.getId());
    }

    /**
     * Test for adding a new Article.
     * @throws Exception 
     */
   
    
    @Test
    public void addArticleTest() throws Exception{
 // Arrange
    Article article4= new Article();
    article4.setArticle("<html>article insert test</html>");
    article4.setName("article4");
    article4.setIsRestricted("N");
    when(articleService.save(article4)).thenReturn(article4);
    MockHttpServletRequestBuilder requestBuilder = post("/articles/").accept(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(article4));
    //Act
    ResultActions resultActions = mvc.perform(requestBuilder);
    //Assert
    resultActions.andExpect(status().isOk())
    .andExpect(content().contentType(contentType))
    .andExpect(jsonPath("$.name",is(article4.getName())));
    verify(articleService,times(1)).save(article4);
}
    /**
     * REST API for Article retrieval by Article name.
     *

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public final Article getArticleByName(@RequestParam(value = "name") final String name) {
        return articleService.getArticleByName(name);
    }
    */
     
@Test  
public void getArticleByNameTest() throws Exception{
      // Arrange
         Article article1 = testDataRepository.getArticles().get("article-1");
         when(articleService.getArticleByName(article1.getName())).thenReturn(article1);
         //Act
         ResultActions resultActions = mvc.perform(get("/articles/search").accept(MediaType.APPLICATION_JSON));
         //Assert
         resultActions.andExpect(content().contentType(contentType))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.name", is(article1.getName())));
         verify(articleService,times(1)).getArticleByName(article1.getName());
     }
    /**
     * REST API for Article retrieval in pages.
     *
     * @param pageNumber Page that needs to be retrieved.
     * @return Page<Article> a page of Article instances
    
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    public final Page<Article> getArticleList(@RequestParam(value = "pageNumber", defaultValue = "1")
    final int pageNumber) {
        return articleService.getPage(pageNumber);
    }
 */
    /**
     * REST API for Article Deletion.
     *
     
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public final Article delete(@PathVariable(value = "id") final String id) {
        return articleService.getArticleById(id);
    }
*/
    /**
     * REST API for updating Article.
     *

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public final Article updateArticle(@PathVariable(value = "id") final String id,
            @RequestBody final Article article) {
        return articleService.updateArticle(id, article.getArticle());
    }
     */
}