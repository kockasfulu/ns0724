package com.primetoolrentals.tooltrek_api.services.checkout;

import com.primetoolrentals.tooltrek_api.dto.RentalAgreementDto;
import com.primetoolrentals.tooltrek_api.dto.RentalRequestDto;

/**
 * Service interface for checkout operations.
 */
public interface CheckoutService {

    /**
     * Performs checkout of a tool based on the rental request.
     *
     * @param rentalRequestDto The rental request DTO containing rental details.
     * @return RentalAgreementDto representing the rental agreement details.
     */
    RentalAgreementDto checkoutTool(RentalRequestDto rentalRequestDto);

    /**
     * Generates a rental agreement document for a given rental ID.
     *
     * @param rentalId The ID of the rental for which the document is generated.
     * @return String representing the generated rental agreement document.
     */
    String generateRentalAgreementDocument(Long rentalId);
}
