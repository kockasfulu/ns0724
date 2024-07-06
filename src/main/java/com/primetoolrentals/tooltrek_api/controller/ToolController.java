package com.primetoolrentals.tooltrek_api.controller;

import com.primetoolrentals.tooltrek_api.dto.ToolDto;
import com.primetoolrentals.tooltrek_api.services.tools.ToolService;
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
 * Controller for managing tools.
 */
@RestController
@RequestMapping("/api/tool")
@RequiredArgsConstructor
public class ToolController {

    private final ToolService toolService;

    /**
     * Get all tools.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tools in the body.
     */
    @Operation(summary = "Get all tools", description = "Retrieve a list of all tools.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of tools",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ToolDto.class))))
    })
    @GetMapping("")
    public ResponseEntity<List<ToolDto>> findAllTools() {
        return new ResponseEntity<>(toolService.findAllTools(), HttpStatus.OK);
    }

    /**
     * Create a new tool.
     *
     * @param toolDto the ToolDto to create.
     * @return the ResponseEntity with status 201 (Created) and the created ToolDto in the body,
     * or with status 406 (Not Acceptable) if the tool already exists,
     * or with status 400 (Bad Request) if the tool could not be created.
     */
    @Operation(summary = "Create a new tool", description = "Add a new tool to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tool created successfully",
                    content = @Content(schema = @Schema(implementation = ToolDto.class))),
            @ApiResponse(responseCode = "406", description = "Tool already exists",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Cannot create tool",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("")
    public ResponseEntity<?> createNewTool(@Valid @RequestBody ToolDto toolDto) {
        if (!toolService.isToolCodeUnique(toolDto.getCode())) {
            return new ResponseEntity<>("Tool already exists: " + toolDto.getCode(), HttpStatus.NOT_ACCEPTABLE);
        }

        ToolDto tool = toolService.addTool(toolDto);
        if (tool == null) {
            return new ResponseEntity<>("Cannot create tool: " + toolDto.getCode(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(tool, HttpStatus.CREATED);
    }
}
