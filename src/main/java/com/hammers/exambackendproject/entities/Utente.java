package com.hammers.exambackendproject.entities;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import java.util.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "Utente", schema = "orders")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;


    @Basic
    @Column(name = "first_name", nullable = true, length = 50)
    private String firstName;

    @Basic
    @Column(name = "last_name", nullable = true, length = 50)
    private String lastName;


    @Basic
    @Column(name = "email", nullable = true, length = 90)
    private String email;

    @Basic
    @Column(name = "address", nullable = true, length = 150)
    private String address;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Acquisto> purchases;


}
