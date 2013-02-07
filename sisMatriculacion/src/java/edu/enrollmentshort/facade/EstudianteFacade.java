/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.enrollmentshort.facade;

import edu.enrollmentshort.modelo.Estudiante;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author edison
 */
@Stateless
//@TransactionManagement(TransactionManagementType.CONTAINER)

public class EstudianteFacade extends AbstractFacade<Estudiante> {

    @PersistenceContext(unitName = "EnrollmentShortPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstudianteFacade() {
        super(Estudiante.class);
    }

    public List<Estudiante> buscarPorClave(String clave) {

        Query query = em.createNamedQuery("Estudiante.buscarPorClave");
        query.setParameter("clave", clave);

        return query.getResultList();
    }

    public List<Estudiante> buscarPorFechaNacimiento(String fechaNacimiento) {
        Query query = em.createNamedQuery("Estudiante.buscarPorFechaNacimiento");
        // "claveFechaNacimiento" es el atributo de los quieries en la clase del modelo (Estudiante)
        // "fechaNacimiento" es el atributo que almacenará el criterio de busqueda en la ventana bsucar
        query.setParameter("claveFechaNacimiento", fechaNacimiento);
        return query.getResultList();
    }
    public List<Estudiante> buscarTodos() { 
        Query query = em.createNamedQuery("Estudiante.buscarTodos");
        return query.getResultList();
    }
     public Estudiante buscarPorId(long id) {

        Query consulta = em.createNamedQuery("Estudiante.buscarPorId");
//        query.setParameter("id", id);
//        return (Estudiante) query.getSingleResult();
        
//         TypedQuery<Estudiante> consulta = em.createNamedQuery("Estudiante.buscarPorId", Estudiante.class);
        //Query consulta = em.createNamedQuery("Estudiante.buscarPorClave",Estudiante.class);
        consulta.setParameter("id",id);   
        if(consulta != null ){
            return (Estudiante)consulta.getSingleResult();
        }else{
            return null;
        }
        
    }
}
