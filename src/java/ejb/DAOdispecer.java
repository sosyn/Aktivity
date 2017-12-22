/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Dispecerhl;
import entity.Dispeceroso;
import entity.Dispecerpol;
import entity.Dispecerzdr;
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
/**
 * !!! POZOR !!! persitence tridy funguje POUZE pokud je v tabulce "dispecerihl"
 * alespon 1 veta Jinak relace dispecerhl.idsiphl > dispecerhl.id konci
 * nedefinovanou chybou a nedari se pridat zaznam ASI musi byt ve vsech
 * tabulkach pri startu veta ???
 */
public class DAOdispecer implements Serializable {

    static final long serialVersionUID = 42L;

    public static final int DISPECERHL_NEW = 0;
    public static final int DISPECERHL_EDIT = 1;

    private Calendar cal = Calendar.getInstance(Locale.getDefault());

    @EJB
    private ejb.DispecerHlFacade ejbDispHlFacade;

    private Dispecerhl dispecerHl = null;
    private ArrayList<Dispecerhl> dispecerHlList = null;
    private boolean dispeceriAzastupci = false;
    private Dispecerhl zastupce = null;

    @EJB
    private ejb.DispecerOsoFacade ejbDispOsoFacade;
    private Dispeceroso dispecerOso = null;
    @EJB
    private ejb.DispecerZdrFacade ejbDispZdrFacade;
    private Dispecerzdr dispecerZdr = null;

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
     * @return the dispeceriAzastupci
     */
    public boolean isDispeceriAzastupci() {
        return dispeceriAzastupci;
    }

    /**
     * @param dispeceriAzastupci the dispeceriAzastupci to set
     */
    public void setDispeceriAzastupci(boolean dispeceriAzastupci) {
        this.dispeceriAzastupci = dispeceriAzastupci;
    }

    /**
     * @return the dispecerHlList
     */
    public ArrayList<Dispecerhl> getDispecerHlList() {
        if (this.dispecerHlList == null) {
            this.dispecerHlList = new ArrayList<>(getEjbDispHlFacade().findAllDispAZast(this.dispeceriAzastupci));
        }
        return dispecerHlList;
    }

    public ArrayList<Dispecerhl> refreshDispecerHlList() {
        this.dispecerHlList = new ArrayList<>(getEjbDispHlFacade().findAllDispAZast(this.dispeceriAzastupci));
        return dispecerHlList;
    }

