/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.spravce;

import ejb.LoginUser;
import entity.Typaktivity;
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
@Named("typAkt")
@SessionScoped
public class TypAkt implements Serializable {

    private Calendar cal = Calendar.getInstance(Locale.getDefault());

    @EJB
    private ejb.TypAktFacade ejbFacade;
    @Inject
    LoginUser loginUser;
    private Typaktivity typAkt = null;
    private ArrayList<Typaktivity> typAktList = null;

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
     * @return the typAkt
     */
    public Typaktivity getTypAkt() {
        if (this.typAkt == null) {
            prepareCreate();
        }
        return typAkt;
    }

    /**
     * @param typAkt the typAkt to set
     */
    public void setTypAkt(Typaktivity typAkt) {
        this.typAkt = typAkt;
    }

    /**
     * @return the typAktList
     */
    public List<Typaktivity> getTypAktList() {
        if (this.typAktList == null) {
            this.typAktList = new ArrayList<Typaktivity>(getEjbFacade().findAll());
        }
        return this.typAktList;

    }

    /**
     * @param typAktList the typAktList to set
     */
    public void setTypAktList(ArrayList<Typaktivity> typAktList) {
        this.typAktList = typAktList;
    }

    public String getTypAktPopis(int typTypuAktivity) {
        switch (typTypuAktivity) {
            case 0:
                return ("Služební cesta");
            case 1:
                return ("Rezervace zdrojů(jiné)");
            default:
                return (typTypuAktivity + "=?");
        }
    }

    public boolean isButtonEnabled(String param) {
        switch (param) {
            case "new":
                return this.typAkt == null || !this.typAkt.isNewEntity();
            case "save":
                return this.typAkt != null;
            case "delete":
                return this.typAkt != null && !this.typAkt.isNewEntity();
        }
        return true;
    }

    public void prepareCreate() {
        this.typAkt = new Typaktivity();
        this.typAkt.setNewEntity(true);
    }

    public void save() {
        if (this.typAkt.isNewEntity()) {
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
        this.typAkt.setNewEntity(false);
        persist(PersistAction.CREATE, "Záznam byl úspěšně vytvořen");
        if (!JsfUtil.isValidationFailed()) {
            // this.typAkt = null; // Remove selection
            this.typAktList = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Záznam byl úspěšně uložen");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Záznam byl úspěšně smazán");
        if (!JsfUtil.isValidationFailed()) {
            this.typAkt = null; // Remove selection
            this.typAktList = null;    // Invalidate list of items to trigger re-query.
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (this.typAkt != null) {
            try {
                switch (persistAction) {
                    case CREATE:
                        getEjbFacade().create(typAkt);
                        break;
                    case UPDATE:
                        getEjbFacade().edit(typAkt);
                        break;
                    case DELETE:
                        getEjbFacade().remove(typAkt);
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

    public Typaktivity getTypaktivity(Object id) {
        return getEjbFacade().find(id);
    }

    /**
     * @return the ejbFacade
     */
    public ejb.TypAktFacade getEjbFacade() {
        return ejbFacade;
    }

    /**
     * @param ejbFacade the ejbFacade to set
     */
    public void setEjbFacade(ejb.TypAktFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

}
