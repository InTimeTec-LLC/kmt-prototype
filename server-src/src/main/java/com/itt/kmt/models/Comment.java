package com.itt.kmt.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

/**
 * This class represents a Comment.
 *
 * @author Rohan Sahu
 */
@Data
public class Comment {
    /**
     * unique identifier.
     */
    @Id
    private String id;

    /**
     * user role.
     */
    private String comment;

    /**
     *  role.
     */
    @CreatedDate
    private String createdTime;

    /**
     * user gave comment.
     */
    private Object commentedBy;
}
