package com.example.hovedopgave_jonasjakobsen.model;

import jakarta.persistence.*;

@Entity
public class EventParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Event event;

    @ManyToOne
    private PrivateUser privateUser;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public PrivateUser getPrivateUser() {
        return privateUser;
    }

    public void setPrivateUser(PrivateUser privateUser) {
        this.privateUser = privateUser;
    }
}
