package com.itt.test.kmt.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.itt.kmt.models.Article;
import com.itt.kmt.models.ArticleType;
import com.itt.kmt.models.User;
import com.itt.kmt.repositories.ArticleRepository;
import com.itt.kmt.repositories.ArticleTypeRepository;
import com.itt.kmt.services.ArticleService;
import com.itt.kmt.services.UserService;
import com.itt.test_category.ServicesTests;
import com.itt.test_data.ArticleTestDataRepository;
import com.itt.test_data.ArticleTypeTestDataRepository;
import com.itt.test_data.TestDataRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@Category(ServicesTests.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceTests {


    /** The article type test data repository. */
    @Autowired
    private ArticleService articleService;

    /** The User service. */
    @MockBean
    private UserService userService;

    /** The article type test data repository. */
    @Autowired
    private ArticleTypeTestDataRepository articleTypeTestDataRepository;

    /** The article test data repository. */
    @Autowired
    private ArticleTestDataRepository articleTestDataRepository;

    @Autowired
    private TestDataRepository testDataRepository;

    @MockBean
    private ArticleRepository articleRepository;

    @MockBean
    private ArticleTypeRepository articleTypeRepository;


    @Before
    public final void setUp() {

    }

    @Test
    public final void save() {

        // Arrange
        Article article1 = articleTestDataRepository.getArticles()
                .get("article-1");

        // Arrange
        User user = testDataRepository.getUsers()
                .get("user-1");
        when(articleRepository.save(article1)).thenReturn(article1);
        when(userService.getUserByID(user.getId())).thenReturn(user);

        // Act
        Article article = articleService.save(article1);

        // Assert
        assertEquals(article.getTitle(), article.getTitle());
        assertEquals(article.getDescription(), article1.getDescription());
        assertEquals(article.getRestricted(), article1.getRestricted());
        assertEquals(article.getNeedsApproval(), article1.getNeedsApproval());
        assertEquals(article.getApproved(), article1.getApproved());
        assertEquals(article.getCreatedBy(), article1.getCreatedBy());
        assertEquals(article.getCreatedTime(), article1.getCreatedTime());
        verify(articleRepository, times(1)).save(article1);
    }

    @Test
    public final void getAllArticleTypes() {

        // Arrange
        ArticleType articleType1 = articleTypeTestDataRepository.getTypes()
                .get("articleType-1");
        ArticleType articleType2 = articleTypeTestDataRepository.getTypes()
                .get("articleType-2");
        List<ArticleType> articleTypes = new ArrayList<ArticleType>();
        articleTypes.add(articleType1);
        articleTypes.add(articleType1);
        when(articleTypeRepository.findAll()).thenReturn(articleTypes);

        // Act
        List<ArticleType> articleType = articleService.getArticleTypes();
        // Assert
        assertTrue(articleTypes.containsAll(articleType));
        verify(articleTypeRepository, times(1)).findAll();
    }
}
