package com.primetoolrentals.tooltrek_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * Exception thrown when a requested tool brand is not found.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Selected tool brand does not exist")
public class ToolBrandNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5488773077439864885L;
}
