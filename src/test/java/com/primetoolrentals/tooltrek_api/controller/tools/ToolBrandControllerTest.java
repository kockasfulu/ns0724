package com.primetoolrentals.tooltrek_api.controller.tools;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.primetoolrentals.tooltrek_api.ClearDatabase;
import com.primetoolrentals.tooltrek_api.ToolTrekApiApplication;
import com.primetoolrentals.tooltrek_api.dto.ToolBrandDto;
import com.primetoolrentals.tooltrek_api.exception.ApiError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test for ToolBrandController using MockMvc.
 */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ToolTrekApiApplication.class)
@AutoConfigureMockMvc
@ClearDatabase
public class ToolBrandControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageSource messageSource;


    /**
     * Tests the creation of valid tool brands through the API.
     *
     * @throws Exception if there is an error during the API call or JSON processing.
     */
    @Test
    public void create_validToolBrands() throws Exception {
        // Create and add two valid tool brands
        ToolBrandDto brand = ToolBrandDto.builder().name("DeWalt").build();
        String body = objectMapper.writeValueAsString(brand);
        mvc.perform(post("/api/tool-brand").contentType("application/json").content(body))
                .andExpect(status().isCreated());

        brand = ToolBrandDto.builder().name("Werner").build();
        body = objectMapper.writeValueAsString(brand);
        mvc.perform(post("/api/tool-brand").contentType("application/json").content(body))
                .andExpect(status().isCreated());

        // Verify that both tool brands are returned when fetching all brands
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mvc.perform(get("/api/tool-brand").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<?> brands = new ObjectMapper().readValue(result.getResponse().getContentAsString(), List.class);
                    assertEquals(2, brands.size());
                });
    }

    /**
     * Tests creating a tool brand with empty input, expecting a BadRequest response.
     *
     * @param name the tool brand name to test.
     * @throws Exception if there is an error during the API call or JSON processing.
     */
    @ParameterizedTest
    @ValueSource(strings = {""})
    public void create_whenInputIsEmpty_thenReturnBadRequest(String name) throws Exception {
        ToolBrandDto brand = ToolBrandDto.builder().name(name).build();
        String body = objectMapper.writeValueAsString(brand);

        mvc.perform(post("/api/tool-brand").contentType("application/json").content(body))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    ApiError apiError = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ApiError.class);
                    assertEquals(HttpStatus.BAD_REQUEST, apiError.getStatus());
                    assertEquals(1, apiError.getErrors().size());
                    assertTrue(apiError.getErrors().contains(messageSource.getMessage("brand.name.length", null, null, Locale.ENGLISH)));
                });

    }

    /**
     * Tests creating a tool brand with short or long input, expecting a BadRequest response.
     *
     * @param name the tool brand name to test.
     * @throws Exception if there is an error during the API call or JSON processing.
     */
    @ParameterizedTest
    @ValueSource(strings = {"a", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    public void create_whenInputIsShortOrLong_thenReturnBadRequest(String name) throws Exception {
        ToolBrandDto brand = ToolBrandDto.builder().name(name).build();
        String body = objectMapper.writeValueAsString(brand);

        mvc.perform(post("/api/tool-brand").contentType("application/json").content(body))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    ApiError apiError = new ObjectMapper().readValue(result.getResponse().getContentAsString(), ApiError.class);
                    assertEquals(HttpStatus.BAD_REQUEST, apiError.getStatus());
                    assertEquals(1, apiError.getErrors().size());
                    assertTrue(apiError.getErrors().contains(messageSource.getMessage("brand.name.length", null, null, Locale.ENGLISH)));
                });

    }

}
