/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.enrollmentshort.controllers;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import seguridad.Usuario;

/**
 *
 * @author tania
 */
@Named
@SessionScoped

public class UsuarioSesion implements Serializable{
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }    
    
}
