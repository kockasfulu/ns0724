package com.primetoolrentals.tooltrek_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * Exception thrown when a requested tool type is not found.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Selected tool type does not exist")
public class ToolTypeNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -2890803289452940691L;
}
