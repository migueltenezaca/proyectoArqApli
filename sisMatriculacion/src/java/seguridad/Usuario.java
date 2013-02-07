/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seguridad;

/**
 *
 * @author tania
 */
public class Usuario {
    private String nombre;
    private String password;

    public Usuario(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
