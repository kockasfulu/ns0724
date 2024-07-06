package com.primetoolrentals.tooltrek_api.controller.rental;

import com.primetoolrentals.tooltrek_api.services.checkout.CheckoutCalculationUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for CheckoutCalculationUtil methods.
 */
public class CheckoutCalculationUtilTest {

    /**
     * Parameterized test to validate formatting of currency amounts.
     *
     * @param amount                  the amount to format.
     * @param expectedFormattedAmount the expected formatted amount.
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/calculationUtils.csv", numLinesToSkip = 1)
    public void validateNumberOfChargeDays(String amount, String expectedFormattedAmount) {
        // Convert amount to double, round it using CheckoutCalculationUtil, then format it as currency
        String result = CheckoutCalculationUtil.formatCurrencyAmount(CheckoutCalculationUtil.roundCalculationResult(Double.parseDouble(amount)));
        assertEquals(expectedFormattedAmount, result);
    }
}
