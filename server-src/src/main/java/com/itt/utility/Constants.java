
package com.itt.utility;

/**
 * The Class Constants.
 */
public final class Constants {

    /**
     * Instantiates a new constants.
     */
    private Constants() {

        super();
    }

    /** The Constant USER_LOGOUT_SUCCESS_MSG. */
    public static final String USER_LOGOUT_SUCCESS_MSG = "User has been logged out successfully";

    /** The Constant DEFAULT_UPDATE_SUCCESS_MSG. */
    public static final String DEFAULT_UPDATE_SUCCESS_MSG = "Modifications have been saved successfully";

    /** The Constant UPDATE_INACTIVE_USER_ERROR_MSG. */
    public static final String UPDATE_INACTIVE_USER_ERROR_MSG = "Update not allowed as the user is inactive";

    /** The Constant CHANGE_USER_STATUS_ERROR_MSG. */
    public static final String CHANGE_USER_STATUS_ERROR_MSG = "Activating an active user or deactivating an "
         + "inactive user not permitted";

    /** The Constant USER_CREATE_SUCCESS_MSG. */
    public static final String USER_ADDED_SUCCESS_MSG = "User has been created successfully";

    /** The Constant USER_DELETE_SUCCESS_MSG. */
    public static final String USER_DELETED_SUCCESS_MSG = "User has been deleted successfully";

    /** The Constant USER_NOT_EXIST_ERROR_MSG. */
    public static final String USER_DOES_NOT_EXIST_ERROR_MSG = "User does not exist";

    /** The Constant USER_ACTIVATED_SUCCESS_MSG. */
    public static final String USER_ACTIVATED_SUCCESS_MSG = "User has been activated successfully";

    /** The Constant USER_VIEWS_OTHER_USER_ERROR_MSG. */
    public static final String USER_VIEWS_OTHER_USER_ERROR_MSG = "User cannot view details of other user";

    /** The Constant USER_DEACTIVATED_SUCCESS_MSG. */
    public static final String USER_DEACTIVATED_SUCCESS_MSG = "User has been deactivated successfully";

    /** The Constant UNAUTHORIZED_ACCESS. */
    public static final String UNAUTHORIZED_ACCESS_MSG = "Unauthorized access";

    /** The Constant BAD_REQUEST_MSG. */
    public static final String BAD_REQUEST_MSG = "Bad request";

    /** Default Page number. **/
    public static final int PAGE_NUMBER = 0;

    /** Default Page Size. **/
    public static final int PAGE_SIZE = 10;

    /** The Constant ARTICLE_CREATED_MESSAGE. */
    public static final String ARTICLE_CREATED_MESSAGE = "Knowledge Article has been created successfully";

    /** The Constant NULL_PASSWORD_MESSAGE. */
    public static final String NULL_PASSWORD_MESSAGE = "Password should not be empty";

    /** The Constant ARTICLE_DELETED_MESSAGE. */
    public static final String ARTICLE_DELETED_MESSAGE = "Knowledge Article has been deleted successfully";

    /** The Constant ARTICLE_APPROVED_MESSAGE. */
    public static final String ARTICLE_APPROVED_MESSAGE = "Knowledge Article approved successfully";

    /** The Constant ARTICLE_APPROVED_MESSAGE. */
    public static final String ARTICLE_POSTED_COMMENT = "Posted review comment successfully";

    /** The Constant ROLE_USER. */
    public static final String ROLE_USER = "user";

    /** The Constant ROLE_MANAGER. */
    public static final String ROLE_MANAGER = "manager";

    /** The Constant ROLE_ADMIN. */
    public static final String ROLE_ADMIN = "admin";

    /** The Constant AUTHORIZATION. */
    public static final String AUTHORIZATION = "Authorization";

    /** The Constant ATTACHMENT_DELETED_MESSAGE. */
    public static final String ATTACHMENT_DELETED_MESSAGE = "Attachment has been deleted successfully";
    /** The Constant ATTACHMENT_ADDED_MESSAGE. */
    public static final String ATTACHMENT_ADDED_MESSAGE = "Attachment has been added successfully";

    /** The Constant INVALID_EMAIL_ID. */
    public static final String INVALID_EMAIL_ID = "Invalid emailid";

    /** The Constant USER_NOT_EXIST. */
    public static final String USER_NOT_EXIST = "User doesnot exist";

    /** The Constant COULD_NOT_PROCESS. */
    public static final String COULD_NOT_PROCESS =
        "Could not process your request. Please contact your administrator";

    /** The Constant PASSWORD_RESET_SUCCESS. */
    public static final String PASSWORD_RESET_SUCCESS =
        "Your password has been reset successfully. Your new password has been sent to your email address";

    /** The Constant ATTACHMENT_NOT_FOUND_ERROR_MESSAGE. */
    public static final String ATTACHMENT_NOT_FOUND_ERROR_MESSAGE = "Attachment not found";
    /** The Constant ATTACHMENT_SUPPORTED_FORMAT_ERROR_MESSAGE. */
    public static final String ATTACHMENT_SUPPORTED_FORMAT_ERROR_MESSAGE = "This format is not supported";
    /** The Constant ATTACHMENT_LIMIT_ERROR_MESSAGE. */
    public static final String ATTACHMENT_LIMIT_ERROR_MESSAGE = "File attachment limit exceedes";
    /** The Constant ATTACHMENT_SIZE_ERROR_MESSAGE. */
    public static final String ATTACHMENT_SIZE_ERROR_MESSAGE = "Attachment size exceeds the allowable limit of 20MB";
    /** The Constant NO_ATTACHMENT_UPLOADED_ERROR_MESSAGE. */
    public static final String NO_ATTACHMENT_UPLOADED_ERROR_MESSAGE = "Please select a file to upload";


    /** The Constant EMPTY_STRING. */
    public static final String EMPTY_STRING = "";

    /** The Constant DUPLICATE_RECORD. */
    public static final String DUPLICATE_RECORD = "Record already exists";
}
