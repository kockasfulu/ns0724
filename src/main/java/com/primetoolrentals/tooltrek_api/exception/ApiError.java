package com.primetoolrentals.tooltrek_api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * A class representing API error responses.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiError implements Serializable {

    @Serial
    private static final long serialVersionUID = 636263210082760663L;

    /**
     * The HTTP status code of the error.
     */
    private HttpStatus status;

    /**
     * The error message associated with the API error.
     */
    private String message;

    /**
     * A list of detailed error messages (if applicable).
     */
    private List<String> errors;
}
