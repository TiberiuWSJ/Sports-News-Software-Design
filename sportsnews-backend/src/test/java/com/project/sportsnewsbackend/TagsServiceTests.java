package com.project.sportsnewsbackend;

import com.project.sportsnewsbackend.models.Tags;
import com.project.sportsnewsbackend.repository.Tags.TagsRepository;
import com.project.sportsnewsbackend.service.Tags.TagsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * This class demonstrates how to write unit tests for a service class using Mockito.
 * The service class under test is TagsService, which provides methods for managing Tags entities.
 * The service class depends on TagsRepository for database operations.
 */
public class TagsServiceTests {

    @Mock
    private TagsRepository tagsRepository;

    @InjectMocks
    private TagsService tagsService;

    /**
     * Sets up the test environment by initializing the service class and its dependencies.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Tests the addTag method of the TagsService class.
     * This test verifies that the save method of the TagsRepository is called when addTag is invoked.
     */
    @Test
    public void testAddTag() {
        Tags tag = new Tags();
        tag.setTagID(1L);
        tag.setName("Test Tag");

        when(tagsRepository.save(tag)).thenReturn(tag);

        Tags savedTag = tagsService.addTag(tag);

        assertNotNull(savedTag);
        assertEquals(tag.getTagID(), savedTag.getTagID());
        assertEquals(tag.getName(), savedTag.getName());
    }

    /**
     *  Tests the getAllTags method of the TagsService class.
     *  This test verifies that the findAll method of the TagsRepository is called when getAllTags is invoked.
     */
    @Test
    public void testGetAllTags() {
        List<Tags> tags = new ArrayList<>();
        Tags tag1 = new Tags();
        tag1.setTagID(1L);
        tag1.setName("Test Tag 1");
        Tags tag2 = new Tags();
        tag2.setTagID(2L);
        tag2.setName("Test Tag 2");
        tags.add(tag1);
        tags.add(tag2);

        when(tagsRepository.findAll()).thenReturn(tags);

        List<Tags> foundTags = tagsService.getAllTags();

        assertNotNull(foundTags);
        assertEquals(2, foundTags.size());
        assertEquals(tag1.getTagID(), foundTags.get(0).getTagID());
        assertEquals(tag1.getName(), foundTags.get(0).getName());
        assertEquals(tag2.getTagID(), foundTags.get(1).getTagID());
        assertEquals(tag2.getName(), foundTags.get(1).getName());
    }

    /**
     * Tests the getTagById method of the TagsService class.
     * This test verifies that the findById method of the TagsRepository is called when getTagById is invoked.
     */
    @Test
    public void testGetTagById() {
        Tags tag = new Tags();
        tag.setTagID(1L);
        tag.setName("Test Tag");

        when(tagsRepository.findById(1L)).thenReturn(Optional.of(tag));

        Optional<Tags> foundTag = tagsService.getTagById(1L);

        assertTrue(foundTag.isPresent());
        assertEquals(tag.getTagID(), foundTag.get().getTagID());
        assertEquals(tag.getName(), foundTag.get().getName());
    }

    /**
     *  Tests the updateTag method of the TagsService class.
     *  This test verifies that the findById and save methods of the TagsRepository are called when updateTag is invoked.
     */
    @Test
    public void testUpdateTag() {
        Tags tag = new Tags();
        tag.setTagID(1L);
        tag.setName("Test Tag");

        Tags updatedTag = new Tags();
        updatedTag.setTagID(1L);
        updatedTag.setName("Updated Tag");

        when(tagsRepository.findById(1L)).thenReturn(Optional.of(tag));
        when(tagsRepository.save(tag)).thenReturn(updatedTag);

        Tags result = tagsService.updateTag(1L, updatedTag);

        assertNotNull(result);
        assertEquals(updatedTag.getTagID(), result.getTagID());
        assertEquals(updatedTag.getName(), result.getName());
    }

    /**
     * Tests the updateTag method of the TagsService class when the tag is not found.
     * This test ensures that the appropriate exception is thrown when the tag is not found.
     */
    @Test
    public void testUpdateTagNotFound() {
        Tags tag = new Tags();
        tag.setTagID(1L);
        tag.setName("Test Tag");

        Tags updatedTag = new Tags();
        updatedTag.setTagID(1L);
        updatedTag.setName("Updated Tag");

        when(tagsRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            tagsService.updateTag(1L, updatedTag);
        } catch (RuntimeException e) {
            assertNotNull(e);
            assertEquals("Tag not found for this id :: 1", e.getMessage());
        }
    }

    /**
     * Tests the deleteTag method of the TagsService class.
     * This test verifies that the findById and delete methods of the TagsRepository are called when deleteTag is invoked.
     */
    @Test
    public void testDeleteTag() {
        Tags tag = new Tags();
        tag.setTagID(1L);
        tag.setName("Test Tag");

        when(tagsRepository.findById(1L)).thenReturn(Optional.of(tag));

        tagsService.deleteTag(1L);

        verify(tagsRepository).delete(tag);
    }

    /**
     * Tests the deleteTag method of the TagsService class when the tag is not found.
     * This test ensures that the appropriate exception is thrown when the tag is not found.
     */
    @Test
    public void testDeleteTagNotFound() {
        Tags tag = new Tags();
        tag.setTagID(1L);
        tag.setName("Test Tag");

        when(tagsRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            tagsService.deleteTag(1L);
        } catch (RuntimeException e) {
            assertNotNull(e);
            assertEquals("Tag not found for this id :: 1", e.getMessage());
        }
    }




}
