package com.seamfix.tutorial.test.jee.runner;

public class EjbWithMockitoRunnerException extends RuntimeException {
    public EjbWithMockitoRunnerException(String message) {
        super(message);
    }

    public EjbWithMockitoRunnerException(String message, Throwable cause) {
        super(message, cause);
    }
}
