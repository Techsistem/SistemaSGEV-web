package com.sgev.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DescontoService {

    /**
     * Regra de Negócio de Desconto:
     * - Vendas abaixo de R$ 100,00: sem desconto (0%)
     * - Vendas entre R$ 100,00 e R$ 500,00: 5% de desconto
     * - Vendas acima de R$ 500,00: 10% de desconto
     * - Percentual personalizado informado manualmente não pode ultrapassar 20%.
     */
    public BigDecimal calcularDescontoProgressivo(BigDecimal valorTotal) {
        if (valorTotal == null || valorTotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor total da venda deve ser maior que zero.");
        }

        BigDecimal percentualDesconto;

        if (valorTotal.compareTo(new BigDecimal("500.00")) > 0) {
            percentualDesconto = new BigDecimal("0.10"); // 10%
        } else if (valorTotal.compareTo(new BigDecimal("100.00")) >= 0) {
            percentualDesconto = new BigDecimal("0.05"); // 5%
        } else {
            percentualDesconto = BigDecimal.ZERO; // 0%
        }

        return valorTotal.multiply(percentualDesconto).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal aplicarDescontoManual(BigDecimal valorTotal, BigDecimal percentualDesconto) {
        if (valorTotal == null || valorTotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da venda deve ser maior que zero.");
        }
        
        if (percentualDesconto == null || percentualDesconto.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O percentual de desconto não pode ser negativo.");
        }

        if (percentualDesconto.compareTo(new BigDecimal("20.00")) > 0) {
            throw new IllegalArgumentException("O desconto manual não pode ser superior a 20%.");
        }

        BigDecimal fator = percentualDesconto.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
        BigDecimal valorDesconto = valorTotal.multiply(fator);

        return valorTotal.subtract(valorDesconto).setScale(2, RoundingMode.HALF_UP);
    }
}