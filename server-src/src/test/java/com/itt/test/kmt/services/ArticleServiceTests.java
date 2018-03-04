package com.itt.test.kmt.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.itt.kmt.models.Article;
import com.itt.kmt.models.User;
import com.itt.kmt.repositories.ArticleRepository;
import com.itt.kmt.repositories.RoleRepository;
import com.itt.kmt.services.ArticleService;
import com.itt.test_category.ServicesTests;
import com.itt.test_data.RoleTestDataRepository;
import com.itt.test_data.TestDataRepository;

@Category(ServicesTests.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceTests {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private TestDataRepository testDataRepository;

    @Autowired
    private RoleTestDataRepository roleTestDataRepository;

    @MockBean
    private ArticleRepository articleRepository;

    @MockBean
    private RoleRepository roleRepository;

    @Before
    public final void setUp() {

    }

    @Test
    public final void getArticleByIdTest() {

        // Arrange
       Article article1 = testDataRepository.getArticles()
                .get("article");
        when(articleRepository.findOne(article1.getId())).thenReturn(article1);

        // Act
        Article article = articleService.getArticleById(article1.getId());

        // Assert
        assertEquals(article.getId(), article1.getId());
        verify(articleRepository, times(1)).findOne(article1.getId());
    }

    @Test(expected=RuntimeException.class)
    public final void getArticleByIdNoArticleFoundTest() {

        // Arrange
        when(articleRepository.findOne(null)).thenReturn(null);

        // Act
        Article article = articleService.getArticleById(null);

        // Assert
        verify(articleRepository, times(1)).findOne(null);
    }

}
