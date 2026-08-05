package com.sgev.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    
    // O nome "sgev-pu" deve ser exatamente o mesmo que você colocou no persistence.xml
    private static final EntityManagerFactory FACTORY = 
            Persistence.createEntityManagerFactory("sgev-pu");

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }
}