package com.primetoolrentals.tooltrek_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * Exception thrown when a requested rental is not found.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Selected rental does not exist")
public class RentalNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -8932400711754545030L;
}
