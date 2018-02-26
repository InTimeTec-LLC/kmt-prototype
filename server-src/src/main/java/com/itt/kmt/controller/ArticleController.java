package com.itt.kmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itt.kmt.model.Article;
import com.itt.kmt.service.ArticleService;
/**
 * This class is responsible for exposing REST APis for Article.
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {

    /**
     * Service implementation for DB entity that provides retrieval methods.
     */
    @Autowired
    private ArticleService articleService;

    /**
     * REST Interface for Article retrieval by id.
     *
     * @param id ID of the Article.
     * @return Article object that corresponds to Article id.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public final Article getArticleById(@PathVariable(value = "id") final String id) {
        return articleService.getArticleById(id);
    }

    /**
     * REST API to add a new Article.
     *
     * @param article Article to be added
     * @return Article object
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
    public final Article addArticle(@RequestBody final Article article) {
        return articleService.save(article);
    }


    /**
     * REST API for Article retrieval by Article name.
     *
     * @param name Name of the Article.
     * @return Article object that corresponds to Article Name.
     */

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public final Article getArticleByName(@RequestParam(value = "name") final String name) {
        return articleService.getArticleByName(name);
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
     * @param id ID of the Article.
     * @return Article that corresponds to id.
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public final Article delete(@PathVariable(value = "id") final String id) {
        return articleService.getArticleById(id);
    }

    /**
     * REST API for updating Article.
     *
     * @param id ID of the Article.
     * @param article the article that needs to be updated
     * @return Article that corresponds to id.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public final Article updateArticle(@PathVariable(value = "id") final String id,
            @RequestBody final Article article) {
        return articleService.updateArticle(id, article.getArticle());
    }

}