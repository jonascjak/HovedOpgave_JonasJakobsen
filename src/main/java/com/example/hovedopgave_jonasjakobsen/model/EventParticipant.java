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
}
