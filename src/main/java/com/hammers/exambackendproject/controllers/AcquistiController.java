package com.hammers.exambackendproject.controllers;

import com.hammers.exambackendproject.authentication.Utils;
import com.hammers.exambackendproject.entities.Acquisto;
import com.hammers.exambackendproject.entities.Prodotto;
import com.hammers.exambackendproject.entities.ProdottoInAcquisto;
import com.hammers.exambackendproject.entities.Utente;
import com.hammers.exambackendproject.exception.QuantityProductUnavailableException;
import com.hammers.exambackendproject.repositories.AcquistoRepository;
import com.hammers.exambackendproject.services.AccountingService;
import com.hammers.exambackendproject.services.AcquistiService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/acquisti")
public class AcquistiController {
    @Autowired
    private AcquistiService acquistiService;

    @Autowired
    private AccountingService accountingService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity create(@RequestBody @Valid Acquisto acquisto) {
        try {
            return new ResponseEntity<>(acquistiService.aggiungiAcquisto(acquisto), HttpStatus.OK);
        } catch (QuantityProductUnavailableException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "la quantita richiesta non è disponibile", e);
        }
    }

    @PreAuthorize("hasAnyAuthority('Client')")
    @PostMapping("/withToken")
    public ResponseEntity addAcquisto(@RequestBody List<ProdottoInAcquisto> prodottiInAcquisto){
        try {
            Utente u = accountingService.find(Utils.getEmail()).get(0);
            System.out.println(u.getEmail());
            System.out.println(prodottiInAcquisto.toString());

            Acquisto a = new Acquisto(u, prodottiInAcquisto);
            acquistiService.aggiungiAcquisto(a);
            return new ResponseEntity("acquisto ok",HttpStatus.OK);
        }
        catch (QuantityProductUnavailableException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "la quantita richiesta non è disponibile", e);
        }

    }
}
