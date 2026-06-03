package com.example.hovedopgave_jonasjakobsen.repository;

import com.example.hovedopgave_jonasjakobsen.model.EventParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, Long> {
    boolean existsByEventIdAndPrivateUserUsername(long id, String name);
}
