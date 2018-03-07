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
    public static final String CREATE_USER_MAIL_TMPLT = "usercreatedmail.ftl";
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

    // subject of emails
    /**
     * subject of mail when new user will register.
     */
    public static final String SUBJECT_CREATE_USER_MAIL = "User created successfully for KMT";
    /**
     * subject of mail when admin deactivate the user.
     */
    public static final String SUBJECT_ACCOUNT_DEACTIVATE_MAIL = "Deactivation of your account for KMT";
    /**
     * subject of mail when admin activate the user.
     */
    public static final String SUBJECT_ACCOUNT_ACTIVATE_MAIL = "Activation of your account for KMT";
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

    //known value of params 
    /**
     * Known value of portal link parameter.
     */
    public static final String PORTAL_LOGIN_LINK = "loginLink";
}
