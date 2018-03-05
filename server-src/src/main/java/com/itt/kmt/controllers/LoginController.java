
package com.itt.kmt.controllers;

import com.itt.kmt.models.User;
import com.itt.kmt.response.models.ResponseMsg;
import com.itt.kmt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.itt.kmt.jwt.JWTUtil;
import com.itt.kmt.jwt.exception.UnauthorizedException;
import com.itt.kmt.response.models.LoginResponseMsg;

/**
 * This class is responsible for exposing REST APis for User.
 */
@RestController
public class LoginController {

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
    public LoginResponseMsg login(@RequestBody final User user) {

            User dbUser = userService.getUserByEmail(user.getEmail());

            if (dbUser != null && dbUser.getPassword()
                                        .equals(user.getPassword())) {
                LoginResponseMsg responseMsg =
                    new LoginResponseMsg(Boolean.TRUE, JWTUtil.sign(dbUser.getEmail(), dbUser.getPassword()));

                dbUser.setPassword(null);
                responseMsg.setUser(dbUser);

                return responseMsg;
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
    public ModelMap unauthorized() {

        ResponseMsg unauthorizedAccessMsg = new ResponseMsg(false, "Unauthorized access");
        return new ModelMap().addAttribute("success", unauthorizedAccessMsg);
    }
}
