
package com.itt.kmt.jwt;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The Class JWTFilter.
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {

    /** The logger. */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
     * (non-Javadoc)
     * @see org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter#
     * isLoginAttempt(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse)
     */
    @Override
    protected boolean isLoginAttempt(final ServletRequest request, final ServletResponse response) {

        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        return authorization != null;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.apache.shiro.web.filter.authc.AuthenticatingFilter#executeLogin(javax
     * .servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    @Override
    protected boolean executeLogin(final ServletRequest request, final ServletResponse response)
        throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        JWTToken token = new JWTToken(authorization);
        getSubject(request, response).login(token);
        return true;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter#
     * isAccessAllowed(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, java.lang.Object)
     */
    @Override
    protected boolean isAccessAllowed(
        final ServletRequest request, final ServletResponse response, final Object mappedValue) {

        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                response401(request, response);
            }
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.apache.shiro.web.filter.PathMatchingFilter#preHandle(javax.servlet.
     * ServletRequest, javax.servlet.ServletResponse)
     */
    @Override
    protected boolean preHandle(final ServletRequest request, final ServletResponse response)
        throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader(
            "Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        if (httpServletRequest.getMethod()
                              .equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * Response 401.
     *
     * @param req the req
     * @param resp the resp
     */
    private void response401(final ServletRequest req, final ServletResponse resp) {

        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            httpServletResponse.sendRedirect("/api/users/401");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
