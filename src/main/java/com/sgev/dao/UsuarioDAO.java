package com.sgev.dao;

import com.sgev.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class UsuarioDAO {

    public Usuario validarLogin(String login, String senha) {
        // Usa a sua classe JPAUtil para abrir a conexão
        EntityManager em = JPAUtil.getEntityManager();
        
        try {
            // Consulta JPQL definitiva que busca no banco MySQL
            String jpql = "SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha";
            
            return em.createQuery(jpql, Usuario.class)
                     .setParameter("login", login)
                     .setParameter("senha", senha)
                     .getSingleResult();
        } catch (NoResultException e) {
            // Se não encontrar o usuário no banco, retorna null com segurança
            return null;
        } finally {
            // Sempre feche a EntityManager para não travar o banco de dados
            em.close();
        }
    }
}