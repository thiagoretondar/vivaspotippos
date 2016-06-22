package com.retondar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by thiagoretondar on 21/06/16.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundProperty extends Exception {

    public NotFoundProperty(String message) {
        super(message);
    }

}
