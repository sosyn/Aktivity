/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.spravce;

import ejb.LoginUser;
import entity.Osoba;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
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
 *
 * Pripojeni na externi aplikaci IS VERA -------------------------------------
 * Pripojovaci retezec: jdbc:oracle:thin:@192.168.3.76:1521:vera Uzivatel: vera
 * Heslo: vera Schema: vera
 *
 */
@Named("osoby")
@SessionScoped
public class Osoby implements Serializable {

    private static final long serialVersionUID = 1L;
    private Calendar cal = Calendar.getInstance(Locale.getDefault());

    @EJB
    private ejb.OsobaFacade ejbFacade;
    @Inject
    LoginUser loginUser;
    private Osoba osoba = null;
    private ArrayList<Osoba> osobaList = null;
    private String heslo = "";

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
     * @return the ejbFacade
     */
    public ejb.OsobaFacade getEjbFacade() {
        return ejbFacade;
    }

    /**
     * @param ejbFacade the ejbFacade to set
     */
    public void setEjbFacade(ejb.OsobaFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    /**
     * @return the osoba
     */
    public Osoba getOsoba() {
        if (this.osoba == null) {
            prepareCreate();
        }
        return osoba;
    }

    public Osoba getOsoba(UUID id) {
        return getEjbFacade().find(id);
    }

    /**
     * @param osoba the osoba to set
     */
    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;
    }

    /**
     * @return the osobaList
     */
    public List<Osoba> getOsobaList() {
        if (this.osobaList == null) {
            this.osobaList = new ArrayList<>(getEjbFacade().findAll());
        }
        return this.osobaList;

    }

    /**
     * @param osobaList the osobaList to set
     */
    public void setOsobaList(ArrayList<Osoba> osobaList) {
        this.osobaList = osobaList;
    }

    /**
     * @return the heslo
     */
    public String getHeslo() {
        return heslo;
    }

    /**
     * @param heslo the heslo to set
     */
    public void setHeslo(String heslo) {
        this.heslo = heslo;
        this.osoba.setPassword(JsfUtil.hashPasswd(heslo));
    }

    public void rowOsobaSelectded(Osoba item) {
        this.heslo = "";
    }

    public boolean isButtonEnabled(String param) {
        switch (param) {
            case "new":
                return this.osoba == null || !this.osoba.isNewEntity();
            case "save":
                return this.osoba != null;
            case "delete":
                return this.osoba != null && !this.osoba.isNewEntity();
        }
        return true;
    }

    public void prepareCreate() {
        this.osoba = new Osoba();
        this.osoba.setNewEntity(true);
        this.heslo = "";
    }

    public void save() {
        if (this.osoba.isNewEntity()) {
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
//        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("OsobaCreated"));
        this.osoba.setNewEntity(false);
        persist(PersistAction.CREATE, "Záznam byl úspěšně vytvořen");
        if (!JsfUtil.isValidationFailed()) {
            // this.osoba = null; // Remove selection
            this.osobaList = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Záznam byl úspěšně uložen");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Záznam byl úspěšně smazán");
        if (!JsfUtil.isValidationFailed()) {
            this.osoba = null; // Remove selection
            this.osobaList = null;    // Invalidate list of items to trigger re-query.
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (this.osoba != null) {
            try {
                switch (persistAction) {
                    case CREATE:
                        getEjbFacade().create(osoba);
                        break;
                    case UPDATE:
                        getEjbFacade().edit(osoba);
                        break;
                    case DELETE:
                        getEjbFacade().remove(osoba);
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
