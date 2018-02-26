
package com.itt.kmt.jwt;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class PermissionMatrix.
 */
public class PermissionMatrix {

    /** The role. */
    private String role;

    /** The permissions. */
    private List<String> permissions = new ArrayList<String>();

    /**
     * Gets the role.
     *
     * @return the role
     */
    public String getRole() {

        return role;
    }

    /**
     * Sets the role.
     *
     * @param role the new role
     */
    public void setRole(final String role) {

        this.role = role;
    }

    /**
     * Gets the permissions.
     *
     * @return the permissions
     */
    public List<String> getPermissions() {

        return permissions;
    }

    /**
     * Sets the permissions.
     *
     * @param permissions the new permissions
     */
    public void setPermissions(final List<String> permissions) {

        this.permissions = permissions;
    }

}
