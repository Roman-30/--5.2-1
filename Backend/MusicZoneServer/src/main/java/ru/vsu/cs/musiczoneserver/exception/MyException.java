package ru.vsu.cs.musiczoneserver.exception;

public class MyException extends Exception {
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public MyException(String message) {
        super(message);
    }
}
