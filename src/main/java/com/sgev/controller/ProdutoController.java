package com.sgev.controller;

import com.sgev.model.Produto;
import com.sgev.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public ResponseEntity<List<Produto>> listar() {
        return ResponseEntity.ok(produtoRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Produto produto) {
        try {
            Produto salvo = produtoRepository.save(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("erro", e.getMessage()));
        }
    }
}