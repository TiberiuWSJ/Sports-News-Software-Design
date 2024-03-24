package com.project.sportsnewsbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;
import org.springframework.cglib.core.Local;

import java.util.Date;
import java.util.List;

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



    @Column(name = "title", nullable = false, unique = true)
    private String title;



    @Column(name = "body", nullable = false, unique = true, length = 4000)
    private String body;



    @Column(name = "image_url", nullable = false)
    private String imageURL;



    @Getter
    @Column(name = "published_date", nullable = false)
    private Date publishedDate;


    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false) // Assuming 'author_id' is the column name in the 'stories' table
    private LocalUser author;

    @OneToMany(mappedBy = "story", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<StoryTag> tags;


    @ManyToOne
    @JoinColumn(name = "user_id") // This column will store the ID of the user who saved the story
    private LocalUser user;


}
