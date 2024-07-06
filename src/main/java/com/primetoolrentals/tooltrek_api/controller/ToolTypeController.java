package com.primetoolrentals.tooltrek_api.controller;

import com.primetoolrentals.tooltrek_api.dto.ToolTypeDto;
import com.primetoolrentals.tooltrek_api.services.tools.ToolTypeService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing tool types.
 */
@RestController
@RequestMapping("/api/tool-type")
@RequiredArgsConstructor
public class ToolTypeController {

    private final ToolTypeService toolTypeService;

    /**
     * Get all tool types.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tool types in the body.
     */
    @Operation(summary = "Get all tool types", description = "Retrieve a list of all tool types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of tool types",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ToolTypeDto.class))))
    })
    @GetMapping("")
    public ResponseEntity<List<ToolTypeDto>> findAllToolTypes() {
        return new ResponseEntity<>(toolTypeService.findAllToolTypes(), HttpStatus.OK);
    }

    /**
     * Create a new tool type.
     *
     * @param toolTypeDto the ToolTypeDto to create.
     * @return the ResponseEntity with status 201 (Created) and the created ToolTypeDto in the body,
     * or with status 406 (Not Acceptable) if the tool type already exists,
     * or with status 400 (Bad Request) if the tool type could not be created.
     */
    @Operation(summary = "Create a new tool type", description = "Add a new tool type to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tool type created successfully",
                    content = @Content(schema = @Schema(implementation = ToolTypeDto.class))),
            @ApiResponse(responseCode = "406", description = "Tool type already exists",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Cannot create tool type",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("")
    public ResponseEntity<?> createNewToolType(@Valid @RequestBody ToolTypeDto toolTypeDto) {
        if (!toolTypeService.isToolTypeNameUnique(toolTypeDto.getName())) {
            return new ResponseEntity<>("Tool type already exists: " + toolTypeDto.getName(), HttpStatus.NOT_ACCEPTABLE);
        }

        ToolTypeDto toolType = toolTypeService.addToolType(toolTypeDto);
        if (toolType == null) {
            return new ResponseEntity<>("Cannot create tool type: " + toolTypeDto.getName(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(toolType, HttpStatus.CREATED);
    }
}
