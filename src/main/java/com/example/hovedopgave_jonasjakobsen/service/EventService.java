package com.example.hovedopgave_jonasjakobsen.service;

import com.example.hovedopgave_jonasjakobsen.model.Event;
import com.example.hovedopgave_jonasjakobsen.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    public List<Event> getEventsForMonth(int year, int month){

        LocalDate start = LocalDate.of(year, month, 1);

        LocalDate end = start.withDayOfMonth(
                start.lengthOfMonth()
        );

        return eventRepository.findByDateBetween(start, end);
    }
}
