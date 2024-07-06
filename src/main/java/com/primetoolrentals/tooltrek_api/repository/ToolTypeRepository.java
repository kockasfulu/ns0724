package com.primetoolrentals.tooltrek_api.repository;

import com.primetoolrentals.tooltrek_api.entity.ToolType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for accessing and managing ToolType entities in the database.
 */
@Repository
public interface ToolTypeRepository extends JpaRepository<ToolType, Long> {

    /**
     * Finds the first ToolType entity by its name.
     *
     * @param name The name of the ToolType to find.
     * @return Optional containing the ToolType entity if found, otherwise empty.
     */
    Optional<ToolType> findFirstByName(String name);
}
