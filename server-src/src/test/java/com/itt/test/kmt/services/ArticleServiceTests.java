package com.itt.test.kmt.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.itt.kmt.models.KBArticle;
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
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.test.context.junit4.SpringRunner;

import com.itt.kmt.models.Approve;
import com.itt.kmt.models.Article;
import com.itt.kmt.models.ArticleType;
import com.itt.kmt.models.Comment;
import com.itt.kmt.models.User;
import com.itt.kmt.repositories.ArticleRepository;
import com.itt.kmt.repositories.ArticleTypeRepository;
import com.itt.kmt.repositories.CommentRepository;
import com.itt.kmt.services.ArticleService;
import com.itt.kmt.services.MailService;
import com.itt.kmt.services.UserService;
import com.itt.test_category.ServicesTests;
import com.itt.test_data.ArticleTestDataRepository;
import com.itt.test_data.ArticleTypeTestDataRepository;
import com.itt.test_data.TestDataRepository;

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

    @MockBean
    private MailService mailService;

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

    private static final String JWT_TEST_TOKEN = "jwtTestToken";

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

        List<ArticleType> articleTypes = new ArrayList<ArticleType>();
        List<ObjectId> articleTypeObjectID = new ArrayList<>();
        ArticleType articleType = articleTypeTestDataRepository.getTypes()
                .get("articleType-3");
        articleTypes.add(articleType);
        articleTypes.forEach(articleTypeFromList-> articleTypeObjectID.add(new ObjectId(articleType.getId())));
        List<Boolean> listStatus = Arrays.asList(true);

        List<Article> articleList = new ArrayList<Article>();
        articleList.add(articleTestDataRepository.getArticles().get("article-1"));
        articleList.add(articleTestDataRepository.getArticles().get("article-2"));
        when(articleTypeRepository.findAll()).thenReturn(articleTypes);
        User user = testDataRepository.getUsers()
                .get("user-7");
        when(userService.getLoggedInUser(JWT_TEST_TOKEN)).thenReturn(user);

        PageRequest pageReq = new PageRequest(0, 1);
        Page<Article> page = new PageImpl<Article>(articleList);
        when(articleRepository.findAllArticles(articleTypeObjectID
                , listStatus, "searchString", pageReq)).thenReturn(page);
        Page<Article> firstPage = articleService
                .getAllWithFiltersAndSearch(null, articleType.getId(), "true", "searchString",
                        pageReq, JWT_TEST_TOKEN);

        // Assert
        assertTrue(page.getContent().containsAll(firstPage.getContent()));
        verify(articleRepository, times(1))
                .findAllArticles(articleTypeObjectID, listStatus, "searchString", pageReq);
    }

    @Test 
    public final void getAllArticlesByUserRoleTest() throws Exception {

        List<ArticleType> articleTypes = new ArrayList<ArticleType>();
        List<ObjectId> articleTypeObjectID = new ArrayList<>();
        ArticleType articleType = articleTypeTestDataRepository.getTypes()
                .get("articleType-3");
        articleTypes.add(articleType);
        articleTypes.forEach(articleTypeFromList-> articleTypeObjectID.add(new ObjectId(articleType.getId())));
        List<Boolean> listStatus = Arrays.asList(true);

        List<Article> articleList = new ArrayList<Article>();
        articleList.add(articleTestDataRepository.getArticles().get("article-1"));
        articleList.add(articleTestDataRepository.getArticles().get("article-2"));
        User user = testDataRepository.getUsers()
                .get("user-6");

        PageRequest pageReq = new PageRequest(0, 1);
        Page<Article> page = new PageImpl<Article>(articleList);
        when(articleRepository.findArticlesByCreatedBy(new ObjectId(user.getId())
                , articleTypeObjectID, listStatus, "searchString",
                pageReq)).thenReturn(page);
        when(userService.getLoggedInUser(JWT_TEST_TOKEN)).thenReturn(user);
        Page<Article> firstPage = articleService
                .getAllWithFiltersAndSearch(null, articleType.getId(), "true", "searchString",
                        pageReq, JWT_TEST_TOKEN);

        // Assert
        assertTrue(page.getContent().containsAll(firstPage.getContent()));
        verify(articleRepository, times(1))
                .findArticlesByCreatedBy(new ObjectId(user.getId())
                        , articleTypeObjectID, listStatus, "searchString",
                        pageReq);
    }


    @Test
    public final void getAllArticlesByManagerRoleTest() throws Exception {
        List<ArticleType> articleTypes = new ArrayList<ArticleType>();
        List<ObjectId> articleTypeObjectID = new ArrayList<>();
        ArticleType articleType = articleTypeTestDataRepository.getTypes()
                .get("articleType-3");
        articleTypes.add(articleType);
        articleTypes.forEach(articleTypeFromList-> articleTypeObjectID.add(new ObjectId(articleType.getId())));
        List<Boolean> listStatus = Arrays.asList(true);

        List<Article> articleList = new ArrayList<Article>();
        articleList.add(articleTestDataRepository.getArticles().get("article-1"));
        articleList.add(articleTestDataRepository.getArticles().get("article-2"));
        when(articleTypeRepository.findAll()).thenReturn(articleTypes);
        User user = testDataRepository.getUsers()
                .get("user-4");
        when(userService.getLoggedInUser(JWT_TEST_TOKEN)).thenReturn(user);

        PageRequest pageReq = new PageRequest(0, 1);
        Page<Article> page = new PageImpl<Article>(articleList);
        when(articleRepository.findAllArticles(new ObjectId(user.getId())
                , articleTypeObjectID, listStatus, "searchString", pageReq)).thenReturn(page);
        Page<Article> firstPage = articleService
                .getAllWithFiltersAndSearch(null, articleType.getId(), "true", "searchString",
                        pageReq, JWT_TEST_TOKEN);

        // Assert
        assertTrue(page.getContent().containsAll(firstPage.getContent()));
        verify(articleRepository, times(1))
                .findAllArticles(new ObjectId(user.getId()), articleTypeObjectID, listStatus, "searchString", pageReq);
    }

    @Test
    public void getArticleByIdAdminTest() throws Exception {
        Article article = articleTestDataRepository.getArticles().get("article-1");
        User user = testDataRepository.getUsers()
                .get("user-7");
        when(articleRepository.findOne(article.getId())).thenReturn(article);
        when(userService.getLoggedInUser(JWT_TEST_TOKEN)).thenReturn(user);
        Article articleRetrived = articleService.getArticleById(article.getId(), JWT_TEST_TOKEN);
        // Assert
        assertThat(article.getId()).isEqualTo(articleRetrived.getId());
        verify(articleRepository, times(1)).findOne(article.getId());
    }

    @Test
    public void getArticleByIdManagerTest() throws Exception {
        Article article = articleTestDataRepository.getArticles().get("article-5");
        User user = testDataRepository.getUsers()
                .get("user-4");
        when(articleRepository.findOne(article.getId())).thenReturn(article);
        when(userService.getUserByID(user.getId())).thenReturn(user);
        when(userService.getLoggedInUser(JWT_TEST_TOKEN)).thenReturn(user);
        Article articleRetrived = articleService.getArticleById(article.getId(), JWT_TEST_TOKEN);
        // Assert
        assertThat(article.getId()).isEqualTo(articleRetrived.getId());
        verify(articleRepository, times(1)).findOne(article.getId());
    }

    @Test(expected = Exception.class)
    public void getArticleByIdUnAuthorizedExceptionTest() throws Exception {
        Article article = articleTestDataRepository.getArticles().get("article-4");
        User user = testDataRepository.getUsers()
                .get("user-3");
        when(articleRepository.findOne(article.getId())).thenReturn(article);
        when(userService.getLoggedInUser(JWT_TEST_TOKEN)).thenReturn(user);
        when(userService.getUserByID(user.getId())).thenReturn(user);
        Article articleRetrived = articleService.getArticleById(article.getId(), JWT_TEST_TOKEN);
        // Assert
        assertThat(article.getId()).isEqualTo(articleRetrived.getId());
        verify(articleRepository, times(1)).findOne(article.getId());
    }

    @Test(expected = Exception.class)
    public void getArticleByIdTestInvalidId() throws Exception {
        Article article = articleTestDataRepository.getArticles().get("article-1");
        User user = testDataRepository.getUsers()
                .get("user-3");
        when(articleRepository.findOne(article.getId())).thenReturn(null);
        when(userService.getLoggedInUser(JWT_TEST_TOKEN)).thenReturn(user);
        articleService.getArticleById(article.getId(), JWT_TEST_TOKEN);
    }

    @Test
    public void updateArticleTest() {
        Article article = articleTestDataRepository.getArticles().get("article-1");
        User user = testDataRepository.getUsers()
                .get("user-5");
        Article updateArticle = article;
        updateArticle.setTitle(article.getTitle() + "test");
        when(articleRepository.findOne(article.getId())).thenReturn(article);
        when(articleRepository.save(updateArticle)).thenReturn(updateArticle);
        when(userService.getLoggedInUser(JWT_TEST_TOKEN)).thenReturn(user);
        Article updatedArticle = articleService.updateArticle(article.getId(), updateArticle, JWT_TEST_TOKEN);
        assertEquals(updatedArticle.getTitle(), updateArticle.getTitle());
        assertThat(updatedArticle.getTitle()).isEqualTo(updateArticle.getTitle());
        verify(articleRepository, times(1)).findOne(article.getId());
    }

    @Test(expected = Exception.class)
    public void updateArticleTestInvalidID() {
        User user = testDataRepository.getUsers()
                .get("user-5");
        Article article = articleTestDataRepository.getArticles().get("article-1");
        when(articleRepository.findOne(article.getId())).thenReturn(null);
        when(userService.getLoggedInUser(JWT_TEST_TOKEN)).thenReturn(user);
        articleService.updateArticle(article.getId(), article, JWT_TEST_TOKEN);
    }

    @Test
    public void deleteArticleByIdTest() throws Exception {

        User user = testDataRepository.getUsers()
                .get("user-5");
        Article article = articleTestDataRepository.getArticles().get("article-4");

        when(userService.getLoggedInUser(JWT_TEST_TOKEN)).thenReturn(user);
        when(articleRepository.findOne(article.getId())).thenReturn(article);
        when(mailService.sendDeleteKAMail(article, false)).thenReturn(new AsyncResult<Boolean>(true));
        when(mailService.sendDeleteKAMail(article, true)).thenReturn(new AsyncResult<Boolean>(true));

        doNothing().when(articleRepository).delete(article.getId());
        articleService.delete(article.getId(), JWT_TEST_TOKEN);

        // Assert
        verify(articleRepository, times(1)).delete(article.getId());
    }

    @Test(expected = Exception.class)
    public void deleteArticleExceptionTest() {

        User user = testDataRepository.getUsers()
                .get("user-3");
        Article article = articleTestDataRepository.getArticles().get("article-4");

        when(userService.getLoggedInUser(JWT_TEST_TOKEN)).thenReturn(user);
        when(articleRepository.findOne(article.getId())).thenReturn(article);
        doNothing().when(articleRepository).delete(article.getId());
        articleService.delete(article.getId(), JWT_TEST_TOKEN);

        // Assert
        verify(articleRepository, times(1)).delete(article.getId());
    }


    @Test
    public void articalReviewCommentTest() {

        User user = testDataRepository.getUsers()
                .get("user-4");
        Article article = articleTestDataRepository.getArticles().get("article-5");
        String testComment = "test comment";

        //test approve
        Approve approve = new Approve();
        approve.setApproved(true);
        approve.setComment(testComment);

        //test comment
        Comment comment = new Comment();
        comment.setComment(testComment);
        comment.setCommentedBy(user);

        when(userService.getLoggedInUser(JWT_TEST_TOKEN)).thenReturn(user);
        when(articleRepository.findOne(article.getId())).thenReturn(article);
        when(commentRepository.save(comment)).thenReturn(comment);
        doNothing().when(commentRepository).delete(comment.getId());
        articleService.articleApproval(approve, article.getId(), JWT_TEST_TOKEN);
        // Assert

        verify(articleRepository, times(1)).save(article);
    }

    @Test
    public void articalApprovalTest() throws MailException, InterruptedException {

        User user = testDataRepository.getUsers()
                .get("user-4");
        Article article = articleTestDataRepository.getArticles().get("article-4");
        String testComment = "test comment";

        //test approve
        Approve approve = new Approve();
        approve.setApproved(false);
        approve.setComment(testComment);

        //test comment
        Comment comment = new Comment();
        comment.setComment(testComment);
        comment.setCommentedBy(user);

        when(userService.getLoggedInUser(JWT_TEST_TOKEN)).thenReturn(user);
        when(articleRepository.findOne(article.getId())).thenReturn(article);
        when(commentRepository.save(comment)).thenReturn(comment);

        when(mailService.sendKAapproveAndPublishMail(article, approve)).thenReturn(new AsyncResult<Boolean>(true));
        when(mailService.sendKAReviewdMail(article, approve)).thenReturn(new AsyncResult<Boolean>(true));

        doNothing().when(articleRepository).delete(article.getId());
        articleService.articleApproval(approve, article.getId(), JWT_TEST_TOKEN);

        approve.setApproved(true);
        articleService.articleApproval(approve, article.getId(), JWT_TEST_TOKEN);
        // Assert

        verify(articleRepository, times(2)).save(article);
    }

    @Test(expected = Exception.class)
    public void articalApprovalExceptionTest() {

        User user = testDataRepository.getUsers()
                .get("user-4");
        Article article = articleTestDataRepository.getArticles().get("article-4");
        String testComment = "test comment";

        //test approve
        Approve approve = new Approve();
        approve.setApproved(false);
        approve.setComment(null);

        //test comment
        Comment comment = new Comment();
        comment.setComment(testComment);
        comment.setCommentedBy(user);

        when(userService.getLoggedInUser(JWT_TEST_TOKEN)).thenReturn(user);
        when(articleRepository.findOne(article.getId())).thenReturn(article);
        when(commentRepository.save(comment)).thenReturn(comment);
        articleService.articleApproval(approve, article.getId(), JWT_TEST_TOKEN);
        // Assert

        verify(articleRepository, times(1)).save(article);
    }

    @Test
    public final void getAllPublishedArticles() throws Exception {
        KBArticle kbArticle = new KBArticle();
        kbArticle.setDescription("test description");
        List<KBArticle> articleList = new ArrayList<KBArticle>();
        articleList.add(kbArticle);
        User user = testDataRepository.getUsers()
                .get("user-4");
        String searchString = "searchString";

        PageRequest pageReq = new PageRequest(0, 1);
        Page<KBArticle> page = new PageImpl<KBArticle>(articleList);
        when(articleRepository.findByTitleAndDescriptionAndApproved(searchString, true,
                pageReq)).thenReturn(page);
        when(userService.getLoggedInUser(JWT_TEST_TOKEN)).thenReturn(user);
        Page<KBArticle> firstPage = articleService
                .getKBArticlesWithSearch(searchString,
                        pageReq);

        // Assert
        assertTrue(page.getContent().containsAll(firstPage.getContent()));
        verify(articleRepository, times(1))
                .findByTitleAndDescriptionAndApproved(searchString, true,
                        pageReq);
    }

}
