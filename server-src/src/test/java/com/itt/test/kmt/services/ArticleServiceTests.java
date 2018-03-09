package com.itt.test.kmt.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.itt.kmt.models.Approve;
import com.itt.kmt.models.Comment;
import com.itt.kmt.repositories.CommentRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

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
import com.itt.utility.Constants;

@Category(ServicesTests.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceTests {


    /**
     * The article type test data repository.
     */
    @Autowired
    private ArticleService articleService;

    /**
     * The User service.
     */
    @MockBean
    private UserService userService;

    /**
     * The article type test data repository.
     */
    @Autowired
    private ArticleTypeTestDataRepository articleTypeTestDataRepository;

    /**
     * The article test data repository.
     */
    @Autowired
    private ArticleTestDataRepository articleTestDataRepository;

    @Autowired
    private TestDataRepository testDataRepository;

    @MockBean
    private ArticleRepository articleRepository;

    @MockBean
    private ArticleTypeRepository articleTypeRepository;

    @MockBean
    private CommentRepository commentRepository;

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


    @Test
    public final void getAllArticles() throws Exception {
        List<Article> articleList = new ArrayList<Article>();
        articleList.add(articleTestDataRepository.getArticles().get("article-1"));
        articleList.add(articleTestDataRepository.getArticles().get("article-2"));
        User user = testDataRepository.getUsers()
                .get("user-7");
        String token = "jwtTestToken";

        Page<Article> page = new PageImpl<Article>(articleList);
        when(articleRepository.findAll(new PageRequest(Constants.PAGE_NUMBER, Constants.PAGE_SIZE))).thenReturn(page);
        when(userService.getLoggedInUser(token)).thenReturn(user);
        Page<Article> firstPage = articleService
                .getAllArticles(new PageRequest(Constants.PAGE_NUMBER, Constants.PAGE_SIZE), token);

        // Assert
        assertThat(firstPage.getTotalPages()).isEqualTo(1);
        verify(articleRepository, times(1))
                .findAll(new PageRequest(Constants.PAGE_NUMBER, Constants.PAGE_SIZE));
    }

    @Test 
    public final void getAllArticlesByUserRoleTest() throws Exception {
        List<Article> articleList = new ArrayList<Article>();
        articleList.add(articleTestDataRepository.getArticles().get("article-1"));
        articleList.add(articleTestDataRepository.getArticles().get("article-2"));
        User user = testDataRepository.getUsers()
                .get("user-6");
        String token = "jwtTestToken";

        Page<Article> page = new PageImpl<Article>(articleList);
        when(articleRepository.findByCreatedBy(new ObjectId(user.getId()),
                new PageRequest(Constants.PAGE_NUMBER, Constants.PAGE_SIZE))).thenReturn(page);
        when(userService.getLoggedInUser(token)).thenReturn(user);
        Page<Article> firstPage = articleService
                .getAllArticles(new PageRequest(Constants.PAGE_NUMBER, Constants.PAGE_SIZE), token);

        // Assert
        assertThat(firstPage.getTotalPages()).isEqualTo(1);
        verify(articleRepository, times(1))
                .findByCreatedBy(new ObjectId(user.getId()),
                        new PageRequest(Constants.PAGE_NUMBER, Constants.PAGE_SIZE));
    }


    @Test
    public final void getAllArticlesByManagerRoleTest() throws Exception {
        List<Article> articleList = new ArrayList<Article>();
        articleList.add(articleTestDataRepository.getArticles().get("article-1"));
        articleList.add(articleTestDataRepository.getArticles().get("article-2"));
        User user = testDataRepository.getUsers()
                .get("user-4");
        String token = "jwtTestToken";

        Page<Article> page = new PageImpl<Article>(articleList);
        when(articleRepository.findByCreatedByAndAndApprover(new ObjectId(user.getId()),
                new ObjectId(user.getId()),
                new PageRequest(Constants.PAGE_NUMBER, Constants.PAGE_SIZE))).thenReturn(page);
        when(userService.getLoggedInUser(token)).thenReturn(user);
        Page<Article> firstPage = articleService
                .getAllArticles(new PageRequest(Constants.PAGE_NUMBER, Constants.PAGE_SIZE), token);

        // Assert
        assertThat(firstPage.getTotalPages()).isEqualTo(1);
        verify(articleRepository, times(1))
                .findByCreatedByAndAndApprover(new ObjectId(user.getId()),
                        new ObjectId(user.getId()),
                        new PageRequest(Constants.PAGE_NUMBER, Constants.PAGE_SIZE));
    }

    @Test
    public void getArticleByIdTest() throws Exception {
        Article article = articleTestDataRepository.getArticles().get("article-1");
        when(articleRepository.findOne(article.getId())).thenReturn(article);
        Article articleRetrived = articleService.getArticleById(article.getId());
        // Assert
        assertThat(article.getId()).isEqualTo(articleRetrived.getId());
        verify(articleRepository, times(1)).findOne(article.getId());
    }

    @Test(expected = Exception.class)
    public void getArticleByIdTestInvalidId() throws Exception {
        Article article = articleTestDataRepository.getArticles().get("article-1");
        when(articleRepository.findOne(article.getId())).thenReturn(null);
        articleService.getArticleById(article.getId());
    }

    @Test
    public void updateArticleTest() {
        Article article = articleTestDataRepository.getArticles().get("article-1");
        Article updateArticle = article;
        updateArticle.setTitle(article.getTitle() + "test");
        when(articleRepository.findOne(article.getId())).thenReturn(article);
        when(articleRepository.save(updateArticle)).thenReturn(updateArticle);
        Article updatedArticle = articleService.updateArticle(article.getId(), updateArticle);
        assertEquals(updatedArticle.getTitle(), updateArticle.getTitle());
        assertThat(updatedArticle.getTitle()).isEqualTo(updateArticle.getTitle());
        verify(articleRepository, times(1)).findOne(article.getId());
    }

    @Test(expected = Exception.class)
    public void updateArticleTestInvalidID() {
        Article article = articleTestDataRepository.getArticles().get("article-1");
        when(articleRepository.findOne(article.getId())).thenReturn(null);
        articleService.updateArticle(article.getId(), article);
    }

    @Test
    public void deleteArticleByIdTest() throws Exception {

        User user = testDataRepository.getUsers()
                .get("user-5");
        Article article = articleTestDataRepository.getArticles().get("article-4");
        String jwtToken = "testToken";

        when(userService.getLoggedInUser(jwtToken)).thenReturn(user);
        when(articleRepository.findOne(article.getId())).thenReturn(article);
        doNothing().when(articleRepository).delete(article.getId());
        articleService.delete(article.getId(), jwtToken);

        // Assert
        verify(articleRepository, times(1)).delete(article.getId());
    }

    @Test(expected = Exception.class)
    public void deleteArticleExceptionTest() {

        User user = testDataRepository.getUsers()
                .get("user-3");
        Article article = articleTestDataRepository.getArticles().get("article-4");
        String jwtToken = "testToken";

        when(userService.getLoggedInUser(jwtToken)).thenReturn(user);
        when(articleRepository.findOne(article.getId())).thenReturn(article);
        doNothing().when(articleRepository).delete(article.getId());
        articleService.delete(article.getId(), jwtToken);

        // Assert
        verify(articleRepository, times(1)).delete(article.getId());
    }

    @Test
    public void articalApprovalTest() {

        User user = testDataRepository.getUsers()
                .get("user-4");
        Article article = articleTestDataRepository.getArticles().get("article-4");
        String jwtToken = "testToken";
        String testComment = "test comment";

        //test approve
        Approve approve = new Approve();
        approve.setApproved(false);
        approve.setComment(testComment);

        //test comment
        Comment comment = new Comment();
        comment.setComment(testComment);
        comment.setCommentedBy(user);

        when(userService.getLoggedInUser(jwtToken)).thenReturn(user);
        when(articleRepository.findOne(article.getId())).thenReturn(article);
        when(commentRepository.save(comment)).thenReturn(comment);
        doNothing().when(articleRepository).delete(article.getId());
        articleService.articleApproval(approve, article.getId(), jwtToken);
        // Assert

        verify(articleRepository, times(1)).save(article);
    }

    @Test(expected = Exception.class)
    public void articalApprovalExceptionTest() {

        User user = testDataRepository.getUsers()
                .get("user-4");
        Article article = articleTestDataRepository.getArticles().get("article-4");
        String jwtToken = "testToken";
        String testComment = "test comment";

        //test approve
        Approve approve = new Approve();
        approve.setApproved(false);
        approve.setComment(null);

        //test comment
        Comment comment = new Comment();
        comment.setComment(testComment);
        comment.setCommentedBy(user);

        when(userService.getLoggedInUser(jwtToken)).thenReturn(user);
        when(articleRepository.findOne(article.getId())).thenReturn(article);
        when(commentRepository.save(comment)).thenReturn(comment);
        doNothing().when(articleRepository).delete(article.getId());
        articleService.articleApproval(approve, article.getId(), jwtToken);
        // Assert

        verify(articleRepository, times(1)).save(article);
    }

}
