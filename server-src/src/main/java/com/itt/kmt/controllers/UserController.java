package com.itt.kmt.controllers;

import java.util.HashMap;
import java.util.List;

import com.itt.kmt.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itt.kmt.models.User;
import com.itt.kmt.repositories.UserRepository;
import com.itt.kmt.response.models.ResponseMsg;
import com.itt.kmt.services.UserService;

/**
 * This class is responsible for exposing REST APis for User.
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {
    /**
     * Service implementation for DB entity that provides retrieval methods.
     */
    @Autowired
    private UserService userService;
    /**
     * repository implementation for DB entity that provides retrieval methods.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * REST API to add a new User.
     *
     * @param map the map which contains the user to be added.
     * @return ModelMap
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public final ModelMap add(@RequestBody
            final HashMap<String, User> map) {
        User user = map.get("user");
        userService.save(user);
        ResponseMsg postResponseMsg = new ResponseMsg(true, "added successfully");
        return new ModelMap().addAttribute("success", postResponseMsg);
    }
    /**
     * REST Interface for user retrieval.
     *
     * @param id id of the entity.
     * @return ModelMap.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelMap getUser(@PathVariable("id") final String id) {
        User user = userRepository.findOne(id);
        return new ModelMap().addAttribute("user", user);
    }
    /**
     * REST Interface for users retrieval.
     * @return ModelMap.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelMap getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ModelMap().addAttribute("users", users);
    }
    /**
     * REST API to delete a User.
     * @param id Id of the user to be deleted.
     * @return ModelMap.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ModelMap deleteUser(@PathVariable("id") final String id) {
        userService.deleteUserById(id);
        ResponseMsg deleteResponseMsg = new ResponseMsg(true, "deleted successfully");
        return new ModelMap().addAttribute("success", deleteResponseMsg);
    }
    /**
     * REST API to update a User.
     * @param map the map that contains the user to be updated.
     * @param id Id of the user to be updated.
     * @return ModelMap.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ModelMap updateUser(@RequestBody
            final HashMap<String, User> map, @PathVariable("id") final String id) {
        User user = map.get("user");
        userService.updateUser(user, id);
        ResponseMsg updateResponseMsg = new ResponseMsg(true, "updated successfully");
        return new ModelMap().addAttribute("success", updateResponseMsg);
    }

    /**
     * REST API to return all Roles.
     * @return ModelMap.
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public ModelMap getUserRoles() {
        List<Role> roleList = userService.getUserRoles();
        return new ModelMap().addAttribute("roles", roleList);
    }
}