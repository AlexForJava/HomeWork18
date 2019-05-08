package com.gmail.exceptions;

/**
 * Created by Space on 08.05.2019.
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        super();
    }
}
