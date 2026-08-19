package com.sgev.main;

import com.sgev.model.Cliente;
import com.sgev.service.ClienteService;

public class Main {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   SGEV-WEB: TESTE DE REGRAS DE NEGÓCIO (SERVICO)");
        System.out.println("==================================================");

        ClienteService clienteService = new ClienteService();

        try {
            // Instanciando cliente para teste
            Cliente c = new Cliente();
            c.setNome("Cliente Teste Refatorado");
            c.setCpf("123.456.789-00"); // CPF com máscara para testar a sanitização automática

            System.out.println("Tentando salvar cliente via ClienteService...");
            clienteService.salvarCliente(c);
            System.out.println("✅ Cliente salvo com sucesso!");

            System.out.println("\n--- Lista de Clientes no Banco ---");
            clienteService.listarTodos().forEach(cliente -> {
                System.out.println("ID: " + cliente.getId() 
                        + " | Nome: " + cliente.getNome() 
                        + " | CPF: " + cliente.getCpf());
            });

        } catch (IllegalArgumentException e) {
            System.err.println("❌ Erro de Validação (Regra de Negócio): " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Erro ao conectar/executar no banco: " + e.getMessage());
        }
    }
}