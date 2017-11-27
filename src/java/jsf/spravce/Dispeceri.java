/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.spravce;

import ejb.LoginUser;
import entity.Dispecerhl;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import jsf.util.JsfUtil;
import jsf.util.JsfUtil.PersistAction;

/**
 *
 * @author Ivo
 */
@Named("dispeceri")
@SessionScoped
public class Dispeceri implements Serializable {

    private Calendar cal = Calendar.getInstance(Locale.getDefault());

    @EJB
    private ejb.DispecerHlFacade ejbFacade;
    @Inject
    LoginUser loginUser;
    private Dispecerhl dispecerHl = null;
    private ArrayList<Dispecerhl> dispecerHlList = null;

    @PostConstruct
    void initLoginUser() {
        Principal userPrincipal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (userPrincipal != null) {
            System.out.println("userPrincipal.getName():" + userPrincipal.getName());
        }
        loginUser.initLoginUser();
        System.out.println("loginUser:" + loginUser);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    /**
     * @return the cal
     */
    public Calendar getCal() {
        return cal;
    }

    /**
     * @param cal the cal to set
     */
    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    /**
     * @return the dispecerHl
     */
    public Dispecerhl getDispecerHl() {
        return this.dispecerHl;
    }

    /**
     * @param dispecerHl the dispecerHl to set
     */
    public void setDispecerHl(Dispecerhl dispecerHl) {
        this.dispecerHl = dispecerHl;
    }

    /**
     * @return the dispecerHlList
     */
    public List<Dispecerhl> getDispecerHlList() {
        if (this.dispecerHlList == null) {
            this.dispecerHlList = new ArrayList<Dispecerhl>(getEjbFacade().findAll());
        }
        return this.dispecerHlList;

    }

    /**
     * @param dispecerHlList the dispecerHlList to set
     */
    public void setDispecerHlList(ArrayList<Dispecerhl> dispecerHlList) {
        this.dispecerHlList = dispecerHlList;
    }

    public Dispecerhl getDispecerHl(Object id) {
        return getEjbFacade().find(id);
    }

    /**
     * @return the ejbFacade
     */
    public ejb.DispecerHlFacade getEjbFacade() {
        return ejbFacade;
    }

    /**
     * @param ejbFacade the ejbFacade to set
     */
    public void setEjbFacade(ejb.DispecerHlFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public boolean isButtonEnabled(String param) {
        switch (param) {
            case "new":
                return true;
            case "edit":
                return this.dispecerHl != null;
            case "delete":
                return this.dispecerHl != null;
        }
        return true;
    }

    public void rowDispecerHlSelectded(Dispecerhl dispecerHl) {
    }

    public String newDispecerHl() {
        String detailDispecerHl = null;
        return detailDispecerHl;
    }

    public String editDispecerHl() {
        String detailDispecerHl = null;
        return detailDispecerHl;
    }

    public String delDispecerHl() {
        String detailDispecerHl = null;
        return detailDispecerHl;
    }

    public void prepareCreate() {
        this.dispecerHl = new Dispecerhl();
        this.dispecerHl.setNewEntity(true);
    }

    public void save() {
        if (this.dispecerHl.isNewEntity()) {
            this.create();
        } else {
            this.update();
        }
    }

    public void delete() {
        this.destroy();
    }

//-----------------------
// Cast PERSISTENCE
//--------------------------    
    public void create() {
//        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TypzdrojeCreated"));
        this.dispecerHl.setNewEntity(false);
        persist(PersistAction.CREATE, "Záznam byl úspěšně vytvořen");
        if (!JsfUtil.isValidationFailed()) {
            // this.dispecerHl = null; // Remove selection
            this.dispecerHlList = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Záznam byl úspěšně uložen");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Záznam byl úspěšně smazán");
        if (!JsfUtil.isValidationFailed()) {
            this.dispecerHl = null; // Remove selection
            this.dispecerHlList = null;    // Invalidate list of items to trigger re-query.
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (this.dispecerHl != null) {
            try {
                switch (persistAction) {
                    case CREATE:
                        getEjbFacade().create(dispecerHl);
                        break;
                    case UPDATE:
                        getEjbFacade().edit(dispecerHl);
                        break;
                    case DELETE:
                        getEjbFacade().remove(dispecerHl);
                        break;
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, "Chyba uložení dat");
                }
                JsfUtil.validationFailed();

            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, "Chyba zpracování");
                JsfUtil.validationFailed();
            }
        }
    }

}
