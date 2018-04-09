/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Dispecerhl;
import entity.Dispecerpol;
import entity.Osoba;
import entity.Zdroj;
import java.io.Serializable;
import java.util.ArrayList;
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
 * alespon 1 veta Jinak relace dispecerhl.iddisphl > dispecerhl.id konci
 * nedefinovanou chybou a nedari se pridat zaznam ASI musi byt ve vsech
 * tabulkach pri startu veta ???
 */
public class DAOdispecer implements Serializable {

    static final long serialVersionUID = 42L;

    public static final int DISPECERHL_NEW = 0;
    public static final int DISPECERHL_EDIT = 1;

    @EJB
    private ejb.LogFacade ejbLogFacade;

    @EJB
    private ejb.DispecerHlFacade ejbDispHlFacade;
    private Dispecerhl dispecerHl = null;
    private ArrayList<Dispecerhl> dispecerHlList = null;
    private ArrayList<Dispecerhl> dHlListDel = null;

    private boolean dispeceriAzastupci = false;
    private Dispecerhl zastupce = null;
    private ArrayList<Dispecerhl> zastListDel = null;

//    @EJB
//    private ejb.DispecerPolFacade ejbDispPolFacade;
    private Dispecerpol dispecerPol = null;
    private ArrayList<Dispecerpol> dPolListDel = null;

    @PostConstruct
    void initDAOdispecer() {
        System.out.println("initDAOdispecer()");
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
            refreshDispecerHlList();
        }
        return dispecerHlList;
    }

    public ArrayList<Dispecerhl> refreshDispecerHlList() {
        this.dispecerHlList = new ArrayList<>(this.ejbDispHlFacade.findAllDispAZast(this.dispeceriAzastupci));
        this.dHlListDel = new ArrayList<>();
        this.dPolListDel = new ArrayList<>();
        this.zastListDel = new ArrayList<>();
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

//-----------------------
// Cast PERSISTENCE pro dispecera
//--------------------------    
    /**
     * Metoda vztvori noveho dispecera
     */
    public void newDispecerHl() {
        this.dispecerHl = new Dispecerhl();
        this.dispecerHl.setNewEntity(true);
        this.dispecerHl.setPlatiod(JsfUtil.startDate());
        this.dispecerHl.setPlatido(JsfUtil.endDate());
        this.dispecerHl.setZastupciList(new ArrayList<Dispecerhl>());
        this.dispecerHl.setDispecerPolList(new ArrayList<Dispecerpol>());
    }

    public void fillDispecerHl(int mod) {
        if (mod == DAOdispecer.DISPECERHL_NEW) {
            newDispecerHl();
        } else {
// !!!!!! Naplnit vsechny matice
        }
    }

    public void dispHlSave() {
        this.ejbDispHlFacade.saveDispecerHl(this.dispecerHl, this.dHlListDel, this.dPolListDel);
        this.dispecerHl = this.ejbDispHlFacade.find(this.dispecerHl.getId());
        this.dispecerHl.setNewEntity(false);
        this.dHlListDel = new ArrayList<>();
        this.dPolListDel = new ArrayList<>();

//        if (this.getDispecerHl().isNewEntity()) {
//            dispHlPersist(JsfUtil.PersistAction.CREATE);
//        } else {
//            dispHlPersist(JsfUtil.PersistAction.UPDATE);
//        }
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
        ArrayList<Dispecerpol> dispPolList = new ArrayList<>(this.dispecerHl.getDispecerPolList());
        for (Dispecerpol dPol : dispPolList) {
            if (dPol.getIdoso() == null) {
                this.dispecerHl.getDispecerPolList().remove(dPol);
            }
        }
        if (this.getDispecerHl() != null) {
            switch (persistAction) {
                case CREATE:
                    this.getDispecerHl().setNewEntity(false);
                    this.ejbDispHlFacade.create(this.dispecerHl);
                    this.dispecerHlList.add(this.dispecerHl);
                    ejbLogFacade.log(this.dispecerHl.getId(), "dispecerhl", "Add dispečer: " + this.dispecerHl.getIdoso().getPopis());
                    break;
                case UPDATE:
                    this.ejbDispHlFacade.edit(this.dispecerHl);
                    ejbLogFacade.log(this.dispecerHl.getId(), "dispecerhl", "Modify dispečer: " + this.dispecerHl.getIdoso().getPopis());
                    break;
                case DELETE:
                    this.ejbDispHlFacade.remove(this.dispecerHl);
                    ejbLogFacade.log(this.dispecerHl.getId(), "dispecerhl", "Del dispečer: " + this.dispecerHl.getIdoso().getPopis());
                    this.dispecerHl = null;
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
        this.zastupce.setPlatiod(dispecerHl.getPlatiod());
        this.zastupce.setPlatido(dispecerHl.getPlatido());
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
                    this.ejbDispHlFacade.create(zast);
                } else {
                    this.ejbDispHlFacade.edit(zast);
                }
            }
        }
    }

//----------------------------------------------
// Cast PERSISTENCE pro polozky dispecera
//----------------------------------------------    
    public void dispPolNew() {
        this.dispecerPol = new Dispecerpol();
        // Dosadit dispecera, který bude mít polozku na starosti
        this.dispecerPol.setIddisphl(dispecerHl);
        this.dispecerPol.setNewEntity(true);
        this.dispecerPol.setPlatiod(dispecerHl.getPlatiod());
        this.dispecerPol.setPlatido(dispecerHl.getPlatido());
        this.dispecerHl.getDispecerPolList().add(this.dispecerPol);
    }

    public void dispPolDel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addDispPolOso(ArrayList<Osoba> osoby) {
        Dispecerpol dispPol;
        for (Osoba osoba : osoby) {
            dispPol = new Dispecerpol();
            dispPol.setNewEntity(true);
            dispPol.setIddisphl(this.dispecerHl);
            dispPol.setIdoso(osoba);
            dispPol.setPopis(osoba.getPopis());
            dispPol.setPlatiod(this.dispecerHl.getPlatiod());
            dispPol.setPlatido(this.dispecerHl.getPlatido());
            this.dispecerHl.getDispecerPolList().add(dispPol);
        }
    }

    public void addDispPolZdr(ArrayList<Zdroj> zdroje) {
        Dispecerpol dispPol;
        //TODO: Dodelat defaultni typ ucastnika
        for (Zdroj zdroj : zdroje) {
            dispPol = new Dispecerpol();
            dispPol.setNewEntity(true);
            dispPol.setIddisphl(this.dispecerHl);
            dispPol.setIdzdr(zdroj);
            dispPol.setPopis(zdroj.getPopis());
            dispPol.setPlatiod(this.dispecerHl.getPlatiod());
            dispPol.setPlatido(this.dispecerHl.getPlatido());
            this.dispecerHl.getDispecerPolList().add(dispPol);
        }
    }

    public void addZastupce(ArrayList<Osoba> osoby) {
        Dispecerhl zastupce;
        for (Osoba osoba : osoby) {
            zastupce = new Dispecerhl();
            zastupce.setNewEntity(true);
            // Dosadit dispecera, jehoz bude zaznam zastupcem
            zastupce.setIddisphl(dispecerHl);
            zastupce.setIdtypschv(dispecerHl.getIdtypschv());
            zastupce.setIdtypzdr(dispecerHl.getIdtypzdr());
            zastupce.setIdoso(osoba);
            zastupce.setPlatiod(dispecerHl.getPlatiod());
            zastupce.setPlatido(dispecerHl.getPlatido());
            this.dispecerHl.getZastupciList().add(zastupce);
        }
    }

}
