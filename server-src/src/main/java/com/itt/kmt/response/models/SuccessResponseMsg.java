
package com.itt.kmt.response.models;

/**
 * The Class ResponseSuccessMsg.
 */
public class SuccessResponseMsg {

    /** The success. */
    private ResponseMsg success;

    /**
     * Instantiates a new response success msg.
     *
     * @param success the success
     */
    public SuccessResponseMsg(final ResponseMsg success) {

        super();
        this.success = success;
    }

    /**
     * Gets the success.
     *
     * @return the success
     */
    public ResponseMsg getSuccess() {

        return success;
    }

    /**
     * Sets the success.
     *
     * @param success the new success
     */
    public void setSuccess(final ResponseMsg success) {

        this.success = success;
    }

}
