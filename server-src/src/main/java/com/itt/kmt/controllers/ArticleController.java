package com.itt.kmt.controllers;

import java.util.HashMap;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itt.kmt.models.Article;
import com.itt.kmt.models.ArticleType;
import com.itt.kmt.response.models.ResponseMsg;
import com.itt.kmt.services.ArticleService;
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
    public ModelMap getArticleById(@PathVariable(value = "id") final String id){
        return new ModelMap().addAttribute("article", articleService.getArticleById(id));
    }

    /**
     * REST Interface for Article retrieval by id.
     *
     * @param id ID of the Article.
     * @return Article object that corresponds to Article id.

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public final ModelMap getArticleById(@PathVariable(value = "id") final String id){
        return new ModelMap().addAttribute("article", articleService.getArticleById(id));
    }
     */

    /**
     * REST API to add a new Article.
     *
     * @param articleMap from which we can take article to be added
     * @return Success object
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ModelMap addArticle(@RequestBody
            final HashMap<String, Article> articleMap) {
        Article article = articleMap.get("article");
        articleService.save(article);
        ResponseMsg postResponseMsg = new ResponseMsg(true, "Article has been added successfully");
        return new ModelMap().addAttribute("success", postResponseMsg);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<Article> getArticleList(Pageable page) {
        return articleService.getArticles(page);
    }
    
    @RequestMapping(value="/{id}" ,method = RequestMethod.PUT)
    public ModelMap updateArticle(@PathVariable(value = "id") final String id, @RequestBody
            final HashMap<String, Article> articleMap) {
        Article article = articleMap.get("article");
        articleService.updateArticle(id,article);
        ResponseMsg putResponseMsg = new ResponseMsg(true, "Modifications have been saved successfully");
        return new ModelMap().addAttribute("success", putResponseMsg);
    }
    

    /**
     * REST API for Article retrieval.
     *
     * @return List<Article> a list of Article instances
     */
    //assigned=<userID>&createdBy=<userID> to get list of articles based on createdBy and assigned user
    /*   @RequestMapping(method = RequestMethod.GET,params = { "assigned", "createdBy" })
    public final List<Article> getArticleList(@RequestParam("assigned") String assigned,@RequestParam("createdBy") String createdBy) {
        return articleService.getArticles(assigned,createdBy,page);

    @RequestMapping(method = RequestMethod.GET)
    public final Page<Article> getArticleList(Pageable page) {
        return articleService.getArticles(page);

    }
     */
    /**
     * REST API to return all Roles.
     * @return ModelMap.
     */
    @RequestMapping(value = "/types", method = RequestMethod.GET)
    @RequiresPermissions("getAllArticleType")
    public ModelMap getArticleTypes() {
        
        List<ArticleType> roleList = articleService.getArticleTypes();
        return new ModelMap().addAttribute("types", roleList);
    }

}








