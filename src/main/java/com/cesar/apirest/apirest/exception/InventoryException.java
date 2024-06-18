package com.cesar.apirest.apirest.exception;

public class InventoryException extends RuntimeException {
    /**
     * Constructs a new InventoryException with the specified detail message.
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     */
    public InventoryException(String message) {
        super(message);
    }

    /**
     * Constructs a new InventoryException with the specified detail message and cause.
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause the cause (which is saved for later retrieval by the getCause() method).
     */
    public InventoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
