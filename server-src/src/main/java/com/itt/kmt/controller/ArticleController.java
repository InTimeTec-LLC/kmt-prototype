package com.itt.kmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
     * REST API for Article retrieval by Article name or content.
     *
     * @param name name depicts the key to search.
     * @return List<Article> objects.
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public final List<Article>  getArticle(@RequestParam(value = "name") final String name) {
        return articleService.getArticleByNameOrContent(name);
    }

    /**
     * REST API for Article retrieval.
     *
     * @return List<Article> a list of Article instances
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    public final List<Article> getArticleList() {
        return articleService.getArticles();
    }

    /**
     * REST API for Article Deletion.
     *
     * @param id ID of the Article.
     * @return Article that corresponds to id.
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public final HttpStatus delete(@PathVariable(value = "id") final String id) {
        articleService.delete(id);
        return HttpStatus.OK;
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
        return articleService.updateArticle(id, article);
    }

}








