package com.primetoolrentals.tooltrek_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.Instant;

/**
 * A base entity class with common fields for database entities.
 */
@MappedSuperclass
@Getter
@Setter
@ToString
public class BaseEntity {

    /**
     * The primary key ID of the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The timestamp when the entity was created.
     */
    @CreationTimestamp(source = SourceType.DB)
    @Column(updatable = false, insertable = false)
    private Instant createdOn;

    /**
     * The timestamp when the entity was last updated.
     */
    @CreationTimestamp(source = SourceType.DB)
    @Column(updatable = false, insertable = false)
    private Instant lastUpdatedOn;
}
