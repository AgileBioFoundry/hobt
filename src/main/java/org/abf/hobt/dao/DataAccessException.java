package org.abf.hobt.dao;

/**
 * RuntimeException thrown on issue trying to access data
 *
 * @author Hector Plahar
 */
public class DataAccessException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public DataAccessException() {
        super();
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }
}
