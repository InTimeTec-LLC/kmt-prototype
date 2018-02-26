package com.itt.kmt.model;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a Article.
 * 
 * @author Ashish Y
 */
//@Data
//@Document
@Getter
@Setter
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
    private DateTime lastModified;
    /**
     * lastModified date and time of the Article.
     */
    @LastModifiedBy 
    private DateTime lastModifiedBy;
    
    /**
     * owner of the Article.
     */
    @CreatedBy
    private String owner;  
    
    /**
     * created date of the Article.
     */
    
    @CreatedDate 
    private Date created;
    /**
     * name of the Article.
     */
    
    private String name;
    /**
     * html content of the Article.
     */
    private String article;
    /**
     * user id of the Article.
     */
    private String userId;  
    /**
     * Restriction of article.
     * default value is false. 
     */
    private String isRestricted;
}
   