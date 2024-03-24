package com.project.sportsnewsbackend.service.Tags;

import com.project.sportsnewsbackend.models.Tags;
import com.project.sportsnewsbackend.repository.Tags.TagsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagsService {

    private final TagsRepository tagsRepository;

    public TagsService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    // Add a new tag
    public Tags addTag(Tags tag) {
        return tagsRepository.save(tag);
    }

    // Fetch all tags
    public List<Tags> getAllTags() {
        return tagsRepository.findAll();
    }

    // Fetch a single tag by its ID
    public Optional<Tags> getTagById(Long id) {
        return tagsRepository.findById(id);
    }

    // Update a tag
    public Tags updateTag(Long id, Tags tagDetails) {
        Tags tag = tagsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found for this id :: " + id));

        tag.setName(tagDetails.getName());
        // Note: Direct manipulation of stories and followers lists is avoided here.
        // These associations are managed elsewhere, ensuring consistency and integrity.

        return tagsRepository.save(tag);
    }

    // Delete a tag
    public void deleteTag(Long id) {
        Tags tag = tagsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found for this id :: " + id));

        tagsRepository.delete(tag);
    }
}
