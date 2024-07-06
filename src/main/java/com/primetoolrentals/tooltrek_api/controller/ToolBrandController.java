package com.primetoolrentals.tooltrek_api.controller;

import com.primetoolrentals.tooltrek_api.dto.ToolBrandDto;
import com.primetoolrentals.tooltrek_api.services.tools.ToolBrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing tool brands.
 */
@RestController
@RequestMapping("/api/tool-brand")
@RequiredArgsConstructor
@Validated
public class ToolBrandController {

    private final ToolBrandService toolBrandService;

    /**
     * Get all tool brands.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tool brands in the body.
     */
    @Operation(summary = "Get all tool brands", description = "Retrieve a list of all tool brands.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of tool brands",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ToolBrandDto.class))))
    })
    @GetMapping("")
    public ResponseEntity<List<ToolBrandDto>> findAllToolBrands() {
        return new ResponseEntity<>(toolBrandService.findAllToolBrands(), HttpStatus.OK);
    }

    /**
     * Create a new tool brand.
     *
     * @param toolBrandDto the ToolBrandDto to create.
     * @return the ResponseEntity with status 201 (Created) and the created ToolBrandDto in the body,
     * or with status 406 (Not Acceptable) if the tool brand already exists,
     * or with status 400 (Bad Request) if the tool brand could not be created.
     */
    @Operation(summary = "Create a new tool brand", description = "Add a new tool brand to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tool brand created successfully",
                    content = @Content(schema = @Schema(implementation = ToolBrandDto.class))),
            @ApiResponse(responseCode = "406", description = "Tool brand already exists",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Cannot create tool brand",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("")
    public ResponseEntity<?> createNewToolBrand(@Valid @RequestBody ToolBrandDto toolBrandDto) {
        if (!toolBrandService.isToolBrandNameUnique(toolBrandDto.getName())) {
            return new ResponseEntity<>("Tool brand already exists: " + toolBrandDto.getName(), HttpStatus.NOT_ACCEPTABLE);
        }

        ToolBrandDto toolBrand = toolBrandService.addToolBrand(toolBrandDto);
        if (toolBrand == null) {
            return new ResponseEntity<>("Cannot create tool brand: " + toolBrandDto.getName(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(toolBrand, HttpStatus.CREATED);
    }
}
