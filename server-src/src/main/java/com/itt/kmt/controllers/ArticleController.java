package com.itt.kmt.controllers;

import java.util.List;

import com.itt.kmt.models.Article;
import com.itt.kmt.response.models.ResponseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public final Article getArticleById(@PathVariable(value = "id") final String id) {
        return articleService.getArticleById(id);
    }

    /**
     * REST API to add a new Article.
     *
     * @param article Article to be added
     * @return Article object
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public final ModelMap addArticle(@RequestBody final Article article) {
        articleService.save(article);
        ResponseMsg postResponseMsg = new ResponseMsg(true, "added successfully");
        return new ModelMap().addAttribute("success", postResponseMsg);
    }

    /**
     * REST API for Article retrieval by Article name or content.
     *
     * @param name name depicts the key to search.
     * @return List<Article> objects.
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public final List<Article>  getArticle(@RequestParam(value = "name") final String name) {
        return articleService.getArticleByNameOrContent(name);
    }

    /**
     * REST API for Article retrieval.
     *
     * @return List<Article> a list of Article instances
     */
    @RequestMapping(method = RequestMethod.GET)
    public final List<Article> getArticleList() {
        return articleService.getArticles();
    }

    /**
     * REST API for Article Deletion.
     *
     * @param id ID of the Article.
     * @return Article that corresponds to id.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final ModelMap delete(@PathVariable(value = "id") final String id) {
        articleService.delete(id);
        ResponseMsg deleteResponseMsg = new ResponseMsg(true, "deleted successfully");
        return new ModelMap().addAttribute("success", deleteResponseMsg);
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








