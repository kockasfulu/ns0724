package com.primetoolrentals.tooltrek_api.repository;

import com.primetoolrentals.tooltrek_api.entity.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for accessing and managing Tool entities in the database.
 */
@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {

    /**
     * Finds the first Tool entity by its code.
     *
     * @param code The code of the Tool to find.
     * @return Optional containing the Tool entity if found, otherwise empty.
     */
    Optional<Tool> findFirstByCode(String code);
}
