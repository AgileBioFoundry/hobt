package org.abf.hobt.account.authentication;

/**
 * Exception class for the Authentication
 *
 * @author Hector Plahar
 */
public class AuthenticationException extends Exception {

    private static final long serialVersionUID = 1l;

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
