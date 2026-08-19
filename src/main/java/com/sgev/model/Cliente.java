package com.sgev.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres.")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O CPF é obrigatório.")
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String telefone;

    @Column(length = 10)
    private String cep;

    @Column(length = 100)
    private String cidade;

    @Column(length = 2)
    private String uf;

    public Cliente() {}

    public Cliente(String nome, String cpf, String email, String telefone, String cep, String cidade, String uf) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.cep = cep;
        this.cidade = cidade;
        this.uf = uf;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }
}
