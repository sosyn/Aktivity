/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.helper;

import entity.Osoba;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Ivo
 *
 * Pripojeni na externi aplikaci IS VERA -------------------------------------
 * Pripojovaci retezec: jdbc:oracle:thin:@192.168.3.76:1521:vera Uzivatel: vera
 * Heslo: vera Schema: vera
 *
 */
@Named("helperOsoba")
@SessionScoped
public class HelperOsoba implements Serializable {

    private Calendar cal = Calendar.getInstance(Locale.getDefault());

    @EJB
    private ejb.OsobaFacade ejbOsobaFacade;
    private Osoba selectedOsoba = null;
    private ArrayList<Osoba> selectedOsoby = null;
    private ArrayList<Osoba> osobyList = null;

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
     * @return the selectedOsoba
     */
    public Osoba getSelectedOsoba() {
        return selectedOsoba;
    }

    public Osoba getSelectedOsoba(UUID id) {
        this.selectedOsoba = this.ejbOsobaFacade.find(id);
        return this.selectedOsoba;
    }

    public Osoba getOsobaDefault() {
        return this.ejbOsobaFacade.findName("default");
    }

    /**
     * @param selectedOsoba the selectedOsoba to set
     */
    public void setSelectedOsoba(Osoba selectedOsoba) {
        this.selectedOsoba = selectedOsoba;
    }

    /**
     * @return the selectedOsoby
     */
    public ArrayList<Osoba> getSelectedOsoby() {
        return selectedOsoby;
    }

    /**
     * @param selectedOsoby the selectedOsoby to set
     */
    public void setSelectedOsoby(ArrayList<Osoba> selectedOsoby) {
        this.selectedOsoby = selectedOsoby;
    }

    /**
     * @return the osobyList
     */
    public List<Osoba> getOsobyList() {
        if (this.osobyList == null) {
            this.osobyList = new ArrayList<>(this.ejbOsobaFacade.findAll());
        }
        return this.osobyList;
    }
    
    /**
     * @param osobyList the osobyList to set
     */
    public void setOsobyList(ArrayList<Osoba> osobyList) {
        this.osobyList = osobyList;
    }

    public void submitHelperOsoby() {
        RequestContext.getCurrentInstance().closeDialog(this.selectedOsoby);
    }
    public void cancelHelperOsoby() {
        RequestContext.getCurrentInstance().closeDialog(null);
    }
}
