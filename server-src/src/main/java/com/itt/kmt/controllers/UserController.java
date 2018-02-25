
package com.itt.kmt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.itt.kmt.jwt.JWTUtil;
import com.itt.kmt.jwt.exception.UnauthorizedException;
import com.itt.kmt.models.User;
import com.itt.kmt.response.models.FailureResponseMsg;
import com.itt.kmt.response.models.ResponseMsg;
import com.itt.kmt.response.models.SuccessResponseMsg;
import com.itt.kmt.services.UserService;

/**
 * This class is responsible for exposing REST APis for User.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    /**
     * Service implementation for DB entity that provides retrieval methods.
     */
    @Autowired
    private UserService userService;

    /**
     * Login.
     * 
     * @param user the user
     * @return the success response msg
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json",
                    consumes = "application/json")
    public SuccessResponseMsg login(@RequestBody
    final User user) {

        if (user != null) {
            User dbUser = userService.getUserByEmail(user.getEmail());
            if (dbUser != null && dbUser.getPassword()
                                        .equals(user.getPassword())) {

                SuccessResponseMsg success = new SuccessResponseMsg(
                    new ResponseMsg(Boolean.TRUE, JWTUtil.sign(dbUser.getEmail(), dbUser.getPassword())));
                return success;
            } else {
                throw new UnauthorizedException();
            }
        } else {
            throw new UnauthorizedException();
        }
    }

    /**
     * Unauthorized.
     *
     * @return the failure response msg
     */
    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public FailureResponseMsg unauthorized() {

        FailureResponseMsg failure = new FailureResponseMsg(new ResponseMsg(Boolean.FALSE, "Unauthorized"));
        return failure;
    }
}
