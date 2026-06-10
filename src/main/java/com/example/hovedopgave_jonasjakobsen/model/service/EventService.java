package com.example.hovedopgave_jonasjakobsen.model.service;

import com.example.hovedopgave_jonasjakobsen.model.*;
import com.example.hovedopgave_jonasjakobsen.model.repository.EventParticipantRepository;
import com.example.hovedopgave_jonasjakobsen.model.repository.EventRepository;
import com.example.hovedopgave_jonasjakobsen.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventParticipantRepository eventParticipantRepository;
    public boolean createEvent(String eventName, String eventGame, String eventDescription, LocalDate date, LocalTime startTime, String userName){
        if(eventName == null || eventName.length() < 3 || eventName.length() > 50){
            return false;
        }
        if(eventGame == null || eventGame.length() < 3 || eventGame.length() > 50){
            return false;
        }
        if(eventDescription.length() > 500){
            return false;
        }

        Optional<User> userOptional = userRepository.findByUsername(userName);
        User user = userOptional.get();

        if (!(user instanceof CompanyUser companyUser)){
            return false;
        }
        Event event = new Event();

        event.setEventName(eventName);
        event.setEventGame(eventGame);
        event.setDescription(eventDescription);
        event.setDate(date);
        event.setStartTime(startTime);
        event.setAddress(companyUser.getCompanyAddress());
        event.setEventOrganiser(companyUser);

        eventRepository.save(event);
        return true;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event findById(long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public void updateEvent(long id, String eventName, String eventGame, String eventDescription, LocalTime startTime) {
        Event event = eventRepository.getReferenceById(id);

        event.setEventName(eventName);
        event.setEventGame(eventGame);
        event.setDescription(eventDescription);
        event.setStartTime(startTime);

        eventRepository.save(event);
    }

    public boolean joinEvent(long id, String name) {
        Event event = eventRepository.findById(id).orElseThrow();

        User foundUser = userRepository.findByUsername(name).orElseThrow();

        if (!(foundUser instanceof PrivateUser user)) {
            return false;
        }

        if (eventParticipantRepository.existsByEventIdAndPrivateUserUsername(id, name)) {
            return false;
        }

        EventParticipant participant = new EventParticipant();
        participant.setEvent(event);
        participant.setPrivateUser(user);

        eventParticipantRepository.save(participant);

        return true;
    }

    public void deleteEvent(long id) {
        eventRepository.deleteById(id);
    }
}
