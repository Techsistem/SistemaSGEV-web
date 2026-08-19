package com.sgev.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class JPAUtil {
    
    // O nome "sgev-pu" deve ser exatamente o mesmo que você colocou no persistence.xml
    private static final EntityManagerFactory FACTORY = 
            Persistence.createEntityManagerFactory("sgev-pu");

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }
}