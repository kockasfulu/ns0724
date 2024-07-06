package com.primetoolrentals.tooltrek_api.repository;

import com.primetoolrentals.tooltrek_api.entity.ToolBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for accessing and managing ToolBrand entities in the database.
 */
@Repository
public interface ToolBrandRepository extends JpaRepository<ToolBrand, Long> {

    /**
     * Finds the first ToolBrand entity by its name.
     *
     * @param name The name of the ToolBrand to find.
     * @return Optional containing the ToolBrand entity if found, otherwise empty.
     */
    Optional<ToolBrand> findFirstByName(String name);
}
