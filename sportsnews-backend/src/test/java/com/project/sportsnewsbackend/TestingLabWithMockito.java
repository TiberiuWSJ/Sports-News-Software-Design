package com.project.sportsnewsbackend;


import com.project.sportsnewsbackend.models.LocalUser;
import com.project.sportsnewsbackend.repository.LocalUser.LocalUserRepository;
import com.project.sportsnewsbackend.repository.Tags.TagsRepository;
import com.project.sportsnewsbackend.service.LocalUser.LocalUserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TestingLabWithMockito {

    @Mock
    private LocalUserRepository mockLocalUserRepository;

    @Mock
    private TagsRepository mockTagsRepository;
    private LocalUserService testLocalUserService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.testLocalUserService = new LocalUserService(mockLocalUserRepository, mockTagsRepository);
    }

    @Test
    public void testSaveUser(){
        LocalUser user = new LocalUser();
        testLocalUserService.saveUser(user);
        Mockito.verify(mockLocalUserRepository).save(user);

    }

    @Test
    public void testDeleteUser(){
        LocalUser user = new LocalUser();
        testLocalUserService.deleteUserById(user.getId());
        Mockito.verify(mockLocalUserRepository).deleteById(user.getId());
    }


}
