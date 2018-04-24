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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jsf.helper.HelperOsoba;
import jsf.helper.HelperZdroj;
import jsf.util.JsfUtil;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Ivo
 */
@Named("dispecerForm")
@SessionScoped
public class DispecerForm implements Serializable {

    private static final long serialVersionUID = 1L;
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

    @Inject
    HelperOsoba helperOsoba;
    @Inject
    HelperZdroj helperZdroj;

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

    /**
     * Ulozit cely formular ActionEvent event
     *
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
            if (msg != null && msg.length() > 0) {
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
    public boolean isGridDispOso() {
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

    public boolean isGridDispZdr() {
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

    /**
     * Metoda nachysta promenne pro vykresleni okna helperu
     *
     * @return
     */
    public Map<String, Object> getDialogOptions() {
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("resizable", true);
        options.put("maximizable", true);
        options.put("draggable", true);
        options.put("height", "670");
        options.put("contentHeight", "700");
        options.put("closeOnEscape", true);
        return options;
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
     */
    public void zastupceNew() {
        // Predplnit platnymi daty a vyloucit osobu dispecera (nemuze byt sam sobe zastupcem)
        helperOsoba.initHelperOsoby(daoDispecer.getDispecerHl().getPlatiod(), daoDispecer.getDispecerHl().getPlatido(), daoDispecer.getDispecerHl().getIdoso());
        RequestContext.getCurrentInstance()
                .openDialog("/helper/helperOsoba", getDialogOptions(), null);
    }

    public void addZastupce(SelectEvent selectEvent) {
        ArrayList<Osoba> osoby = (ArrayList<Osoba>) selectEvent.getObject();
        if (osoby == null) {
            return;
        }
        this.daoDispecer.addZastupce(osoby);
    }

// Osoby
    /**
     * Kontrola dostupnosti tlacitek
     *
     * @param param
     * @return
     */
    public boolean isButtonDispPolOsoEnabled(String param) {
        switch (param) {
            case "new":
                return true;
            case "edit":
                return this.daoDispecer.getDispecerPol() != null;
            case "delete":
                return this.daoDispecer.getDispecerPol() != null;
        }
        return true;
    }

    /**
     * Generovani nove polozky spravovane dispecerem
     *
     */
    public void dispPolOsoNew() {
        // Predplnit HelperOsoba platnymi daty a vyloucit osobu dispecera, nemuze sam sobe delat dispecink
        helperOsoba.initHelperOsoby(daoDispecer.getDispecerHl().getPlatiod(), daoDispecer.getDispecerHl().getPlatido(), daoDispecer.getDispecerHl().getIdoso());
        RequestContext.getCurrentInstance()
                .openDialog("/helper/helperOsoba", getDialogOptions(), null);
    }

    /**
     * Metoda naplni seznam osob z helperu
     *
     * @param selectEvent - udalost, ktera nese navratovou hodnotu z Helperu
     */
    public void addDispPolOso(SelectEvent selectEvent) {
        ArrayList<Osoba> osoby = (ArrayList<Osoba>) selectEvent.getObject();
        if (osoby == null) {
            return;
        }
        this.daoDispecer.addDispPolOso(osoby);
    }

// Zdroje
    /**
     * Kontrola dostupnosti tlacitek
     *
     * @param param
     * @return
     */
    public boolean isButtonDispPolZdrEnabled(String param) {
        switch (param) {
            case "new":
                return true;
            case "edit":
                return this.daoDispecer.getDispecerPol() != null;
            case "delete":
                return this.daoDispecer.getDispecerPol() != null;
        }
        return true;
    }

    /**
     * Generovani nove polozky spravovane dispecerem
     *
     */
    public void dispPolZdrNew() {
        helperZdroj.initHelperZdroj(daoDispecer.getDispecerHl().getPlatiod(), daoDispecer.getDispecerHl().getPlatido(), daoDispecer.getDispecerHl().getIdtypzdr());
        RequestContext.getCurrentInstance()
                .openDialog("/helper/helperZdroje", getDialogOptions(), null);
    }

    /**
     * Metoda naplni seznam osob z helperu
     *
     * @param selectEvent - udalost, ktera nese navratovou hodnotu z Helperu
     */
    public void addDispPolZdr(SelectEvent selectEvent) {
        ArrayList<Zdroj> zdroje = (ArrayList<Zdroj>) selectEvent.getObject();
        if (zdroje == null) {
            return;
        }
        this.daoDispecer.addDispPolZdr(zdroje);
    }
}
