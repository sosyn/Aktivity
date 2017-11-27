/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.spravce;

import ejb.LoginUser;
import entity.Dispecerhl;
import entity.Dispecerpol;
import entity.Osoba;
import entity.Typzdroje;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Ivo
 */
@Named("dispecerForm")
@SessionScoped
public class DispecerForm implements Serializable {

    private Calendar cal = Calendar.getInstance(Locale.getDefault());

    @EJB
    private ejb.DispecerHlFacade ejbDispHlFacade;
    @EJB
    private ejb.DispecerPolFacade ejbDispPolFacade;
    @EJB
    private ejb.TypZdrFacade ejbTypZdrFacade;
    @EJB
    private ejb.OsobaFacade ejbOsobaFacade;
    @Inject
    private LoginUser loginUser;
    private Dispecerhl dispecerHl = null;
    private ArrayList<Dispecerhl> zastupceList = null;
    private ArrayList<Dispecerpol> dispecerPolList = null;
    private ArrayList<Typzdroje> typZdrList = null;
    private ArrayList<Osoba> osobaList = null;

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
     * @return the zastupceList
     */
    public List<Dispecerhl> getZastupceList() {
        if (this.zastupceList == null) {
            this.zastupceList = new ArrayList<Dispecerhl>(ejbDispHlFacade.findAll());
        }
        return this.zastupceList;

    }

    /**
     * @param zastupceList the zastupceList to set
     */
    public void setZastupceList(ArrayList<Dispecerhl> zastupceList) {
        this.zastupceList = zastupceList;
    }

    public Dispecerhl getDispecerHl(Object id) {
        return ejbDispHlFacade.find(id);
    }

    /**
     * @return the dispecerPolList
     */
    public ArrayList<Dispecerpol> getDispecerPolList() {
        return dispecerPolList;
    }

    /**
     * @param dispecerPolList the dispecerPolList to set
     */
    public void setDispecerPolList(ArrayList<Dispecerpol> dispecerPolList) {
        this.dispecerPolList = dispecerPolList;
    }

    /**
     * @return the typZdrList
     */
    public ArrayList<Typzdroje> getTypZdrList() {
        if (this.typZdrList == null) {
            this.typZdrList = new ArrayList<>(ejbTypZdrFacade.findAll());
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
     * @return the osobaList
     */
    public ArrayList<Osoba> getOsobaList() {
        return osobaList;
    }

    /**
     * @param osobaList the osobaList to set
     */
    public void setOsobaList(ArrayList<Osoba> osobaList) {
        this.osobaList = osobaList;
    }

    public void platiOdListener() {
        if (dispecerHl.getPlatiod().after(dispecerHl.getPlatido())) {
            this.cal.setTime(dispecerHl.getPlatiod());
            if (cal.get(Calendar.HOUR_OF_DAY) < 17) {
                cal.set(Calendar.HOUR_OF_DAY, 17);
            }
            dispecerHl.setPlatido(this.cal.getTime());
        }
    }

    public void platiDoListener() {
    }
    
    /**
     * Uložení záznam dispecerHl do databáze - persistence
     */
    public void saveDispecer() {
    }

}
