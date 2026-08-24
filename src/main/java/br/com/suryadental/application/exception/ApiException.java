package br.com.suryadental.application.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
