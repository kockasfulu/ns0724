package com.primetoolrentals.tooltrek_api.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity class representing a tool.
 */
@Entity
@Table(name = "tools", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Tool extends BaseEntity {

    /**
     * The unique code assigned to the tool.
     */
    @Column(nullable = false, length = 10, unique = true)
    private String code;

    /**
     * The brand of the tool.
     */
    @ManyToOne
    @JoinColumn(name = "tool_brand_id", nullable = false)
    private ToolBrand toolBrand;

    /**
     * The type of the tool.
     */
    @ManyToOne
    @JoinColumn(name = "tool_type_id", nullable = false)
    private ToolType toolType;
}
