package org.example.exceptions;

public class WrongNumbersException extends IllegalArgumentException{
    @Override
    public String getMessage() {
        return "Wrong amount or ID";
    }
}
