package com.hammers.exambackendproject.repositories;

import com.hammers.exambackendproject.entities.Acquisto;
import com.hammers.exambackendproject.entities.Utente;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;
import java.util.Date;
import java.util.List;


@Repository
public interface AcquistoRepository extends JpaRepository<Acquisto, Integer> {

    List<Acquisto> findByBuyer(Utente user);

    List<Acquisto> findByPurchaseTime(Date date);

    @Query("select a from Acquisto a where a.purchaseTime > ?1 and a.purchaseTime < ?2 and a.buyer = ?3")
    List<Acquisto> findByBuyerInPeriod(Date startDate, Date endDate, Utente user);
}
