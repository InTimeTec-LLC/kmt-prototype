package com.itt.kmt.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;


/**
 * This class represents a User.
 *
 * @author Rakshit Rajeev
 */
@Getter
@Setter
@Data
public class Role implements Serializable {

    /**
     * unique identifier.
     */
    @Id
    private String id;

    /**
     * roleType of the user.
     */
    private String role;
}
