package com.itt.kmt.models;

//import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
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
     * approval Time of this article.
     */
//    private String approvalTime;
//    /**
//     * set Approval Time.
//     */
//    public void setApprovalTime() {
//        if (!approved && approvalTime == null) {
//            approvalTime = Instant.now().toString();
//        }
//    }
    /**
     * get Approval Time.
     * @return approvalTime, gets the Approval Time.
//     */
//    public String getApprovalTime() {
//        return approvalTime;
//    }

}
