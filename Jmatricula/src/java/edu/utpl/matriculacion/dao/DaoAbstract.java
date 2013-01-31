
package edu.utpl.matriculacion.dao;

import java.util.List;
import javax.persistence.EntityManager;


public abstract class DaoAbstract<T> {

    T instancia;
    EntityManager entityManager;

    public DaoAbstract(T t) {
        instancia = t;
        entityManager = FuenteDatos.getEntityManager();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public T getInstancia() {
        return instancia;
    }

    public void setInstancia(T instancia) {
        this.instancia = instancia;
    }

    public void persist() throws Exception {
        this.entityManager.getTransaction().begin();

        try {
            this.entityManager.persist(instancia);
            this.entityManager.getTransaction().commit();
        } catch (Exception e) {
            this.entityManager.getTransaction().isActive();
            this.entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public void update() throws Exception {
        this.entityManager.getTransaction().begin();
        try {
            this.entityManager.merge(instancia);
            this.entityManager.getTransaction().commit();
        } catch (Exception e) {
            this.entityManager.getTransaction().isActive();
            this.entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public void remover(T t) throws Exception {
        this.entityManager.getTransaction().begin();
        try {
            this.entityManager.remove(t);
            this.entityManager.getTransaction().commit();
        } catch (Exception e) {
            this.entityManager.getTransaction().isActive();
            this.entityManager.getTransaction().rollback();
            throw e;
        }

    }

    public T FindBy(Long id) {
        return (T) this.entityManager.find(instancia.getClass(), id);
    }

    public abstract List<T> buscarTodo();
}
