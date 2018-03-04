/*package com.itt.test.kmt.services;

import static org.junit.Assert.assertEquals;
<<<<<<< HEAD
=======
import static org.junit.Assert.assertTrue;
>>>>>>> 08c0da635d85f074d5c990da4cff92385773fb11
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

<<<<<<< HEAD
=======
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
>>>>>>> 08c0da635d85f074d5c990da4cff92385773fb11
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

<<<<<<< HEAD
import com.itt.kmt.models.Article;
import com.itt.kmt.models.User;
import com.itt.kmt.repositories.ArticleRepository;
import com.itt.kmt.repositories.RoleRepository;
import com.itt.kmt.services.ArticleService;
import com.itt.test_category.ServicesTests;
import com.itt.test_data.RoleTestDataRepository;
import com.itt.test_data.TestDataRepository;
=======
import java.util.ArrayList;
import java.util.List;
>>>>>>> 08c0da635d85f074d5c990da4cff92385773fb11

@Category(ServicesTests.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceTests {
<<<<<<< HEAD
    @Autowired
    private ArticleService articleService;

    @Autowired
    private TestDataRepository testDataRepository;

    @Autowired
    private RoleTestDataRepository roleTestDataRepository;
=======


    *//** The article type test data repository. *//*
    @Autowired
    private ArticleService articleService;

    *//** The User service. *//*
    @MockBean
    private UserService userService;

    *//** The article type test data repository. *//*
    @Autowired
    private ArticleTypeTestDataRepository articleTypeTestDataRepository;

    *//** The article test data repository. *//*
    @Autowired
    private ArticleTestDataRepository articleTestDataRepository;

    @Autowired
    private TestDataRepository testDataRepository;
>>>>>>> 08c0da635d85f074d5c990da4cff92385773fb11

    @MockBean
    private ArticleRepository articleRepository;

    @MockBean
<<<<<<< HEAD
    private RoleRepository roleRepository;
=======
    private ArticleTypeRepository articleTypeRepository;

>>>>>>> 08c0da635d85f074d5c990da4cff92385773fb11

    @Before
    public final void setUp() {

    }

    @Test
<<<<<<< HEAD
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

=======
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
>>>>>>> 08c0da635d85f074d5c990da4cff92385773fb11
}
*/