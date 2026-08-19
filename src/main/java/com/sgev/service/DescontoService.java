package com.sgev.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DescontoService {

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
}
