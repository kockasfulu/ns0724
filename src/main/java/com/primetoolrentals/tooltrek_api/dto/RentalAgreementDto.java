package com.primetoolrentals.tooltrek_api.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Data Transfer Object for the Rental Agreement.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RentalAgreementDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -5116359523933460183L;

    /**
     * The rental ID.
     */
    private Long rentalId;

    /**
     * The code of the tool being rented.
     */
    private String toolCode;

    /**
     * The type of the tool being rented.
     */
    private String toolType;

    /**
     * The brand of the tool being rented.
     */
    private String toolBrand;

    /**
     * The number of days for the rental period.
     */
    private Integer rentalDays;

    /**
     * The date when the tool was checked out.
     */
    private LocalDate checkoutDate;

    /**
     * The date when the tool is due to be returned.
     */
    private LocalDate dueDate;

    /**
     * The daily rental charge for the tool.
     */
    private Double dailyRentalCharge;

    /**
     * The number of chargeable days in the rental period.
     */
    private Integer chargeDays;

    /**
     * The pre-discount total rental charge.
     */
    private Double preDiscountCharge;

    /**
     * The discount percentage applied to the rental.
     */
    private Integer discountPercent;

    /**
     * The amount discounted from the pre-discount charge.
     */
    private Double discountAmount;

    /**
     * The final total charge for the rental after the discount.
     */
    private Double finalCharge;
}
