package com.win.itemsharingplatform.exception;

public class InvalidFileException extends RuntimeException {
    public InvalidFileException(String contentType) {
        super(String.format("Failas yra netinkamo formato (%s).", contentType));
    }
}
