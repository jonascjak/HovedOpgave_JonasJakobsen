package com.example.hovedopgave_jonasjakobsen.model.repository;
import com.example.hovedopgave_jonasjakobsen.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
