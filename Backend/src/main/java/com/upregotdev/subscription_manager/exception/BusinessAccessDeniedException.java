package com.upregotdev.subscription_manager.exception;

public class BusinessAccessDeniedException extends RuntimeException {
    public BusinessAccessDeniedException(String message) {
        super(message);
    }
}