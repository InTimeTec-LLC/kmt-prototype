package com.itt.kmt.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import lombok.Data;



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
    @LastModifiedBy 
    private String lastModifiedBy;
    /**
     * owner of the Article.
     */
    @CreatedBy
    private String owner;  
    /**
     * created date of the Article.
     */
    
    @CreatedDate 
    private String createdTime;
    /**
     * name of the Article.
     */
    private String name;
    /**
     * html content of the Article.
     */
    private String content;
    /**
     * Restriction of article.
     * default value is false. 
     */
    private Boolean isRestricted = false;
    /**
     * Restriction of article.
     * default value is false. 
     */
    private Boolean needsApproval = false;
    /**
     * Restriction of article.
     * default value is false. 
     */
    private ArticleApproval articleApproval;
 
}
   