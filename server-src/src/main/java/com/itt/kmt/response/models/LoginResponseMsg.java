
package com.itt.kmt.response.models;

import com.itt.kmt.models.User;

/**
 * The Class ResponseBean.
 */
public class LoginResponseMsg {

    /** The statusCode. */
    private boolean status;

    /** The errorMessage. */
    private String accessToken;

    /** The user details. */
    private User user;

    /**
     * Instantiates a new response success.
     *
     * @param status the status
     * @param message the message
     */
    public LoginResponseMsg(final boolean status, final String accessToken) {

        super();
        this.status = status;
        this.accessToken = accessToken;
    }

    /**
     * Checks if is status.
     *
     * @return true, if is status
     */
    public boolean isStatus() {

        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(final boolean status) {

        this.status = status;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getAccessToken() {

        return accessToken;
    }

    /**
     * Sets the message.
     *
     * @param message the new message
     */
    public void setAccessToken(final String accessToken) {

        this.accessToken = accessToken;
    }
    /**
     * Gets the user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }
    /**
     * Sets the user.
     *
     * @param user the new user
     */
    public void setUser(User user) {
        this.user = user;
    }

}
