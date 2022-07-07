package com.assigment.exceptions;

public class NotFoundAuthenticationExecution extends RuntimeException {
    public NotFoundAuthenticationExecution() {
        super("Could not find authentication.");
    }
}
