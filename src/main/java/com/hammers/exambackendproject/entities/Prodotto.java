package com.hammers.exambackendproject.entities;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "prodotto", schema = "orders")
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "name", nullable = true, length = 50)
    private String name;


    @Basic
    @Column(name = "description", nullable = true, length = 500)
    private String description;

    @Basic
    @Column(name = "price", nullable = true)
    private float price;

    @Basic
    @Column(name = "category",nullable = true)
    private String category;

    @Basic
    @Column(name = "quantity", nullable = true)
    private int quantity;

    @Version
    @Column(name = "version", nullable = false)
    @JsonIgnore
    private long version;

    @OneToMany(targetEntity = ProdottoInAcquisto.class, mappedBy = "product", cascade = CascadeType.MERGE)
    @JsonIgnore
    @ToString.Exclude
    private List<ProdottoInAcquisto> productsInPurchase;


}