package com.itt.kmt.models;

import lombok.Data;


/**
 * This class represents a Approve.
 *
 * @author Rohan Sahu
 */
@Data
public class Approve {

    /**
     * given comment.
     */
    private String comment;

    /**
     * article approve.
     */
    private boolean approved;
}
