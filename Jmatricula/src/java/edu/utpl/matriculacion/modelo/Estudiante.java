
package edu.utpl.matriculacion.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
public class Estudiante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "varchar (100)", nullable = false)
    private String nombres;
    @Column(columnDefinition = "varchar (100)", nullable = false)
    private String apellidos;
    @Column(columnDefinition = "varchar (15)", nullable = false)
    private String numero_doc_iden;
    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "foto", length = 100000000)
    private byte[] foto;
    @Column(columnDefinition = "varchar (100)", nullable = false)
    private String email;
    @Column(columnDefinition = "varchar (250)", nullable = false)
    private String direccion;
    @Enumerated(EnumType.STRING)
    Genero genero;
    @Enumerated(EnumType.STRING)
    private Tipo_Doc_Ident tipoDocIdent;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaNacimiento;
    @Column(columnDefinition = "tinyint(1)", nullable = false)
    private Boolean estado;
    @Column(columnDefinition = "varchar (50)", nullable = false)
    private String telefono;
    @Column(columnDefinition = "varchar (250)", nullable = false)
    private String ciudad;
    @OneToMany(mappedBy = "estudiante")
    List<Matricula> matriculas;

    public Estudiante() {
        this.matriculas = new ArrayList<Matricula>();
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNumero_doc_iden() {
        return numero_doc_iden;
    }

    public void setNumero_doc_iden(String numero_doc_iden) {
        this.numero_doc_iden = numero_doc_iden;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Tipo_Doc_Ident getTipoDocIdent() {
        return tipoDocIdent;
    }

    public void setTipoDocIdent(Tipo_Doc_Ident tipoDocIdent) {
        this.tipoDocIdent = tipoDocIdent;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }
}
