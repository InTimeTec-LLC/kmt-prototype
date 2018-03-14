package com.itt.kmt.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * This class represents a UserResponse.
 *
 * @author Rohan Sahu
 */
@Data
@Getter
@Setter
public class UserResponse {

    /**
     * constructor of the userResponse.
     * @param id Id of the User.
     * @param firstName FirstName of the User.
     * @param lastName LastName of the User.
     * @param email Email of the User.
     */
    public UserResponse(final String id, final String firstName, final String lastName, final String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * id of the user.
     */
    private String id;

    /**
     * firstname of the user.
     */
    private String firstName;
    /**
     * lastname of the user.
     */
    private String lastName;
    /**
     * email of the user.
     */
    private String email;
}
