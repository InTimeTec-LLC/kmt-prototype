package com.itt.test.kmt.services;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 10;
    
    
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

    /*  @Test
    public final void getArticleTypeByIDTest() throws Exception{
        Article article1 = articleTestDataRepository.getArticles()
                .get("article-1");
        when(articleRepository.findByIsbn(book1.getIsbn())).thenReturn(books);
        return articleTypeRepository.findOne(id);

    }
    public Page<Article> getAllArticles(final Pageable page) {

       return articleRepository.findAll(page);

    }

     */
    @Test 
    public final void getAllArticlesTest() throws Exception {
        List<Article> articleList = new ArrayList<Article>();
        articleList.add(articleTestDataRepository.getArticles().get("article-1"));
        articleList.add(articleTestDataRepository.getArticles().get("article-2"));

        Page<Article> page = new PageImpl<Article>(articleList);
        when(articleRepository.findAll(new PageRequest(PAGE_NUMBER, PAGE_SIZE))).thenReturn(page);
        Page<Article> firstPage = articleService.getAllArticles(new PageRequest(PAGE_NUMBER, PAGE_SIZE));

        // Assert
        assertThat(firstPage.getTotalPages()).isEqualTo(1);
        verify(articleRepository, times(1)).findAll(new PageRequest(PAGE_NUMBER, PAGE_SIZE));
    }
    /*
     *  public Article getArticleById(final String id) {

        Article article = articleRepository.findOne(id);
       if (article == null) {

            throw new RuntimeException("No articles found");

        }

        return article;

    }

     * */
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

    /*
     * public Article updateArticle(final String id, final Article updatedArticle) {
       Article article = articleRepository.findOne(id);
        if (article == null) {
            throw new RuntimeException("Article not found");
        }
        //        article.setU(updatedArticle.getOwner());
        article.setTitle(updatedArticle.getTitle());
        article.setRestricted(updatedArticle.getRestricted());
        article.setNeedsApproval(updatedArticle.getNeedsApproval());
        article.setDescription(updatedArticle.getDescription());
        article.setApprover(updatedArticle.getApprover());
        article.setApproved(updatedArticle.getApproved());
        return articleRepository.save(article);
    }
     */
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

}
