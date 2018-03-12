package com.itt.kmt.models;


import lombok.Data;
import org.springframework.data.annotation.Id;


/**
 * This class represents a Knowledge Base Article search page.
 *
 * @author Rohan S
 */
@Data
public class KBArticle {

    /**
     * unique identifier.
     */
    @Id
    private String id;

    /**
     * name of the Article.
     */

    private String title;

    /**
     * html content of the Article.
     */
    private String description;

    /**
     * lastModified date and time of the Article.
     */
    private String lastModifiedTime;


    /**
     * created by user id  of the Article.
     */
    private Object createdBy;

    /**
     * Restriction of article.
     */
    private Boolean restricted;

    /**
     * ArticleType of Article.
     */
    private Object articleType;
}

