package com.primetoolrentals.tooltrek_api.controller.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.primetoolrentals.tooltrek_api.ClearDatabase;
import com.primetoolrentals.tooltrek_api.ToolTrekApiApplication;
import com.primetoolrentals.tooltrek_api.dto.ToolTypeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test for ToolTypeController using MockMvc.
 */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ToolTrekApiApplication.class)
@AutoConfigureMockMvc
@ClearDatabase
public class ToolTypeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Tests the creation of a valid tool type through the API.
     *
     * @throws Exception if there is an error during the API call or JSON processing.
     */
    @Test
    public void create_validToolType() throws Exception {
        // Create a DTO object representing a new tool type
        ToolTypeDto ladder = ToolTypeDto.builder()
                .name("Ladder")
                .dailyCharge(1.99)
                .isWeekdayCharge(true)
                .isWeekendCharge(true)
                .isHolidayCharge(false)
                .build();

        // Convert the DTO to JSON string
        String body = objectMapper.writeValueAsString(ladder);

        // Perform POST request to create a new tool type and verify the HTTP status
        mvc.perform(post("/api/tool-type").contentType("application/json").content(body))
                .andDo(print()) // Print the request and response for debugging
                .andExpect(status().isCreated()); // Expect HTTP status 201 Created
    }

}
