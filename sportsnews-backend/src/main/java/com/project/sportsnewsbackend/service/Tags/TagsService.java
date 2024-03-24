package com.project.sportsnewsbackend.service.Tags;

import com.project.sportsnewsbackend.models.Tags;
import com.project.sportsnewsbackend.repository.Tags.TagsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing tag-related operations within the sports news platform.
 * This class provides methods to perform CRUD operations on tags, leveraging the {@link TagsRepository}.
 * It serves as an intermediary between the controller layer and the repository, facilitating the manipulation
 * and retrieval of tag data from the database.
 *
 * @author Rodanciuc Tiberiu-Gabriel
 */
@Service
public class TagsService {

    private final TagsRepository tagsRepository;

    /**
     * Constructs a TagsService with the necessary {@link TagsRepository}.
     *
     * @param tagsRepository The repository used for tag operations.
     */
    public TagsService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    /**
     * Adds a new tag to the database.
     *
     * @param tag The {@link Tags} entity to add.
     * @return The saved {@link Tags} entity.
     */
    public Tags addTag(Tags tag) {
        return tagsRepository.save(tag);
    }

    /**
     * Retrieves all tags from the database.
     *
     * @return A list of {@link Tags} entities.
     */
    public List<Tags> getAllTags() {
        return tagsRepository.findAll();
    }

    /**
     * Fetches a single tag by its ID.
     *
     * @param id The ID of the tag to retrieve.
     * @return An {@link Optional} containing the found tag or empty if not found.
     */
    public Optional<Tags> getTagById(Long id) {
        return tagsRepository.findById(id);
    }

    /**
     * Updates an existing tag with new details. If the tag with the specified ID
     * does not exist, a {@link RuntimeException} is thrown.
     *
     * @param id The ID of the tag to update.
     * @param tagDetails The new details for the tag.
     * @return The updated {@link Tags} entity.
     */
    public Tags updateTag(Long id, Tags tagDetails) {
        Tags tag = tagsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found for this id :: " + id));

        tag.setName(tagDetails.getName());
        // Updates are limited to the name in this method. Associations are managed elsewhere.
        return tagsRepository.save(tag);
    }

    /**
     * Deletes a tag by its ID. If the tag is not found, a {@link RuntimeException} is thrown.
     *
     * @param id The ID of the tag to delete.
     */
    public void deleteTag(Long id) {
        Tags tag = tagsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found for this id :: " + id));

        tagsRepository.delete(tag);
    }
}
