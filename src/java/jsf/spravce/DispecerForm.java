/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.spravce;

import ejb.DAOdispecer;
import entity.Dispecerhl;
import entity.Dispecerpol;
import entity.Osoba;
import entity.Typschv;
import entity.Typzdroje;
import java.io.Serializable;
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
import javax.inject.Inject;
import javax.inject.Named;
import jsf.util.JsfUtil;

/**
 *
 * @author Ivo
 */
@Named("dispecerForm")
@SessionScoped
public class DispecerForm implements Serializable {

    private Calendar cal = Calendar.getInstance(Locale.getDefault());

    @Inject DAOdispecer daoDispecer;

    @EJB
    private ejb.TypZdrFacade ejbTypZdrFacade;
    @EJB
    private ejb.TypSchvFacade ejbTypSchvFacade;
    @EJB
    private ejb.OsobaFacade ejbOsobaFacade;

    private ArrayList<Typzdroje> typZdrList = null;
    private ArrayList<Typschv> typSchvList = null;
    private ArrayList<Osoba> osobaList = null;

    @PostConstruct
    void initDispecerForm() {
        System.out.println("initDispecerForm()");
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
        return this.daoDispecer.getDispecerHl();
    }

    /**
     * @param dispecerHl the dispecerHl to set
     */
    public void setDispecerHl(Dispecerhl dispecerHl) {
        this.daoDispecer.setDispecerHl(dispecerHl);
    }

    /**
     * @return the dispecerHlList
     */
    public List<Dispecerhl> getDispecerHlList() {
        return this.daoDispecer.getDispecerHlList();
    }

    /**
     * @param dispecerHlList the dispecerHlList to set
     */
    public void setDispecerHlList(ArrayList<Dispecerhl> dispecerHlList) {
        this.daoDispecer.setDispecerHlList(dispecerHlList);
    }

    public Dispecerhl getDispecerHl(Object id) {
        return this.daoDispecer.getEjbDispHlFacade().find(id);
    }

    /**
     * @return the dispecerPolList
     */
    public ArrayList<Dispecerpol> getDispecerPolList() {
        return this.daoDispecer.getDispecerPolList();
    }

    /**
     * @param dispecerPolList the dispecerPolList to set
     */
    public void setDispecerPolList(ArrayList<Dispecerpol> dispecerPolList) {
        this.daoDispecer.setDispecerPolList(dispecerPolList);
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
     * @return the typSchvList
     */
    public ArrayList<Typschv> getTypSchvList() {
        if (this.typSchvList == null) {
            this.typSchvList = new ArrayList<>(ejbTypSchvFacade.findAll());
        }
        return typSchvList;
    }

    /**
     * @param typSchvList the typSchvList to set
     */
    public void setTypSchvList(ArrayList<Typschv> typSchvList) {
        this.typSchvList = typSchvList;
    }

    /**
     * @return the osobaList
     */
    public ArrayList<Osoba> getOsobaList() {
        if (this.osobaList == null) {
            this.osobaList = new ArrayList<>(ejbOsobaFacade.findAllSortByName());
        }
        return osobaList;
    }

    /**
     * @param osobaList the osobaList to set
     */
    public void setOsobaList(ArrayList<Osoba> osobaList) {
        this.osobaList = osobaList;
    }

    public void platiOdListener() {
        if (this.daoDispecer.getDispecerHl().getPlatiod().after(this.daoDispecer.getDispecerHl().getPlatido())) {
            this.cal.setTime(this.daoDispecer.getDispecerHl().getPlatiod());
            if (cal.get(Calendar.HOUR_OF_DAY) < 17) {
                cal.set(Calendar.HOUR_OF_DAY, 17);
            }
            this.daoDispecer.getDispecerHl().setPlatido(this.cal.getTime());
        }
    }

    public void platiDoListener() {
    }

    /**
     * Uložení záznam dispecerHl do databáze - persistence
     */
    public void prepareCreate() {
        this.daoDispecer.newDispecerHl();
    }

    public String save() {
        try {
            this.daoDispecer.saveDispHl();
            JsfUtil.addSuccessMessage("Záznam byl úspěšně uložen.");
        } catch (EJBException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
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
            this.daoDispecer.resetDispecerHl();
            JsfUtil.validationFailed();

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex, "Chyba zpracování");
            this.daoDispecer.resetDispecerHl();
            JsfUtil.validationFailed();
        }
        return "/spravce/dispeceri";
    }

    public String cancel() {
        this.daoDispecer.resetDispecerHl();
        return "/spravce/dispeceri";
    }
}
