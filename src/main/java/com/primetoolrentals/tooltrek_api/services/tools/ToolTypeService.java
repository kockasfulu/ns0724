package com.primetoolrentals.tooltrek_api.services.tools;

import com.primetoolrentals.tooltrek_api.dto.ToolTypeDto;

import java.util.List;

/**
 * Service interface for managing tool types.
 */
public interface ToolTypeService {

    /**
     * Adds a new tool type.
     *
     * @param toolTypeDto The ToolTypeDto object containing details of the tool type to create.
     * @return The created ToolTypeDto object.
     */
    ToolTypeDto addToolType(ToolTypeDto toolTypeDto);

    /**
     * Retrieves all tool types.
     *
     * @return List of ToolTypeDto objects representing all tool types.
     */
    List<ToolTypeDto> findAllToolTypes();

    /**
     * Checks if the tool type name is unique.
     *
     * @param name The name of the tool type to check.
     * @return true if the tool type name is unique, false otherwise.
     */
    boolean isToolTypeNameUnique(String name);
}
