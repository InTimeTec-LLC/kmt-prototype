
package com.itt.kmt.controllers;

import javax.servlet.http.HttpServletRequest;

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
import com.itt.kmt.models.User;
import com.itt.kmt.response.models.LoginResponseMsg;
import com.itt.kmt.response.models.ResponseMsg;
import com.itt.kmt.services.UserService;
import com.itt.utility.Constants;

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
            String jwtToken = "";
            if (dbUser != null && dbUser.getPassword()
                                        .equals(user.getPassword()) && dbUser.isActive()) {
                LoginResponseMsg responseMsg = new LoginResponseMsg();

                jwtToken = JWTUtil.sign(dbUser.getEmail(), dbUser.getPassword());
                
                LoginResponseMsg.StatusMsg statusMsg = responseMsg.new StatusMsg();
                statusMsg.setStatus(Boolean.TRUE);
                statusMsg.setAccessToken(jwtToken);

                // Updating user login status as true
                userService.setUserSession(jwtToken, Boolean.TRUE);
                
                dbUser.setPassword(null);
                responseMsg.setUser(dbUser);
                responseMsg.setSuccess(statusMsg);
                return responseMsg;
            } else {
                throw new UnauthorizedException();
            }
    }

    /**
     * Logout.
     *
     * @param request the request
     * @return the model map
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET, produces = "application/json",
                    consumes = "application/json")
    public ModelMap logout(final HttpServletRequest request) {
        
        String jwtToken = request.getHeader("Authorization");

        userService.setUserSession(jwtToken, Boolean.FALSE);
        
        ResponseMsg postResponseMsg = new ResponseMsg(Boolean.TRUE, Constants.USER_LOGOUT_SUCCESS_MSG);
        return new ModelMap().addAttribute("success", postResponseMsg);
    }
    
    /**
     * Unauthorized.
     *
     * @return the failure response msg
     */
    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelMap unauthorized() {

        ResponseMsg unauthorizedAccessMsg = new ResponseMsg(false, Constants.UNAUTHORIZED_ACCESS_MSG);
        return new ModelMap().addAttribute("success", unauthorizedAccessMsg);
    }
}
