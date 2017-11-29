/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Dispecerhl;
import entity.Dispecerpol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import jsf.util.JsfUtil;

/**
 *
 * @author Ivo
 */
@Named(value = "daoDispecer")
@SessionScoped
//@Stateful
public class DAOdispecer implements Serializable{

    static final long serialVersionUID = 42L;
    
    private Calendar cal = Calendar.getInstance(Locale.getDefault());

    @EJB
    private ejb.DispecerHlFacade ejbDispHlFacade;

    private Dispecerhl dispecerHl = null;
    private ArrayList<Dispecerhl> dispecerHlList = null;
    private Dispecerhl zastupce = null;
    private ArrayList<Dispecerhl> zastupceList = null;

    @EJB
    private ejb.DispecerPolFacade ejbDispPolFacade;
    private Dispecerpol dispecerPol = null;

    @PostConstruct
    void initDAOdispecer() {
        System.out.println("initDAOdispecer()");
    }

    /**
     * @return the ejbDispHlFacade
     */
    public ejb.DispecerHlFacade getEjbDispHlFacade() {
        return ejbDispHlFacade;
    }

    /**
     * @param ejbDispHlFacade the ejbDispHlFacade to set
     */
    public void setEjbDispHlFacade(ejb.DispecerHlFacade ejbDispHlFacade) {
        this.ejbDispHlFacade = ejbDispHlFacade;
    }

    /**
     * @return the dispecerHl
     */
    public Dispecerhl getDispecerHl() {
        return dispecerHl;
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
    public ArrayList<Dispecerhl> getDispecerHlList() {
        if (this.dispecerHlList == null) {
            this.dispecerHlList = new ArrayList<>(getEjbDispHlFacade().findAll());
        }
        return dispecerHlList;
    }

    /**
     * @param dispecerHlList the dispecerHlList to set
     */
    public void setDispecerHlList(ArrayList<Dispecerhl> dispecerHlList) {
        this.dispecerHlList = dispecerHlList;
    }

    /**
     * @return the ejbDispPolFacade
     */
    public ejb.DispecerPolFacade getEjbDispPolFacade() {
        return ejbDispPolFacade;
    }

    /**
     * @param ejbDispPolFacade the ejbDispPolFacade to set
     */
    public void setEjbDispPolFacade(ejb.DispecerPolFacade ejbDispPolFacade) {
        this.ejbDispPolFacade = ejbDispPolFacade;
    }

    /**
     * @return the dispecerPol
     */
    public Dispecerpol getDispecerPol() {
        return dispecerPol;
    }

    /**
     * @param dispecerPol the dispecerPol to set
     */
    public void setDispecerPol(Dispecerpol dispecerPol) {
        this.dispecerPol = dispecerPol;
    }

    /**
     * @return the dispecerPolList
     */
    public ArrayList<Dispecerpol> getDispecerPolList() {
        return (ArrayList<Dispecerpol>) this.dispecerHl.getDispecerpolList();
    }

    /**
     * @param dispecerPolList the dispecerPolList to set
     */
    public void setDispecerPolList(ArrayList<Dispecerpol> dispecerPolList) {
        this.dispecerHl.setDispecerpolList(dispecerPolList);
    }

    /**
     * @return the zastupce
     */
    public Dispecerhl getZastupce() {
        return zastupce;
    }

    /**
     * @param zastupce the zastupce to set
     */
    public void setZastupce(Dispecerhl zastupce) {
        this.zastupce = zastupce;
    }

    /**
     * @param zastupceList the zastupceList to set
     */
    public void setZastupceList(ArrayList<Dispecerhl> zastupceList) {
        this.zastupceList = zastupceList;
    }

    /**
     * @return the zastupceList
     */
    public ArrayList<Dispecerhl> getZastupceList() {
        return zastupceList;
    }

    /**
     * Metoda vztvori noveho dispecera
     */
    public void newDispecerHl() {
        this.dispecerHl= new Dispecerhl();
        this.dispecerHl.setNewEntity(true);
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        this.dispecerHl.setPlatiod(cal.getTime());
        cal.set(Calendar.YEAR, 2100);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        this.dispecerHl.setPlatido(cal.getTime());
    }

    public void fullDispecerHl(int mod) {
        if (mod == 0) {
            newDispecerHl();
        } else {
// !!!!!! Naplnit vsechny matice
        }
    }

//-----------------------
// Cast PERSISTENCE
//--------------------------    
    public void saveDispHl() {
        if (this.getDispecerHl().isNewEntity()) {
            persist(JsfUtil.PersistAction.CREATE);
        } else {
            persist(JsfUtil.PersistAction.UPDATE);
        }
    }

    public void delete() {
        persist(JsfUtil.PersistAction.DELETE);
    }

    private void persist(JsfUtil.PersistAction persistAction) {
        if (this.getDispecerHl() != null) {
            switch (persistAction) {
                case CREATE:
                    getEjbDispHlFacade().create(getDispecerHl());
                    this.getDispecerHl().setNewEntity(false);
                    this.dispecerHlList.add(this.dispecerHl);
                    break;
                case UPDATE:
                    getEjbDispHlFacade().edit(getDispecerHl());
                    break;
                case DELETE:
                    getEjbDispHlFacade().remove(getDispecerHl());
                    break;
            }
        }
    }

    public void resetDispecerHl() {
        if (this.dispecerHl != null) {
            if (this.dispecerHl.isNewEntity()) {
                this.dispecerHl = null;
            } else {
                this.dispecerHl = ejbDispHlFacade.find(this.dispecerHl.getId());
            }
        }
    }
}
