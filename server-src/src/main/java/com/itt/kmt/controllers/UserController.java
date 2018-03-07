package com.itt.kmt.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itt.kmt.models.Role;
import com.itt.kmt.models.User;
import com.itt.kmt.request.models.UserRequst;
import com.itt.kmt.response.models.ResponseMsg;
import com.itt.kmt.services.UserService;
import com.itt.utility.Constants;

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
     * REST API to add a new User.
     *
     * @param userRequest the user request
     * @param result the result
     * @return the model map
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @RequiresPermissions("addUser")
    public ModelMap add(@Valid
    @RequestBody
    final UserRequst userRequest, final BindingResult result) {

        User user = userRequest.getUser();
        String errorMsg = userService.validateUser(user, result);
        if (errorMsg != null && !errorMsg.isEmpty()) {
            
            ResponseMsg postResponseMsg = new ResponseMsg(false, errorMsg);
            return new ModelMap().addAttribute("success", postResponseMsg);
        }

        userService.save(user);
        ResponseMsg postResponseMsg = new ResponseMsg(true, Constants.USER_ADDED_SUCCESS_MSG);
        return new ModelMap().addAttribute("success", postResponseMsg);
    }
    /**
     * REST Interface for user retrieval.
     *
     * @param id id of the entity.
     * @return ModelMap.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @RequiresPermissions("getUserById")
    public ModelMap getUser(@PathVariable("id") final String id) {
        User user = userService.getUserByID(id);
        return new ModelMap().addAttribute("user", user);
    }
    /**
     * REST Interface for users retrieval.
     * @param request request sent.
     * @param response response received.
     * @return ModelMap.
     * @throws Exception.
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("getAllUser")
    public ModelMap getAllUsers(final HttpServletRequest request, final HttpServletResponse response) {

        String jwtToken = request.getHeader("Authorization");
        User loggedInUser = userService.getLoggedInUser(jwtToken);

        List<User> users = userService.getAllUsers();
        users.remove(loggedInUser);

        return new ModelMap().addAttribute("users", users);
    }
    /**
     * REST Interface for Admin and Managers retrieval.
     * @param request request sent.
     * @param response response received.
     * @return ModelMap.
     */
    @RequestMapping(value = "/approvers", method = RequestMethod.GET)
    @RequiresPermissions("getAllApprovers")
    public ModelMap getAllApprovers(final HttpServletRequest request, final HttpServletResponse response) {

        String jwtToken = request.getHeader("Authorization");
        User loggedInUser = userService.getLoggedInUser(jwtToken);

        List<User> adminAndManager = new ArrayList<User>();
        List<String> roles = new ArrayList<String>();
        roles.add("admin");
        roles.add("manager");
        adminAndManager.addAll(userService.getAllActiveUsersByRoles(roles));
        adminAndManager.remove(loggedInUser);

        return new ModelMap().addAttribute("users", adminAndManager);
    }
    /**
     * REST API to change status of a User.
     * @param id id of the user.
     * @param active status of the user.
     * @return ModelMap.
     */
    @RequestMapping(value = "/state/{id}/{active}", method = RequestMethod.PUT, produces = "application/json")
    @RequiresPermissions("changeUserStatus")
    public ModelMap changeUserStatus(@PathVariable("id") final String id, 
            @PathVariable("active") final boolean active) {
        userService.changeUserStatus(id, active);
        ResponseMsg activateResponseMsg;
        if (active) {
            activateResponseMsg = new ResponseMsg(true, "activated successfully");
        } else {
            activateResponseMsg = new ResponseMsg(true, "deactivated successfully"); 
        }

        return new ModelMap().addAttribute("success", activateResponseMsg);
    }
    /**
     * REST API to update a User.
     *
     * @param userRequest the user request
     * @param result the result
     * @param id Id of the user to be updated.
     * @return ModelMap.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    @RequiresPermissions("updateUser")
    public ModelMap updateUser(@Valid @RequestBody
    final UserRequst userRequest, final BindingResult result, @PathVariable("id")
    final String id) {

        User user = userRequest.getUser();
        String errorMsg = userService.validateUser(user, result);
        if (errorMsg != null && !errorMsg.isEmpty()) {
            
            ResponseMsg postResponseMsg = new ResponseMsg(false, errorMsg);
            return new ModelMap().addAttribute("success", postResponseMsg);
        }
        
        userService.updateUser(user, id);
        ResponseMsg updateResponseMsg = new ResponseMsg(true, Constants.DEFAULT_UPDATE_SUCCESS_MSG);
        return new ModelMap().addAttribute("success", updateResponseMsg);
    }
    /**
     * REST API to return all Roles.
     * @return ModelMap.
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    @RequiresPermissions("getAllRoles")
    public ModelMap getUserRoles() {
        List<Role> roleList = userService.getUserRoles();
        return new ModelMap().addAttribute("roles", roleList);
    }
}