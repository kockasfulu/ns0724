package com.primetoolrentals.tooltrek_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * A Data Transfer Object for tool brands.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ToolBrandDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 9175435729561750306L;

    /**
     * The ID of the tool brand.
     */
    private Long id;

    /**
     * The name of the tool brand.
     * Cannot be null and must be between 2 and 30 characters long.
     */
    @NotNull(message = "{brand.name.required}")
    @Size(min = 2, max = 30, message = "{brand.name.length}")
    private String name;
}
