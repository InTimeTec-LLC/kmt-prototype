package com.itt.kmt.models;

import lombok.Data;

@Data
public class UserResponse {

    /**
     * constructor of the userResponse.
     */
    public UserResponse(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

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
