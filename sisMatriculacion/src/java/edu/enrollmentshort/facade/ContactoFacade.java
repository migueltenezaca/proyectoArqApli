/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.enrollmentshort.facade;

import edu.enrollmentshort.modelo.Contacto;
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
public class ContactoFacade extends AbstractFacade<Contacto> {
    @PersistenceContext(unitName = "EnrollmentShortPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContactoFacade() {
        super(Contacto.class);
    }
    public List<Contacto> buscarPorEstudiante(long EstudianteId){
      Query query = em.createNamedQuery("Contacto.buscarPorEstudiante");
      query.setParameter("EstudianteID", EstudianteId);
      return query.getResultList();
    } 
    
}
