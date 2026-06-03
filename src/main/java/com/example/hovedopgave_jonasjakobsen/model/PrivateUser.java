package com.example.hovedopgave_jonasjakobsen.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "private_users")
public class PrivateUser extends User{

    @OneToMany(mappedBy = "privateUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventParticipant> eventParticipations = new ArrayList<>();
    public PrivateUser() {
    }
}
