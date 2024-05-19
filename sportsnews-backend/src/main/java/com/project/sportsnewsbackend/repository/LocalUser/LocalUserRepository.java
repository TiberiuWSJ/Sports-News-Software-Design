package com.project.sportsnewsbackend.repository.LocalUser;

import com.project.sportsnewsbackend.models.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for {@link LocalUser} entities.
 * This repository provides CRUD operations and finder methods for managing {@link LocalUser} entities within the database.
 * It leverages Spring Data JPA's capabilities to reduce boilerplate data access code and supports custom query methods as needed.
 *
 * Use this repository to perform database operations related to local users, such as finding a user by email,
 * listing users by roles (e.g., journalists or moderators), or any other operations specific to the {@link LocalUser} entity.
 *
 * @author Rodanciuc Tiberiu-Gabriel
 */
@Repository
public interface LocalUserRepository extends JpaRepository<LocalUser, Long> {
    Optional<LocalUser> findByEmail(String email);
    Optional<LocalUser> findByFirstNameAndLastName(String firstName, String lastName);
    boolean existsByEmail(String email);
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
    LocalUser findByEmailAndPassword(String email, String password);
}

