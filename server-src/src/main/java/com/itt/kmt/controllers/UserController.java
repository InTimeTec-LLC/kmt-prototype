package com.itt.jmtemplate.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itt.jmtemplate.model.User;
import com.itt.jmtemplate.services.UserService;

/**
 * This class is responsible for exposing REST APis for User.
 */
@RestController
public class UserController {
    /**
     * Service implementation for DB entity that provides retrieval methods.
     */
    @Autowired
    private UserService userService;

    /**
     * REST API to add a new User.
     *
     * @param user the user to be added
     * @return User
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = "application/json")
    public final User add(@RequestBody
            final User user) {
        return userService.save(user);
    }

    /**
     * REST Interface for user retrieval.
     *
     * @param email Email of the entity.
     * @return Users.
     */
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public final User getUser(@RequestParam(value = "email", defaultValue = "admin@gmail.com", required = false)
            final String email) {
        User userByEmail = userService.getUserByEmail(email);
        return userByEmail;
    }
    /**
     * REST Interface for user retrieval.
     *
     * @param email Email of the entity.
     * @return Users.
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public final List<User> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users;
    }
    /**
     * REST API to delete a User.
     * @param email of the user to be added
     */
    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") final String id) {
        userService.deleteUserById(id);
    }
    /**
     * REST API to update a User.
     *
     * @param user the user to be updated
     * @return User
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT, produces = "application/json")
    public final User updateUser(@RequestBody
            final User user) {
        return userService.updateUser(user);
    }
}