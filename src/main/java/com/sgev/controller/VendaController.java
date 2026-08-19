package com.sgev.controller;

import com.sgev.model.Cliente;
import com.sgev.model.Venda;
import com.sgev.repository.VendaRepository;
import com.sgev.service.ClienteService;
import com.sgev.service.DescontoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vendas")
@CrossOrigin(origins = "*")
public class VendaController {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private DescontoService descontoService;

    @GetMapping
    public ResponseEntity<List<Venda>> listar() {
        return ResponseEntity.ok(vendaRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> registrarVenda(@RequestBody Map<String, Object> payload) {
        try {
            Long clienteId = Long.valueOf(payload.get("clienteId").toString());
            BigDecimal subtotal = new BigDecimal(payload.get("subtotal").toString());

            Cliente cliente = clienteService.buscarPorId(clienteId);
            BigDecimal desconto = descontoService.calcularDescontoProgressivo(subtotal);
            BigDecimal totalFinal = subtotal.subtract(desconto);

            Venda venda = new Venda(cliente, subtotal, desconto, totalFinal);
            Venda salva = vendaRepository.save(venda);

            return ResponseEntity.status(HttpStatus.CREATED).body(salva);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
}
