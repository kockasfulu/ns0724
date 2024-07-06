package com.primetoolrentals.tooltrek_api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

/**
 * A Data Transfer Object for rental requests.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RentalRequestDto {

    /**
     * The rental ID.
     */
    private Long rentalId;

    /**
     * The ID of the tool to be rented.
     * Cannot be null and must be a positive number.
     */
    @NotNull(message = "{rental.request.tool.required}")
    @Min(value = 1, message = "{rental.request.tool.invalid}")
    private Long toolId;

    /**
     * The number of days for the rental.
     * Cannot be null and must be a positive number.
     */
    @NotNull(message = "{rental.request.daycount.required}")
    @Min(value = 1, message = "{rental.request.daycount.invalid}")
    private Integer rentalDayCount;

    /**
     * The discount percentage for the rental.
     * Cannot be null and must be between 0 and 100.
     */
    @NotNull(message = "{rental.request.discount.required}")
    @Min(value = 0, message = "{rental.request.discount.invalid}")
    @Max(value = 100, message = "{rental.request.discount.invalid}")
    private Integer rentalDiscount;

    /**
     * The date when the rental starts.
     * Cannot be null and must follow the pattern yyyy-MM-dd.
     */
    @NotNull(message = "{rental.request.date.required}")
    @Pattern(regexp = "^((?:19|20)[0-9][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$", message = "{rental.request.date.invalid}")
    private String rentalDate;
}
