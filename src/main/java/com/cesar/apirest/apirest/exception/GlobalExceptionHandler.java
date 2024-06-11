package com.cesar.apirest.apirest.exception;

import com.cesar.apirest.apirest.item.exception.ItemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler handles exceptions thrown by controllers in the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ItemException thrown by controllers.
     * @param e The ItemException object.
     * @return ResponseEntity with HTTP status NOT_FOUND and the exception message.
     */
    @ExceptionHandler(ItemException.class)
    public ResponseEntity<String> handleItemException(ItemException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Handles generic Exception thrown by controllers.
     * @param e The Exception object.
     * @return ResponseEntity with HTTP status INTERNAL_SERVER_ERROR and a generic error message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }
}
