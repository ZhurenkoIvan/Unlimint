package org.example.exceptions;

public class WrongLineFormatException extends ArrayIndexOutOfBoundsException{
    @Override
    public String getMessage() {
        return "Неправильный формат строки";
    }
}
