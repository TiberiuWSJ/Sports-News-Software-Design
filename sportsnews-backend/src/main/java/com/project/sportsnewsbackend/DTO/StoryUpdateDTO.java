package com.project.sportsnewsbackend.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Getter
@Setter
public class StoryUpdateDTO {
    private String title;
    private String body;
    private String imageURL;
    private Date publishedDate;
    private List<String> tagNames;

    // Getters and Setters
}
