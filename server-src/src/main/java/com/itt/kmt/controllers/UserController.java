package com.itt.kmt.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
     * @param request request sent.
     * @param id id of the entity.
     * @return ModelMap.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @RequiresPermissions("getUserById")
    public ModelMap getUser(final HttpServletRequest request, @PathVariable("id") final String id) {
        String jwtToken = request.getHeader("Authorization");

        User loggedInUser = userService.getLoggedInUser(jwtToken);
        if (!loggedInUser.getUserRole().equals("admin")) {
            if (loggedInUser.getId().equals(id)) {
                loggedInUser.setPassword(null);
                return new ModelMap().addAttribute("user", loggedInUser);
            } else {
                throw new RuntimeException(Constants.USER_VIEWS_OTHER_USER_ERROR_MSG);
            }
        }

        User user = userService.getUserByID(id);
        return new ModelMap().addAttribute("user", user);
    }
    /**
     * REST Interface for users retrieval.
     * @param request request sent.
     * @param pageablePage page containing users.
     * @param role of the user.
     * @param status of the user.
     * @param search parameter to search user.
     * @return Page page of users.
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("getAllUser")
    public Page<User> getAllUsers(final HttpServletRequest request,
            @PageableDefault(value = Constants.PAGE_SIZE)final Pageable pageablePage,
            @RequestParam(required = false) final String role,
            @RequestParam(required = false) final Boolean status,
            @RequestParam(required = false) final String search) {

        String jwtToken = request.getHeader("Authorization");

        User loggedInUser = userService.getLoggedInUser(jwtToken);
        return userService.filterUsersByStatusAndRole(search, role, status, 
                loggedInUser.getEmail(), pageablePage);
    }
    /**
     * REST Interface for Admin and Managers retrieval.
     * @param request request sent.
     * @param pageablePage page containing users.
     * @param response response received.
     * @return ModelMap.
     */
    @RequestMapping(value = "/approvers", method = RequestMethod.GET)
    @RequiresPermissions("getAllApprovers")
    public ModelMap getAllApprovers(final HttpServletRequest request, final HttpServletResponse response, 
            final Pageable pageablePage) {

        String jwtToken = request.getHeader("Authorization");
        User loggedInUser = userService.getLoggedInUser(jwtToken);

        List<User> adminAndManager = new ArrayList<User>();

        adminAndManager.addAll(userService.getAllUsersByRolesAndStatus("admin", true, loggedInUser.getEmail(), 
                pageablePage).getContent());
        adminAndManager.addAll(userService.getAllUsersByRolesAndStatus("manager", true, loggedInUser.getEmail(), 
                pageablePage).getContent());
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
        if (active) {
            return new ModelMap().addAttribute("success", new ResponseMsg(true, Constants.USER_ACTIVATED_SUCCESS_MSG));
        } else {
            return new ModelMap().addAttribute("success", new ResponseMsg(true,
                    Constants.USER_DEACTIVATED_SUCCESS_MSG));
        }
    }
    /**
     * REST API to update a User.
     *
     * @param userRequest the user request
     * @param result the result
     * @param id Id of the user to be updated.
     * @param httpServletRequest HttpServletRequest.
     * @return ModelMap.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    @RequiresPermissions("updateUser")
    public ModelMap updateUser(@Valid @RequestBody


    final UserRequst userRequest, final BindingResult result, @PathVariable("id")
    final String id, final HttpServletRequest httpServletRequest) {

        User user = userRequest.getUser();
        String errorMsg = userService.validateUser(user, result);
        if (errorMsg != null && !errorMsg.isEmpty()) {
            ResponseMsg postResponseMsg = new ResponseMsg(false, errorMsg);
            return new ModelMap().addAttribute("success", postResponseMsg);
        }


        userService.updateUser(user, id, httpServletRequest.getHeader(Constants.AUTHORIZATION));
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