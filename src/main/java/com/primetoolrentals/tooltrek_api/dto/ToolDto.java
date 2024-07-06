package com.primetoolrentals.tooltrek_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * A Data Transfer Object for tools.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ToolDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 6660054083962021430L;

    /**
     * The ID of the tool.
     */
    private Long id;

    /**
     * The code of the tool.
     * Cannot be null and must match the pattern /^[A-Z]{4,10}$/ (4 to 10 uppercase letters).
     */
    @NotNull(message = "{tool.code.required}")
    @Pattern(regexp = "^[A-Z]{4,10}$", message = "{tool.code.format}")
    private String code;

    /**
     * The ID of the tool brand.
     * Cannot be null and must be a positive number.
     */
    @NotNull(message = "{tool.brand.required}")
    @Min(value = 1, message = "{tool.brand.invalid}")
    private Long toolBrandId;

    /**
     * The name of the tool brand (optional, for DTO use).
     */
    private String toolBrandName;

    /**
     * The ID of the tool type.
     * Cannot be null and must be a positive number.
     */
    @NotNull(message = "{tool.type.required}")
    @Min(value = 1, message = "{tool.type.invalid}")
    private Long toolTypeId;

    /**
     * The name of the tool type (optional, for DTO use).
     */
    private String toolTypeName;
}
