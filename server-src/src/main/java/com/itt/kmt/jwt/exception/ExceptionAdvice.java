
package com.itt.kmt.jwt.exception;

import com.itt.kmt.response.models.FailureResponseMsg;
import com.itt.kmt.response.models.ResponseMsg;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

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
    public FailureResponseMsg handle401(final ShiroException e) {

        FailureResponseMsg failure = new FailureResponseMsg(new ResponseMsg(Boolean.FALSE, e.getMessage()));
        return failure;
    }

    /**
     * Handle 401.
     *
     * @return the failure response msg
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public FailureResponseMsg handle401() {

        FailureResponseMsg failure = new FailureResponseMsg(new ResponseMsg(Boolean.FALSE, "Unauthorized"));
        return failure;
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
    public FailureResponseMsg globalException(final HttpServletRequest request, final Throwable ex) {

        FailureResponseMsg failure = new FailureResponseMsg(new ResponseMsg(Boolean.FALSE, ex.getMessage()));
        return failure;
    }
}
