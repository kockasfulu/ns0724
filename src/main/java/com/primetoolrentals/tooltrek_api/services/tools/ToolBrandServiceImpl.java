package com.primetoolrentals.tooltrek_api.services.tools;

import com.primetoolrentals.tooltrek_api.dto.ToolBrandDto;
import com.primetoolrentals.tooltrek_api.entity.ToolBrand;
import com.primetoolrentals.tooltrek_api.repository.ToolBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of ToolBrandService that manages CRUD operations for tool brands.
 */
@Service
@RequiredArgsConstructor
public class ToolBrandServiceImpl implements ToolBrandService {

    private final ToolBrandRepository toolBrandRepository;

    /**
     * Adds a new tool brand.
     *
     * @param toolBrandDto The ToolBrandDto object containing details of the tool brand to create.
     * @return The created ToolBrandDto object.
     */
    @Override
    public ToolBrandDto addToolBrand(ToolBrandDto toolBrandDto) {
        final ToolBrand newToolBrand = ToolBrand.builder()
                .name(toolBrandDto.getName())
                .build();
        final ToolBrand savedToolBrand = toolBrandRepository.save(newToolBrand);
        return toolBrandToToolBrandDto(savedToolBrand);
    }

    /**
     * Retrieves all tool brands.
     *
     * @return List of ToolBrandDto objects representing all tool brands.
     */
    @Override
    public List<ToolBrandDto> findAllToolBrands() {
        return toolBrandRepository.findAll().stream().map(this::toolBrandToToolBrandDto).toList();
    }

    /**
     * Checks if the tool brand name is unique.
     *
     * @param name The name of the tool brand to check.
     * @return true if the tool brand name is unique, false otherwise.
     */
    @Override
    public boolean isToolBrandNameUnique(String name) {
        return toolBrandRepository.findFirstByName(name).isEmpty();
    }

    /**
     * Converts a ToolBrand entity to ToolBrandDto.
     *
     * @param toolBrand The ToolBrand entity to convert.
     * @return The corresponding ToolBrandDto object.
     */
    private ToolBrandDto toolBrandToToolBrandDto(ToolBrand toolBrand) {
        return ToolBrandDto.builder()
                .id(toolBrand.getId())
                .name(toolBrand.getName())
                .build();
    }
}
