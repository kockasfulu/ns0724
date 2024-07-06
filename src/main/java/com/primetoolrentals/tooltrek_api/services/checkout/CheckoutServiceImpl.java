package com.primetoolrentals.tooltrek_api.services.checkout;

import com.primetoolrentals.tooltrek_api.dto.RentalAgreementDto;
import com.primetoolrentals.tooltrek_api.dto.RentalDaysDto;
import com.primetoolrentals.tooltrek_api.dto.RentalRequestDto;
import com.primetoolrentals.tooltrek_api.entity.Rental;
import com.primetoolrentals.tooltrek_api.entity.Tool;
import com.primetoolrentals.tooltrek_api.entity.ToolBrand;
import com.primetoolrentals.tooltrek_api.entity.ToolType;
import com.primetoolrentals.tooltrek_api.exception.RentalNotFoundException;
import com.primetoolrentals.tooltrek_api.exception.ToolNotFoundException;
import com.primetoolrentals.tooltrek_api.repository.RentalRepository;
import com.primetoolrentals.tooltrek_api.repository.ToolRepository;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Service implementation for handling tool checkout operations.
 */
@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    /**
     * Mustache template for generating rental agreements.
     */
    private static final String RENTAL_AGREEMENT_TEMPLATE = """
Rental Agreement

Tool code: {{tool_code}}
Tool type: {{tool_type}}
Tool brand: {{tool_brand}}
Check out date: {{checkout_date}}
Due date: {{due_date}}
Daily rental charge: {{daily_rental_charge}}
Charge days: {{charge_days}}
Pre-discount charge: {{pre_discount_charge}}
Discount percent: {{discount_percent}}
Discount amount: {{discount_amount}}
Final charge: {{final_charge}}""";

    private final RentalRepository rentalRepository;
    private final ToolRepository toolRepository;

    /**
     * Checkout a tool based on the provided rental request.
     *
     * @param rentalRequestDto The rental request DTO containing details of the tool to be rented.
     * @return RentalAgreementDto representing the generated rental agreement.
     * @throws ToolNotFoundException if the tool specified in the rental request does not exist.
     */
    @Override
    public RentalAgreementDto checkoutTool(RentalRequestDto rentalRequestDto) {
        // Find the tool from the repository or throw ToolNotFoundException
        Tool tool = toolRepository.findById(rentalRequestDto.getToolId())
                .orElseThrow(ToolNotFoundException::new);

        // Create a new Rental entity
        Rental rental = Rental.builder()
                .tool(tool)
                .rentalDate(LocalDate.parse(rentalRequestDto.getRentalDate()))
                .rentalDayCount(rentalRequestDto.getRentalDayCount())
                .rentalDiscount(rentalRequestDto.getRentalDiscount())
                .build();

        // Save the new Rental entity
        Rental newRental = rentalRepository.save(rental);

        // Generate and return the rental agreement DTO
        return generateRentalAgreement(newRental);
    }

    /**
     * Generate a rental agreement document for the specified rental ID.
     *
     * @param rentalId The ID of the rental for which the agreement document is generated.
     * @return String representing the rental agreement document.
     * @throws RentalNotFoundException if the rental with the specified ID does not exist.
     */
    @Override
    public String generateRentalAgreementDocument(Long rentalId) {
        // Find the rental from the repository or throw RentalNotFoundException
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(RentalNotFoundException::new);

        // Generate the rental agreement DTO
        RentalAgreementDto agreement = generateRentalAgreement(rental);

        // Compile the Mustache template for generating the document
        Template tmpl = Mustache.compiler().compile(RENTAL_AGREEMENT_TEMPLATE);

        // Prepare data for template rendering
        Map<String, String> data = new HashMap<>();
        data.put("tool_code", agreement.getToolCode());
        data.put("tool_type", agreement.getToolType());
        data.put("tool_brand", agreement.getToolBrand());
        data.put("checkout_date", CheckoutDateUtil.formatRentalDate(agreement.getCheckoutDate()));
        data.put("due_date", CheckoutDateUtil.formatRentalDate(agreement.getDueDate()));
        data.put("daily_rental_charge", CheckoutCalculationUtil.formatCurrencyAmount(agreement.getDailyRentalCharge()));
        data.put("charge_days", Integer.toString(agreement.getChargeDays()));
        data.put("pre_discount_charge", CheckoutCalculationUtil.formatCurrencyAmount(agreement.getPreDiscountCharge()));
        data.put("discount_percent", CheckoutCalculationUtil.formatPercentAmount(agreement.getDiscountPercent()));
        data.put("discount_amount", CheckoutCalculationUtil.formatCurrencyAmount(agreement.getDiscountAmount()));
        data.put("final_charge", CheckoutCalculationUtil.formatCurrencyAmount(agreement.getFinalCharge()));

        // Render the template with data and return the result as a String
        return tmpl.execute(data);
    }

    /**
     * Generate a rental agreement DTO from the specified rental entity.
     *
     * @param rental The rental entity for which the agreement DTO is generated.
     * @return RentalAgreementDto representing the generated rental agreement.
     */
    private RentalAgreementDto generateRentalAgreement(Rental rental) {
        // Extract tool, brand, and type information from the rental
        Tool tool = rental.getTool();
        ToolBrand brand = tool.getToolBrand();
        ToolType type = tool.getToolType();

        // Calculate checkout date, due date, and rental days breakdown
        LocalDate checkoutDate = rental.getRentalDate();
        LocalDate dueDate = checkoutDate.plusDays(rental.getRentalDayCount());
        RentalDaysDto rentalDaysDto = CheckoutDateUtil.numberOfChargeDays(checkoutDate, rental.getRentalDayCount());
        int chargeDays = calculateChargeDays(type, rentalDaysDto);
        double preDiscountCharge = CheckoutCalculationUtil.roundCalculationResult(chargeDays * type.getDailyCharge().doubleValue());
        double discountAmount = CheckoutCalculationUtil.roundCalculationResult(preDiscountCharge * rental.getRentalDiscount() / 100D);
        double finalCharge = CheckoutCalculationUtil.roundCalculationResult(preDiscountCharge - discountAmount);

        // Build and return the RentalAgreementDto
        return RentalAgreementDto.builder()
                .rentalId(rental.getId())
                .toolCode(tool.getCode())
                .toolType(type.getName())
                .toolBrand(brand.getName())
                .rentalDays(rentalDaysDto.getTotalRentalDays())
                .checkoutDate(checkoutDate)
                .dueDate(dueDate)
                .dailyRentalCharge(type.getDailyCharge().doubleValue())
                .chargeDays(chargeDays)
                .preDiscountCharge(preDiscountCharge)
                .discountPercent(rental.getRentalDiscount())
                .discountAmount(discountAmount)
                .finalCharge(finalCharge)
                .build();
    }

    /**
     * Calculate the number of chargeable days based on tool type and rental days.
     *
     * @param type           The tool type for which the chargeable days are calculated.
     * @param rentalDaysDto  DTO containing breakdown of rental days (weekday, weekend, holiday).
     * @return int representing the total chargeable days.
     */
    private int calculateChargeDays(ToolType type, RentalDaysDto rentalDaysDto) {
        // Calculate the total chargeable days based on weekday, weekend, and holiday rules
        return rentalDaysDto.getWeekdayRentalDays() * CheckoutCalculationUtil.covertBooleanToInt(type.getIsWeekdayCharge()) +
                rentalDaysDto.getWeekendRentalDays() * CheckoutCalculationUtil.covertBooleanToInt(type.getIsWeekendCharge()) +
                rentalDaysDto.getHolidaysRentalDays() * CheckoutCalculationUtil.covertBooleanToInt(type.getIsHolidayCharge());
    }
}
