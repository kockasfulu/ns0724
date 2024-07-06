package com.primetoolrentals.tooltrek_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * A Data Transfer Object for tool types.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ToolTypeDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 7556354162023715355L;

    /**
     * The ID of the tool type.
     */
    private Long id;

    /**
     * The name of the tool type.
     * Cannot be null and must be between 2 and 30 characters long.
     */
    @NotNull(message = "{type.name.required}")
    @Size(min = 2, max = 30, message = "{type.name.length}")
    private String name;

    /**
     * The daily rental charge for the tool type.
     * Cannot be null and must be a positive number.
     */
    @NotNull(message = "{type.charge.amount.required}")
    @Min(value = 0, message = "{type.charge.amount.invalid}")
    private Double dailyCharge;

    /**
     * Flag indicating if weekday charges apply for the tool type.
     * Cannot be null.
     */
    @NotNull(message = "{type.weekday.charge.required}")
    private Boolean isWeekdayCharge;

    /**
     * Flag indicating if weekend charges apply for the tool type.
     * Cannot be null.
     */
    @NotNull(message = "{type.weekend.charge.required}")
    private Boolean isWeekendCharge;

    /**
     * Flag indicating if holiday charges apply for the tool type.
     * Cannot be null.
     */
    @NotNull(message = "{type.holiday.charge.required}")
    private Boolean isHolidayCharge;
}
