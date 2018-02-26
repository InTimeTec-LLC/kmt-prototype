package com.itt.kmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itt.kmt.model.Article;
//import com.itt.kmt.repository.ArticlePagingAndSortingRepository;
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
     * Instance of repository implementation that supports paging and Sorting.
     */
   // @Autowired
   // private ArticlePagingAndSortingRepository articlePagingAndSortingRepository;

    /**
     * Gets the Article given the id.
     * 
     * @param id ID of the Article
     * @return Article object matching the id
     */
    public Article getArticleById(final String id) {
        Article article = articleRepository.findOne(id);
        if (article == null) {
            article = new Article();
        }
        return article;
    }


    /**
     * Gets the Article given the name.
     * 
     * @param name Name of the Article
     * @return Article object matching the name
     */
    public Article getArticleByName(final String name) {
        List<Article> articles = articleRepository.findByName(name);
        if (articles == null) {
            return new Article();
        }
        return articles.get(0);
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
    }

    /**
     * updates the DBEntity(Article) from the database.
     * 
     * @param id of Article to be updated.
     * @param articlePage , the article field of Article that needs to be updated.
     * @return Article
     */
    public Article updateArticle(final String id, final String articlePage) {
        Article article = articleRepository.findOne(id);
        article.setArticle(articlePage);
        return articleRepository.save(article);
    }

    /**
     * Gets the Page of Article objects given a page number.
     * 
     * @param pageNumber Index of the page
     * @return Page<Article> Page of Article objects
     
    public Page<Article> getPage(final int pageNumber) {
        int pageSize = 1;
        return articlePagingAndSortingRepository.findAll(new PageRequest(pageNumber - 1, pageSize));
    }*/

}
