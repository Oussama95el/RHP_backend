package com.simplon.rhp.Exeptions;

public class UsernameExistException extends Exception {
    public UsernameExistException(String errorMessage) {
        super(errorMessage);
    }
}
