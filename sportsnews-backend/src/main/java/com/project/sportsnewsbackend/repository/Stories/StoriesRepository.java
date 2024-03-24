package com.project.sportsnewsbackend.repository.Stories;

import com.project.sportsnewsbackend.models.Stories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Stories} entities. It extends the {@link JpaRepository} interface
 * provided by Spring Data JPA, inheriting basic CRUD operations for managing {@link Stories} entities
 * within the database.
 *
 * This repository interface can be extended with custom query methods to support additional
 * querying capabilities tailored to the needs of the sports news platform. For example, finding stories
 * by title, filtering stories by publication date, or listing stories associated with a specific tag.
 *
 * Utilize this interface within service classes to interact with the database through the provided
 * CRUD operations and any custom methods defined here.
 *
 * @author Rodanciuc Tiberiu-Gabriel
 */
@Repository
public interface StoriesRepository extends JpaRepository<Stories, Long> {
    // Potential place for custom query methods
}
