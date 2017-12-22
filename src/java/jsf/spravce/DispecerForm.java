/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.spravce;

import ejb.DAOdispecer;
import entity.Osoba;
import entity.Typschv;
import entity.Typzdroje;
import entity.Zdroj;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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

    @Inject
    DAOdispecer daoDispecer;

    @EJB
    private ejb.TypZdrFacade ejbTypZdrFacade;
    @EJB
    private ejb.TypSchvFacade ejbTypSchvFacade;
    @EJB
    private ejb.OsobaFacade ejbOsobaFacade;
    @EJB
    private ejb.ZdrojeFacade ejbZdrojeFacade;

    private ArrayList<Typzdroje> typZdrList = null;
    private ArrayList<Typschv> typSchvList = null;
    private ArrayList<Osoba> osobaList = null;
    private ArrayList<Zdroj> zdrojList = null;

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
        return this.typSchvList;
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
            this.osobaList = new ArrayList<>(ejbOsobaFacade.findAlltByName());
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
     * @return the zdrojeList
     */
    public ArrayList<Zdroj> getZdrojList() {
        if (this.zdrojList == null) {
            this.zdrojList = new ArrayList<>(ejbZdrojeFacade.findAll());
        }
        return this.zdrojList;
    }

    /**
     * @param zdrojList
     */
    public void setZdrojList(ArrayList<Zdroj> zdrojList) {
        this.zdrojList = zdrojList;
    }

    /**
     * Kontrola vazby platiOd > platiDo
     */
    public void platiOdListener() {
        if (this.daoDispecer.getDispecerHl().getPlatiod().after(this.daoDispecer.getDispecerHl().getPlatido())) {
            this.daoDispecer.getDispecerHl().setPlatido(this.daoDispecer.getDispecerHl().getPlatiod());
        }
    }

    /**
     * Kontrola vazby platiDo > platiOd
     */
    public void platiDoListener() {
        if (this.daoDispecer.getDispecerHl().getPlatiod().after(this.daoDispecer.getDispecerHl().getPlatido())) {
            this.daoDispecer.getDispecerHl().setPlatiod(this.daoDispecer.getDispecerHl().getPlatido());
        }
    }
// Zastupce

    /**
     * Metody pro zastupce
     *
     * @param param
     * @return
     */
    public boolean isButtonZastEnabled(String param) {
        switch (param) {
            case "new":
                return true;
            case "edit":
                return this.daoDispecer.getZastupce() != null;
            case "delete":
                return this.daoDispecer.getZastupce() != null;
        }
        return true;
    }

    /**
     * Generovani noveho zastupce dispecera
     *
     * @return
     */
    public String zastupceNew() {
        String zastupci = null;
        this.daoDispecer.zastNew();
        return zastupci;
    }

    /**
     * Nepouziva se
     *
     * @return
     */
    public String zastupceEdit() {
        String zastupci = null;
        return zastupci;
    }

    /**
     * Vymazani zastupce ze seznamu !!!POZOR!!! dodelat kontrolu na jiz pouzity
     * zaznam - ten nelze smazat- uz se s nim pracovalo
     *
     * @return
     */
    public String zastupceDel() {
        try {
            this.daoDispecer.zastDel();
            JsfUtil.addSuccessMessage("Záznam byl úspěšně smazán.");
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
            JsfUtil.validationFailed();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex, "Chyba zpracování");
            JsfUtil.validationFailed();
        }
        return null;
    }

    /**
     * Ulozit cely formular
     * ActionEvent event
     * @return
     */
    public String save() {
        if (this.daoDispecer.getDispecerHl().getIdoso() == null
                || this.daoDispecer.getDispecerHl().getIdtypschv() == null
                || this.daoDispecer.getDispecerHl().getIdtypzdr() == null) {
            return null;
        }
        try {
            this.daoDispecer.dispHlSave();
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
            this.daoDispecer.dispHlReset();
            JsfUtil.validationFailed();

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex, "Chyba zpracování");
            this.daoDispecer.dispHlReset();
            JsfUtil.validationFailed();
        }
        return "/spravce/dispeceri";
    }

    /**
     * Akce na situaci, kdy uzivatel neulozi zaznam
     *
     * @return
     */
    public String cancel() {
        this.daoDispecer.dispHlReset();
        return "/spravce/dispeceri";
    }

