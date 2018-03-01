
package com.itt.kmt.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * The Class JWTToken.
 */
public class JWTToken implements AuthenticationToken {

    /** The token. */
    private String token;

    /**
     * Instantiates a new JWT token.
     *
     * @param token the token
     */
    public JWTToken(final String token) {

        this.token = token;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.shiro.authc.AuthenticationToken#getPrincipal()
     */
    @Override
    public Object getPrincipal() {

        return token;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.shiro.authc.AuthenticationToken#getCredentials()
     */
    @Override
    public Object getCredentials() {

        return token;
    }
}
