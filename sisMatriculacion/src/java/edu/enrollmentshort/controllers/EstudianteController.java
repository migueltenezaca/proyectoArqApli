package edu.enrollmentshort.controllers;

import edu.enrollmentshort.controllers.util.JsfUtil;
import edu.enrollmentshort.controllers.util.PaginationHelper;
import edu.enrollmentshort.facade.EstudianteFacade;
import edu.enrollmentshort.modelo.Contacto;
import edu.enrollmentshort.modelo.Estudiante;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@Named("estudianteController")
//@RequestScoped
@ConversationScoped
public class EstudianteController implements Serializable {

    private Estudiante current;
    //private DataModel items = null;
    private List<Estudiante> resultList;
    @EJB
    private edu.enrollmentshort.facade.EstudianteFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    // Nuevos atributos(modificaciones)
    private String criterio;
    @Inject
    Conversation conversation;
    private Long estudianteId;
    
    @EJB 
    private edu.enrollmentshort.facade.ContactoFacade ejbContacto;

    public EstudianteController() {
        System.out.println("Ingreso al constuctor de Estudiante Controller");
        resultList = new ArrayList<Estudiante>();
//        current = new Estudiante();

    }
    public Estudiante getCurrent() {
        return current;
    }

    public void setCurrent(Estudiante current) {
        System.out.println("========> INGRESO a fijar Estudiante: " + current);
        this.beginConversation();
        this.current = current;
    }

    public Estudiante getSelected() {
        if (current == null) {
            current = new Estudiante();
            selectedItemIndex = -1;
        }
        return current;
    }

    private EstudianteFacade getFacade() {
        return ejbFacade;
    }

    public List<Estudiante> getResultList() {
        return resultList;
    }

