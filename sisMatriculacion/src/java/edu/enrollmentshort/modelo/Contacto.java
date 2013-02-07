/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.enrollmentshort.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.TableGenerator;

/**
 *
 * @author edison
 */
@Entity
@TableGenerator(name = "ContactoGenerator", table = "GeneradorIdentificador",
pkColumnName = "nombre", valueColumnName = "valor", pkColumnValue = "Contacto",
initialValue = 1, allocationSize = 1)
@NamedQueries(value = {
    @NamedQuery(name = "Contacto.buscarPorEstudiante",
    query = "select c from Contacto c where c.estudiante.id = :EstudianteID"
    )})
public class Contacto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ContactoGenerator")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TipoContacto tipo;
    @Column(length = 100)
    private String descripcion;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EstudianteID")
    private Estudiante estudiante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoContacto getTipo() {
        return tipo;
    }

    public void setTipo(TipoContacto tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(object instanceof Contacto)) {
            return false;
        }
        Contacto other = (Contacto) object;
        if ((this.descripcion == null && other.descripcion != null) || (this.descripcion != null && !this.descripcion.equals(other.descripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.enrrollmentshort.modelo.Contacto[ id=" + id + " ]";
    }
}
