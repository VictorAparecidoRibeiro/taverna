package br.com.projeto.agenda.util.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class JPAHelper {   

    private EntityManagerFactory factory;

    public EntityManagerFactory getFactory() {
        return factory;
    }    

    public JPAHelper() {        
        //tavernaOVHPU
        //tavernaPU
        factory = Persistence.createEntityManagerFactory("tavernaOVHPU");        
    }

    @Produces
    @RequestScoped
    public EntityManager createEntityManager() {
        return factory.createEntityManager();
    }

    public void closeEntityManager(@Disposes EntityManager manager) {
        manager.close();
    }                
    
    
}
