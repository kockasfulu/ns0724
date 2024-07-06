package com.primetoolrentals.tooltrek_api.services.tools;

import com.primetoolrentals.tooltrek_api.dto.ToolBrandDto;

import java.util.List;

/**
 * Service interface for managing tool brands.
 */
public interface ToolBrandService {

    /**
     * Adds a new tool brand.
     *
     * @param toolBrandDto The DTO containing the details of the tool brand to be added.
     * @return The DTO of the added tool brand.
     */
    ToolBrandDto addToolBrand(ToolBrandDto toolBrandDto);

    /**
     * Retrieves all tool brands.
     *
     * @return A list of DTOs representing all tool brands.
     */
    List<ToolBrandDto> findAllToolBrands();

    /**
     * Checks if a tool brand name is unique.
     *
     * @param name The name of the tool brand to check.
     * @return true if the tool brand name is unique; false otherwise.
     */
    boolean isToolBrandNameUnique(String name);
}
