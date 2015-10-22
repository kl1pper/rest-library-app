 package ru.dmitry.library.exceptions;

/**
 * Ошибка на сервере
 */
public class ServiceErrorException extends Exception {

    public ServiceErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceErrorException(String message) {
        super(message);
    }

}
