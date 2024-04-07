package com.project.sportsnewsbackend.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Data Transfer Object for creating a new story.
 * <p>
 * This class encapsulates all the necessary information required to create a new story entity,
 * including the story's title, body content, image URL, publication date, the author's ID,
 * and a list of tag names associated with the story.
 * </p>
 */
@Getter
@Setter
public class StoryCreationDTO {

    /**
     * The title of the story.
     */
    private String title;

    /**
     * The main content or body of the story.
     */
    private String body;

    /**
     * The URL to an image related to the story.
     */
    private String imageURL;

    /**
     * The date and time when the story is published.
     */
    private Date publishedDate;

    /**
     * The ID of the author who created the story.
     */
    private Long authorId;

    /**
     * A list of names of tags associated with the story.
     * <p>
     * These tags help categorize the story and make it more discoverable to users interested in related topics.
     * </p>
     */
    private List<String> tagNames;

    // Lombok generates getters and setters
}
