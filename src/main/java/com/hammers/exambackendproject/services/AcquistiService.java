package com.hammers.exambackendproject.services;

import com.hammers.exambackendproject.entities.Acquisto;
import com.hammers.exambackendproject.entities.Prodotto;
import com.hammers.exambackendproject.entities.ProdottoInAcquisto;
import com.hammers.exambackendproject.entities.Utente;
import com.hammers.exambackendproject.exception.QuantityProductUnavailableException;
import com.hammers.exambackendproject.repositories.AcquistoRepository;
import com.hammers.exambackendproject.repositories.ProdottoInAcquistoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static javax.print.attribute.standard.MediaSizeName.A;

@Service
public class AcquistiService {
    @Autowired
    private AcquistoRepository acquistoRepository;

    @Autowired
    private ProdottoInAcquistoRepository prodottoInAcquistoRepository;

    @Autowired
    private EntityManager entityManager;



    @Transactional(readOnly = false)
    public Acquisto aggiungiAcquisto(Acquisto acquisto) throws QuantityProductUnavailableException {
        Acquisto result = acquistoRepository.save(acquisto);
        for ( ProdottoInAcquisto pia : result.getProductsInPurchase() ) {
            pia.setPurchase(result);
            ProdottoInAcquisto justAdded = prodottoInAcquistoRepository.save(pia);
            Prodotto product = justAdded.getProduct();
            int newQuantity = product.getQuantity() - pia.getQuantity();
            if ( newQuantity < 0 ) {
                throw new QuantityProductUnavailableException();
            }
            product.setQuantity(newQuantity);
            entityManager.refresh(pia);
        }
        entityManager.refresh(result);
        return result;
    }
}
