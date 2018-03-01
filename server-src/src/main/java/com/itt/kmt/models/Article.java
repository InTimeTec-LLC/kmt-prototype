package com.itt.kmt.models;

import lombok.Data;
import org.joda.time.Instant;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;


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
     * User who can approve this article.
     */
    private String approver;
    /**
     * approval Status of this article.
     * default value is false. 
     */
    private Boolean approved = false;
    /**
     * approval Time of this article.
     */
    private String approvalTime;
    /**
     * set Approval Time.
     */
    public void setApprovalTime() {
        if (!approved && approvalTime == null) {
            approvalTime = Instant.now().toString();
        }
    }
    /**
     * get Approval Time.
     * @return approvalTime, gets the Approval Time.
     */
    public String getApprovalTime() {
        return approvalTime;
    }

}
