package edu.enrollmentshort.controllers;

import edu.enrollmentshort.controllers.util.JsfUtil;
import edu.enrollmentshort.controllers.util.PaginationHelper;
import edu.enrollmentshort.facade.PeriodoAcademicoFacade;
import edu.enrollmentshort.modelo.PeriodoAcademico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

@Named("periodoAcademicoController")
@ConversationScoped
//@SessionScoped
public class PeriodoAcademicoController implements Serializable {

    private PeriodoAcademico current;
    private DataModel items = null;
    private List<PeriodoAcademico> periodoList;
    @EJB
    private edu.enrollmentshort.facade.PeriodoAcademicoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    @Inject
    Conversation conversation;
    private Long periodoAcademicoId;

    public PeriodoAcademicoController() {
        System.out.println("Ingreso al construtor de Periodo Academico Controller");
        periodoList = new ArrayList<PeriodoAcademico>();
        current = new PeriodoAcademico();
    }
     public PeriodoAcademico getCurrent() {
        return current;
    }

    public Long getPeriodoAcademicoId() {
        if (this.current != null) {
            return this.current.getId();
        } else {
            return null;
        }
    }

    public void setPeriodoAcademicoId(Long periodoAcademicoId) {
        System.out.println("========> INGRESO a Fijar periodo: " + periodoAcademicoId);
        this.beginConversation();
        if (periodoAcademicoId != null && periodoAcademicoId.longValue() > 0) {
            this.current = ejbFacade.find(periodoAcademicoId);
            System.out.println("========> INGRESO a Editar periodo: " + current.getFechaFin());
        } else {
            System.out.println("========> INGRESO a Crear Estudiante: ");
            this.current = new PeriodoAcademico();
        }
    }

    public void setCurrent(PeriodoAcademico current) {
        System.out.println("========> INGRESO a fijar al Periodo: " + current);
        this.beginConversation();
        this.current = current;
    }

    public PeriodoAcademico getSelected() {
        if (current == null) {
            current = new PeriodoAcademico();
            selectedItemIndex = -1;
        }
        return current;
    }

    private PeriodoAcademicoFacade getFacade() {
        return ejbFacade;
    }

    public List<PeriodoAcademico> getPeriodoList() {
        return periodoList;
    }

    public void setPeriodoList(List<PeriodoAcademico> periodoList) {
        this.periodoList = periodoList;
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
    
    
    
//METODOS IMPLEMENTADOS 
    
    public String findAll() {
        System.out.println("Ingreso a buscar todos los periodos ");
        periodoList = ejbFacade.buscarTodos();

        for (PeriodoAcademico periodo : periodoList) {
            System.out.println("los periodos son: " + periodo);

        }
        String summary = "Encontrado Correctamente!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
        //puedo hacer retornar a la pagina q se quiera
        return "/periodoAcademico/List";

    }

    
    public String persist() {

        System.out.println("========> INGRESO a Grabar nuevo Estudiante: " + current.getFechaFin());
        ejbFacade.create(current);
        this.endConversation();

        String summary = ResourceBundle.getBundle("/Bundle").getString("PeriodoAcademicoCreated");
        JsfUtil.addSuccessMessage(summary);
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/periodoAcademico/List?faces-redirect=true";
        //return "/vehicle/BrandList";

    }

    public String update() {

        System.out.println("========> INGRESO a Actualizar al Estudiante: " + current.getFechaFin());
        ejbFacade.edit(current);
        System.out.println("ya modifique");
        this.endConversation();

        String summary = ResourceBundle.getBundle("/Bundle").getString("PeriodoAcademicoUpdated");
        FacesContext.getCurrentInstance().addMessage("successInfo", new FacesMessage(FacesMessage.SEVERITY_INFO, summary, summary));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/periodoAcademico/List?faces-redirect=true";

    }

    public String delete() {
        System.out.println("========> INGRESO a Eliminar Estudiante: " + current.getFechaFin());
        ejbFacade.remove(current);

        this.findAll();

        this.endConversation();

        String summary = "Periodo Eliminado Correctamente!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));


        return "/periodoAcademico/List?faces-redirect=true";

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
    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (PeriodoAcademico) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new PeriodoAcademico();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PeriodoAcademicoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (PeriodoAcademico) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

//    public String update() {
//        try {
//            getFacade().edit(current);
//            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PeriodoAcademicoUpdated"));
//            return "View";
//        } catch (Exception e) {
//            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
//            return null;
//        }
//    }

    public String destroy() {
        current = (PeriodoAcademico) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PeriodoAcademicoDeleted"));
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
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    @FacesConverter(forClass = PeriodoAcademico.class)
    public static class PeriodoAcademicoControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PeriodoAcademicoController controller = (PeriodoAcademicoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "periodoAcademicoController");
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

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PeriodoAcademico) {
                PeriodoAcademico o = (PeriodoAcademico) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + PeriodoAcademico.class.getName());
            }
        }
    }
}
