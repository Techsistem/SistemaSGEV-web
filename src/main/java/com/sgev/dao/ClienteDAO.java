package com.sgev.dao;

import com.sgev.model.Cliente;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ClienteDAO {

    public void salvar(Cliente cliente) {
        EntityManager em = (EntityManager) JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Cliente buscarPorId(int id) {
        EntityManager em = (EntityManager) JPAUtil.getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public Cliente buscarPorCpf(String cpf) {
        EntityManager em = (EntityManager) JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Cliente c WHERE c.cpf = :cpf", Cliente.class)
                     .setParameter("cpf", cpf)
                     .getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void atualizar(Cliente cliente) {
        EntityManager em = (EntityManager) JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Cliente> buscarPorNome(String nome) {
        EntityManager em = (EntityManager) JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Cliente c WHERE c.nome LIKE :nome", Cliente.class)
                     .setParameter("nome", "%" + nome + "%")
                     .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Cliente> listar() {
        EntityManager em = (EntityManager) JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
        } finally {
            em.close();
        }
    }
}