/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.spravce;

import ejb.LoginUser;
import entity.Osoba;
import entity.Typzdroje;
import entity.Zdroj;
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
import jsf.helper.HelperOsoba;
import jsf.util.JsfUtil;
import jsf.util.JsfUtil.PersistAction;
import jsf.helper.HelperOsobaListener;

/**
 *
 * @author Ivo
 *
 * Pripojeni na externi aplikaci IS VERA -------------------------------------
 * Pripojovaci retezec: jdbc:oracle:thin:@192.168.3.76:1521:vera Uzivatel: vera
 * Heslo: vera Schema: vera
 *
 */
@Named("zdroje")
@SessionScoped
public class Zdroje implements Serializable, HelperOsobaListener {

    private Calendar cal = Calendar.getInstance(Locale.getDefault());

    @EJB
    private ejb.ZdrojeFacade ejbFacade;
    @EJB
    private ejb.OsobaFacade ejbOsobaFacade;
    @EJB
    private ejb.TypZdrFacade ejbTypZdrFacade;
    @Inject
    LoginUser loginUser;
    @Inject
    private HelperOsoba helperOsoba;

    private Zdroj zdroj = null;
    private ArrayList<Zdroj> zdrojList = null;
    private ArrayList<Typzdroje> typZdrList = null;

    @PostConstruct
    void init() {
        helperOsoba.addHelperOsobaListener(this);
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
    public ejb.ZdrojeFacade getEjbFacade() {
        return ejbFacade;
    }

    /**
     * @param ejbFacade the ejbFacade to set
     */
    public void setEjbFacade(ejb.ZdrojeFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    /**
     * @return the ejbOsobaFacade
     */
    public ejb.OsobaFacade getEjbOsobaFacade() {
        return ejbOsobaFacade;
    }

    /**
     * @param ejbOsobaFacade the ejbOsobaFacade to set
     */
    public void setEjbOsobaFacade(ejb.OsobaFacade ejbOsobaFacade) {
        this.ejbOsobaFacade = ejbOsobaFacade;
    }

    /**
     * @return the ejbTypZdrFacade
     */
    public ejb.TypZdrFacade getEjbTypZdrFacade() {
        return ejbTypZdrFacade;
    }

    /**
     * @param ejbTypZdrFacade the ejbTypZdrFacade to set
     */
    public void setEjbTypZdrFacade(ejb.TypZdrFacade ejbTypZdrFacade) {
        this.ejbTypZdrFacade = ejbTypZdrFacade;
    }

    /**
     * @return the zdroj
     */
    public Zdroj getZdroj() {
        if (this.zdroj == null) {
            prepareCreate();
        }
        return zdroj;
    }

    public Zdroj getZdroj(UUID id) {
        return getEjbFacade().find(id);
    }

    /**
     * @param zdroj the zdroj to set
     */
    public void setZdroj(Zdroj zdroj) {
        this.zdroj = zdroj;
    }

    /**
     * @return the zdrojList
     */
    public List<Zdroj> getZdrojList() {
        if (this.zdrojList == null) {
            this.zdrojList = new ArrayList<>(getEjbFacade().findAll());
        }
        return this.zdrojList;
    }

    /**
     * @param zdrojList the zdrojList to set
     */
    public void setZdrojList(ArrayList<Zdroj> zdrojList) {
        this.zdrojList = zdrojList;
    }

    /**
     * @return the typZdrList
     */
    public ArrayList<Typzdroje> getTypZdrList() {
        if (this.typZdrList == null) {
            this.typZdrList = new ArrayList<>(getEjbTypZdrFacade().findAll());
        }
        return typZdrList;
    }

    /**
     * @param typZdrList the typZdrList to set
     */
    public void setTypZdrList(ArrayList<Typzdroje> typZdrList) {
        this.typZdrList = typZdrList;
    }

    /**
     * @return the helperOsoba
     */
    public HelperOsoba gethelperOsoba() {
        return helperOsoba;
    }

    /**
     * @param helperOsoba the helperOsoba to set
     */
    public void sethelperOsoba(HelperOsoba helperOsoba) {
        this.helperOsoba = helperOsoba;
    }

    public boolean isButtonEnabled(String param) {
        switch (param) {
            case "new":
                return this.zdroj == null || !this.zdroj.isNewEntity();
            case "save":
                return this.zdroj != null;
            case "delete":
                return this.zdroj != null && !this.zdroj.isNewEntity();
        }
        return true;
    }

    public void prepareCreate() {
        this.zdroj = new Zdroj();
        this.zdroj.setIdoso(gethelperOsoba().getOsobaDefault());
        this.zdroj.setNewEntity(true);

    }

    public void rowSelected() {
        helperOsoba.getSelectedOsoba(zdroj.getIdoso().getId());
    }

    public void prepareHelperOsoba(Osoba osoba) {
        helperOsoba.addHelperOsobaListener(this);
        this.gethelperOsoba().setSelectedOsoba(osoba);
    }

    public void save() {
        if (this.zdroj.isNewEntity()) {
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
//        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ZdrojCreated"));
        this.zdroj.setNewEntity(false);
        persist(PersistAction.CREATE, "Záznam byl úspěšně vytvořen");
        if (!JsfUtil.isValidationFailed()) {
            // this.zdroj = null; // Remove selection
            this.zdrojList = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Záznam byl úspěšně uložen");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Záznam byl úspěšně smazán");
        if (!JsfUtil.isValidationFailed()) {
            this.zdroj = null; // Remove selection
            this.zdrojList = null;    // Invalidate list of items to trigger re-query.
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (this.zdroj != null) {
            try {
                switch (persistAction) {
                    case CREATE:
                        getEjbFacade().create(zdroj);
                        break;
                    case UPDATE:
                        getEjbFacade().edit(zdroj);
                        break;
                    case DELETE:
                        getEjbFacade().remove(zdroj);
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

    @Override
    public void actionHelperOsoba(Osoba osoba) {
        this.zdroj.setIdoso(osoba);
    }
}
