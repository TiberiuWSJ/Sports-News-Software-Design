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

public class TagsServiceTests {

    @Mock
    private TagsRepository tagsRepository;

    @InjectMocks
    private TagsService tagsService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

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

    @Test
    public void testDeleteTag() {
        Tags tag = new Tags();
        tag.setTagID(1L);
        tag.setName("Test Tag");

        when(tagsRepository.findById(1L)).thenReturn(Optional.of(tag));

        tagsService.deleteTag(1L);

        verify(tagsRepository).delete(tag);
    }

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
