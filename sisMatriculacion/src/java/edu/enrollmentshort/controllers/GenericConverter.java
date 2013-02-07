/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.enrollmentshort.controllers;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.faces.view.Location;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;

/**
 *
 * @author edison
 */
@ConversationScoped
@FacesConverter(value = "genericConverter")
public class GenericConverter implements Serializable, Converter {

    //@PersistenceContext(name="lotcarPU")
    //@Inject @Any
    private EntityManager em;

    public GenericConverter() {
        try {
            Properties p = System.getProperties();
            System.out.println("=================> OBJECT ADDED");
            //INICIAR CONTEXTOS
            final Context ctx = new InitialContext(p);
            // BUSCAR EL CONTEXTO Y LO CONVIERTE EN ENTITY MANAGER
            //JAVA:COMP/ENV ES OBLIGATORIO
            // PERSISTENCE/EM ES EL NOMBRE QUE TIENE EL EM EN EL WEB.XML
            em = (EntityManager) ctx.lookup("java:comp/env/persistence/em");
            //ctx.lookup("java:comp/env/[name of your persistence unit here]");

        } catch (final NamingException ne) {
            ne.printStackTrace();
        }
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string.trim().equals("")) {
            return null;
        } else {
            try {
                System.out.println("==== String en converter: " + string);
                System.out.println("==== EM en converter: " + em);
                return em.find(getClazz(fc, uic), Long.parseLong(string));
            } catch (Exception e) {
                e.printStackTrace();
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Conversion Error"));
            }
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null || o.equals("")) {
            return "";
        } else {
            try {
                return String.valueOf(getId(o));
            } catch (Exception e) {
                e.printStackTrace();
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Conversion Error"));
            }
        }
    }

    private Long getId(Object o)
            throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class clazz = o.getClass();
        Method method = clazz.getMethod("getId");
        Object value = method.invoke(o);
        return (Long) value;
    }

    @SuppressWarnings("unchecked")
    private Class getClazz(FacesContext facesContext, UIComponent component) {

        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExpression = facesContext.getApplication().getExpressionFactory()
                .createValueExpression(elContext, component.getValueExpression("value").getExpressionString(), Location.class);

        return valueExpression.getType(facesContext.getELContext());
    }
}
