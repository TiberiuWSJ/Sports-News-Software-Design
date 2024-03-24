package com.project.sportsnewsbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Represents a story or article within the sports news platform.
 * Each story contains a title, body, an image URL, and publication date.
 * Stories are linked to an author (a {@link LocalUser}) and can be associated with multiple tags ({@link StoryTag}).
 * Additionally, stories can be saved by users for later reading.
 *
 * @author Rodanciuc Tiberiu-Gabriel
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stories")
public class Stories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storyID", nullable = false)
    private Long storyID;

    /**
     * The title of the story, unique across the platform to ensure distinctiveness.
     */
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    /**
     * The main content or body of the story, capped at 4000 characters.
     * Each story is required to have a non-null body, ensuring substantial content.
     */
    @Column(name = "body", nullable = false, unique = true, length = 4000)
    private String body;

    /**
     * URL to an image relevant to the story, enhancing the reader's experience.
     */
    @Column(name = "image_url", nullable = false)
    private String imageURL;

    /**
     * The date and time when the story was published.
     */
    @Column(name = "published_date", nullable = false)
    private Date publishedDate;

    /**
     * The author of the story, represented by a {@link LocalUser}.
     * Each story must have an author, establishing accountability and origin.
     */
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private LocalUser author;

    /**
     * A list of tags ({@link StoryTag}) associated with the story,
     * allowing for categorization and easier discovery of related content.
     */
    @OneToMany(mappedBy = "story", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<StoryTag> tags;

    /**
     * A reference to a user who has saved this story for later reading.
     * This field allows for the establishment of a personal connection between users and stories.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private LocalUser user;
}
