package com.retondar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by thiagoretondar on 19/06/16.
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class PositionAlreadyOccupiedException extends Exception {

    public PositionAlreadyOccupiedException() {

    }

    public PositionAlreadyOccupiedException(String message) {
        super(message);
    }
}
