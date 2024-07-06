package com.primetoolrentals.tooltrek_api.services.checkout;

import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Utility class for checkout calculations and formatting.
 */
@Service
public class CheckoutCalculationUtil {

    private static final String LOCALE_LANGUAGE = "en";
    private static final String LOCALE_COUNTRY = "US";
    private static final NumberFormat AMOUNT_CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(new Locale(LOCALE_LANGUAGE, LOCALE_COUNTRY));

    /**
     * Rounds a given amount to two decimal places.
     *
     * @param amount The amount to round.
     * @return The rounded amount.
     */
    public static double roundCalculationResult(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }

    /**
     * Formats a currency amount into a string representation.
     *
     * @param amount The amount to format.
     * @return The formatted currency string.
     */
    public static String formatCurrencyAmount(double amount) {
        return AMOUNT_CURRENCY_FORMAT.format(amount);
    }

    /**
     * Formats a percentage amount into a string representation.
     *
     * @param amount The percentage amount.
     * @return The formatted percentage string.
     */
    public static String formatPercentAmount(int amount) {
        return amount + "%";
    }

    /**
     * Converts a boolean value to an integer (1 for true, 0 for false).
     *
     * @param foo The boolean value to convert.
     * @return 1 if true, 0 if false.
     */
    public static int covertBooleanToInt(boolean foo) {
        return (foo) ? 1 : 0;
    }
}
