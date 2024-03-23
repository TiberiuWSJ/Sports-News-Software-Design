package com.project.sportsnewsbackend.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@IdClass(StoryTagId.class)
@Entity
@Table
public class StoryTag {


    @Id
    @ManyToOne
    @JoinColumn(name = "story_id", referencedColumnName = "StoryID")
    private Stories story;

    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id", referencedColumnName = "TagID")
    private Tags tag;
}
