
package com.itt.kmt.models;

import org.springframework.data.annotation.Id;

import lombok.Data;

/**
 * This class represents Role.
 * 
 * @author Manoj Mewara
 */

@Data
public class Role {

    /**
     * unique identifier.
     */
    @Id
    private String id;
    /**
     * roleName of Role.
     */
    private String roleName;

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
    public void setId(final String id) {

        this.id = id;
    }

    /**
     * Gets the role name.
     *
     * @return the role name
     */
    public String getRoleName() {

        return roleName;
    }

    /**
     * Sets the role name.
     *
     * @param roleName the new role name
     */
    public void setRoleName(final String roleName) {

        this.roleName = roleName;
    }
}
