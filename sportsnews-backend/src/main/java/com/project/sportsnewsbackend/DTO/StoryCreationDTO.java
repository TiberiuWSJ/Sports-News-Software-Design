package com.project.sportsnewsbackend.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class StoryCreationDTO {
    private String title;
    private String body;
    private String imageURL;
    private Date publishedDate;
    private Long authorId;
    private List<String> tagNames;

    // Getters and Setters
}
