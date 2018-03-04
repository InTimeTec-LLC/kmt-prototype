package com.itt.kmt.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.itt.kmt.models.Article;
import com.itt.kmt.models.ArticleType;
import com.itt.kmt.models.User;
import com.itt.kmt.models.UserResponse;
import com.itt.kmt.repositories.ArticlePagingAndSortingRepository;
import com.itt.kmt.repositories.ArticleRepository;
import com.itt.kmt.repositories.ArticleTypeRepository;


/**
 * Service class that acts as an intermediary between controller and the
 * database for all basic CRUD operations. The business logic should reside in
 * service class.
 * 
 * @author Ashish Y
 */
@Service
public class ArticleService {

    /**
     * Instance of the basic Repository implementation.
     */
    @Autowired
    private ArticleRepository articleRepository;

    /**
     * Instance of the basic Repository implementation.
     */
    @Autowired
    private ArticlePagingAndSortingRepository articlePagingAndSortingRepository;

    /**
     * Instance of user service.
     */
    @Autowired
    private UserService userService;

    /**
     * Instance of the basic Article Type repository.
     */
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public Article getArticleById(final String id) {
        Article article = articleRepository.findOne(id);
        if (article == null) {
            throw new RuntimeException("No articles found");
        }
        return article;
    }



    /**
     * Saves the Article to database.
     * 
     * @param article Article object to be saved
     * @return Article object with userResponse included.
     */
    public Article save(final Article article) {

        if (article.getCreatedBy() != null) {
            User createdByUser = userService.getUserByID(article.getCreatedBy().toString());
            article.setCreatedBy(convertUserIntoUserResponse(createdByUser));
        }
        if (article.getApprover() != null) {
            User approver = userService.getUserByID(article.getApprover().toString());
            article.setApprover(convertUserIntoUserResponse(approver));
        }

        return articleRepository.save(article);
    }

    /**
     * Convert user object into article response user format.
     *
     * @param user User object to be converted
     * @return article user response details.
     */
    /*    public void delete(final String id) {
        articleRepository.delete(id);
        if (articleRepository.exists(id)) {
            throw new RuntimeException("article deletion Failed");
        }
    }

    /**
     * updates the DBEntity(Article) from the database.
     * 
     * @param id of Article to be updated.
     * @param updateArticle , Article object that needs to be updated.
     * @return Article

    public Article updateArticle(final String id, final Article updateArticle) {
        Article article = articleRepository.findOne(id);
        if (article == null) {
            throw new RuntimeException("Article not found");
        }
        //        article.setU(updateArticle.getOwner());
        article.setTitle(updateArticle.getTitle());
        article.setRestricted(updateArticle.getRestricted());
        article.setNeedsApproval(updateArticle.getNeedsApproval());
        article.setDescription(updateArticle.getDescription());
        article.setApprover(updateArticle.getApprover());
        article.setApproved(updateArticle.getApproved());
        return articleRepository.save(article);
    }
     */
    private UserResponse convertUserIntoUserResponse(final User user) {
        UserResponse userResponse = new UserResponse(user.getId(),
                user.getFirstName(), user.getLastName(), user.getEmail());
        return userResponse;
    }
    /**
     * Get all the Article Types.
     * @return List of all the Article Type.
     */
    /* public List<Article> getArticles(String assigned, String createdBy, Pageable page) {
        if (assigned != null && createdBy == null) {
            return articlePagingAndSortingRepository.findByApprover(assigned, page);
        } else if (assigned == null && createdBy != null) {
            return articlePagingAndSortingRepository.findByCreatedBy(createdBy, page);
        } else if (assigned != null && createdBy != null) {
            throw new RuntimeException("Article cant be approved by owner");
        } else {
            return (List<Article>) articlePagingAndSortingRepository.findAll(page);
        }
     */
    public Page<Article> getArticles(Pageable page) {
        return articleRepository.findAll(page);
    }

    public List<ArticleType> getArticleTypes() {
        return (List<ArticleType>) articleTypeRepository.findAll();
    }


    /**
     * updates the DBEntity(Article) from the database.
     * 
     * @param id of Article to be updated.
     * @param updateArticle , Article object that needs to be updated.
     * @return Article
     */
    public Article updateArticle(final String id, final Article updateArticle) {
        Article article = articleRepository.findOne(id);
        if (article == null) {
            throw new RuntimeException("Article not found");
        }
        if (updateArticle.getLastModifiedBy() != null) {
            User getLastModifiedBy = userService.getUserByID(updateArticle.getLastModifiedBy().toString());
            article.setLastModifiedBy(convertUserIntoUserResponse(getLastModifiedBy));
        }
        if (updateArticle.getApprover() != null) {
            User approver = userService.getUserByID(updateArticle.getApprover().toString());
            article.setApprover(convertUserIntoUserResponse(approver));
        }
        article.setArticleType(updateArticle.getArticleType());
        article.setTitle(updateArticle.getTitle());
        article.setRestricted(updateArticle.getRestricted());
        article.setNeedsApproval(updateArticle.getNeedsApproval());
        article.setDescription(updateArticle.getDescription());
        article.setApproved(updateArticle.getApproved());
        return articleRepository.save(article);

    }
}
