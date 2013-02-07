/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.enrollmentshort.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author edison
 */
@Entity
@TableGenerator(name = "MatriculaGenerator", table = "GeneradorIdentificador",
pkColumnName = "nombre", valueColumnName = "valor", pkColumnValue = "Matricula",
initialValue = 1, allocationSize = 1)
@NamedQueries(value = {
    @NamedQuery(name = "Matricula.buscarPorFechaMatricula",
    query = "select m from Matricula m where"
    + " m.fechaMatricula=:claveFechaMatriculacion")
})
public class Matricula implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MatriculaGenerator")
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaMatricula;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "estudienteIdM")
    private Estudiante estudiante;
    @ManyToOne
    private PeriodoAcademico periodoAcademico;
    
    public Matricula() {
        this.fechaMatricula = new Date();
    }
    
    public PeriodoAcademico getPeriodoAcademico() {
        return periodoAcademico;
    }
    
    public void setPeriodoAcademico(PeriodoAcademico periodoAcademico) {
        this.periodoAcademico = periodoAcademico;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Date getFechaMatricula() {
        return fechaMatricula;
    }
    
    public void setFechaMatricula(Date fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }
    
    public Estudiante getEstudiante() {
        return estudiante;
    }
    
    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Matricula)) {
            return false;
        }
        Matricula other = (Matricula) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "edu.enrrollmentshort.modelo.Matricula[ id=" + id + " ]";
    }
}
