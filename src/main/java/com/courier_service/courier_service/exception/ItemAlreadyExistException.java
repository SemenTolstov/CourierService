package com.courier_service.courier_service.exception;

public class ItemAlreadyExistException extends RuntimeException {
    public ItemAlreadyExistException(String errorMessage) {
        super(errorMessage);
    }

}
