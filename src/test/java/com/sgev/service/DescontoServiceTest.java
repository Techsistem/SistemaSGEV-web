package com.sgev.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class DescontoServiceTest {

    private DescontoService descontoService;

    @BeforeEach
    public void setUp() {
        descontoService = new DescontoService();
    }

    @Test
    @DisplayName("Deverá retornar 0.00 de desconto para compras abaixo de R$ 100,00")
    public void testeDescontoSemDesconto() {
        BigDecimal valor = new BigDecimal("80.00");
        BigDecimal desconto = descontoService.calcularDescontoProgressivo(valor);
        assertEquals(new BigDecimal("0.00"), desconto);
    }

    @Test
    @DisplayName("Deverá calcular 5% de desconto para compras entre R$ 100 e R$ 500")
    public void testeDescontoCincoPorCento() {
        BigDecimal valor = new BigDecimal("200.00");
        BigDecimal desconto = descontoService.calcularDescontoProgressivo(valor);
        assertEquals(new BigDecimal("10.00"), desconto);
    }

    @Test
    @DisplayName("Deverá calcular 10% de desconto para compras acima de R$ 500")
    public void testeDescontoDezPorCento() {
        BigDecimal valor = new BigDecimal("1000.00");
        BigDecimal desconto = descontoService.calcularDescontoProgressivo(valor);
        assertEquals(new BigDecimal("100.00"), desconto);
    }
}
