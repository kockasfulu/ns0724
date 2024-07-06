package com.primetoolrentals.tooltrek_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * Exception thrown when a requested tool is not found.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Selected tool does not exist")
public class ToolNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -8277137234381764784L;
}
