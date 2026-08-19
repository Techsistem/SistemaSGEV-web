package com.sgev.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true, length = 50)
    private String login;

    @Column(nullable = false, length = 255)
    private String senha;

    @Column(length = 100)
    private String nome;

    @Column(length = 20)
    private String perfil; // ADMIN ou COLABORADOR

    // OBRIGATÓRIO: Construtor vazio para o JPA
    public Usuario() {}

    // Getters e Setters (Você pode gerar no NetBeans: Alt+Insert)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }
}