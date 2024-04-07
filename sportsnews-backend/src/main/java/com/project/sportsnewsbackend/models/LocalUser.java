package com.project.sportsnewsbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Represents a user within the sports news platform.
 * This class encapsulates both journalists and moderators, including personal information,
 * authentication details, roles, and preferences. It provides links to stories authored by the user,
 * the tags they follow, and stories saved for later reading, reflecting the user's interaction
 * and contributions within the platform.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "local_user")
public class LocalUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * A flag indicating whether the user has the journalist role.
     */
    @Column(name = "isJournalist", nullable = false)
    private Boolean isJournalist;

    /**
     * A flag indicating whether the user has the moderator role.
     */
    @Column(name = "isModerator", nullable = false)
    private Boolean isModerator;

    @Column(name = "favorite_team", nullable = false)
    private String favoriteTeam;

    @Column(name = "favorite_sportsman", nullable = false)
    private String favoriteSportsman;

    /**
     * The list of stories authored by this user, highlighting their journalistic contributions.
     */
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stories> authoredStories;

    /**
     * The tag followed by the user, indicating their primary interest within the sports domain.
     */
    @ManyToOne
    @JoinColumn(name = "followed_tag_id") // This column links to the Tags entity.
    private Tags followedTag;

    /**
     * The list of stories saved by the user for later reading, indicating their interests and preferences.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "user_saved_stories",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "story_id")
    )
    private List<Stories> savedStories;
}
