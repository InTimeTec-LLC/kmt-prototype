
package com.itt.kmt.response.models;

/**
 * The Class ResponseSuccessMsg.
 */
public class FailureResponseMsg {

    /** The failure. */
    private ResponseMsg failure;

    /**
     * Instantiates a new response success msg.
     *
     * @param failure the failure
     */
    public FailureResponseMsg(final ResponseMsg failure) {

        super();
        this.failure = failure;
    }

    /**
     * Gets the failure.
     *
     * @return the failure
     */
    public ResponseMsg getFailure() {

        return failure;
    }

    /**
     * Sets the failure.
     *
     * @param failure the new failure
     */
    public void setFailure(final ResponseMsg failure) {

        this.failure = failure;
    }

}
