package com.project.sportsnewsbackend.repository.Stories;

import com.project.sportsnewsbackend.models.Stories;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    @Transactional
    @Modifying
    @Query("DELETE FROM Stories s WHERE s.storyID = :id")
    void deleteByIdCustom(@Param("id") Long id);

    @Query("SELECT s FROM Stories s WHERE s.title LIKE %:keyword% OR s.body LIKE %:keyword%")
    List<Stories> searchStories(String keyword);
}
