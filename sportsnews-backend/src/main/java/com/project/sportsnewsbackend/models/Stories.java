package com.project.sportsnewsbackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.util.Date;
import java.util.List;

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
    @JoinColumn(name = "user_id", nullable = false)
    private LocalUser author;

    @OneToMany(mappedBy = "story", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<StoryTag> tags;


}
