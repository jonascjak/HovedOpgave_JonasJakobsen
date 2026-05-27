package com.example.hovedopgave_jonasjakobsen.service;


import com.example.hovedopgave_jonasjakobsen.model.PrivateUser;
import com.example.hovedopgave_jonasjakobsen.model.Role;
import com.example.hovedopgave_jonasjakobsen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createPrivateUser(String username, String password, String name) {

        if (userRepository.findByUsername(username).isPresent()) {
            return false;
        }

        PrivateUser user = new PrivateUser();

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setRole(Role.PRIVATE);

        userRepository.save(user);
        return true;
    }
}