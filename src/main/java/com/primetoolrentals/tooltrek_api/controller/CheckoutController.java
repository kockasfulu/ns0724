package com.primetoolrentals.tooltrek_api.controller;

import com.primetoolrentals.tooltrek_api.dto.RentalAgreementDto;
import com.primetoolrentals.tooltrek_api.dto.RentalRequestDto;
import com.primetoolrentals.tooltrek_api.services.checkout.CheckoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Controller class for handling tool rental checkout and agreement retrieval.
 */
@RestController
@RequestMapping("/api/rental")
@RequiredArgsConstructor
@Validated
public class CheckoutController {

    private final CheckoutService checkoutService;

    /**
     * Endpoint for checking out a tool.
     *
     * @param rentalRequestDto the rental request data transfer object containing the rental details.
     * @return a ResponseEntity containing the rental agreement DTO if successful, or a BAD_REQUEST status if the checkout fails.
     */
    @Operation(summary = "Checkout a tool", description = "Checks out a tool based on the rental request details provided.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tool successfully checked out",
                    content = @Content(schema = @Schema(implementation = RentalAgreementDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request or cannot checkout tool",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("")
    public ResponseEntity<?> checkout(@Valid @RequestBody RentalRequestDto rentalRequestDto) {
        RentalAgreementDto agreementDto = checkoutService.checkoutTool(rentalRequestDto);
        if (agreementDto == null) {
            return new ResponseEntity<>("Cannot checkout tool", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(agreementDto, HttpStatus.CREATED);
    }

    /**
     * Endpoint for retrieving the rental agreement document for a given rental ID.
     *
     * @param rentalId the ID of the rental.
     * @return a ResponseEntity containing the rental agreement document as a String.
     */
    @Operation(summary = "Get rental agreement", description = "Retrieves the rental agreement document for a given rental ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental agreement document retrieved",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid rental ID",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{rentalId}/agreement")
    public ResponseEntity<String> getRentalAgreement(@Min(1) @PathVariable("rentalId") Long rentalId) throws IOException {
        return new ResponseEntity<>(checkoutService.generateRentalAgreementDocument(rentalId), HttpStatus.OK);
    }
}