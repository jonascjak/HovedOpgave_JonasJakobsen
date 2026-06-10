package com.example.hovedopgave_jonasjakobsen.repository;


import com.example.hovedopgave_jonasjakobsen.model.CompanyUser;
import com.example.hovedopgave_jonasjakobsen.model.Role;
import com.example.hovedopgave_jonasjakobsen.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<CompanyUser> findByRole(Role role);
}
