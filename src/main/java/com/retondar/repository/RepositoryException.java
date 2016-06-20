package com.retondar.repository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by thiagoretondar on 19/06/16.
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class RepositoryException extends Exception {

    public RepositoryException(String message) {
        super(message);
    }

}
