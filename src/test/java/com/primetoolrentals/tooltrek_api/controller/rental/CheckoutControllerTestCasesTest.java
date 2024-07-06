package com.primetoolrentals.tooltrek_api.controller.rental;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.primetoolrentals.tooltrek_api.ClearDatabase;
import com.primetoolrentals.tooltrek_api.ToolTrekApiApplication;
import com.primetoolrentals.tooltrek_api.dto.*;
import com.primetoolrentals.tooltrek_api.exception.ApiError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for the CheckoutController covering various rental scenarios.
 */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ToolTrekApiApplication.class)
@AutoConfigureMockMvc
@ClearDatabase
@DisplayName("CheckoutController: Test cases covering all the requested cases.")
public class CheckoutControllerTestCasesTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageSource messageSource;

    /**
     * Test case for handling an invalid rental request with an excessive discount percentage.
     * Expects a BadRequest response with an appropriate error message.
     *
     * @throws Exception if there is an error performing the HTTP request or validating the response
     */
    @Test
    @DisplayName("Test an invalid rental request with excessive discount percentage.")
    public void create_rental_test1() throws Exception {
        // Setup: Seed database with necessary tools
        ToolsDto tools = seedDatabaseForTestingRentalCreation();
        ToolDto tool = tools.getJakr();

        // Create an invalid rental request with excessive discount percentage
        RentalRequestDto req = RentalRequestDto.builder()
                .rentalDiscount(101)
                .rentalDayCount(5)
                .rentalDate("2015-09-03")
                .toolId(tool.getId())
                .build();
        String body = objectMapper.writeValueAsString(req);

        // Perform POST request to create rental and validate response
        mvc.perform(post("/api/rental").contentType("application/json").content(body))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    // Verify that the response contains expected error details
                    ApiError apiError = objectMapper.readValue(result.getResponse().getContentAsString(), ApiError.class);
                    assertEquals(HttpStatus.BAD_REQUEST, apiError.getStatus());
                    assertTrue(apiError.getErrors().contains(messageSource.getMessage("rental.request.discount.invalid", null, null, Locale.ENGLISH)));
                });
    }

    /**
     * Test case for creating a valid rental request for a ladder tool (`LADW`).
     * Expects a CREATED response with the correct rental agreement details.
     *
     * @throws Exception if there is an error performing the HTTP request or validating the response
     */
    @Test
    @DisplayName("Test creating a valid rental request for a ladder tool (`LADW`).")
    public void create_rental_test2() throws Exception {
        // Setup: Seed database with necessary tools
        ToolsDto tools = seedDatabaseForTestingRentalCreation();
        ToolDto tool = tools.getLadw();

        // Create a valid rental request for the ladder tool (`LADW`)
        RentalRequestDto req = RentalRequestDto.builder()
                .rentalDiscount(10)
                .rentalDayCount(3)
                .rentalDate("2020-07-02")
                .toolId(tool.getId())
                .build();
        String body = objectMapper.writeValueAsString(req);
        objectMapper.registerModule(new JavaTimeModule());

        // Perform POST request to create rental and validate response
        mvc.perform(post("/api/rental").contentType("application/json").content(body))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    // Verify that the rental agreement returned matches expected details
                    RentalAgreementDto agreementDto = objectMapper.readValue(result.getResponse().getContentAsString(), RentalAgreementDto.class);
                    req.setRentalId(agreementDto.getRentalId());
                    assertEquals(LocalDate.parse("2020-07-05"), agreementDto.getDueDate());
                    assertEquals(1.99, agreementDto.getDailyRentalCharge());
                    assertEquals(2, agreementDto.getChargeDays());
                    assertEquals(3.98, agreementDto.getPreDiscountCharge());
                    assertEquals(0.40, agreementDto.getDiscountAmount());
                    assertEquals(3.58, agreementDto.getFinalCharge());
                });

        // Verify the content of the agreement document returned by GET request
        String expectedAgreementDoc = """
                Rental Agreement

                Tool code: LADW
                Tool type: Ladder
                Tool brand: Werner
                Check out date: 07/02/20
                Due date: 07/05/20
                Daily rental charge: $1.99
                Charge days: 2
                Pre-discount charge: $3.98
                Discount percent: 10%
                Discount amount: $0.40
                Final charge: $3.58""";
        mvc.perform(get("/api/rental/" + req.getRentalId() + "/agreement").contentType("application/json").content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String agreementDoc = result.getResponse().getContentAsString();
                    assertEquals(expectedAgreementDoc, agreementDoc);
                });
    }

    /**
     * Test case for creating a valid rental request for a chainsaw tool (`CHNS`).
     * Expects a CREATED response with the correct rental agreement details.
     *
     * @throws Exception if there is an error performing the HTTP request or validating the response
     */
    @Test
    @DisplayName("Test creating a valid rental request for a chainsaw tool (`CHNS`).")
    public void create_rental_test3() throws Exception {
        // Setup: Seed database with necessary tools
        ToolsDto tools = seedDatabaseForTestingRentalCreation();
        ToolDto tool = tools.getChns();

        // Create a valid rental request for the chainsaw tool (`CHNS`)
        RentalRequestDto req = RentalRequestDto.builder()
                .rentalDiscount(25)
                .rentalDayCount(5)
                .rentalDate("2015-07-02")
                .toolId(tool.getId())
                .build();
        String body = objectMapper.writeValueAsString(req);
        objectMapper.registerModule(new JavaTimeModule());

        // Perform POST request to create rental and validate response
        mvc.perform(post("/api/rental").contentType("application/json").content(body))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    // Verify that the rental agreement returned matches expected details
                    RentalAgreementDto agreementDto = objectMapper.readValue(result.getResponse().getContentAsString(), RentalAgreementDto.class);
                    req.setRentalId(agreementDto.getRentalId());
                    assertEquals(LocalDate.parse("2015-07-07"), agreementDto.getDueDate());
                    assertEquals(1.49, agreementDto.getDailyRentalCharge());
                    assertEquals(3, agreementDto.getChargeDays());
                    assertEquals(4.47, agreementDto.getPreDiscountCharge());
                    assertEquals(1.12, agreementDto.getDiscountAmount());
                    assertEquals(3.35, agreementDto.getFinalCharge());
                });

        // Verify the content of the agreement document returned by GET request
        String expectedAgreementDoc = """
                Rental Agreement

                Tool code: CHNS
                Tool type: Chainsaw
                Tool brand: Stihl
                Check out date: 07/02/15
                Due date: 07/07/15
                Daily rental charge: $1.49
                Charge days: 3
                Pre-discount charge: $4.47
                Discount percent: 25%
                Discount amount: $1.12
                Final charge: $3.35""";
        mvc.perform(get("/api/rental/" + req.getRentalId() + "/agreement").contentType("application/json").content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String agreementDoc = result.getResponse().getContentAsString();
                    assertEquals(expectedAgreementDoc, agreementDoc);
                });
    }

    /**
     * Test case for creating a valid rental request for a jackhammer tool (`JAKD`).
     * Expects a CREATED response with the correct rental agreement details.
     *
     * @throws Exception if there is an error performing the HTTP request or validating the response
     */
    @Test
    @DisplayName("Test creating a valid rental request for a jackhammer tool (`JAKD`).")
    public void create_rental_test4() throws Exception {
        // Setup: Seed database with necessary tools
        ToolsDto tools = seedDatabaseForTestingRentalCreation();
        ToolDto tool = tools.getJakd();

        // Create a valid rental request for the jackhammer tool (`JAKD`)
        RentalRequestDto req = RentalRequestDto.builder()
                .rentalDiscount(0)
                .rentalDayCount(6)
                .rentalDate("2015-09-03")
                .toolId(tool.getId())
                .build();
        String body = objectMapper.writeValueAsString(req);
        objectMapper.registerModule(new JavaTimeModule());

        // Perform POST request to create rental and validate response
        mvc.perform(post("/api/rental").contentType("application/json").content(body))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    // Verify that the rental agreement returned matches expected details
                    RentalAgreementDto agreementDto = objectMapper.readValue(result.getResponse().getContentAsString(), RentalAgreementDto.class);
                    req.setRentalId(agreementDto.getRentalId());
                    assertEquals(LocalDate.parse("2015-09-09"), agreementDto.getDueDate());
                    assertEquals(2.99, agreementDto.getDailyRentalCharge());
                    assertEquals(3, agreementDto.getChargeDays());
                    assertEquals(8.97, agreementDto.getPreDiscountCharge());
                    assertEquals(0.00, agreementDto.getDiscountAmount());
                    assertEquals(8.97, agreementDto.getFinalCharge());
                });

        // Verify the content of the agreement document returned by GET request
        String expectedAgreementDoc = """
                Rental Agreement

                Tool code: JAKD
                Tool type: Jackhammer
                Tool brand: DeWalt
                Check out date: 09/03/15
                Due date: 09/09/15
                Daily rental charge: $2.99
                Charge days: 3
                Pre-discount charge: $8.97
                Discount percent: 0%
                Discount amount: $0.00
                Final charge: $8.97""";
        mvc.perform(get("/api/rental/" + req.getRentalId() + "/agreement").contentType("application/json").content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String agreementDoc = result.getResponse().getContentAsString();
                    assertEquals(expectedAgreementDoc, agreementDoc);
                });
    }

    /**
     * Test case for creating a rental without any discount for a jackhammer tool (`JAKR`)
     * over a 9-day period starting from 2015-07-02. Expects a CREATED response with the
     * correct rental agreement details.
     *
     * @throws Exception if there is an error performing the HTTP request or validating the response
     */
    @Test
    @DisplayName("Test creating a valid rental request for a jackhammer tool (`JAKR`) in 2015.")
    public void create_rental_test5() throws Exception {
        // Seed the database with necessary tool brands, types, and tools for testing
        ToolsDto tools = seedDatabaseForTestingRentalCreation();

        // Retrieve the specific tool (`JAKR`) for testing
        ToolDto tool = tools.getJakr();

        // Build a rental request with specific parameters
        RentalRequestDto req = RentalRequestDto.builder()
                .rentalDiscount(0)
                .rentalDayCount(9)
                .rentalDate("2015-07-02")
                .toolId(tool.getId())
                .build();

        // Convert the rental request to JSON format
        String body = objectMapper.writeValueAsString(req);

        // Register JavaTimeModule for handling date/time objects
        objectMapper.registerModule(new JavaTimeModule());

        // Perform an HTTP POST request to create a new rental
        mvc.perform(post("/api/rental")
                        .contentType("application/json")
                        .content(body))

                // Print the response for debugging purposes
                .andDo(print())

                // Assert that the HTTP status is CREATED (201)
                .andExpect(status().isCreated())

                // Validate the response content
                .andExpect(result -> {
                    // Parse the response body into a RentalAgreementDto object
                    RentalAgreementDto agreementDto = objectMapper.readValue(result.getResponse().getContentAsString(), RentalAgreementDto.class);

                    // Assert expected values in the rental agreement
                    assertEquals(LocalDate.parse("2015-07-11"), agreementDto.getDueDate());
                    assertEquals(2.99, agreementDto.getDailyRentalCharge());
                    assertEquals(5, agreementDto.getChargeDays());
                    assertEquals(14.95, agreementDto.getPreDiscountCharge());
                    assertEquals(0D, agreementDto.getDiscountAmount());
                    assertEquals(14.95, agreementDto.getFinalCharge());
                });
    }

    /**
     * Test case for creating a rental with a 50% discount for a jackhammer tool (`JAKR`)
     * over a 9-day period starting from 2020-07-02. Expects a CREATED response with the
     * correct rental agreement details.
     *
     * @throws Exception if there is an error performing the HTTP request or validating the response
     */
    @Test
    @DisplayName("Test creating a valid rental request for a jackhammer tool (`JAKR`) in 2020.")
    public void create_rental_test6() throws Exception {
        // Seed the database with necessary tool brands, types, and tools for testing
        ToolsDto tools = seedDatabaseForTestingRentalCreation();

        // Retrieve the specific tool (`JAKR`) for testing
        ToolDto tool = tools.getJakr();

        // Build a rental request with specific parameters
        RentalRequestDto req = RentalRequestDto.builder()
                .rentalDiscount(50)
                .rentalDayCount(9)
                .rentalDate("2020-07-02")
                .toolId(tool.getId())
                .build();

        // Convert the rental request to JSON format
        String body = objectMapper.writeValueAsString(req);

        // Register JavaTimeModule for handling date/time objects
        objectMapper.registerModule(new JavaTimeModule());

        // Perform an HTTP POST request to create a new rental
        mvc.perform(post("/api/rental")
                        .contentType("application/json")
                        .content(body))

                // Print the response for debugging purposes
                .andDo(print())

                // Assert that the HTTP status is CREATED (201)
                .andExpect(status().isCreated())

                // Validate the response content
                .andExpect(result -> {
                    // Parse the response body into a RentalAgreementDto object
                    RentalAgreementDto agreementDto = objectMapper.readValue(result.getResponse().getContentAsString(), RentalAgreementDto.class);

                    // Assert expected values in the rental agreement
                    assertEquals(LocalDate.parse("2020-07-11"), agreementDto.getDueDate());
                    assertEquals(2.99, agreementDto.getDailyRentalCharge());
                    assertEquals(5, agreementDto.getChargeDays());
                    assertEquals(14.95, agreementDto.getPreDiscountCharge());
                    assertEquals(7.48, agreementDto.getDiscountAmount());
                    assertEquals(7.47, agreementDto.getFinalCharge());
                });
    }

    /**
     * Seeds the database with necessary tool brands, types, and tools for testing rental creation.
     * This method performs HTTP POST requests to create tool brands, tool types, and tools, and
     * retrieves their IDs for further use in testing.
     *
     * @return ToolsDto containing the created tools for testing
     * @throws Exception if there is an error performing the HTTP requests or parsing the responses
     */
    private ToolsDto seedDatabaseForTestingRentalCreation() throws Exception {
        // Create ToolBrandDto objects for Stihl, Werner, DeWalt, and Ridgid tool brands
        ToolBrandDto stihl = ToolBrandDto.builder().name("Stihl").build();
        String body = objectMapper.writeValueAsString(stihl);

        // Perform HTTP POST request to create Stihl tool brand
        mvc.perform(post("/api/tool-brand")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    // Read and set the ID of the created tool brand from the response
                    ToolBrandDto res = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ToolBrandDto.class);
                    stihl.setId(res.getId());
                });

        // Repeat the above steps for Werner, DeWalt, and Ridgid tool brands
        ToolBrandDto werner = ToolBrandDto.builder().name("Werner").build();
        body = objectMapper.writeValueAsString(werner);
        mvc.perform(post("/api/tool-brand")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    ToolBrandDto res = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ToolBrandDto.class);
                    werner.setId(res.getId());
                });

        ToolBrandDto dewalt = ToolBrandDto.builder().name("DeWalt").build();
        body = objectMapper.writeValueAsString(dewalt);
        mvc.perform(post("/api/tool-brand")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    ToolBrandDto res = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ToolBrandDto.class);
                    dewalt.setId(res.getId());
                });

        ToolBrandDto ridgid = ToolBrandDto.builder().name("Ridgid").build();
        body = objectMapper.writeValueAsString(ridgid);
        mvc.perform(post("/api/tool-brand")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    ToolBrandDto res = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ToolBrandDto.class);
                    ridgid.setId(res.getId());
                });

        // Create ToolTypeDto objects for Ladder, Chainsaw, and Jackhammer tool types
        ToolTypeDto ladder = ToolTypeDto.builder()
                .name("Ladder")
                .dailyCharge(1.99)
                .isWeekdayCharge(true)
                .isWeekendCharge(true)
                .isHolidayCharge(false)
                .build();
        body = objectMapper.writeValueAsString(ladder);
        mvc.perform(post("/api/tool-type")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    ToolTypeDto res = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ToolTypeDto.class);
                    ladder.setId(res.getId());
                });

        ToolTypeDto chainSaw = ToolTypeDto.builder()
                .name("Chainsaw")
                .dailyCharge(1.49)
                .isWeekdayCharge(true)
                .isWeekendCharge(false)
                .isHolidayCharge(true)
                .build();
        body = objectMapper.writeValueAsString(chainSaw);
        mvc.perform(post("/api/tool-type")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    ToolTypeDto res = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ToolTypeDto.class);
                    chainSaw.setId(res.getId());
                });

        ToolTypeDto jackhammer = ToolTypeDto.builder()
                .name("Jackhammer")
                .dailyCharge(2.99)
                .isWeekdayCharge(true)
                .isWeekendCharge(false)
                .isHolidayCharge(false)
                .build();
        body = objectMapper.writeValueAsString(jackhammer);
        mvc.perform(post("/api/tool-type")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    ToolTypeDto res = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ToolTypeDto.class);
                    jackhammer.setId(res.getId());
                });

        // Create ToolDto objects for specific tools: CHNS, LADW, JAKD, JAKR
        ToolDto chns = ToolDto.builder()
                .code("CHNS")
                .toolBrandId(stihl.getId())
                .toolTypeId(chainSaw.getId())
                .build();
        body = objectMapper.writeValueAsString(chns);
        mvc.perform(post("/api/tool")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    ToolDto res = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ToolDto.class);
                    chns.setId(res.getId());
                });

        ToolDto ladw = ToolDto.builder()
                .code("LADW")
                .toolBrandId(werner.getId())
                .toolTypeId(ladder.getId())
                .build();
        body = objectMapper.writeValueAsString(ladw);
        mvc.perform(post("/api/tool")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    ToolDto res = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ToolDto.class);
                    ladw.setId(res.getId());
                });

        ToolDto jakd = ToolDto.builder()
                .code("JAKD")
                .toolBrandId(dewalt.getId())
                .toolTypeId(jackhammer.getId())
                .build();
        body = objectMapper.writeValueAsString(jakd);
        mvc.perform(post("/api/tool")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    ToolDto res = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ToolDto.class);
                    jakd.setId(res.getId());
                });

        ToolDto jakr = ToolDto.builder()
                .code("JAKR")
                .toolBrandId(ridgid.getId())
                .toolTypeId(jackhammer.getId())
                .build();
        body = objectMapper.writeValueAsString(jakr);
        mvc.perform(post("/api/tool")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    ToolDto res = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ToolDto.class);
                    jakr.setId(res.getId());
                });

        // Return a ToolsDto object containing the created tools for testing
        return ToolsDto.builder()
                .chns(chns)
                .ladw(ladw)
                .jakd(jakd)
                .jakr(jakr)
                .build();
    }

}
