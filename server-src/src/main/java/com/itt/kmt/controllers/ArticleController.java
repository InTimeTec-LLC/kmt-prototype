package com.itt.kmt.controllers;

import com.itt.kmt.models.Article;
import com.itt.kmt.models.ArticleType;
import com.itt.kmt.response.models.ResponseMsg;
import com.itt.kmt.services.ArticleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
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








