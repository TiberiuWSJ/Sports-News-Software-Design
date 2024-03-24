package com.project.sportsnewsbackend.models;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the composite key for {@link StoryTag} entity,
 * encapsulating the association between a story and a tag.
 * This class defines the composite key made up of the story ID and the tag ID,
 * corresponding to the primary key types of {@link Stories} and {@link Tags}, respectively.
 *
 * <p>Implements {@link Serializable} to support serialization and use as an identifier
 * in the Hibernate and JPA context.</p>
 *
 * <p>Overrides {@code equals} and {@code hashCode} to ensure proper comparison and hashing,
 * which is crucial for composite key usage in JPA.</p>
 *
 * @author Rodanciuc Tiberiu-Gabriel
 */
public class StoryTagId implements Serializable {
    private Long story; // Corresponds to the primary key type of Stories
    private Long tag; // Corresponds to the primary key type of Tags

    /**
     * Default constructor for JPA and Hibernate.
     */
    public StoryTagId() {
    }

    /**
     * Constructs a new {@code StoryTagId} with the specified story ID and tag ID.
     *
     * @param storyId The ID of the story part of this composite key.
     * @param tagId The ID of the tag part of this composite key.
     */
    public StoryTagId(Long storyId, Long tagId) {
        this.story = storyId;
        this.tag = tagId;
    }

    // Getters and setters for story and tag can be added here for completeness

    /**
     * Checks if this {@code StoryTagId} is equal to another object.
     *
     * @param o The object to compare this {@code StoryTagId} against.
     * @return true if the given object represents a {@code StoryTagId} equivalent to this instance, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoryTagId that = (StoryTagId) o;
        return Objects.equals(story, that.story) &&
                Objects.equals(tag, that.tag);
    }

    /**
     * Computes the hash code for this {@code StoryTagId}.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(story, tag);
    }
}
