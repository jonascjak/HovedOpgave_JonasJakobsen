package com.example.hovedopgave_jonasjakobsen.model;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private String eventName;

    @Column(nullable = false)
    private String eventGame;


    @Column(length = 500)
    private String description;

    private String address;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_organiser_id", nullable = false)
    private CompanyUser eventOrganiser;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventParticipant> participants = new ArrayList<>();

    public long getId() {
        return id;
    }

    public List<EventParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<EventParticipant> participants) {
        this.participants = participants;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventGame() {
        return eventGame;
    }

    public void setEventGame(String eventGame) {
        this.eventGame = eventGame;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CompanyUser getEventOrganiser() {
        return eventOrganiser;
    }

    public void setEventOrganiser(CompanyUser eventOrganiser) {
        this.eventOrganiser = eventOrganiser;
    }


}