    public void setResultList(List<Estudiante> resultList) {
        this.resultList = resultList;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

//    public String prepareList() {
//        recreateModel();
//        return "List";
//    }
    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

//    public String prepareView() {
//        current = (Estudiante) getItems().getRowData();
//        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
//        return "View";
//    }
    // getter and setter modificados 
    public Long getEstudianteId() {
        if (this.current != null) {
            return this.current.getId();
        } else {
            return null;
        }

    }

    public void setEstudianteId(Long estudianteId) {
        System.out.println("========> INGRESO a Fijar Estudiante: " + estudianteId);
        this.beginConversation();
        if (estudianteId != null && estudianteId.longValue() > 0) {
//            this.current = ejbFacade.buscarPorId(estudianteId);
            this.current = ejbFacade.find(estudianteId);
            List<Contacto> contactos = ejbContacto.buscarPorEstudiante(estudianteId);
            this.current.setContacto(contactos);
            System.out.println("========> INGRESO a Editar Estudiante: " + current.getNombres());
        } else {
            System.out.println("========> INGRESO a Crear Estudiante: ");
            this.current = new Estudiante();
        }
    }

    /*
     * ****************************** METODOS REALIZADOS********* 
     
     */
    //Metodo de buscar Estudiante
    // Busca un estudiante mediante un "criterio" ingresado por el usuario
    public String find() {
        System.out.println("Ingreso a buscar con criterio: " + criterio);
        resultList = ejbFacade.buscarPorClave(criterio);

        for (Estudiante estudiante : resultList) {
            System.out.println(estudiante);

        }
        String summary = "Encontrado Correctamente!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
        //puedo hacer retornar a la pagina q se quiera
        return "/estudiante/List"; 
    }
    public String findAll() {
        System.out.println("Ingreso a buscar Todos Estudiantes" );
        resultList = ejbFacade.buscarTodos();
//        for (Estudiante estudiante : resultList) {
//            System.out.println(estudiante);
//        }
        String summary = "Encontrado Correctamente!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
        //puedo hacer retornar a la pagina q se quiera
        return "/estudiante/List"; 
    }
    public String findForId(long id){
        ejbFacade.buscarPorId(current.getId());
        return "/estudiante/edit";
    }
    
    public List<Estudiante> findCompleteBrand(String query) {
        System.out.println("====INGRESO A BUSCAR AUTOCOMPLETE CON::" + query);
        return ejbFacade.buscarPorClave(query);
    }

    public void agregarContacto() {
        Contacto c = new Contacto();
        current.agregar(c);
    }

    public String createInstance() {
        //return "/vehicle/Edit?faces-redirect=true";
        System.out.println("========> INGRESO a Crear Instance estudiante: " + current.getNombres());
        this.current = new Estudiante();
        return "/estudiante/Edit?faces-redirect=true";
        //return "/vehicle/BrandEdit";
    }

    public String persist() { 
        System.out.println("========> INGRESO a Grabar nuevo Estudiante: " + current.getNombres());
        ejbFacade.create(current);
        this.endConversation();

        String summary = ResourceBundle.getBundle("/Bundle").getString("EstudianteCreated");
        JsfUtil.addSuccessMessage(summary);
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/estudiante/List?faces-redirect=true";
        //return "/vehicle/BrandList";

    }

    public String update() {

        System.out.println("========> INGRESO a Actualizar al Estudiante: " + current.getNombres());
        ejbFacade.edit(current);
        System.out.println("ya modifique");
        this.endConversation();

        String summary = ResourceBundle.getBundle("/Bundle").getString("EstudianteUpdated");
        FacesContext.getCurrentInstance().addMessage("successInfo", new FacesMessage(FacesMessage.SEVERITY_INFO, summary, summary));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/estudiante/List?faces-redirect=true";

    }

    public String delete() {
        System.out.println("========> INGRESO a Eliminar Estudiante: " + current.getNombres());
        ejbFacade.remove(current);
        this.find();
        this.endConversation();

        String summary = "Estudiante Eliminado Correctamente!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));


        return "/estudiante/List?faces-redirect=true";

    }

    public String cancelEdit() {
        System.out.println("me acaban de llamar: canceledit()");
        this.endConversation();
        return "/estudiante/List?faces-redirect=true";
    }

    public void beginConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
            System.out.println("========> INICIANDO CONVERSACION: ");
        }
    }

    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
            System.out.println("========> FINALIZANDO CONVERSACION: ");
        }
    }

    public String prepareCreate() {
        current = new Estudiante();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EstudianteCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    //    public String prepareEdit() {
//        current = (Estudiante) getItems().getRowData();
//        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
//        return "Edit";
//    }
//    public String update() {
//        try {
//            getFacade().edit(current);
//            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EstudianteUpdated"));
//            return "View";
//        } catch (Exception e) {
//            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
//            return null;
//        }
//    }
//    public String destroy() {
//        current = (Estudiante) getItems().getRowData();
//        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
//        performDestroy();
//        recreatePagination();
//        recreateModel();
//        return "List";
//    }
//
//    public String destroyAndView() {
//        performDestroy();
//        recreateModel();
//        updateCurrentItem();
//        if (selectedItemIndex >= 0) {
//            return "View";
//        } else {
//            // all items were removed - go back to list
//            recreateModel();
//            return "List";
//        }
//    }
    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EstudianteDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

//    public DataModel getItems() {
//        if (items == null) {
//            items = getPagination().createPageDataModel();
//        }
//        return items;
//    }
//
//    private void recreateModel() {
//        items = null;
//    }
    private void recreatePagination() {
        pagination = null;
    }

//    public String next() {
//        getPagination().nextPage();
//        recreateModel();
//        return "List";
//    }
//    public String previous() {
//        getPagination().previousPage();
//        recreateModel();
//        return "List";
//    }
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = Estudiante.class)
    public static class EstudianteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EstudianteController controller = (EstudianteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "estudianteController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Long getKey(String value) {
            Estudiante es;
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Estudiante) {
                Estudiante o = (Estudiante) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Estudiante.class.getName());
            }
        }
    }
}
