/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.spravce;

import ejb.LoginUser;
import entity.Typzdroje;
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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import jsf.util.JsfUtil;
import jsf.util.JsfUtil.PersistAction;

/**
 *
 * @author Ivo
 */
@Named("typZdr")
@SessionScoped
public class TypZdr implements Serializable {

    private Calendar cal = Calendar.getInstance(Locale.getDefault());

    @EJB
    private ejb.TypZdrFacade ejbFacade;
    @Inject
    LoginUser loginUser;
    private Typzdroje typZdr = null;
    private ArrayList<Typzdroje> typZdrList = null;

    private Object[][] typZdrCis = new Object[][]{
        {"Vozidlo", 0},
        {"Místnost", 1},
        {"Dopravní prostředek (jiný než vozidlo)", 2},
        {"Výpočetní technika", 3},
        {"Ostatní nezařazené", 4}};

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
     * @return the typZdr
     */
    public Typzdroje getTypZdr() {
        if (this.typZdr == null) {
            prepareCreate();
        }
        return typZdr;
    }

    /**
     * @param typZdr the typZdr to set
     */
    public void setTypZdr(Typzdroje typZdr) {
        this.typZdr = typZdr;
    }

    /**
     * @return the typZdrList
     */
    public List<Typzdroje> getTypZdrList() {
        if (this.typZdrList == null) {
            this.typZdrList = new ArrayList<Typzdroje>(getEjbFacade().findAll());
        }
        return this.typZdrList;

    }

    /**
     * @param typZdrList the typZdrList to set
     */
    public void setTypZdrList(ArrayList<Typzdroje> typZdrList) {
        this.typZdrList = typZdrList;
    }

    /**
     * @return the typZdrCis
     */
    public Object[][] getTypZdrCis() {
        return typZdrCis;
    }

    public String getTypZdrCisNazev(int typZdrCis) {
        String nazev = "?";
        for (Object[] ciselnik : this.getTypZdrCis()) {
            if ((int) ciselnik[1] == typZdrCis) {
                return (String) ciselnik[0];
            }
        }
        return nazev;
    }

    public boolean isButtonEnabled(String param) {
        switch (param) {
            case "new":
                return this.typZdr == null || !this.typZdr.isNewEntity();
            case "save":
                return this.typZdr != null;
            case "delete":
                return this.typZdr != null && !this.typZdr.isNewEntity();
        }
        return true;
    }

    public void prepareCreate() {
        this.typZdr = new Typzdroje();
        this.typZdr.setNewEntity(true);
    }

    public void save() {
        if (this.typZdr.isNewEntity()) {
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
        this.typZdr.setNewEntity(false);
        persist(PersistAction.CREATE, "Záznam byl úspěšně vytvořen");
        if (!JsfUtil.isValidationFailed()) {
            // this.typZdr = null; // Remove selection
            this.typZdrList = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Záznam byl úspěšně uložen");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Záznam byl úspěšně smazán");
        if (!JsfUtil.isValidationFailed()) {
            this.typZdr = null; // Remove selection
            this.typZdrList = null;    // Invalidate list of items to trigger re-query.
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (this.typZdr != null) {
            try {
                switch (persistAction) {
                    case CREATE:
                        getEjbFacade().create(typZdr);
                        break;
                    case UPDATE:
                        getEjbFacade().edit(typZdr);
                        break;
                    case DELETE:
                        getEjbFacade().remove(typZdr);
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

    public Typzdroje getTypzdroje(Object id) {
        return getEjbFacade().find(id);
    }

    /**
     * @return the ejbFacade
     */
    public ejb.TypZdrFacade getEjbFacade() {
        return ejbFacade;
    }

    /**
     * @param ejbFacade the ejbFacade to set
     */
    public void setEjbFacade(ejb.TypZdrFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

}
