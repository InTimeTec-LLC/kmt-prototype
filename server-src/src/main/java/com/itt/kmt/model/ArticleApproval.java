package com.itt.kmt.model;

import lombok.Data;

/**
 * This class represents a ArticleApproval.
 * 
 * @author Ashish Y
 */
@Data
public class ArticleApproval {

    /**
     * approval Status of the Article.
     */
    private Boolean approvalStatus =  false;
    /**
     * approvers, semicolon separated userIds.
     * who can approve an article
     */
    private String approvers;
    /**
     * created date of the approval.
     */
    private String createdTime;

    /**
     * id of the user who approved.
     */
    private String approvedBy;  
    /**
     * approval Time of the Article.
     */

    private String approvedTime;
    /**
     * unparameterized constructor.
     */
    public ArticleApproval() {
        super();
    }
    
    /**
     * parameterized constructor.
     * @param approvalStatus approvalStatus of the Article.
     * @param approvers approvers of the Article.
     * @param approvedBy approvedBy of the Article.
     */
    public ArticleApproval(final Boolean approvalStatus, final String approvers, final String approvedBy) {
        super();
        this.approvalStatus = approvalStatus;
        this.approvers = approvers;
        this.approvedBy = approvedBy;
    }
   
}
