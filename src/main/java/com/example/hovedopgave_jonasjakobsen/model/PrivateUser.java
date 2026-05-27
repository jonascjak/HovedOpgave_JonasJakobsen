package com.example.hovedopgave_jonasjakobsen.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "private_users")
public class PrivateUser extends User{

    public PrivateUser() {
    }
}
