package com.itt.kmt.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.util.List;

/**
 * This class represents a Article.
 * 
 * @author Ashish Y
 */
@Data
public class Article {


    /**
     * unique identifier.
     */
    @Id
    private String id;
    /**
     * version of the Article.
     */
    @Version 
    private Long version;
    /**
     * lastModified date and time of the Article.
     */
    @LastModifiedDate
    private String lastModifiedTime;

    /**
     * last Modified user id  of the Article.
     */
    private Object lastModifiedBy;

    /**
     * created by user id  of the Article.
     */
    private Object createdBy;
    
    /**
     * List of attachment for Article.
     */
    private List<Attachment> attachments;

    /**
     * ArticleType of Article.
     */
    private Object articleType;

    /**
     * created time of the Article.
     */
    @CreatedDate
    private String createdTime;

    /**
     * name of the Article.
     */
    private String title;
    /**
     * html content of the Article.
     */
    private String description;
    /**
     * Restriction of article.
     */
    private Boolean restricted;
    /**
     * Restriction of article.
     * default value is false. 
     */
    private Boolean needsApproval = false;
    /**
     * User who can approve this article.
     */
    private Object approver;
    /**
     * approval Status of this article.
     * default value is false. 
     */
    private Boolean approved = false;

    /**
     * review comment for the article.
     */
    private List<Comment> comments;

}
