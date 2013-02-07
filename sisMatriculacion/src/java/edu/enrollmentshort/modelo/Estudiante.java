/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.enrollmentshort.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@TableGenerator(name = "EstudianteGenerator", table = "GeneradorIdentificador",
pkColumnName = "nombre", valueColumnName = "valor", pkColumnValue = "Estudiante",
initialValue = 1, allocationSize = 1)
@NamedQueries(value = {
    @NamedQuery(name = "Estudiante.buscarPorClave",
    query = "select e from Estudiante e where"
    + " lower(e.dni) like lower(concat('%',:clave,'%'))"
    + "or"
    + " lower(e.nombres) like lower(concat('%',:clave,'%'))"
    + "or"
    + " lower(e.apellidos) like lower(concat('%',:clave,'%'))"
    + "order by e.nombres"),
    @NamedQuery(name = "Estudiante.buscarPorFechaNacimiento",
    query = "select e from Estudiante e where"
    + " e.fechaNacimiento=:claveFechaNacimiento"),
    @NamedQuery(name = "Estudiante.buscarPorId",
    query = "select distinct e from Estudiante e LEFT JOIN FETCH e.contactos where e.id = :id"), 
    @NamedQuery(name = "Estudiante.buscarTodos",
    query = "select e from Estudiante e")    
})
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "EstudianteGenerator")
    private Long id;
    private String dni;
    @Column(length = 50)
    @NotNull(message = "El nombre no puede estar vacío")
    private String nombres;
    @NotNull(message = "El apellido no puede estar vacío")
    private String apellidos;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaNacimiento;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    @NotNull
    private TipoGenero genero;
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contacto> contactos;
    @OneToMany(mappedBy = "estudiante", orphanRemoval = true)
    private List<Matricula> matriculas;

    public Estudiante() {
        this.contactos = new ArrayList<Contacto>();
        this.matriculas = new ArrayList<Matricula>();
        //this.fechaNacimiento = new Date();
    }
   

    public Estudiante(Long id) {
        this.id = id;
    }

    public void agregar(Matricula matricula) {
        if (!matriculas.contains(matricula)) {
            matriculas.add(matricula);
            matricula.setEstudiante(this);
        }
    }

    public void eliminar(Matricula matricula) {
        boolean removido = matriculas.remove(matricula);
        if (removido) {
            matricula.setEstudiante(null);
        }
    }

    public void agregar(Contacto contacto) {
        if (!contactos.contains(contacto)) {
            contactos.add(contacto);
            contacto.setEstudiante(this);
        }
    }

    public void eliminar(Contacto contacto) {
        boolean removido = contactos.remove(contacto);
        if (removido) {
            contacto.setEstudiante(null);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public TipoGenero getGenero() {
        return genero;
    }

    public void setGenero(TipoGenero genero) {
        this.genero = genero;
    }

    public List<Contacto> getContacto() {
        return contactos;
    }

    public void setContacto(List<Contacto> contacto) {
        this.contactos = contacto;
    }

    public List<Contacto> getContactos() {
        return contactos;
    }

    public void setContactos(List<Contacto> contactos) {
        this.contactos = contactos;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
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
        if (!(object instanceof Estudiante)) {
            return false;
        }
        Estudiante other = (Estudiante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombres+" "+apellidos;
    }
}
