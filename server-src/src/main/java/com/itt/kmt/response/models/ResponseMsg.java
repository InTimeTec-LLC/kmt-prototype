package com.itt.kmt.response.models;

/**
 * The Class ResponseBean.
 */
public class ResponseMsg {

    /** The statusCode. */
    private boolean status;

    /** The errorMessage. */
    private String message;

    /**
     * Instantiates a new response success.
     *
     * @param status the status
     * @param message the message
     */
    public ResponseMsg(final boolean status, final String message) {

        super();
        this.status = status;
        this.message = message;
    }

    /**
     * Checks if is status.
     *
     * @return true, if is status
     */
    public boolean getStatus() {

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
    public String getMessage() {

        return message;
    }

    /**
     * Sets the message.
     *
     * @param message the new message
     */
    public void setMessage(final String message) {

        this.message = message;
    }

}