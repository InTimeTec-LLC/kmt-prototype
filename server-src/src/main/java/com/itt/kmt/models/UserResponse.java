package com.itt.kmt.models;

import lombok.Data;

<<<<<<< e228bf1867c95ddf1315bdd0522a560b7c87b6a5

/**
 * This class represents a UserResponse.
 *
 * @author Rohan Sahu
 */
=======
>>>>>>> Article new schema changes
@Data
public class UserResponse {

    /**
     * constructor of the userResponse.
<<<<<<< e228bf1867c95ddf1315bdd0522a560b7c87b6a5
     * @param id Id of the User.
     * @param firstName FirstName of the User.
     * @param lastName LastName of the User.
     * @param email Email of the User.
     */
    public UserResponse(final String id, final String firstName, final String lastName, final String email) {
        this.id = id;
=======
     */
    public UserResponse(String firstName, String lastName, String email) {
>>>>>>> Article new schema changes
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
<<<<<<< e228bf1867c95ddf1315bdd0522a560b7c87b6a5
     * id of the user.
     */
    private String id;

    /**
=======
>>>>>>> Article new schema changes
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
