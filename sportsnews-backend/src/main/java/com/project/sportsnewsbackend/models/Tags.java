package com.project.sportsnewsbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Represents a tag entity within the sports news platform, allowing for the categorization of stories.
 * Tags provide a way to organize stories into different themes or topics, enhancing discoverability and searchability.
 * Each tag is unique and can be associated with multiple stories through the {@link StoryTag} entity.
 * Tags also track the users who have chosen to follow them, indicating their interests and preferences.
 *
 * @author Rodanciuc Tiberiu-Gabriel
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Tags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tagID", nullable = false)
    private Long tagID;

    /**
     * The name of the tag, unique across all tags to ensure distinct identification.
     */
    @Column(name = "tag_name", unique = true, nullable = false)
    private String name;

    /**
     * A list of {@link StoryTag} associations, representing all the stories categorized under this tag.
     * This allows for a many-to-many relationship between stories and tags to be modeled.
     */
    @OneToMany(mappedBy = "tag", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<StoryTag> stories;

    /**
     * A list of {@link LocalUser} entities that follow this tag, indicating a preference or interest
     * in the topics or themes represented by the tag. This establishes a direct relationship between
     * tags and users, supporting personalized content delivery and notifications.
     */
    @OneToMany(mappedBy = "followedTag")
    private List<LocalUser> followers;


}