// Polozky podrizene dispecerovi (osoby nebo zdroje)
    public boolean isGridDispPolOso() {
        boolean isOso = false;
        if (this.daoDispecer.getDispecerHl() != null) {
            if (this.daoDispecer.getDispecerHl().getIdtypzdr() != null) {
                if (this.daoDispecer.getDispecerHl().getIdtypzdr().getOsoba() != null) {
                    isOso = (this.daoDispecer.getDispecerHl().getIdtypzdr().getOsoba() == 0);
                }
            }
        }
        return isOso;
    }

    public boolean isGridDispPolZdr() {
        boolean isZdr = true;
        if (this.daoDispecer.getDispecerHl() != null) {
            if (this.daoDispecer.getDispecerHl().getIdtypzdr() != null) {
                if (this.daoDispecer.getDispecerHl().getIdtypzdr().getOsoba() != null) {
                    isZdr = (this.daoDispecer.getDispecerHl().getIdtypzdr().getOsoba() != 0);
                }
            }
        }
        return isZdr;
    }
// Osoby
    /**
     * Kontrola dostupnosti tlacitek
     *
     * @param param
     * @return
     */
    public boolean isButtonDispOsoEnabled(String param) {
        switch (param) {
            case "new":
                return true;
            case "edit":
                return this.daoDispecer.getDispecerOso() != null;
            case "delete":
                return this.daoDispecer.getDispecerOso() != null;
        }
        return true;
    }

    /**
     * Generovani nove polozky spravovane dispecerem
     *
     * @return dispPol
     */
    public String dispOsoNew() {
        String dispOso = null;
        this.daoDispecer.dispOsoNew();
        return dispOso;
    }

    /**
     * Nepouziva se
     *
     * @return
     */
    public String dispOsoEdit() {
        String dispOso = null;
        return dispOso;
    }

    /**
     * Smaze zaznam o spravovane polozce dispecerem !!!POZOR!!! dodelat kontrolu
     * na jiz pouzity zaznam - ten nelze smazat- uz se s nim pracovalo
     *
     * @return
     */
    public String dispPolDel() {
        try {
            this.daoDispecer.dispOsoDel();
            JsfUtil.addSuccessMessage("Záznam byl úspěšně smazán.");
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
            JsfUtil.validationFailed();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex, "Chyba zpracování");
            JsfUtil.validationFailed();
        }
        return null;
    }

// Zdroje


    /**
     * Kontrola dostupnosti tlacitek
     *
     * @param param
     * @return
     */
    public boolean isButtonDispZdrEnabled(String param) {
        switch (param) {
            case "new":
                return true;
            case "edit":
                return this.daoDispecer.getDispecerZdr() != null;
            case "delete":
                return this.daoDispecer.getDispecerZdr() != null;
        }
        return true;
    }

    /**
     * Generovani nove polozky spravovane dispecerem
     *
     * @return dispPol
     */
    public String dispZdrNew() {
        String dispZdr = null;
        this.daoDispecer.dispZdrNew();
        return dispZdr;
    }

    /**
     * Nepouziva se
     *
     * @return
     */
    public String dispZdrEdit() {
        String dispZdr = null;
        return dispZdr;
    }

    /**
     * Smaze zaznam o spravovane polozce dispecerem !!!POZOR!!! dodelat kontrolu
     * na jiz pouzity zaznam - ten nelze smazat- uz se s nim pracovalo
     *
     * @return
     */
    public String dispZdrDel() {
        try {
            this.daoDispecer.dispZdrDel();
            JsfUtil.addSuccessMessage("Záznam byl úspěšně smazán.");
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
            JsfUtil.validationFailed();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex, "Chyba zpracování");
            JsfUtil.validationFailed();
        }
        return null;
    }    
}
