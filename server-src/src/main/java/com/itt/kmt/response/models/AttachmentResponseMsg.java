
package com.itt.kmt.response.models;

import com.itt.kmt.models.Attachment;

/**
 * The Class AttachmentResponseMsg.
 */
public class AttachmentResponseMsg {

    /** The statusCode. */
    private boolean status;

    /** The errorMessage. */
    private String message;

    /** The attachement. */
    private Attachment attachement;

    /**
     * Sets the attachement.
     *
     * @param attachement the new attachement
     */
    public void setAttachement(final Attachment attachement) {

        this.attachement = attachement;
    }

    /**
     * Gets the attachement.
     *
     * @return the attachement
     */
    public Attachment getAttachement() {

        return attachement;
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
