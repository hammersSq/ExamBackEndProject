package com.hammers.exambackendproject.repositories;

import com.hammers.exambackendproject.entities.Utente;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;
import java.util.List;


@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer> {

    List<Utente> findByFirstName(String firstName);
    List<Utente> findByLastName(String lastName);
    List<Utente> findByEmail(String email);
    boolean existsByEmail(String email);

}