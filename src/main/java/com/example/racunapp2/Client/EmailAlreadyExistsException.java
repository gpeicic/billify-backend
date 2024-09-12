package com.example.racunapp2.Client;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String message) {
            super(message);
            }
}
