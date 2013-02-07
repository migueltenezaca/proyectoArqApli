/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.enrollmentshort.modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author edison
 */
@Entity
@TableGenerator(name = "PeriodoAcademicoGenerator", table = "GeneradorIdentificador",
pkColumnName = "nombre", valueColumnName = "valor", pkColumnValue = "PeriodoAcademico",
initialValue = 1, allocationSize = 1)
@NamedQueries(value = {
    //    @NamedQuery(name = "PeriodoAcademico.buscarPorPeriodo",
    //    query = "select p from PeriodoAcademico p where"
    //    + " p.fechaInicio=:claveFechaPeriodo"), 
    @NamedQuery(name = "PeriodoAcademico.buscarTodos",
    query = "select p from PeriodoAcademico p")
})
public class PeriodoAcademico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PeriodoAcademicoGenerator")
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull(message = "Tiene que existir una fecha de inicio del año")
    private Date fechaInicio;
    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull(message = "Tiene que existir una fecha de finalizacion del año")
    private Date fechaFin;
    @OneToMany(mappedBy = "periodoAcademico")
    private List<Matricula> matriculas;

    public PeriodoAcademico() {
    }

    public void agregar(Matricula matricula) {
        if (!matriculas.contains(matricula)) {
            matriculas.add(matricula);
            matricula.setPeriodoAcademico(this);
        }
    }

    public void eliminar(Matricula matricula) {
        boolean removido = matriculas.remove(matricula);
        if (removido) {
            matricula.setEstudiante(null);
        }
    }

    public List<Matricula> getMatricula() {
        return matriculas;
    }

    public void setMatricula(Matricula matricula) {
        this.matriculas = (List<Matricula>) matricula;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
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
        if (!(object instanceof PeriodoAcademico)) {
            return false;
        }
        PeriodoAcademico other = (PeriodoAcademico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String obtenerAnio(Date date) {
        String formato = "yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        return String.valueOf(dateFormat.format(date));
   }

    @Override
    public String toString() {
//        Calendar ca = null;
//        ca.setTime(fechaInicio);

        return obtenerAnio(fechaInicio) ;
    }
}
