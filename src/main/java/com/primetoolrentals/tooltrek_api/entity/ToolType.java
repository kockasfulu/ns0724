package com.primetoolrentals.tooltrek_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

/**
 * Entity class representing a tool type.
 */
@Entity
@Table(name = "tool_types")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ToolType extends BaseEntity {

    /**
     * The name of the tool type.
     */
    @Column(nullable = false, length = 30, unique = true)
    private String name;

    /**
     * The daily rental charge for the tool type.
     */
    @Column(precision = 10, scale = 2)
    private BigDecimal dailyCharge;

    /**
     * Flag indicating if weekday charges apply for the tool type.
     */
    @Column(nullable = false)
    private Boolean isWeekdayCharge;

    /**
     * Flag indicating if weekend charges apply for the tool type.
     */
    @Column(nullable = false)
    private Boolean isWeekendCharge;

    /**
     * Flag indicating if holiday charges apply for the tool type.
     */
    @Column(nullable = false)
    private Boolean isHolidayCharge;
}
