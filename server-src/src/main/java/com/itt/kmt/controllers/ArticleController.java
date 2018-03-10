package com.itt.kmt.controllers;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itt.kmt.models.Approve;
import com.itt.kmt.models.Article;
import com.itt.kmt.models.ArticleType;
import com.itt.kmt.response.models.ResponseMsg;
import com.itt.kmt.services.ArticleService;
import com.itt.utility.Constants;


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
        articleService.save(articleMap.get("article"));
        ResponseMsg postResponseMsg = new ResponseMsg(true, Constants.ARTICLE_CREATED_MESSAGE);
        return new ModelMap().addAttribute("success", postResponseMsg);
    }
    /**
     * REST Interface for Article updation by id.
     *
     * @param id ID of the Article.
     * @param articleMap from which we can take article to be updated.
     * @return Success object
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ModelMap updateArticle(@PathVariable(value = "id") final String id,
            @RequestBody final HashMap<String, Article> articleMap) {
        articleService.updateArticle(id, articleMap.get("article"));
        return new ModelMap().addAttribute("success",
                new ResponseMsg(true, Constants.DEFAULT_UPDATE_SUCCESS_MSG));
    }
    /**
     * REST Interface for Article retrieval by id.
     *
     * @param id ID of the Article.
     * @return Article object that corresponds to Article id.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelMap getArticleById(@PathVariable(value = "id") final String id) {
        return new ModelMap().addAttribute("article", articleService.getArticleById(id));
    }

    /**
     * REST API for retrieval of Article list.
     *
     * @param page , It is a pageable object with default size of 10 elements.
     * @param httpServletRequest , Servlet request object.
     * @return Page<Article> objects.
     */
  /*  @RequestMapping(method = RequestMethod.GET)
    public Page<Article> getArticles(@PageableDefault(value = Constants.PAGE_SIZE)final Pageable page,
                                     final HttpServletRequest httpServletRequest) {
        String jwtToken = httpServletRequest.getHeader(Constants.AUTHORIZATION);
        return articleService.getAllArticles(page, jwtToken);
    }
*/
    /**
     * REST API to return all Article types.
     * @return ModelMap.
     */
    @RequestMapping(value = "/types", method = RequestMethod.GET)
    @RequiresPermissions("getAllArticleType")
    public ModelMap getArticleTypes() {
        List<ArticleType> articleTypeList = articleService.getArticleTypes();
        return new ModelMap().addAttribute("types", articleTypeList);
    }

//    @RequiresRole
    /**
     * REST Interface for Article retrieval by id.
     *
     * @param id ID of the Article.
     * @param httpServletRequest servlet request.
     * @return Article object that corresponds to Article id.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ModelMap deleteArticleById(@PathVariable(value = "id") final String id,
                                      final HttpServletRequest httpServletRequest) {
        String jwtToken = httpServletRequest.getHeader(Constants.AUTHORIZATION);
        articleService.delete(id, jwtToken);
        return new ModelMap().addAttribute("success",
                new ResponseMsg(true, Constants.ARTICLE_DELETED_MESSAGE));
    }


    /**
     * REST API for approval process of article.
     * @param id ID of the Article.
     * @param httpServletRequest servlet request.
     * @param approveMap from which we can take article to be updated.
     * @return ModelMap.
     */
    @RequestMapping(value = "/approve/{id}", method = RequestMethod.PUT)
    public ModelMap getArticleTypes(@PathVariable(value = "id") final String id,
                                    @RequestBody final HashMap<String, Approve> approveMap,
                                    final HttpServletRequest httpServletRequest) {
        Approve approve = approveMap.get("approve");
        String jwtToken = httpServletRequest.getHeader(Constants.AUTHORIZATION);
        Boolean approval = articleService.articleApproval(approve, id, jwtToken);
        ResponseMsg activateResponseMsg = null;
        if (approval) {
            activateResponseMsg = new ResponseMsg(true, Constants.ARTICLE_APPROVED_MESSAGE);
        } else {
            activateResponseMsg = new ResponseMsg(true, Constants.ARTICLE_POSTED_COMMENT);
        }
        return new ModelMap().addAttribute("success", activateResponseMsg);
    }
    
//    @RequestMapping(method = RequestMethod.GET, params = {"assigned", "type", "status", "search"})
//    public Page<Article>  getArticlesByApprover(@RequestParam(value = "assigned") final String assigned,
//            @RequestParam(value = "search") final String search,
//            @PageableDefault(value = Constants.PAGE_SIZE)final Pageable page) {
//        return articleService.getAllArticlesByApprover(assigned, search, page);
//    }
    
    //@RequestMapping(method = RequestMethod.GET, params = { "assigned", "createdBy", "type", "status", "search"})
    @RequestMapping(method = RequestMethod.GET)
    public Page<Article>  getArticlesByCreated(@RequestParam(value = "assigned", required = false) final String assigned,
                                               @RequestParam(value = "createdBy", required = false) final String createdBy,
                                               @RequestParam(value = "type", required = false) final String type,
                                               @RequestParam(value = "status", required = false) final String status,
                                               @RequestParam(value = "search", required = false, defaultValue = "") final String search,
            @PageableDefault(value = Constants.PAGE_SIZE)final Pageable page) {
        return articleService.getAllWithFiltersAndSearch(assigned, createdBy, type, search, status, page);
    }


//    @RequestMapping(method = RequestMethod.GET, params = { "type", "status", "search"})
//    public Page<Article>  getArticlesByTypeAndStatus(@RequestParam(value = "type") final String type,
//            @RequestParam(value = "status") final String status,
//            @RequestParam(value = "search") final String search,
//            @PageableDefault(value = Constants.PAGE_SIZE)final Pageable page) {
//        return articleService.getArticlesByTypeAndStatus(type, status, search, page);
//    }
    
}