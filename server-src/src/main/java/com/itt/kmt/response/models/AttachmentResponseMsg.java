
package com.itt.kmt.response.models;

/**
 * The Class AttachmentResponseMsg.
 */
public class AttachmentResponseMsg {

    /** The statusCode. */
    private boolean status;

    /** The errorMessage. */
    private String message;

    /** The success. */
    private AttachmentInfo attachement;

    /**
     * The Class AttachmentInfo.
     */
    public class AttachmentInfo {

        /** The id. */
        private String id;

        /** The filename. */
        private String filename;

        /** The url. */
        private String url;

        /** The article id. */
        private String articleId;

        /**
         * Gets the id.
         *
         * @return the id
         */
        public String getId() {

            return id;
        }

        /**
         * Sets the id.
         *
         * @param id the new id
         */
        public void setId(String id) {

            this.id = id;
        }

        /**
         * Gets the filename.
         *
         * @return the filename
         */
        public String getFilename() {

            return filename;
        }

        /**
         * Sets the filename.
         *
         * @param filename the new filename
         */
        public void setFilename(String filename) {

            this.filename = filename;
        }

        /**
         * Gets the url.
         *
         * @return the url
         */
        public String getUrl() {

            return url;
        }

        /**
         * Sets the url.
         *
         * @param url the new url
         */
        public void setUrl(String url) {

            this.url = url;
        }

        /**
         * Gets the article id.
         *
         * @return the article id
         */
        public String getArticleId() {

            return articleId;
        }

        /**
         * Sets the article id.
         *
         * @param articleId the new article id
         */
        public void setArticleId(String articleId) {

            this.articleId = articleId;
        }

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
    public void setStatus(boolean status) {

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
    public void setMessage(String message) {

        this.message = message;
    }

    /**
     * Gets the attachement.
     *
     * @return the attachement
     */
    public AttachmentInfo getAttachement() {

        return attachement;
    }

    /**
     * Sets the attachement.
     *
     * @param attachement the new attachement
     */
    public void setAttachement(AttachmentInfo attachement) {

        this.attachement = attachement;
    }

}
