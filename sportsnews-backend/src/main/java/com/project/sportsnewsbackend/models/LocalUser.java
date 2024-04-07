package com.project.sportsnewsbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Represents a local user within the system, encapsulating details for both journalists and moderators.
 * This entity includes personal information, authentication details, roles, and preferences.
 * It also links to stories authored by the user, tags they follow, and stories they've saved,
 * reflecting their interactions and contributions within the sports news domain.
 *
 * @author Rodanciuc Tiberiu-Gabriel
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
     * Indicates if the user holds the journalist role within the platform.
     */
    @Column(name = "isJournalist", nullable = false)
    private Boolean isJournalist;

    /**
     * Indicates if the user holds the moderator role within the platform.
     */
    @Column(name = "isModerator", nullable = false)
    private Boolean isModerator;

    @Column(name = "favorite_team", nullable = false)
    private String favoriteTeam;

    @Column(name = "favorite_sportsman", nullable = false)
    private String favoriteSportsman;

    /**
     * Stories authored by the user, highlighting their contributions as a journalist.
     */
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = CascadeType.ALL)

    private List<Stories> authoredStories;

    /**
     * The single tag followed by the user, reflecting their primary interest within the sports domain.
     */
    @ManyToOne
    @JoinColumn(name = "followed_tag_id")

    private Tags followedTag;

    /**
     * Stories saved by the user for later reading, indicating their interests and preferences.
     */
    @ManyToMany
    @JoinTable(
            name = "user_saved_stories",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "story_id", referencedColumnName = "storyID")}
    )

    private List<Stories> savedStories;

}
