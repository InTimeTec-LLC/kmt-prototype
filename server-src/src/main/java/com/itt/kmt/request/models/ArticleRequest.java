package com.itt.kmt.request.models;

import com.itt.kmt.models.Article;

import javax.validation.Valid;

/**
 * The Class ArticleRequest.
 */
public class ArticleRequest {


    /** The article. */
    @Valid
    private Article article;

    /**
     * Instantiates a new article requst.
     */
    public ArticleRequest() {

        super();
    }

    /**
     * Gets the article.
     *
     * @return the article
     */
    public Article getArticle() {

        return article;
    }

    /**
     * Sets the article.
     *
     * @param article the new article
     */
    public void setArticle(final Article article) {

        this.article = article;
    }
}
