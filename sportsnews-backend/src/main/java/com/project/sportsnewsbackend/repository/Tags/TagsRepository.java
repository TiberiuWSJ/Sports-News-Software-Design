package com.project.sportsnewsbackend.repository.Tags;

import com.project.sportsnewsbackend.models.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for {@link Tags} entities. Extends {@link JpaRepository} to provide
 * standard CRUD operations for {@link Tags} entities within the sports news platform's database.
 *
 * This interface supports the automatic generation of basic CRUD operations for tags without the need
 * for implementing these methods. It can also be extended to include custom query methods to fulfill
 * specific application requirements, such as finding tags by name or listing popular tags.
 *
 * Utilize this interface in service layers to facilitate interaction with the tags stored in the database,
 * leveraging Spring Data JPA's features to simplify data access and manipulation.
 *
 * @author Rodanciuc Tiberiu-Gabriel
 */
@Repository
public interface TagsRepository extends JpaRepository<Tags, Long> {
    Optional<Tags> findByName(String name);
}
