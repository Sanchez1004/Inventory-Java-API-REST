package com.cesar.apirest.apirest.exception;

/**
 * Custom exception class for handling specific exceptions related to items.
 */
public class ItemException extends RuntimeException {

    /**
     * Constructs a new ItemException with the specified detail message.
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     */
    public ItemException(String message) {
        super(message);
    }

    /**
     * Constructs a new ItemException with the specified detail message and cause.
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause the cause (which is saved for later retrieval by the getCause() method).
     */
    public ItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
