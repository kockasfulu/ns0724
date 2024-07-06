package com.primetoolrentals.tooltrek_api.dto;

import lombok.*;

/**
 * A Data Transfer Object for rental days details.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RentalDaysDto {

    /**
     * The total number of rental days.
     */
    private int totalRentalDays;

    /**
     * The number of rental days that are holidays.
     */
    private int holidaysRentalDays;

    /**
     * The number of rental days that are weekends.
     */
    private int weekendRentalDays;

    /**
     * The number of rental days that are weekdays.
     */
    private int weekdayRentalDays;
}
