package com.primetoolrentals.tooltrek_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Entity class representing a rental.
 */
@Entity
@Table(name = "rentals")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Rental extends BaseEntity {

    /**
     * The tool rented in this rental.
     */
    @ManyToOne
    @JoinColumn(name = "tool_id", nullable = false)
    private Tool tool;

    /**
     * The number of days for the rental.
     */
    @Column(nullable = false)
    private Integer rentalDayCount;

    /**
     * The discount percentage for the rental.
     */
    @Column(nullable = false)
    private Integer rentalDiscount;

    /**
     * The date when the rental starts.
     */
    @Column(nullable = false)
    private LocalDate rentalDate;
}
