package com.sgev.dao;

import com.sgev.model.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;


public class ProdutoDAO {

    // Busca pelo ID (Chave primária automática)
    public Produto buscarPorId(int id) {
        EntityManager em = (EntityManager) JPAUtil.getEntityManager();
        try {
            return em.find(Produto.class, id);
        } finally {
            em.close();
        }
    }

    // Busca pelo Código de Barras (O que você digita na tela de vendas)
    public Produto buscarPorCodigo(String codigo) {
        EntityManager em = (EntityManager) JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Produto p WHERE p.codigoBarras = :codigo", Produto.class)
                     .setParameter("codigo", codigo)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null; // Retorna null se não achar o código exato
        } finally {
            em.close();
        }
    }

    // NOVO: Busca por Nome (Para o campo "Pesquisar produto" na tela de vendas)
    public List<Produto> buscarPorNome(String nome) {
        EntityManager em = (EntityManager) JPAUtil.getEntityManager();
        try {
            // O operador LIKE permite encontrar nomes parciais (ex: digitar "Cabo" acha "Cabo USB")
            return em.createQuery("SELECT p FROM Produto p WHERE p.nome LIKE :nome", Produto.class)
                     .setParameter("nome", "%" + nome + "%")
                     .getResultList();
        } finally {
            em.close();
        }
    }
    
    // Método para salvar (Garante o commit no banco de dados)
    public void salvar(Produto produto) {
        EntityManager em = (EntityManager) JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(produto);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}