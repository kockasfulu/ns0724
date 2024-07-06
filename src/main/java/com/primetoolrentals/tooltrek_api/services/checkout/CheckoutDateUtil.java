package com.primetoolrentals.tooltrek_api.services.checkout;

import com.primetoolrentals.tooltrek_api.dto.RentalDaysDto;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for checkout date calculations.
 */
@Service
public class CheckoutDateUtil {

    private static final int FOURTH_DAY = 4;
    public static DateTimeFormatter RENTAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yy");

    /**
     * Formats a rental date into a string representation.
     *
     * @param rentalDate The rental date to format.
     * @return The formatted string representation of the rental date.
     */
    public static String formatRentalDate(LocalDate rentalDate) {
        return RENTAL_DATE_FORMATTER.format(rentalDate);
    }

    /**
     * Calculates the due date based on the checkout date and rental days.
     *
     * @param checkoutDate The checkout date.
     * @param rentalDays   The number of rental days.
     * @return The due date.
     */
    public static LocalDate calculateDueDate(LocalDate checkoutDate, int rentalDays) {
        return checkoutDate.plusDays(rentalDays);
    }

    /**
     * Checks if a given date is a weekend (Saturday or Sunday).
     *
     * @param date The date to check.
     * @return True if the date is a weekend, false otherwise.
     */
    public static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    /**
     * Checks if a given date is Labor Day (first Monday in September).
     *
     * @param localDate The date to check.
     * @return True if the date is Labor Day, false otherwise.
     */
    public static boolean isLaborDay(LocalDate localDate) {
        return localDate.getMonth() == Month.SEPTEMBER && localDate.getDayOfMonth() < 8 && localDate.getDayOfWeek().equals(DayOfWeek.MONDAY);
    }

    /**
     * Checks if a given date is Independence Day (July 4th).
     *
     * @param localDate The date to check.
     * @return True if the date is Independence Day, false otherwise.
     */
    public static boolean isIndependenceDay(LocalDate localDate) {
        return localDate.getMonth() == Month.JULY && localDate.getDayOfMonth() == FOURTH_DAY;
    }

    /**
     * Calculates the number of chargeable rental days based on checkout date and rental days.
     *
     * @param checkoutDate The checkout date.
     * @param rentalDays   The number of rental days.
     * @return RentalDaysDto containing the breakdown of rental days (total, weekday, holidays, weekends).
     */
    public static RentalDaysDto numberOfChargeDays(LocalDate checkoutDate, int rentalDays) {
        int holidayRentalDays = 0;
        int weekendRentalDays = 0;
        int weekdayRentalDays = 0;
        boolean observeIndependenceDay = false;

        LocalDate rentalDay;
        for (int i = 1; i <= rentalDays; i++) {
            rentalDay = checkoutDate.plusDays(i);

            // Check if the rental day is Labor Day
            if (isLaborDay(rentalDay)) {
                holidayRentalDays++;
                continue;
            }

            // Check if the rental day is Independence Day
            if (isIndependenceDay(rentalDay)) {
                if (!isWeekend(rentalDay)) {
                    holidayRentalDays++;
                } else {
                    weekendRentalDays++;
                    if (rentalDay.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                        if (weekdayRentalDays > 0) {
                            holidayRentalDays++;
                            weekdayRentalDays--;
                        }
                    } else {
                        observeIndependenceDay = true;
                    }
                }
                continue;
            }

            // Check if the rental day is a weekend
            if (isWeekend(rentalDay)) {
                weekendRentalDays++;
            }

            // Check if the rental day is a weekday
            if (!isWeekend(rentalDay)) {
                if (observeIndependenceDay) {
                    holidayRentalDays++;
                    observeIndependenceDay = false;
                } else {
                    weekdayRentalDays++;
                }
                continue;
            }
        }

        return RentalDaysDto.builder()
                .totalRentalDays(weekdayRentalDays + holidayRentalDays + weekendRentalDays)
                .weekdayRentalDays(weekdayRentalDays)
                .holidaysRentalDays(holidayRentalDays)
                .weekendRentalDays(weekendRentalDays)
                .build();
    }
}
