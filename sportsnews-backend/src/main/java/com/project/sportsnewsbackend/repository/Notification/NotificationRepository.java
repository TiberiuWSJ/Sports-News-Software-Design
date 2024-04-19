package com.project.sportsnewsbackend.repository.Notification;

import com.project.sportsnewsbackend.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByLocalUserId(Long userId);
}