package com.example.hovedopgave_jonasjakobsen.model.service;


import com.example.hovedopgave_jonasjakobsen.model.*;
import com.example.hovedopgave_jonasjakobsen.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EncryptionService encryptionService;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public boolean createPrivateUser(String username, String password, String name) {

        if (userRepository.findByUsername(username).isPresent()) {
            return false;
        }

        PrivateUser user = new PrivateUser();

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(encryptionService.encrypt(name));
        user.setRole(Role.PRIVATE);

        userRepository.save(user);
        return true;
    }

    public List<CompanyUser> findAllStores() {
        return userRepository.findByRole(Role.COMPANY);
    }

    public void deleteUser(long id, Authentication authentication){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getUsername().equals(authentication.getName())) {
            throw new RuntimeException("Du kan kun ændre slette din egen profil");
        }
        userRepository.deleteById(id);
    }

    public void updateUser(long id, String username, String name, String password, String passwordCheck, Authentication authentication){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getUsername().equals(authentication.getName())) {
            throw new RuntimeException("Du kan kun ændre din egen profil");
        }

        boolean usernameChanged = !user.getUsername().equals(username);

        if (usernameChanged && userRepository.existsByUsername(username)) {
            throw new RuntimeException("Brugernavnet er allerede taget");
        }

        user.setUsername(username);
        user.setName(encryptionService.encrypt(name));
        if (password != null && !password.isBlank()) {

            if (!password.equals(passwordCheck)) {
                throw new RuntimeException("Kodeordene matcher ikke");
            }

            user.setPassword(passwordEncoder.encode(password));
        }
        userRepository.save(user);
    }
}