package com.itt.kmt.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * This class represents a User.
 *
 * @author Rohan Sahu
 */
@Data
public class Role implements Serializable {

    /**
     * unique identifier.
     */
    @Id
    private String id;

    /**
     * user role.
     */
    private String role;

}
