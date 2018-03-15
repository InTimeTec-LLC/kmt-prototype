package com.itt.kmt.models;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a User.
 * 
 * @author Rakshit Rajeev
 */
@Data
@Getter
@Setter
public class User implements Serializable {
    /**
     * unique identifier.
     */
    @Id
    private String id;
    /**
     * roleType of the user.
     */
    @NotBlank
    private String  userRole;
    /**
     * firstname of the user.
     */
    @NotBlank
    private String firstName;
    /**
     * lastname of the user.
     */
    @NotBlank
    private String lastName;
    /**
     * email of the user.
     */
    @NotBlank
    @Email
    private String email;
    /**
     * last login date of the user.
     */
    private Date lastLogin;
    /**
     * joining date of the user.
     */
    private Date dateJoined;
    /**
     * active status of the user.
     */
    private boolean active = true;
    /**
     * password of the user.
     */
    private String password;
    /**
     * session of the user.
     */
    private boolean session = false;
}
