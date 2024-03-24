package com.project.sportsnewsbackend.service.LocalUser;

import com.project.sportsnewsbackend.models.LocalUser;
import com.project.sportsnewsbackend.repository.LocalUser.LocalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalUserService {

    private final LocalUserRepository localUserRepository;

    @Autowired
    public LocalUserService(LocalUserRepository localUserRepository) {
        this.localUserRepository = localUserRepository;
    }

    public List<LocalUser> findAllUsers() {
        return localUserRepository.findAll();
    }

    public Optional<LocalUser> findUserById(Long id) {
        return localUserRepository.findById(id);
    }

    public LocalUser saveUser(LocalUser user) {
        return localUserRepository.save(user);
    }

    public void deleteUserById(Long id) {
        localUserRepository.deleteById(id);
    }

    // More methods can be added as per the business logic requirements.
}
