package com.itt.jmtemplate.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

/**
 * This class represents Role.
 * @author Rakshit Rajeev
 */
@Data
public class Role {
    /**
     * roleName of Role.
     */
    private String roleName;
    /**
     * roleId of role.
     */
    private int roleId;
    /**
     * unique identifier.
     */
    @Id
    private String id;
    /**
     * permissions associated with role.
     */
    private List<Permission> permissions;
    /**
     * @return the permissions
     */
    public List<Permission> getPermissions() {
	return permissions;
    }
    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(List<Permission> permissions) {
	this.permissions = permissions;
    }
    /**
     * @return the roleName
     */
    public String getRoleName() {
	return roleName;
    }
    /**
     * @param roleName the roleName to set
     */
    public void setRoleName(String roleName) {
	this.roleName = roleName;
    }
    /**
     * @return the roleId
     */
    public int getRoleId() {
	return roleId;
    }
    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(int roleId) {
	this.roleId = roleId;
    }
}