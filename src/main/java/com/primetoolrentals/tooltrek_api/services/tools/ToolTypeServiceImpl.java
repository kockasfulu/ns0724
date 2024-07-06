package com.primetoolrentals.tooltrek_api.services.tools;

import com.primetoolrentals.tooltrek_api.dto.ToolTypeDto;
import com.primetoolrentals.tooltrek_api.entity.ToolType;
import com.primetoolrentals.tooltrek_api.repository.ToolTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implementation of ToolTypeService that manages CRUD operations for Tool Types.
 */
@Service
@RequiredArgsConstructor
public class ToolTypeServiceImpl implements ToolTypeService {

    private final ToolTypeRepository toolTypeRepository;

    /**
     * Adds a new tool type.
     *
     * @param toolTypeDto The ToolTypeDto object containing details of the tool type to create.
     * @return The created ToolTypeDto object.
     */
    @Override
    public ToolTypeDto addToolType(ToolTypeDto toolTypeDto) {
        final ToolType newToolType = ToolType.builder()
                .name(toolTypeDto.getName())
                .dailyCharge(BigDecimal.valueOf(toolTypeDto.getDailyCharge()))
                .isHolidayCharge(toolTypeDto.getIsHolidayCharge())
                .isWeekdayCharge(toolTypeDto.getIsWeekdayCharge())
                .isWeekendCharge(toolTypeDto.getIsWeekendCharge())
                .build();
        final ToolType persistedToolType = toolTypeRepository.save(newToolType);
        return ToolTypeDto.builder()
                .id(persistedToolType.getId())
                .name(persistedToolType.getName())
                .dailyCharge(persistedToolType.getDailyCharge().doubleValue())
                .isWeekdayCharge(persistedToolType.getIsWeekdayCharge())
                .isHolidayCharge(persistedToolType.getIsHolidayCharge())
                .isWeekendCharge(persistedToolType.getIsWeekendCharge())
                .build();
    }

    /**
     * Retrieves all tool types.
     *
     * @return List of ToolTypeDto objects representing all tool types.
     */
    @Override
    public List<ToolTypeDto> findAllToolTypes() {
        return toolTypeRepository.findAll().stream().map(this::toolTypeToToolTypeDto).toList();
    }

    /**
     * Checks if the tool type name is unique.
     *
     * @param name The name of the tool type to check.
     * @return true if the tool type name is unique, false otherwise.
     */
    @Override
    public boolean isToolTypeNameUnique(String name) {
        return toolTypeRepository.findFirstByName(name).isEmpty();
    }

    /**
     * Converts a ToolType entity to ToolTypeDto.
     *
     * @param toolType The ToolType entity to convert.
     * @return ToolTypeDto representing the converted ToolType entity.
     */
    private ToolTypeDto toolTypeToToolTypeDto(ToolType toolType) {
        return ToolTypeDto.builder()
                .id(toolType.getId())
                .name(toolType.getName())
                .dailyCharge(toolType.getDailyCharge().doubleValue())
                .isHolidayCharge(toolType.getIsHolidayCharge())
                .isWeekdayCharge(toolType.getIsWeekdayCharge())
                .isWeekendCharge(toolType.getIsWeekendCharge())
                .build();
    }
}