    /**
     * @param dispecerHlList the dispecerHlList to set
     */
    public void setDispecerHlList(ArrayList<Dispecerhl> dispecerHlList) {
        this.dispecerHlList = dispecerHlList;
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
     * @return the ejbDispOsoFacade
     */
    public ejb.DispecerOsoFacade getEjbDispOsoFacade() {
        return ejbDispOsoFacade;
    }

    /**
     * @param ejbDispOsoFacade the ejbDispOsoFacade to set
     */
    public void setEjbDispOsoFacade(ejb.DispecerOsoFacade ejbDispOsoFacade) {
        this.ejbDispOsoFacade = ejbDispOsoFacade;
    }

    /**
     * @return the dispecerOso
     */
    public Dispeceroso getDispecerOso() {
        return dispecerOso;
    }

    /**
     * @param dispecerOso the dispecerOso to set
     */
    public void setDispecerOso(Dispeceroso dispecerOso) {
        this.dispecerOso = dispecerOso;
    }

    /**
     * @return the ejbDispZdrFacade
     */
    public ejb.DispecerZdrFacade getEjbDispZdrFacade() {
        return ejbDispZdrFacade;
    }

    /**
     * @param ejbDispZdrFacade the ejbDispZdrFacade to set
     */
    public void setEjbDispZdrFacade(ejb.DispecerZdrFacade ejbDispZdrFacade) {
        this.ejbDispZdrFacade = ejbDispZdrFacade;
    }

    /**
     * @return the dispecerZdr
     */
    public Dispecerzdr getDispecerZdr() {
        return dispecerZdr;
    }

    /**
     * @param dispecerZdr the dispecerZdr to set
     */
    public void setDispecerZdr(Dispecerzdr dispecerZdr) {
        this.dispecerZdr = dispecerZdr;
    }

    
    
//-----------------------
// Cast PERSISTENCE pro dispecera
//--------------------------    
    /**
     * Metoda vztvori noveho dispecera
     */
    public void newDispecerHl() {
        this.dispecerHl = new Dispecerhl();
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
        this.dispecerHl.setZastupciList(new ArrayList<Dispecerhl>());
        this.dispecerHl.setDispecerOsoList(new ArrayList<Dispeceroso>());
        this.dispecerHl.setDispecerZdrList(new ArrayList<Dispecerzdr>());
    }

    public void fillDispecerHl(int mod) {
        if (mod == DAOdispecer.DISPECERHL_NEW) {
            newDispecerHl();
        } else {
// !!!!!! Naplnit vsechny matice
        }
    }

    public void dispHlSave() {
        if (this.getDispecerHl().isNewEntity()) {
            dispHlPersist(JsfUtil.PersistAction.CREATE);
        } else {
            dispHlPersist(JsfUtil.PersistAction.UPDATE);
        }
    }

    public void dispHlDelete() {
        dispHlPersist(JsfUtil.PersistAction.DELETE);
    }

    private void dispHlPersist(JsfUtil.PersistAction persistAction) {
        ArrayList<Dispecerhl> zastupciList = new ArrayList<>(this.dispecerHl.getZastupciList());
        for (Dispecerhl zast : zastupciList) {
            if (zast.getIdoso() == null) {
                this.dispecerHl.getZastupciList().remove(zast);
            }
        }
        ArrayList<Dispeceroso> dispOsoList = new ArrayList<>(this.dispecerHl.getDispecerOsoList());
        for (Dispeceroso dOso : dispOsoList) {
            if (dOso.getIdoso() == null) {
                this.dispecerHl.getDispecerOsoList().remove(dOso);
            }
        }
        ArrayList<Dispecerzdr> dispZdrList = new ArrayList<>(this.dispecerHl.getDispecerZdrList());
        for (Dispecerzdr dZdr : dispZdrList) {
            if (dZdr.getIdzdr()== null) {
                this.dispecerHl.getDispecerZdrList().remove(dZdr);
            }
        }
        if (this.getDispecerHl() != null) {
            switch (persistAction) {
                case CREATE:
                    this.getDispecerHl().setNewEntity(false);
                    getEjbDispHlFacade().create(this.dispecerHl);
                    this.dispecerHlList.add(this.dispecerHl);
                    break;
                case UPDATE:
                    getEjbDispHlFacade().edit(this.dispecerHl);
                    break;
                case DELETE:
                    getEjbDispHlFacade().remove(this.dispecerHl);
                    break;
            }
        }
    }

    public void dispHlReset() {
        if (this.dispecerHl != null) {
            if (this.dispecerHl.isNewEntity()) {
                this.dispecerHl = null;
            } else {
                this.dispecerHl = ejbDispHlFacade.find(this.dispecerHl.getId());
            }
        }
    }

//-----------------------
// Cast PERSISTENCE pro zastupce
//--------------------------    
    public void zastNew() {
        this.zastupce = new Dispecerhl();
        // Dosadit dispecera, jehoz bude zaznam zastupcem
        this.zastupce.setIddisphl(dispecerHl);
        this.zastupce.setIdtypschv(dispecerHl.getIdtypschv());
        this.zastupce.setIdtypzdr(dispecerHl.getIdtypzdr());
        this.zastupce.setNewEntity(true);
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        this.zastupce.setPlatiod(cal.getTime());
        cal.set(Calendar.YEAR, 2100);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        this.zastupce.setPlatido(cal.getTime());
        this.dispecerHl.getZastupciList().add(this.zastupce);
    }

    public void zastEdit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void zastDel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void zastSave() {
        for (Dispecerhl zast : this.dispecerHl.getZastupciList()) {
            if (zast.getIdoso() != null) {
                zast.setIdtypschv(dispecerHl.getIdtypschv());
                zast.setIdtypzdr(dispecerHl.getIdtypzdr());
                if (zast.isNewEntity()) {
                    zast.setNewEntity(false);
                    getEjbDispHlFacade().create(zast);
                } else {
                    getEjbDispHlFacade().edit(zast);
                }
            }
        }
    }

//----------------------------------------------
// Cast PERSISTENCE pro podrizene osoby dispecera
//----------------------------------------------    
    public void dispOsoNew() {
        this.setDispecerOso(new Dispeceroso());
        // Dosadit dispecera, který bude mít polozku na starosti
        this.getDispecerOso().setIddisphl(dispecerHl);
        this.getDispecerOso().setNewEntity(true);
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        this.getDispecerOso().setPlatiod(cal.getTime());
        cal.set(Calendar.YEAR, 2100);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        this.getDispecerOso().setPlatido(cal.getTime());

        this.dispecerHl.getDispecerOsoList().add(this.getDispecerOso());
    }

    public void dispOsoDel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
//----------------------------------------------
// Cast PERSISTENCE pro spravovane zdroje
//----------------------------------------------    
    public void dispZdrNew() {
        this.setDispecerZdr(new Dispecerzdr());
        // Dosadit dispecera, který bude mít polozku na starosti
        this.getDispecerZdr().setIddisphl(dispecerHl);
        this.getDispecerZdr().setNewEntity(true);
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        this.getDispecerZdr().setPlatiod(cal.getTime());
        cal.set(Calendar.YEAR, 2100);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        this.getDispecerZdr().setPlatido(cal.getTime());

        this.dispecerHl.getDispecerZdrList().add(this.getDispecerZdr());
    }

    public void dispZdrDel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
