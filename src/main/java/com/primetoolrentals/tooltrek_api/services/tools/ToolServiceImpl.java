package com.primetoolrentals.tooltrek_api.services.tools;

import com.primetoolrentals.tooltrek_api.dto.ToolDto;
import com.primetoolrentals.tooltrek_api.entity.Tool;
import com.primetoolrentals.tooltrek_api.entity.ToolBrand;
import com.primetoolrentals.tooltrek_api.entity.ToolType;
import com.primetoolrentals.tooltrek_api.exception.ToolBrandNotFoundException;
import com.primetoolrentals.tooltrek_api.exception.ToolTypeNotFoundException;
import com.primetoolrentals.tooltrek_api.repository.ToolBrandRepository;
import com.primetoolrentals.tooltrek_api.repository.ToolRepository;
import com.primetoolrentals.tooltrek_api.repository.ToolTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of ToolService interface for managing tools.
 */
@Service
@RequiredArgsConstructor
public class ToolServiceImpl implements ToolService {

    private final ToolBrandRepository toolBrandRepository;
    private final ToolRepository toolRepository;
    private final ToolTypeRepository toolTypeRepository;

    /**
     * Adds a new tool.
     *
     * @param toolDto The ToolDto object containing details of the tool to create.
     * @return The created ToolDto object.
     * @throws ToolBrandNotFoundException If the tool brand with the specified ID is not found.
     * @throws ToolTypeNotFoundException  If the tool type with the specified ID is not found.
     */
    @Override
    public ToolDto addTool(ToolDto toolDto) {
        final ToolBrand brand = toolBrandRepository.findById(toolDto.getToolBrandId()).orElseThrow(ToolBrandNotFoundException::new);
        final ToolType type = toolTypeRepository.findById(toolDto.getToolTypeId()).orElseThrow(ToolTypeNotFoundException::new);

        final Tool tool = Tool.builder()
                .toolType(type)
                .toolBrand(brand)
                .code(toolDto.getCode())
                .build();
        final Tool newTool = toolRepository.save(tool);
        return ToolDto.builder()
                .id(newTool.getId())
                .code(newTool.getCode())
                .toolBrandName(brand.getName())
                .toolBrandId(brand.getId())
                .toolTypeName(type.getName())
                .toolTypeId(type.getId())
                .build();
    }

    /**
     * Retrieves all tools.
     *
     * @return List of ToolDto objects representing all tools.
     */
    @Override
    public List<ToolDto> findAllTools() {
        return toolRepository.findAll().stream().map(this::toolToToolDto).toList();
    }

    /**
     * Checks if the tool code is unique.
     *
     * @param code The code of the tool to check.
     * @return true if the tool code is unique, false otherwise.
     */
    @Override
    public boolean isToolCodeUnique(String code) {
        return toolRepository.findFirstByCode(code).isEmpty();
    }

    /**
     * Converts a Tool entity to ToolDto.
     *
     * @param tool The Tool entity to convert.
     * @return The corresponding ToolDto object.
     */
    private ToolDto toolToToolDto(Tool tool) {
        return ToolDto.builder()
                .id(tool.getId())
                .code(tool.getCode())
                .toolBrandName(tool.getToolBrand().getName())
                .toolBrandId(tool.getToolBrand().getId())
                .toolTypeName(tool.getToolType().getName())
                .toolTypeId(tool.getToolType().getId())
                .build();
    }
}
