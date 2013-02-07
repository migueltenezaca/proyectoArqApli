/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.enrollmentshort.controllers;

import edu.enrollmentshort.modelo.PeriodoAcademico;
import edu.enrollmentshort.modelo.TipoContacto;
import edu.enrollmentshort.modelo.TipoGenero;
import javax.inject.Named;

/**
 *
 * @author edison
 */
@Named(value = "typeListController")
public class TypeListController {

    /**
     * Creates a new instance of TypeListController
     */
    public TypeListController() {
    }

    public TipoGenero[] getFuelTypeList() {
        return TipoGenero.values();
    }

    public TipoContacto[] getContactoTypeList() {
        return TipoContacto.values();
    }
   
}
