package com.sgev.service;

import com.sgev.dao.ClienteDAO;
import com.sgev.model.Cliente;
import java.util.List;

public class ClienteService {

    private final ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }

    public void salvarCliente(Cliente cliente) {
        // Regra de Negócio 1: Nome é obrigatório
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }

        // Regra de Negócio 2: Validação de tamanho do CPF
        if (cliente.getCpf() == null || cliente.getCpf().replaceAll("\\D", "").length() != 11) {
            throw new IllegalArgumentException("CPF inválido! O CPF deve conter 11 dígitos.");
        }

        // Sanitização: Remove pontuações do CPF antes de validar/salvar
        String cpfLimpo = cliente.getCpf().replaceAll("\\D", "");
        cliente.setCpf(cpfLimpo);

        // Regra de Negócio 3: Verificar se o CPF já está cadastrado no banco
        if (clienteDAO.buscarPorCpf(cpfLimpo) != null) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este CPF.");
        }

        // Persistência via DAO
        clienteDAO.salvar(cliente);
    }

    public List<Cliente> listarTodos() {
        return clienteDAO.listar();
    }
}