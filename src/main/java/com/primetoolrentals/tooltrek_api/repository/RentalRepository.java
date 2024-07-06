package com.primetoolrentals.tooltrek_api.repository;

import com.primetoolrentals.tooltrek_api.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing Rental entities in the database.
 */
@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
}