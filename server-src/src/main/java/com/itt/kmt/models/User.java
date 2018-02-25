package com.itt.jmtemplate.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

/**
 * This class represents a User.
 * 
 * @author Rakshit Rajeev
 */
@Data
public class User implements Serializable {
    /**
     * roleType of the user.
     */
    private List<Role> roles;

    /**
     * username of the user.
     */
    private String username;
    /**
     * password of the user.
     */
    private String password;
    /**
     * firstname of the user.
     */
    private String firstname;
    /**
     * lastname of the user.
     */
    private String lastname;
    /**
     * email of the user.
     */
    private String email;
    /**
     * address of the user.
     */
    private String address;
    /**
     * phone of the user.
     */
    private int phone;
    /**
     * status of the user.
     */
    private boolean isActive;
    /**
     * unique identifier.
     */
    @Id
    private String id;
    /**
     * gets the username of the user.
     * @return username of the user.
     */
    public String getUsername() {
        return username;
    }
    /**
     * @param username Username of the user
     * sets the username of the user.
     */
    public void setUsername(final String username) {
        this.username = username;
    }
    /**
     * gets the password of the user.
     * @return password of the user
     */
    public String getPassword() {
	return password;
    }
    /**
     * @param password Password of the user
     * sets the password of the user.
     */
    public void setPassword(final String password) {
	this.password = password;
    }
    /**
     * gets the firstname of the user.
     * @return firstname of the user
     */
    public String getFirstname() {
	return firstname;
    }
    /**
     * @param firstname Firstname of the user
     * sets the firstname of the user.
     */
    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }
    /**
     * gets the lastname of the user.
     * @return lastname of the user
     */
    public String getLastname() {
        return lastname;
    }
    /**
     * @param lastname Lastname of the user
     * sets the lastname of the user.
     */
    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }
    /**
     * gets the email of the user.
     * @return email of the user
     */
    public String getEmail() {
	return email;
    }
    /**
     * @param email Email of the user
     * sets the email of the user.
     */
    public void setEmail(final String email) {
	this.email = email;
    }
    /**
     * gets the address of the user.
     * @return address of the user
     */
    public String getAddress() {
        return address;
    }
    /**
     * @param address Address of the user
     * sets the address of the user.
     */
    public void setAddress(final String address) {
        this.address = address;
    }
    /**
     * gets the phone of the user.
     * @return phone of the user
     */
    public int getPhone() {
        return phone;
    }
    /**
     * @param phone Phone of the user
     * sets the phone of the user.
     */
    public void setPhone(final int phone) {
        this.phone = phone;
    }		 
    /**
     * gets the roleType of the user.
     * @return roleType of the user
     */
    public List<Role> getRoles() {
        return roles;
    }
    /**
     * @param roleType RoleType of the user
     * sets the address of the user.
     */
    public void setRoles(final List<Role> roles) {
        this.roles = roles;
    }
    /**
     * gets the isActive status of the user.
     * @return isActive status of the user
     */
    public boolean getIsActive() {
        return isActive;
    }
    /**
     * @param isActive status of the user
     * sets the status of the user.
     */
    public void setIsActive(final boolean isActive) {
        this.isActive = isActive;
    }
}
