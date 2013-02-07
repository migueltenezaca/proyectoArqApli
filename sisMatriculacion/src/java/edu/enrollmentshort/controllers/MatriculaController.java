package edu.enrollmentshort.controllers;

import edu.enrollmentshort.controllers.util.JsfUtil;
import edu.enrollmentshort.controllers.util.PaginationHelper;
import edu.enrollmentshort.facade.EstudianteFacade;
import edu.enrollmentshort.facade.MatriculaFacade;
import edu.enrollmentshort.modelo.Estudiante;
import edu.enrollmentshort.modelo.Matricula;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
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

@Named("matriculaController")
@ConversationScoped
public class MatriculaController implements Serializable {

    private Matricula current;
    private DataModel items = null;
    @EJB
    private edu.enrollmentshort.facade.MatriculaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    @Inject
    Conversation conversation;
    private Long estudianteId;
    @EJB
    EstudianteFacade ef;
    
    
@Inject
private edu.enrollmentshort.controllers.EstudianteController estController;

    public MatriculaController() {
        System.out.println("Ingreso al constructor de matricula controller");
        current = new Matricula();
        System.out.println("Pase el Contructor");
        //current.setEstudiante(estController.getCurrent());
    }

    //METODOS DE MATRICULA CONTROLLER
//    public void addEstudent() {
//       current.setEstudiante(estController.getCurrent());
//    }
    
    
    public void addEstudent(){
        beginConversation();
        if(estudianteId!=null && estudianteId.longValue() >0){
            Estudiante e = ef.find(estudianteId);
            current.setEstudiante(e);
                    
        } else{
            Estudiante e= new Estudiante();
        }
        
    }

    public Long getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Long estudianteId) {
        this.estudianteId = estudianteId;
    }
    
    
    

    public String persist() {

        System.out.println("========> INGRESO a Grabar nueva Matricula ");
        ejbFacade.create(current);
        this.endConversation();

        String summary = ResourceBundle.getBundle("/Bundle").getString("MatriculaCreated");
        JsfUtil.addSuccessMessage(summary);
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/estudiante/List?faces-redirect=true";
        //return "/vehicle/BrandList";

    }

    public String update() {

        System.out.println("========> INGRESO a Actualizar Matricula: ");
        ejbFacade.edit(current);
        System.out.println("ya modifique");
        this.endConversation();

        String summary = ResourceBundle.getBundle("/Bundle").getString("MatriculaUpdated");
        FacesContext.getCurrentInstance().addMessage("successInfo", new FacesMessage(FacesMessage.SEVERITY_INFO, summary, summary));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/estudiante/List?faces-redirect=true";

    }

    public String delete() {
        System.out.println("========> INGRESO a Anular Matricula ");
        ejbFacade.remove(current);

        this.endConversation();

        String summary = "Matricula Anulada Correctamente!";
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

    public Matricula getCurrent() {
        return current;
    }

    public void setCurrent(Matricula current) {
        System.out.println("===================> Fijando Matricula");
        this.beginConversation();
        this.current = current;
    }

    public Matricula getSelected() {
        if (current == null) {
            current = new Matricula();
            selectedItemIndex = -1;
        }
        return current;
    }

    private MatriculaFacade getFacade() {
        return ejbFacade;
    }

//    public EstudianteController getEstController() {
//        return estController;
//    }
//
//    public void setEstController(EstudianteController estController) {
//        this.estController = estController;
//    }
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

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Matricula) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Matricula();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MatriculaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Matricula) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

//    public String update() {
//        try {
//            getFacade().edit(current);
//            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MatriculaUpdated"));
//            return "View";
//        } catch (Exception e) {
//            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
//            return null;
//        }
//    }
    public String destroy() {
        current = (Matricula) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MatriculaDeleted"));
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

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = Matricula.class)
    public static class MatriculaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MatriculaController controller = (MatriculaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "matriculaController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Long getKey(String value) {
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
            if (object instanceof Matricula) {
                Matricula o = (Matricula) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Matricula.class.getName());
            }
        }
    }
}
