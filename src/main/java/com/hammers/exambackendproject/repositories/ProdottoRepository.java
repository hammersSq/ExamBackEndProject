package com.hammers.exambackendproject.repositories;

import com.hammers.exambackendproject.entities.Prodotto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;
import java.util.List;


@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Integer> {

    Page<Prodotto> findByNameContaining(String name, Pageable paging);
    Page<Prodotto> findByCategoryContaining(String category,Pageable paging);


}