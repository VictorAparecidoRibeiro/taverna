package br.com.projeto.agenda.util.app;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;


public class PadraoDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void criar(Object objeto) {
        entityManager.persist(objeto);
        entityManager.flush();
    }

    public Object salvar(Object objeto) {
        return entityManager.merge(objeto);
    }

    public void excluir(Object objeto) {
        entityManager.remove(entityManager.merge(objeto));
        entityManager.flush();
    }
   
    public <T extends Object> T find(Class<T> classe, Object id) {
        TypedQuery query = entityManager.createNamedQuery(classe.getSimpleName() + ".findById", classe);
        query.setParameter("id", id);

        Object retorno;

        try {
            retorno = query.getSingleResult();
        } catch (NoResultException e) {
            retorno = null;
        }
        return (T) retorno;
    }

    public List findAll(Class classe) {
        TypedQuery query = entityManager.createNamedQuery(classe.getSimpleName() + ".findAll", classe);
        List retorno = query.getResultList();
        return retorno;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
        

}
