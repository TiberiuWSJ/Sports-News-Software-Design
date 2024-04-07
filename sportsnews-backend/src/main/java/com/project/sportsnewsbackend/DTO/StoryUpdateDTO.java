package com.project.sportsnewsbackend.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Data Transfer Object for updating an existing story.
 * <p>
 * This class is used to pass data necessary for updating a story's details,
 * including its title, body, associated image, publication date, and related tags.
 * It encapsulates all the modifiable fields that can be updated in an existing story.
 * </p>
 */
@Getter
@Setter
public class StoryUpdateDTO {

    /**
     * The new title for the story. Can be used to replace the existing title.
     */
    private String title;

    /**
     * The new body content of the story. This replaces the existing content.
     */
    private String body;

    /**
     * The new image URL related to the story. It updates the current image URL associated with the story.
     */
    private String imageURL;

    /**
     * The updated publication date for the story. This can be used to correct or change the originally published date.
     */
    private Date publishedDate;

    /**
     * A list of new tag names associated with the story. This allows for updating the tags related to the story,
     * making it possible to reflect changes in the topics or categories the story belongs to.
     */
    private List<String> tagNames;

    // Lombok generates getters and setters
}
