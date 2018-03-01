package com.itt.kmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itt.kmt.model.Article;
import com.itt.kmt.repository.ArticleRepository;


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
     * Gets the Article given the id.
     * 
     * @param id ID of the Article
     * @return Article object matching the id
     */
    public Article getArticleById(final String id) {
        Article article = articleRepository.findOne(id);
        if (article == null) {
            throw new RuntimeException("No users found");
        }
        return article;
    }


    /**
     * Gets the Article given the name.
     * 
     * @param name Name of the Article
     * @return Article object matching the name
     */
    public List<Article> getArticleByNameOrContent(final String name) {
        List<Article> articles = articleRepository.findByNameLikeOrContentLike(name, name);
        if (articles == null) {
            throw new RuntimeException("No Users Found for search element :" + name);
        }
        return articles;
    }

    /**
     * Saves the Article to database.
     * 
     * @param article Article object to be saved
     * @return Article object with id included.
     */
    public Article save(final Article article) {
        return articleRepository.save(article);
    }

    /**
     * Deletes the DBEntity(Article) from the database.
     * 
     * @param id of Article to be deleted.
     */
    public void delete(final String id) {
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
     */
    public Article updateArticle(final String id, final Article updateArticle) {
        Article article = articleRepository.findOne(id);
        if (article == null) {
            throw new RuntimeException("Article not found");
        }
        article.setOwner(updateArticle.getOwner());
        article.setName(updateArticle.getName());
        article.setIsRestricted(updateArticle.getIsRestricted());
        article.setNeedsApproval(updateArticle.getNeedsApproval());
        article.setContent(updateArticle.getContent());
        article.setApprover(updateArticle.getApprover());
        article.setApproved(updateArticle.getApproved());
        return articleRepository.save(article);
    }

    /**
     * Get all available articles the DBEntity(Article) from the database.
     * 
     * @return List<Article> get list of articles.
     */
    public List<Article> getArticles() {
        return (List<Article>) articleRepository.findAll();
    }
}