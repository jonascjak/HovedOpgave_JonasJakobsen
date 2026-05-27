package com.example.hovedopgave_jonasjakobsen.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;

    private LocalTime startTime;
    private String eventName;

    private String eventGame;

    @Column(length = 1000)
    private String description;

    private String address;

    @ManyToOne
    private CompanyUser eventOrganiser;

    public long getId() {
        return id;
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
