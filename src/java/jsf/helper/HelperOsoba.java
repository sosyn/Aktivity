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
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

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
    private ejb.OsobaFacade ejbFacade;
    private Osoba selectedOsoba = null;
    private ArrayList<Osoba> selectedOsoby = null;
    private ArrayList<Osoba> osobyList = null;
    private ArrayList<HelperOsobaListener> helperOsobaListeners = new ArrayList<>();
    private ArrayList<HelperOsobyListener> helperOsobyListeners = new ArrayList<>();

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
     * @return the selectedOsoba
     */
    public Osoba getSelectedOsoba() {
        return selectedOsoba;
    }

    public Osoba getSelectedOsoba(Integer id) {
        this.selectedOsoba = getEjbFacade().find(id);
        return this.selectedOsoba;
    }

    public Osoba getOsobaDefault() {
        return getEjbFacade().findName("default");
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
            this.osobyList = new ArrayList<>(getEjbFacade().findAll());
        }
        return this.osobyList;
    }

    /**
     * @param osobyList the osobyList to set
     */
    public void setOsobyList(ArrayList<Osoba> osobyList) {
        this.osobyList = osobyList;
    }


    // Posluchaci jednoho vyberu osoby
    public void addHelperOsobaListener(HelperOsobaListener helperOsobaListener) {
        this.helperOsobaListeners.add(helperOsobaListener);
    }
    public void removeHelperOsobaListener(HelperOsobaListener helperOsobaListener) {
        this.helperOsobaListeners.remove(helperOsobaListener);
    }

    /**
     */
    public void fireHelperOsoba() {
        System.out.println("this.osoba =" + this.selectedOsoba);
        for (HelperOsobaListener helperOsobaListener : helperOsobaListeners) {
            helperOsobaListener.actionHelperOsoba(this.selectedOsoba);
        }

    }

    // Posluchaci vice nasobneho vyberu osob
    public void addHelperOsobyListener(HelperOsobyListener helperOsobyListener) {
        this.helperOsobyListeners.add(helperOsobyListener);
    }
    public void removeHelperOsobyListener(HelperOsobyListener helperOsobyListener) {
        this.helperOsobyListeners.remove(helperOsobyListener);
    }

    /**
     */
    public void fireHelperOsoby() {
        for (HelperOsobyListener helperOsobyListener : helperOsobyListeners) {
            helperOsobyListener.actionHelperOsoby(this.selectedOsoby);
        }
    }

}
