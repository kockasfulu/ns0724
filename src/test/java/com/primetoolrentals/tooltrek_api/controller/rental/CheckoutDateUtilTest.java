package com.primetoolrentals.tooltrek_api.controller.rental;

import com.primetoolrentals.tooltrek_api.dto.RentalDaysDto;
import com.primetoolrentals.tooltrek_api.services.checkout.CheckoutDateUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CheckoutDateUtil methods.
 */
public class CheckoutDateUtilTest {

    /**
     * Tests CheckoutDateUtil.isWeekend method for weekdays.
     *
     * @param localDate the date to test.
     */
    @ParameterizedTest
    @ValueSource(strings = {"2024-07-08", "2024-07-09", "2024-07-10", "2024-07-11", "2024-07-12"})
    public void isWeekendUtilReturnsFalseForWeekdays(String localDate) {
        LocalDate date = LocalDate.parse(localDate);
        assertFalse(CheckoutDateUtil.isWeekend(date));
    }

    /**
     * Tests CheckoutDateUtil.isWeekend method for weekends.
     *
     * @param localDate the date to test.
     */
    @ParameterizedTest
    @ValueSource(strings = {"2024-07-13", "2024-07-14"})
    public void isWeekendUtilReturnsTrueForWeekends(String localDate) {
        LocalDate date = LocalDate.parse(localDate);
        assertTrue(CheckoutDateUtil.isWeekend(date));
    }

    /**
     * Tests CheckoutDateUtil.isLaborDay method for non-Labor Day dates.
     *
     * @param localDate the date to test.
     */
    @ParameterizedTest
    @ValueSource(strings = {"2024-08-02", "2024-10-02", "2024-09-01", "2024-09-03", "2024-09-04", "2024-09-05", "2024-09-06", "2024-09-07", "2024-09-08"})
    public void isLaborDayReturnsFalseIfNotLaborDay(String localDate) {
        LocalDate date = LocalDate.parse(localDate);
        assertFalse(CheckoutDateUtil.isLaborDay(date));
    }

    /**
     * Tests CheckoutDateUtil.isLaborDay method for Labor Day dates.
     *
     * @param localDate the date to test.
     */
    @ParameterizedTest
    @ValueSource(strings = {"2024-09-02", "2025-09-01", "2023-09-04"})
    public void isLaborDayReturnsTrueIfLaborDay(String localDate) {
        LocalDate date = LocalDate.parse(localDate);
        assertTrue(CheckoutDateUtil.isLaborDay(date));
    }

    /**
     * Tests CheckoutDateUtil.isIndependenceDay method for non-Independence Day dates.
     *
     * @param localDate the date to test.
     */
    @ParameterizedTest
    @ValueSource(strings = {"2024-06-04", "2024-09-04", "2024-07-01", "2024-07-02", "2024-07-03", "2024-07-05", "2024-07-06", "2024-07-07", "2024-07-08", "2024-07-09", "2024-07-10", "2024-07-11", "2024-07-12"})
    public void isIndependenceDayReturnsFalseIfNotIndependenceDay(String localDate) {
        LocalDate date = LocalDate.parse(localDate);
        assertFalse(CheckoutDateUtil.isIndependenceDay(date));
    }

    /**
     * Tests CheckoutDateUtil.isIndependenceDay method for Independence Day dates.
     *
     * @param localDate the date to test.
     */
    @ParameterizedTest
    @ValueSource(strings = {"2024-07-04", "2025-07-04", "2023-07-04"})
    public void isIndependenceDayReturnsTrueIfIndependenceDay(String localDate) {
        LocalDate date = LocalDate.parse(localDate);
        assertTrue(CheckoutDateUtil.isIndependenceDay(date));
    }

    /**
     * Tests CheckoutDateUtil.numberOfChargeDays method using CSV file as a data source.
     *
     * @param localDate            the date to test.
     * @param rentalDayCount       the rental day count.
     * @param expectedWeekdayRentalDays expected number of weekday rental days.
     * @param expectedWeekendRentalDays expected number of weekend rental days.
     * @param expectedHolidaysRentalDays expected number of holidays rental days.
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/dateUtils.csv", numLinesToSkip = 1)
    public void validateNumberOfChargeDays(String localDate, String rentalDayCount, String expectedWeekdayRentalDays, String expectedWeekendRentalDays, String expectedHolidaysRentalDays) {
        LocalDate date = LocalDate.parse(localDate);
        RentalDaysDto rentalDays = CheckoutDateUtil.numberOfChargeDays(date, Integer.parseInt(rentalDayCount));
        assertEquals(Integer.parseInt(expectedWeekdayRentalDays), rentalDays.getWeekdayRentalDays());
        assertEquals(Integer.parseInt(expectedWeekendRentalDays), rentalDays.getWeekendRentalDays());
        assertEquals(Integer.parseInt(expectedHolidaysRentalDays), rentalDays.getHolidaysRentalDays());
        assertEquals(Integer.parseInt(rentalDayCount), rentalDays.getTotalRentalDays());
    }

    /**
     * Tests CheckoutDateUtil.formatRentalDate method.
     *
     * @param localDate     the date to format.
     * @param expectedFormat the expected formatted date string.
     */
    @ParameterizedTest
    @CsvSource(value = {"2024-07-04,07/04/24", "2023-11-13,11/13/23", "2001-01-01,01/01/01"})
    public void rentalDayFormatterTest(String localDate, String expectedFormat) {
        LocalDate date = LocalDate.parse(localDate);
        assertEquals(expectedFormat, CheckoutDateUtil.formatRentalDate(date));
    }

    /**
     * Tests CheckoutDateUtil.calculateDueDate method.
     *
     * @param localDate      the start date.
     * @param rentalDayCount the rental day count.
     * @param expectedDate   the expected due date.
     */
    @ParameterizedTest
    @CsvSource(value = {"2024-07-01,1,2024-07-02", "2024-07-01,2,2024-07-03", "2024-07-01,3,2024-07-04"})
    public void calculateDueDateTest(String localDate, String rentalDayCount, String expectedDate) {
        LocalDate date = LocalDate.parse(localDate);
        LocalDate expectedDueDate = LocalDate.parse(expectedDate);
        LocalDate dueDate = CheckoutDateUtil.calculateDueDate(date, Integer.parseInt(rentalDayCount));
        assertEquals(expectedDueDate, dueDate);
    }
}
