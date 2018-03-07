package com.itt.kmt.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a class responsible to handle all whitelabel error issues.
 * 
 * @author ashish.y
 *
 */
@RestController
public class KMTErrorController implements ErrorController {
    /**
     * This is custom implementation of whitelabel issue.
     * when /error is called it will invoke this method,  
     * which in turn redirects to "/" which is index page
     * 
     *@param response , response is a reference of HttpServletResponse object, which we are use to redirect. 
     *@throws IOException .
     */
    @RequestMapping(value = "/errorPage", method = RequestMethod.GET)
    public void redirect(final HttpServletResponse response) throws IOException {
        response.sendRedirect("/");
    }

    /**
     * This is custom implementation of Angular Routes.
     * when a route like /login is called it will invoke this method,  
     * which in turn redirects to "/" which is index page
     * 
     *@param response , response is a reference of HttpServletResponse object, which we are use to redirect 
     */
    @RequestMapping(value = "/{[path:[^\\.]*}")
    public void redirectToIndex(final HttpServletResponse response)  {
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * override getErrorPath of error controller.
     * @return redirect to "/" index page
     */
    @Override
    public String getErrorPath() {
        return "redirect:/";
    }

}
