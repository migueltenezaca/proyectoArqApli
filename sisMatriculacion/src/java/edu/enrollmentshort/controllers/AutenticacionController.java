/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.enrollmentshort.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import seguridad.Usuario;

/**
 *
 * @author tania
 */
@Named
@RequestScoped
public class AutenticacionController {
    
    private String nombreUsuario;
    private String clave;
    
    @Inject 
    UsuarioSesion usuarioSesion;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    public String autenticar(){
        Usuario usuario = null;
        if(nombreUsuario.equals("admin") && clave.equals("admin")){
          usuario = new Usuario(nombreUsuario, clave);  
        }
        if(usuario != null){
            System.out.println("Ingreso correctamente");
            usuarioSesion.setUsuario(usuario);
            return "/index.xhtml?faces-redirect=true";
        }
        System.out.println("No ingreso");
        return "/login.xhtml";
    }
}
