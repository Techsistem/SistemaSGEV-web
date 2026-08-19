package com.sgev.service;

import com.sgev.model.Cliente;
import com.sgev.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) {
        String cpfLimpo = cliente.getCpf() != null ? cliente.getCpf().replaceAll("\\D", "") : "";
        
        if (cpfLimpo.length() != 11) {
            throw new IllegalArgumentException("CPF inválido! O CPF deve conter exatamente 11 dígitos numéricos.");
        }
        
        cliente.setCpf(cpfLimpo);

        if (cliente.getId() == null && clienteRepository.existsByCpf(cpfLimpo)) {
            throw new IllegalArgumentException("Erro: Já existe um cliente cadastrado com este CPF.");
        }

        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));
    }

    public void salvarCliente(Cliente c) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
