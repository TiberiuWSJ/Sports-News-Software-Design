package com.project.sportsnewsbackend.models;

import java.io.Serializable;
import java.util.Objects;

public class StoryTagId implements Serializable {
    private Long story; // corresponds to the primary key type of Stories
    private Long tag; // corresponds to the primary key type of Tags

    public StoryTagId() {
    }

    public StoryTagId(Long storyId, Long tagId) {
        this.story = storyId;
        this.tag = tagId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoryTagId that = (StoryTagId) o;
        return Objects.equals(story, that.story) &&
                Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(story, tag);
    }
}
