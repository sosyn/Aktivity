/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.spravce;

import ejb.LoginUser;
import entity.Typschv;
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
@Named("typSchv")
@SessionScoped
public class TypSchv implements Serializable {

    private static final long serialVersionUID = 1L;
    private Calendar cal = Calendar.getInstance(Locale.getDefault());

    @EJB
    private ejb.TypSchvFacade ejbFacade;
    @Inject
    LoginUser loginUser;
    private Typschv typSchv = null;
    private ArrayList<Typschv> typSchvList = null;

    private final Object[][] typSchvCis = new Object[][]{
        {"Administrátor", "A"},
        {"Tajemník", "T"},
        {"Vedoucí odboru", "VO"},
        {"Vedoucí oddělení", "VOdd"},
        {"Zástupce vedoucího odboru", "VOZ"},
        {"Zástupce vedoucího oddělení", "VOddZ"},
        {"Dispečer", "D"},
        {"Ostatní", " "}
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
     * @return the typSchv
     */
    public Typschv getTypSchv() {
        if (this.typSchv == null) {
            prepareCreate();
        }
        return typSchv;
    }

    /**
     * @param typSchv the typSchv to set
     */
    public void setTypSchv(Typschv typSchv) {
        this.typSchv = typSchv;
    }

    /**
     * @return the typSchvList
     */
    public List<Typschv> getTypSchvList() {
        if (this.typSchvList == null) {
            this.typSchvList = new ArrayList(getEjbFacade().findAll());
        }
        return this.typSchvList;

    }

    /**
     * @param typSchvList the typSchvList to set
     */
    public void setTypSchvList(ArrayList<Typschv> typSchvList) {
        this.typSchvList = typSchvList;
    }

    /**
     * @return the typZdrCis
     */
    public Object[][] getTypSchvCis() {
        return typSchvCis;
    }

    public String getTypSchvCisNazev(String typSchvCis) {
        String nazev = "?";
        for (Object[] ciselnik : this.getTypSchvCis()) {
            if (((String) ciselnik[1]).equals(typSchvCis)) {
                return (String) ciselnik[0];
            }
        }
        return nazev;
    }

    public boolean isButtonEnabled(String param) {
        switch (param) {
            case "new":
                return this.typSchv == null || !this.typSchv.isNewEntity();
            case "save":
                return this.typSchv != null;
            case "delete":
                return this.typSchv != null && !this.typSchv.isNewEntity();
        }
        return true;
    }

    public void prepareCreate() {
        this.typSchv = new Typschv();
        this.typSchv.setNewEntity(true);
    }

    public void save() {
        if (this.typSchv.isNewEntity()) {
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
        this.typSchv.setNewEntity(false);
        persist(PersistAction.CREATE, "Záznam byl úspěšně vytvořen");
        if (!JsfUtil.isValidationFailed()) {
            // this.typSchv = null; // Remove selection
            this.typSchvList = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Záznam byl úspěšně uložen");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Záznam byl úspěšně smazán");
        if (!JsfUtil.isValidationFailed()) {
            this.typSchv = null; // Remove selection
            this.typSchvList = null;    // Invalidate list of items to trigger re-query.
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (this.typSchv != null) {
            try {
                switch (persistAction) {
                    case CREATE:
                        getEjbFacade().create(typSchv);
                        break;
                    case UPDATE:
                        getEjbFacade().edit(typSchv);
                        break;
                    case DELETE:
                        getEjbFacade().remove(typSchv);
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

    public Typschv getTypschv(Object id) {
        return getEjbFacade().find(id);
    }

    /**
     * @return the ejbFacade
     */
    public ejb.TypSchvFacade getEjbFacade() {
        return ejbFacade;
    }

    /**
     * @param ejbFacade the ejbFacade to set
     */
    public void setEjbFacade(ejb.TypSchvFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

}
