package com.hammers.exambackendproject.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "acquisto", schema = "orders")
public class Acquisto {

    public Acquisto(Utente buyer, List<ProdottoInAcquisto> prodottiInAcquisto){
        this.buyer=buyer;
        this.productsInPurchase=prodottiInAcquisto;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "purchase_time")
    private Date purchaseTime;

    @ManyToOne
    @JoinColumn(name = "buyer")
    private Utente buyer;


    @OneToMany(mappedBy = "purchase", cascade = CascadeType.MERGE)
    private List<ProdottoInAcquisto> productsInPurchase;


    public Acquisto() {

    }
}