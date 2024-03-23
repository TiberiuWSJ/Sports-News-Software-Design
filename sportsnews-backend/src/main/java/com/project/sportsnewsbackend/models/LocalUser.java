package com.project.sportsnewsbackend.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "local_user")
public class LocalUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;



    @Column(name = "email", nullable = false, unique = true)
    private String email;



    @Column(name = "password", nullable = false, unique = true)
    private String password;


    @Column(name = "first_name", nullable = false)
    private String firstName;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Getter
    @Column(name = "last_name", nullable = false)
    private String last_name;

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


    @Column(name = "isJournalist", nullable = false)
    private Boolean isJournalist;

    public Boolean getJournalist() {
        return isJournalist;
    }

    public void setJournalist(Boolean journalist) {
        isJournalist = journalist;
    }

    @Column(name = "isModerator", nullable = false)
    private Boolean isModerator;

    public Boolean getModerator() {
        return isModerator;
    }

    public void setModerator(Boolean moderator) {
        isModerator = moderator;
    }

    @Column(name = "favorite_team", nullable = false)
    private String FavoriteTeam;

    public String getFavouriteTeam() {
        return FavoriteTeam;
    }

    public void setFavouriteTeam(String favouriteTeam) {
        FavoriteTeam = favouriteTeam;
    }


    @Column(name = "favorite_sportsman", nullable = false)
    private String FavoriteSportsman;

    public String getFavouriteSportsman() {
        return FavoriteSportsman;
    }

    public void setFavouriteSportsman(String favouriteSportsman) {
        FavoriteSportsman = favouriteSportsman;
    }

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
