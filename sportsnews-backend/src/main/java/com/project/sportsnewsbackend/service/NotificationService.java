package com.project.sportsnewsbackend.service;

import com.project.sportsnewsbackend.models.LocalUser;
import com.project.sportsnewsbackend.models.Stories;
import com.project.sportsnewsbackend.repository.LocalUser.LocalUserRepository;
import com.project.sportsnewsbackend.repository.Tags.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class NotificationService {

    @Autowired
    private LocalUserRepository localUserRepository;
    @Autowired
    private TagsRepository tagsRepository;

    public void notifyFollowersOfTag(Long tagId, Stories newStory) {
        // Find all users following this tag
        List<LocalUser> followers = tagsRepository.findById(tagId)
                .orElseThrow()
                .getFollowers();

        for (LocalUser follower : followers) {
            sendNotification(follower, newStory);
        }
    }

    private void sendNotification(LocalUser user, Stories story) {
        // Implement your notification logic here
        // For example, send an email to the user with the new story details
        System.out.println("Notifying " + user.getId() + " about new story: " + story.getTitle());

    }
}
