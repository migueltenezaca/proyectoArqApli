/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.enrollmentshort.facade;

import edu.enrollmentshort.modelo.Matricula;
import edu.enrollmentshort.modelo.PeriodoAcademico;
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
public class PeriodoAcademicoFacade extends AbstractFacade<PeriodoAcademico> {
    @PersistenceContext(unitName = "EnrollmentShortPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PeriodoAcademicoFacade() {
        super(PeriodoAcademico.class);
    }
//    public List<PeriodoAcademico> buscarPorPeriodo(String claveFechaPeriodo){
//      Query query = em.createNamedQuery("PeriodoAcademico.buscarPorPeriodo");
//      query.setParameter("claveFechaPeriodo", claveFechaPeriodo);
//      return query.getResultList();
//    }
    
    public List<PeriodoAcademico> buscarTodos(){
        Query query =em.createNamedQuery("PeriodoAcademico.buscarTodos");
        return query.getResultList();
        
    }
}
