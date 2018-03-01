
package com.itt.kmt.response.models;

import com.itt.kmt.models.User;

/**
 * The Class ResponseBean.
 */
public class LoginResponseMsg {

    /** The user details. */
    private User user;
    
    /** The success. */
    private StatusMsg success;
   
   /**
    * The Class StatusMsg.
    */
   public class StatusMsg {

        /** The statusCode. */
        private boolean status;

        /** The errorMessage. */
        private String accessToken;

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
         * @param accessToken the new message
         */
        public void setAccessToken(final String accessToken) {

            this.accessToken = accessToken;
        }
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

    
    /**
     * Gets the success.
     *
     * @return the success
     */
    public StatusMsg getSuccess() {
    
        return success;
    }

    
    /**
     * Sets the success.
     *
     * @param success the new success
     */
    public void setSuccess(final StatusMsg success) {
    
        this.success = success;
    }

    
}
