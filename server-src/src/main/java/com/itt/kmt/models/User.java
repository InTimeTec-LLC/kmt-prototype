
package com.itt.kmt.models;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

/**
 * This class represents User.
 * 
 * @author Manoj Mewara
 */

@Data
public class User implements Serializable {

    /**
     * unique identifier.
     */
    @Id
    private String id;
    /**
     * roleType of the user.
     */
    private List<Role> roles;

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
    private String phone;
    /**
     * status of the user.
     */
    private boolean isActive;

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
     * gets the password of the user.
     * 
     * @return password of the user
     */
    public String getPassword() {

        return password;
    }

    /**
     * Sets the password.
     *
     * @param password Password of the user sets the password of the user.
     */
    public void setPassword(final String password) {

        this.password = password;
    }

    /**
     * gets the firstname of the user.
     * 
     * @return firstname of the user
     */
    public String getFirstname() {

        return firstname;
    }

    /**
     * Sets the firstname.
     *
     * @param firstname Firstname of the user sets the firstname of the user.
     */
    public void setFirstname(final String firstname) {

        this.firstname = firstname;
    }

    /**
     * gets the lastname of the user.
     * 
     * @return lastname of the user
     */
    public String getLastname() {

        return lastname;
    }

    /**
     * Sets the lastname.
     *
     * @param lastname Lastname of the user sets the lastname of the user.
     */
    public void setLastname(final String lastname) {

        this.lastname = lastname;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {

        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(final String email) {

        this.email = email;
    }

    /**
     * gets the address of the user.
     * 
     * @return address of the user
     */
    public String getAddress() {

        return address;
    }

    /**
     * Sets the address.
     *
     * @param address Address of the user sets the address of the user.
     */
    public void setAddress(final String address) {

        this.address = address;
    }

    /**
     * gets the phone of the user.
     * 
     * @return phone of the user
     */
    public String getPhone() {

        return phone;
    }

    /**
     * Sets the phone.
     *
     * @param phone Phone of the user sets the phone of the user.
     */
    public void setPhone(final String phone) {

        this.phone = phone;
    }

    /**
     * gets the roleType of the user.
     * 
     * @return roleType of the user
     */
    public List<Role> getRoles() {

        return roles;
    }

    /**
     * Sets the roles.
     *
     * @param roles the new roles
     */
    public void setRoles(final List<Role> roles) {

        this.roles = roles;
    }

    /**
     * Checks if is active.
     *
     * @return true, if is active
     */
    public boolean isActive() {

        return isActive;
    }

    /**
     * Sets the active.
     *
     * @param isActive the new active
     */
    public void setActive(final boolean isActive) {

        this.isActive = isActive;
    }

}
