package com.project.sportsnewsbackend.repository.StoryTag;

import com.project.sportsnewsbackend.models.StoryTag;
import com.project.sportsnewsbackend.models.StoryTagId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryTagRepository extends JpaRepository<StoryTag, StoryTagId> {

}
