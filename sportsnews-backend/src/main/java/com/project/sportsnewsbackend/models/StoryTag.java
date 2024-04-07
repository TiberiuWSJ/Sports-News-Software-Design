package com.project.sportsnewsbackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * Represents the association entity between {@link Stories} and {@link Tags},
 * creating a many-to-many relationship between stories and tags through a composite key.
 * This entity allows stories to be categorized under multiple tags, enhancing discoverability and organization.
 * Each instance of StoryTag corresponds to a unique combination of a story and a tag.
 *
 * @author Rodanciuc Tiberiu-Gabriel
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(StoryTagId.class) // Specify the class used for the composite key
@Entity
@Table
public class StoryTag {

    /**
     * The story part of the composite key, linking to the {@link Stories} entity.
     * This many-to-one relationship indicates the story associated with a tag.
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "story_id", referencedColumnName = "StoryID")
    @Getter
    private Stories story;

    /**
     * The tag part of the composite key, linking to the {@link Tags} entity.
     * This many-to-one relationship indicates the tag associated with a story.
     */
    @Id
    @ManyToOne
    @Setter
    @JoinColumn(name = "tag_id", referencedColumnName = "TagID")
    private Tags tag;
}
