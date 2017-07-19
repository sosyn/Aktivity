/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.spravce;

import ejb.LoginUser;
import entity.Typucast;
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
@Named("typUcast")
@SessionScoped
public class TypUcast implements Serializable {

    private Calendar cal = Calendar.getInstance(Locale.getDefault());

    @EJB
    private ejb.TypUcastFacade ejbFacade;
    @Inject
    LoginUser loginUser;
    private Typucast typUcast = null;
    private ArrayList<Typucast> typUcastList = null;

    private final Object[][] typUcastCis = new Object[][]{
        {"Řidič", 0},
        {"Spolujezdec", 1},
        {"Organizátor", 2},
        {"Účastník", 3},
        {"Uživatel", 4},
        {"Ostatní", 5}
    };

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
     * @return the typUcast
     */
    public Typucast getTypUcast() {
        if (this.typUcast == null) {
            prepareCreate();
        }
        return typUcast;
    }

    /**
     * @param typUcast the typUcast to set
     */
    public void setTypUcast(Typucast typUcast) {
        this.typUcast = typUcast;
    }

    /**
     * @return the typUcastList
     */
    public List<Typucast> getTypUcastList() {
        if (this.typUcastList == null) {
            this.typUcastList = new ArrayList<Typucast>(getEjbFacade().findAll());
        }
        return this.typUcastList;

    }

    /**
     * @param typUcastList the typUcastList to set
     */
    public void setTypUcastList(ArrayList<Typucast> typUcastList) {
        this.typUcastList = typUcastList;
    }

    public Object[][] getTypUcastCis() {
        return this.typUcastCis;
    }

    public String getTypUcastCisNazev(int typUcastCis) {
        String nazev = "?";
        for (Object[] ciselnik : this.getTypUcastCis()) {
            if ((int) ciselnik[1] == typUcastCis) {
                return (String) ciselnik[0];
            }
        }
        return nazev;
    }

    public boolean isButtonEnabled(String param) {
        switch (param) {
            case "new":
                return this.typUcast == null || !this.typUcast.isNewEntity();
            case "save":
                return this.typUcast != null;
            case "delete":
                return this.typUcast != null && !this.typUcast.isNewEntity();
        }
        return true;
    }

    public void prepareCreate() {
        this.typUcast = new Typucast();
        this.typUcast.setNewEntity(true);
    }

    public void save() {
        if (this.typUcast.isNewEntity()) {
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
        this.typUcast.setNewEntity(false);
        persist(PersistAction.CREATE, "Záznam byl úspěšně vytvořen");
        if (!JsfUtil.isValidationFailed()) {
            // this.typUcast = null; // Remove selection
            this.typUcastList = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Záznam byl úspěšně uložen");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Záznam byl úspěšně smazán");
        if (!JsfUtil.isValidationFailed()) {
            this.typUcast = null; // Remove selection
            this.typUcastList = null;    // Invalidate list of items to trigger re-query.
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (this.typUcast != null) {
            try {
                switch (persistAction) {
                    case CREATE:
                        getEjbFacade().create(typUcast);
                        break;
                    case UPDATE:
                        getEjbFacade().edit(typUcast);
                        break;
                    case DELETE:
                        getEjbFacade().remove(typUcast);
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

    public Typucast getTypucast(Object id) {
        return getEjbFacade().find(id);
    }

    /**
     * @return the ejbFacade
     */
    public ejb.TypUcastFacade getEjbFacade() {
        return ejbFacade;
    }

    /**
     * @param ejbFacade the ejbFacade to set
     */
    public void setEjbFacade(ejb.TypUcastFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

}
