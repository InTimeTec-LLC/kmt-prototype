package com.itt.utility;

/**
 * this class contains all constants related to email details.
 */
public final class EmailConstants {
    /**
     * Instantiates a new constants.
     */
    private EmailConstants() {

        super();
    }

    // email scenerio mails
    /**
     * email template name when new user created .
     */
    public static final String CREATE_USER_MAIL_TMPLT = "user-created-mail.ftl";
    /**
     * email template name when admin reset the users password .
     */
    public static final String EDIT_USER_MAIL_TMPLT = "admin-reset-password-mail.ftl";
    /**
     * email template name when a user is activated .
     */
    public static final String ACTIVATE_USER_MAIL_TMPLT = "user-activate-mail.ftl";
    /**
     * email template name when a user is deactivated.
     */
    public static final String DEACTIVATE_USER_MAIL_TMPLT = "user-deactivate-mail.ftl";
    /**
     * email template name when Article will be created.
     */
    public static final String CREATE_KA_MAIL_TMPLT = "create-KA-mail.ftl";
    /**
     * email template name when Article will be deleted.
     */
    public static final String DELETE_KA_MAIL_TMPLT = "delete-KA-mail.ftl";
    /**
     * email template name when Article will be deleted by Admin.
     */
    public static final String DELETE_KA_BYADMIN_MAIL_TMPLT = "delete-KA-byAdmin-mail.ftl";
    /**
     * email template name when a Article will be approved and published.
     */
    public static final String APPROVED_AND_PUBLISH_KA_MAIL_TMPLT = "KA-Approved-and-published.ftl";
    /**
     * email template name when Article will be reviewed by reviewer.
     */
    public static final String REVIEWED_KA_MAIL_TMPLT = "KA-Reviewed-mail.ftl";
    /**
     * email template name when Article will be reviewed by reviewer.
     */
    public static final String REVIEWED_USER_NOTIFICATION_TMPLT = "user-notification-mail.ftl";
    /**
     * email template name when Article will not be approved.
     */
    public static final String NOT_REVIEWED_KA_MAIL_TMPLT = "KA-Not-reviewd-mail.ftl";
    /**
     * email template is for gentle reminder to reviewer.
     */
    public static final String REMINDER_MAIL_TMPLT = "reminder-mail.ftl";
    // subject of emails
    /**
     * subject of mail when new user will register.
     */
    public static final String SUBJECT_CREATE_USER_MAIL = "User created successfully for Cognizance";
    /**
     * subject of mail when admin deactivate the user.
     */
    public static final String SUBJECT_ACCOUNT_DEACTIVATE_MAIL = "Deactivation of your account for Cognizance";
    /**
     * subject of mail when admin activate the user.
     */
    public static final String SUBJECT_ACCOUNT_ACTIVATE_MAIL = "Activation of your account for Cognizance";
    /**
     * subject of mail when admin reset the user's password.
     */
    public static final String SUBJECT_ADMIN_RESET_PASS_MAIL = "Password reset successfully for Cognizance";
    /**
     * subject of mail when creator will submit the Article.
     */
    public static final String SUBJECT_ARTICLE_CREATE_MAIL = "Article submitted for review";
    /**
     * subject of mail when Article will be reviewed by reviewer.
     */
    public static final String SUBJECT_ARTICLE_REVIEWED_MAIL = "Article Reviewed";
    /**
     * subject of mail when Article will be Approved and publish.
     */
    public static final String SUBJECT_ARTICLE_APPROVED_AND_PUBLISHED_MAIL = "Article Approved and Published";
    /**
     * subject of mail when user delete the Article.
     */
    public static final String SUBJECT_ARTICLE_DELETED_MAIL = "Article submitted for review is deleted";
    /**
     * subject of mail when user/approver is deactivated.
     */
    public static final String SUBJECT_NOTIFICATION_MAIL = "[Cognizance]: Needs Attention";
    /**
     * subject of mail when admin delete the Article.
     */
    public static final String SUBJECT_ARTICLE_DELETEDBY_ADMIN_MAIL = "Article submitted for review is deleted";
    //template parameters
    /**
     * parameter of templates first name .
     */
    public static final String PARAM_USER_FIRST_NAME = "firstName";
    /**
     * parameter of templates user email id.
     */
    public static final String PARAM_USER_MAIL_ID = "emailId";
    /**
     * parameter of templates user password.
     */
    public static final String PARAM_USER_MAIL_PASSWORD = "password";
    /**
     * parameter of templates portal login link.
     */
    public static final String PARAM_PORTAL_LOGIN_LINK = "loginLink";
    /**
     * parameter of templates subject of email.
     */
    public static final String PARAM_EMAIL_SUBJECT = "subject";
    /**
     * parameter of templates comments of email.
     */
    public static final String PARAM_COMMENTS = "comments";
    /**
     * parameter of templates article linke  of email.
     */
    public static final String PARAM_ARTICLE_LINK = "articleLink";
    /**
     * parameter of templates article title of email.
     */
    public static final String PARAM_ARTICLE_TITLE = "articleTitle";

    //known value of params 
    /**
     * Known value of portal link parameter.
     */
    public static final String PORTAL_LOGIN_LINK = "http://18.144.60.207";
}
