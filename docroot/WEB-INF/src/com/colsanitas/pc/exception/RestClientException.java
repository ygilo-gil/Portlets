package com.colsanitas.pc.exception;

/**
 * @author Andres Felipe Gonzalez (Ticxar SAS)
 *
 */
public class RestClientException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public RestClientException(String message) {
        super(message);
    }

    public RestClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestClientException(Throwable cause) {
        super(cause);
    }

}
