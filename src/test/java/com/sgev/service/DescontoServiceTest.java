package com.sgev.service;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

        assertEquals(new BigDecimal("0.00"), desconto, "Desconto deveria ser zero");
    }

    @Test
    @DisplayName("Deverá calcular 5% de desconto para compras entre R$ 100 e R$ 500")
    public void testeDescontoCincoPorCento() {
        BigDecimal valor = new BigDecimal("200.00");
        BigDecimal desconto = descontoService.calcularDescontoProgressivo(valor);

        // 5% de R$ 200,00 = R$ 10,00
        assertEquals(new BigDecimal("10.00"), desconto, "Desconto deveria ser de R$ 10,00 (5%)");
    }

    @Test
    @DisplayName("Deverá calcular 10% de desconto para compras acima de R$ 500")
    public void testeDescontoDezPorCento() {
        BigDecimal valor = new BigDecimal("1000.00");
        BigDecimal desconto = descontoService.calcularDescontoProgressivo(valor);

        // 10% de R$ 1000,00 = R$ 100,00
        assertEquals(new BigDecimal("100.00"), desconto, "Desconto deveria ser de R$ 100,00 (10%)");
    }

    @Test
    @DisplayName("Deverá lançar exceção ao tentar aplicar desconto manual superior a 20%")
    public void testeDescontoManualAcimaDoLimite() {
        BigDecimal valorTotal = new BigDecimal("100.00");
        BigDecimal descontoInvalido = new BigDecimal("25.00"); // 25%

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            descontoService.aplicarDescontoManual(valorTotal, descontoInvalido);
        });

        assertEquals("O desconto manual não pode ser superior a 20%.", exception.getMessage());
    }
}