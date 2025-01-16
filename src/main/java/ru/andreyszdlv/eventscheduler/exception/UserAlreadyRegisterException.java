package ru.andreyszdlv.eventscheduler.exception;

public class UserAlreadyRegisterException extends RuntimeException {
    public UserAlreadyRegisterException(String message) {
        super(message);
    }
}
