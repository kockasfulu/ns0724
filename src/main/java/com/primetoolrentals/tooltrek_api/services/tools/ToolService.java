package com.primetoolrentals.tooltrek_api.services.tools;

import com.primetoolrentals.tooltrek_api.dto.ToolDto;

import java.util.List;

/**
 * Service interface for managing tools.
 */
public interface ToolService {

    /**
     * Adds a new tool.
     *
     * @param toolDto The ToolDto object containing details of the tool to create.
     * @return The created ToolDto object.
     */
    ToolDto addTool(ToolDto toolDto);

    /**
     * Retrieves all tools.
     *
     * @return List of ToolDto objects representing all tools.
     */
    List<ToolDto> findAllTools();

    /**
     * Checks if the tool code is unique.
     *
     * @param code The code of the tool to check.
     * @return true if the tool code is unique, false otherwise.
     */
    boolean isToolCodeUnique(String code);
}
