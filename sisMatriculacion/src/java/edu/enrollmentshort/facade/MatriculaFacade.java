/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.enrollmentshort.facade;

import edu.enrollmentshort.modelo.Matricula;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author edison
 */
@Stateless
public class MatriculaFacade extends AbstractFacade<Matricula> {
    @PersistenceContext(unitName = "EnrollmentShortPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MatriculaFacade() {
        super(Matricula.class);
    }
    public List<Matricula> buscarPorFechaMatricula(String claveFechaMatricula){
      Query query = em.createNamedQuery("Matricula.buscarPorFechaMatricula");
      query.setParameter("claveFechaMatricula", claveFechaMatricula);
      return query.getResultList();
    }
}
