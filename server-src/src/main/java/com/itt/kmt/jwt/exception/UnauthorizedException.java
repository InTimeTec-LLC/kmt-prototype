
package com.itt.kmt.jwt.exception;

/**
 * The Class UnauthorizedException.
 */
public class UnauthorizedException extends RuntimeException {

    /**
     * Instantiates a new unauthorized exception.
     *
     * @param msg the msg
     */
    public UnauthorizedException(final String msg) {

        super(msg);
    }

    /**
     * Instantiates a new unauthorized exception.
     */
    public UnauthorizedException() {

        super();
    }
}
