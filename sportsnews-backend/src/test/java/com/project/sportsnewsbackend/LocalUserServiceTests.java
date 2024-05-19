package com.project.sportsnewsbackend;

import com.project.sportsnewsbackend.models.LocalUser;
import com.project.sportsnewsbackend.models.Tags;
import com.project.sportsnewsbackend.repository.LocalUser.LocalUserRepository;
import com.project.sportsnewsbackend.repository.Notification.NotificationRepository;
import com.project.sportsnewsbackend.repository.Tags.TagsRepository;
import com.project.sportsnewsbackend.service.LocalUser.LocalUserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

/**
 * This class demonstrates how to write unit tests for a service class using Mockito.
 * The service class under test is LocalUserService, which provides methods for managing LocalUser entities.
 * The service class depends on LocalUserRepository and TagsRepository for database operations.
 */

public class LocalUserServiceTests {

    @Mock
    private LocalUserRepository mockLocalUserRepository;
    @Mock
    private TagsRepository mockTagsRepository;

    @Mock
    NotificationRepository notificationRepository;

    private LocalUserService testLocalUserService;


    /**
     * Sets up the test environment by initializing the service class and its dependencies.
     * This method is executed before each test method.
     * It initializes the service class and mocks the repository dependencies.
     * The service class is instantiated with the mocked repositories.
     * This allows the test methods to interact with the service class using the mocked repositories.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.testLocalUserService = new LocalUserService(mockLocalUserRepository, mockTagsRepository, notificationRepository);
    }


    /**
     * Tests the findAllUsers method of the LocalUserService class.
     * This test verifies that the findAll method of the LocalUserRepository is called when findAllUsers is invoked.
     */
    @Test
    public void testFindAllUsers() {
        Mockito.when(mockLocalUserRepository.findAll()).thenReturn(Arrays.asList(new LocalUser(), new LocalUser()));
        testLocalUserService.findAllUsers();
        Mockito.verify(mockLocalUserRepository).findAll();
    }


    /**
     *  Tests the findUserById method of the LocalUserService class.
     *  This test verifies that the findById method of the LocalUserRepository is called when findUserById is invoked.
     */
    @Test
    public void testFindUserById() {
        Long userId = 1L;
        Mockito.when(mockLocalUserRepository.findById(userId)).thenReturn(Optional.of(new LocalUser()));
        testLocalUserService.findUserById(userId);
        Mockito.verify(mockLocalUserRepository).findById(userId);
    }

    @Test
    public void testSaveUser() {
        LocalUser user = new LocalUser();
        Mockito.when(mockLocalUserRepository.save(user)).thenReturn(user);
        testLocalUserService.saveUser(user);
        Mockito.verify(mockLocalUserRepository).save(user);
    }

    @Test
    public void testDeleteUserById() {
        Long userId = 1L;
        // Setup the mock to return true when existsById is called with the user ID
        Mockito.when(mockLocalUserRepository.existsById(userId)).thenReturn(true);
        // Call the method under test
        testLocalUserService.deleteUserById(userId);
        // Verify that deleteById was called on the userRepository
        Mockito.verify(mockLocalUserRepository).deleteById(userId);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUserById_UserNotFound() {
        Long userId = 1L;
        //  Setup the mock to return false when existsById is called with the user ID
        Mockito.when(mockLocalUserRepository.existsById(userId)).thenReturn(false);
        try {
            // Call the method under test
            testLocalUserService.deleteUserById(userId);
        } finally {
            //  Verify that deleteById was never called on the userRepository because the user was not found
            Mockito.verify(mockLocalUserRepository, Mockito.never()).deleteById(userId);
        }
    }

    @Test
    public void testSetUserFavoriteTag() {
        Long userId = 1L;
        Long tagId = 100L;
        LocalUser user = new LocalUser();
        Tags tag = new Tags();
        // Setup the mock to return the user when findById is called
        Mockito.when(mockLocalUserRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(mockTagsRepository.findById(tagId)).thenReturn(Optional.of(tag));

        // Call the method under test
        testLocalUserService.setUserFavoriteTag(userId, tagId);

        Mockito.verify(mockLocalUserRepository).findById(userId);
        Mockito.verify(mockTagsRepository).findById(tagId);
        Mockito.verify(mockLocalUserRepository).save(user);
    }

    @Test
    public void testSetUserFavoriteTag_UserNotFound() {
        Long userId = 1L;
        Long tagId = 100L;

        // Setup the mock to return an empty Optional, simulating the user not being found
        Mockito.when(mockLocalUserRepository.findById(userId)).thenReturn(Optional.empty());

        // Call the method under test
        testLocalUserService.setUserFavoriteTag(userId, tagId);

        // Verify that findById was called on the userRepository
        Mockito.verify(mockLocalUserRepository).findById(userId);

        // Verify that findById was never called on the tagsRepository because the user was not found
        Mockito.verify(mockTagsRepository, Mockito.never()).findById(tagId);

        // Verify that save was never called because no user was found to update
        Mockito.verify(mockLocalUserRepository, Mockito.never()).save(Mockito.any(LocalUser.class));
    }

    @Test
    public void testSetUserFavoriteTag_TagNotFound() {
        Long userId = 1L;
        Long tagId = 100L;
        LocalUser user = new LocalUser();

        // Setup the mock to return the user when findById is called
        Mockito.when(mockLocalUserRepository.findById(userId)).thenReturn(Optional.of(user));

        // Setup the mock to return an empty Optional, simulating the tag not being found
        Mockito.when(mockTagsRepository.findById(tagId)).thenReturn(Optional.empty());

        // Call the method under test
        testLocalUserService.setUserFavoriteTag(userId, tagId);

        // Verify that findById was called on the userRepository
        Mockito.verify(mockLocalUserRepository).findById(userId);

        // Verify that findById was called on the tagsRepository because the user was found
        Mockito.verify(mockTagsRepository).findById(tagId);

        // Verify that save was never called because the tag was not found
        Mockito.verify(mockLocalUserRepository, Mockito.never()).save(Mockito.any(LocalUser.class));
    }


}
