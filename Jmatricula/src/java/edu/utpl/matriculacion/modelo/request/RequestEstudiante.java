/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utpl.matriculacion.modelo.request;

import edu.utpl.matriculacion.modelo.Estudiante;
import javax.annotation.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author INNOVASOFTWARE
 */
@ManagedBean 
@RequestScoped 
public class RequestEstudiante {
    
    private Estudiante estudiante;

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public RequestEstudiante() {
    }
    
}
