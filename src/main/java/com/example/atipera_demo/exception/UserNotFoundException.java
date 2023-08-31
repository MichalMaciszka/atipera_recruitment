package com.example.atipera_demo.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException() {
        super("User was not found");
    }
}
