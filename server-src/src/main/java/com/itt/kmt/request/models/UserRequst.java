
package com.itt.kmt.request.models;

import javax.validation.Valid;

import com.itt.kmt.models.User;

/**
 * The Class UserRequst.
 */
public class UserRequst {

    /** The user. */
    @Valid
    private User user;

    /**
     * Instantiates a new user requst.
     */
    public UserRequst() {

        super();
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
    public void setUser(final User user) {

        this.user = user;
    }

}
