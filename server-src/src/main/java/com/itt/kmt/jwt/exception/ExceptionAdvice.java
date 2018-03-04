
package com.itt.kmt.jwt.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.itt.kmt.response.models.ResponseMsg;
/**
 * The Class ExceptionAdvice.
 */
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * Handle 401.
     *
     * @param e the e
     * @return the failure response msg
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ModelMap handle401(final ShiroException e) {
e.printStackTrace();
        return new ModelMap().addAttribute("success", new ResponseMsg(Boolean.FALSE, "Unauthorized access"));
    }

    /**
     * Handle 401.
     *
     * @return the failure response msg
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ModelMap handle401() {
        return new ModelMap().addAttribute("success", new ResponseMsg(Boolean.FALSE, "Unauthorized access"));
    }

    /**
     * Global exception.
     *
     * @param request the request
     * @param ex the ex
     * @return the failure response msg
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelMap globalException(final HttpServletRequest request, final Throwable ex) {
        ex.printStackTrace();
        
        return new ModelMap().addAttribute("success", new ResponseMsg(Boolean.FALSE, "Bad Request"));
    }
}
