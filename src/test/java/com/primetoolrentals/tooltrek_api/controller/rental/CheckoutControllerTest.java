package com.primetoolrentals.tooltrek_api.controller.rental;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.primetoolrentals.tooltrek_api.ClearDatabase;
import com.primetoolrentals.tooltrek_api.ToolTrekApiApplication;
import com.primetoolrentals.tooltrek_api.dto.RentalRequestDto;
import com.primetoolrentals.tooltrek_api.exception.ApiError;
import com.primetoolrentals.tooltrek_api.exception.ToolNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for CheckoutController covering various negative scenarios.
 */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ToolTrekApiApplication.class)
@AutoConfigureMockMvc
@ClearDatabase
@DisplayName("CheckoutController: Test cases covering all the negative cases.")
public class CheckoutControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageSource messageSource;

    /**
     * Test case: Create rental request with no required fields present.
     * Expected: BadRequest response with appropriate error messages.
     */
    @Test
    public void create_noRequiredFieldsPresent_thenReturnBadRequest() throws Exception {
        RentalRequestDto req = RentalRequestDto.builder().build();
        String body = objectMapper.writeValueAsString(req);

        mvc.perform(post("/api/rental").contentType("application/json").content(body))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    ApiError apiError = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ApiError.class);
                    assertEquals(HttpStatus.BAD_REQUEST, apiError.getStatus());
                    assertEquals(4, apiError.getErrors().size());
                    assertTrue(apiError.getErrors().contains(messageSource.getMessage("rental.request.tool.required", null, null, Locale.ENGLISH)));
                    assertTrue(apiError.getErrors().contains(messageSource.getMessage("rental.request.daycount.required", null, null, Locale.ENGLISH)));
                    assertTrue(apiError.getErrors().contains(messageSource.getMessage("rental.request.discount.required", null, null, Locale.ENGLISH)));
                    assertTrue(apiError.getErrors().contains(messageSource.getMessage("rental.request.date.required", null, null, Locale.ENGLISH)));
                });
    }

    /**
     * Parameterized test case: Create rental request with invalid tool ID.
     * @param toolId invalid tool ID
     * Expected: BadRequest response with appropriate error message.
     */
    @ParameterizedTest
    @ValueSource(longs = {-1, 0})
    public void create_toolCodeInvalid_thenReturnBadRequest(Long toolId) throws Exception {
        RentalRequestDto req = RentalRequestDto.builder().toolId(toolId).build();
        String body = objectMapper.writeValueAsString(req);

        mvc.perform(post("/api/rental").contentType("application/json").content(body))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    ApiError apiError = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ApiError.class);
                    assertEquals(HttpStatus.BAD_REQUEST, apiError.getStatus());
                    assertTrue(apiError.getErrors().contains(messageSource.getMessage("rental.request.tool.invalid", null, null, Locale.ENGLISH)));
                });
    }

    /**
     * Parameterized test case: Create rental request with invalid rental day count.
     * @param dayCount invalid rental day count
     * Expected: BadRequest response with appropriate error message.
     */
    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    public void create_rentalDayCountInvalid_thenReturnBadRequest(int dayCount) throws Exception {
        RentalRequestDto req = RentalRequestDto.builder().rentalDayCount(dayCount).build();
        String body = objectMapper.writeValueAsString(req);

        mvc.perform(post("/api/rental").contentType("application/json").content(body))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    ApiError apiError = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ApiError.class);
                    assertEquals(HttpStatus.BAD_REQUEST, apiError.getStatus());
                    assertTrue(apiError.getErrors().contains(messageSource.getMessage("rental.request.daycount.invalid", null, null, Locale.ENGLISH)));
                });
    }

    /**
     * Parameterized test case: Create rental request with invalid discount percentage.
     * @param discountPercent invalid discount percentage
     * Expected: BadRequest response with appropriate error message.
     */
    @ParameterizedTest
    @ValueSource(ints = {-1, 101})
    public void create_discountPercentInvalid_thenReturnBadRequest(int discountPercent) throws Exception {
        RentalRequestDto req = RentalRequestDto.builder().rentalDiscount(discountPercent).build();
        String body = objectMapper.writeValueAsString(req);

        mvc.perform(post("/api/rental").contentType("application/json").content(body))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    ApiError apiError = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ApiError.class);
                    assertEquals(HttpStatus.BAD_REQUEST, apiError.getStatus());
                    assertTrue(apiError.getErrors().contains(messageSource.getMessage("rental.request.discount.invalid", null, null, Locale.ENGLISH)));
                });
    }

    /**
     * Parameterized test case: Create rental request with invalid rental date format.
     * @param rentalDate invalid rental date format
     * Expected: BadRequest response with appropriate error message.
     */
    @ParameterizedTest
    @ValueSource(strings = {"2024/07/04", "07/04/2024", "07-04-2024"})
    public void create_rentalDateInvalid_thenReturnBadRequest(String rentalDate) throws Exception {
        RentalRequestDto req = RentalRequestDto.builder().rentalDate(rentalDate).build();
        String body = objectMapper.writeValueAsString(req);

        mvc.perform(post("/api/rental").contentType("application/json").content(body))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    ApiError apiError = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ApiError.class);
                    assertEquals(HttpStatus.BAD_REQUEST, apiError.getStatus());
                    assertTrue(apiError.getErrors().contains(messageSource.getMessage("rental.request.date.invalid", null, null, Locale.ENGLISH)));
                });
    }

    /**
     * Test case: Create rental request with non-existing tool ID.
     * Expected: NotFound response with ToolNotFoundException.
     */
    @Test
    public void create_invalidTool_thenReturnNotFound() throws Exception {
        RentalRequestDto req = RentalRequestDto.builder()
                .rentalDiscount(1)
                .rentalDayCount(1)
                .rentalDate("2024-07-04")
                .toolId(1L)
                .build();
        String body = objectMapper.writeValueAsString(req);

        mvc.perform(post("/api/rental").contentType("application/json").content(body))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(ToolNotFoundException.class, result.getResolvedException()));
    }
}
