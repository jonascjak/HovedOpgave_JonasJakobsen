package com.example.hovedopgave_jonasjakobsen.config;

import com.example.hovedopgave_jonasjakobsen.model.Role;
import com.example.hovedopgave_jonasjakobsen.model.CompanyUser;
import com.example.hovedopgave_jonasjakobsen.model.PrivateUser;
import com.example.hovedopgave_jonasjakobsen.model.repository.UserRepository;
import com.example.hovedopgave_jonasjakobsen.model.service.EncryptionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TestDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final EncryptionService encryptionService;
    public TestDataLoader(UserRepository userRepository,
                          PasswordEncoder passwordEncoder, EncryptionService encryptionService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.encryptionService = encryptionService;
    }

    @Override
    public void run(String... args) {

        if (userRepository.findByUsername("company").isEmpty()) {

            CompanyUser companyUser = new CompanyUser();

            companyUser.setUsername("company");
            companyUser.setPassword(passwordEncoder.encode("1234"));
            companyUser.setName(encryptionService.encrypt("Test Company"));

            companyUser.setRole(Role.COMPANY);

            companyUser.setCompanyAddress("Testvej 1");
            companyUser.setCompanyWebsiteURL("https://company.dk");

            userRepository.save(companyUser);
        }

        if (userRepository.findByUsername("zzgames").isEmpty()) {

            CompanyUser companyUser = new CompanyUser();

            companyUser.setUsername("zzgames");
            companyUser.setPassword(passwordEncoder.encode("1234"));
            companyUser.setName(encryptionService.encrypt("ZZGames"));

            companyUser.setRole(Role.COMPANY);

            companyUser.setCompanyAddress("Rugvænget 44");
            companyUser.setCompanyWebsiteURL("https://zzgames.dk");

            userRepository.save(companyUser);
        }


        if (userRepository.findByUsername("private").isEmpty()) {

            PrivateUser privateUser = new PrivateUser();

            privateUser.setUsername("private");
            privateUser.setPassword(passwordEncoder.encode("1234"));
            privateUser.setName(encryptionService.encrypt("Private Test User"));

            privateUser.setRole(Role.PRIVATE);

            userRepository.save(privateUser);
        }
    }
}