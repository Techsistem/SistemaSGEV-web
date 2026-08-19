package com.sgev.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorSubtotal;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorDesconto;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private LocalDateTime dataHora = LocalDateTime.now();

    public Venda() {}

    public Venda(Cliente cliente, BigDecimal valorSubtotal, BigDecimal valorDesconto, BigDecimal valorTotal) {
        this.cliente = cliente;
        this.valorSubtotal = valorSubtotal;
        this.valorDesconto = valorDesconto;
        this.valorTotal = valorTotal;
        this.dataHora = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public BigDecimal getValorSubtotal() { return valorSubtotal; }
    public void setValorSubtotal(BigDecimal valorSubtotal) { this.valorSubtotal = valorSubtotal; }

    public BigDecimal getValorDesconto() { return valorDesconto; }
    public void setValorDesconto(BigDecimal valorDesconto) { this.valorDesconto = valorDesconto; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}
