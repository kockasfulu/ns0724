package com.primetoolrentals.tooltrek_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

/**
 * Entity class representing a tool brand.
 */
@Entity
@Table(name = "tool_brands")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ToolBrand extends BaseEntity {

    /**
     * The name of the tool brand.
     */
    @Column(nullable = false, length = 30, unique = true)
    private String name;
}
