package com.hammers.exambackendproject.controllers;

import com.hammers.exambackendproject.entities.Utente;
import com.hammers.exambackendproject.exception.MailUserAlreadyExistsException;
import com.hammers.exambackendproject.services.AccountingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/utenti")
public class AccountingController {
    @Autowired
    private AccountingService accountingService;

    @PostMapping
    public ResponseEntity crea(@RequestBody  @Valid Utente utente){
        try{
            Utente aggiunto=accountingService.registraUtente(utente);
            return new ResponseEntity(aggiunto, HttpStatus.OK);
        }
        catch (MailUserAlreadyExistsException e){
            return new ResponseEntity(new String("E-mail gia usata"),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping
    public List<Utente> getAll() {
        return accountingService.getAll();
    }




    @PostMapping("/registraUtente")
    public ResponseEntity Registra(@RequestBody @Valid Utente utente, @RequestParam String username, @RequestParam String password){
        try{
            Utente aggiunto=accountingService.registraUtentePassword(utente,password,username);
            return new ResponseEntity(aggiunto, HttpStatus.OK);
        }
        catch (MailUserAlreadyExistsException e){
            return new ResponseEntity(new String("E-mail gia usata"),HttpStatus.BAD_REQUEST);
        }
    }
}
