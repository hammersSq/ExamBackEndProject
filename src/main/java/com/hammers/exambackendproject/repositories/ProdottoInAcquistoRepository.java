package com.hammers.exambackendproject.repositories;

import com.hammers.exambackendproject.entities.ProdottoInAcquisto;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;


@Repository
public interface ProdottoInAcquistoRepository extends JpaRepository<ProdottoInAcquisto, Integer> {


}