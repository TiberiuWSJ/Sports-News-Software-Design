package com.project.sportsnewsbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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


    @Getter
    @Setter
    @Column(name = "last_name", nullable = false)
    private String lastName;



    @Column(name = "isJournalist", nullable = false)
    private Boolean isJournalist;


    @Column(name = "isModerator", nullable = false)
    private Boolean isModerator;



    @Column(name = "favorite_team", nullable = false)
    private String FavoriteTeam;




    @Column(name = "favorite_sportsman", nullable = false)
    private String FavoriteSportsman;



    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Stories> authoredStories; // Renamed for clarity

    @ManyToOne
    @JoinColumn(name = "followed_tag_id")
    private Tags followedTag;

    @ManyToMany
    @JoinTable(
            name = "user_saved_stories", // This is a new table for the many-to-many relationship
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "story_id", referencedColumnName = "storyID")}
    )
    private List<Stories> savedStories;


}
