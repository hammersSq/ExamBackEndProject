package com.hammers.exambackendproject.controllers;

import com.hammers.exambackendproject.entities.Prodotto;
import com.hammers.exambackendproject.services.ProdottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prodotti")
public class ProdottiController {
    @Autowired
    private ProdottoService prodottoService;

    @GetMapping
    public ResponseEntity getAll(@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String sortedBy){
        List<Prodotto> result=prodottoService.showAll(pageNumber,pageSize,sortedBy);
        if(result.size()<=0){
            return new ResponseEntity(new String("nessun risultato"), HttpStatus.OK);
        }
        return new ResponseEntity(result,HttpStatus.OK);
    }
    @GetMapping("/search/byname")
    public ResponseEntity getByName(@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String sortedBy,@RequestParam String name){
        List<Prodotto> result=prodottoService.showProductsByName(name,pageNumber,pageSize,sortedBy);
        if(result.size()<=0){
            return new ResponseEntity("nessun risultato", HttpStatus.OK);
        }
        return new ResponseEntity(result,HttpStatus.OK);
    }
    @GetMapping("/search/category")
    public ResponseEntity getByCategory(@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String sortedBy,@RequestParam String category){
        List<Prodotto> result=prodottoService.showProductsByCategory(category,pageNumber,pageSize,sortedBy);
        if(result.size()<=0){
            return new ResponseEntity("nessun risultato", HttpStatus.OK);
        }
        return new ResponseEntity(result,HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity addProdotto(@RequestBody Prodotto p){
        return new ResponseEntity(prodottoService.aggiungiProdotto(p),HttpStatus.OK);
    }


}
