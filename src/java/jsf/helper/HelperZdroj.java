/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.helper;

import entity.Cesta;
import entity.Osoba;
import entity.Rezervace;
import entity.Zdroj;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
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
@Named("helperZdroj")
@SessionScoped
public class HelperZdroj implements Serializable {

    private Calendar cal = Calendar.getInstance(Locale.getDefault());

    @EJB
    private ejb.RezervaceFacade rezFacade;
    @EJB
    private ejb.ZdrojeFacade zdrojeFacade;
    private Zdroj selectedZdr = null;
    private ArrayList<Zdroj> zdrojList = null;
    private Rezervace selectedRez = null;
    private ArrayList<Rezervace> rezervaceList = new ArrayList<>();
    Osoba osoba;
    Date platiOd;
    Date platiDo;
    private ArrayList<HelperZdrojListener> helperZdrojListeners = new ArrayList<>();

    public void initHelperZdroj(Osoba osoba, Date platiOd, Date platiDo) {
        this.osoba = osoba;
        this.platiOd = platiOd;
        this.platiDo = platiDo;
    }

    /**
     * @return the rezFacade
     */
    public ejb.RezervaceFacade getRezFacade() {
        return rezFacade;
    }

    /**
     * @param rezFacade the rezFacade to set
     */
    public void setRezFacade(ejb.RezervaceFacade rezFacade) {
        this.rezFacade = rezFacade;
    }

    /**
     * @return the zdrojeFacade
     */
    public ejb.ZdrojeFacade getZdrojeFacade() {
        return zdrojeFacade;
    }

    /**
     * @param zdrojeFacade the zdrojeFacade to set
     */
    public void setZdrojeFacade(ejb.ZdrojeFacade zdrojeFacade) {
        this.zdrojeFacade = zdrojeFacade;
    }

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
     * @return the selectedZdr
     */
    public Zdroj getSelectedZdr() {
        return selectedZdr;
    }

    /**
     * @param selectedZdr the selectedZdr to set
     */
    public void setSelectedZdr(Zdroj selectedZdr) {
        this.selectedZdr = selectedZdr;
    }

    /**
     * @return the zdrojList
     */
    public ArrayList<Zdroj> getZdrojList() {
        if (zdrojList == null) {
            zdrojList = new ArrayList<>(zdrojeFacade.findAll());
        }
        return zdrojList;
    }

    /**
     * @param zdrojList the zdrojList to set
     */
    public void setZdrojList(ArrayList<Zdroj> zdrojList) {
        this.zdrojList = zdrojList;
    }

    /**
     * @return the selectedRez
     */
    public Rezervace getSelectedRez() {
        return selectedRez;
    }

    /**
     * @param selectedRez the selectedRez to set
     */
    public void setSelectedRez(Rezervace selectedRez) {
        this.selectedRez = selectedRez;
    }

    /**
     * @return the helperZdrojListeners
     */
    public ArrayList<HelperZdrojListener> getHelperZdrojListeners() {
        return helperZdrojListeners;
    }

    /**
     * @param helperZdrojListeners the helperZdrojListeners to set
     */
    public void setHelperZdrojListeners(ArrayList<HelperZdrojListener> helperZdrojListeners) {
        this.helperZdrojListeners = helperZdrojListeners;
    }

    public String getHRkapacita(Zdroj zdroj) {
        rezervaceList = new ArrayList<>();
        int pocetUcastniku = 0;
        for (Rezervace rez : zdroj.getRezervaceList()) {
            if (rez.getPlatiod().before(this.platiDo) && rez.getPlatido().after(this.platiOd)) {
                if (rez.getIdcest() != null && rez.getIdcest().getUcastnikList() != null) {
                    pocetUcastniku += rez.getIdcest().getUcastnikList().size();
                }
                rezervaceList.add(rez);
            }
        }
//        return String.format("%1$2d/%2$2d", pocetUcastniku, zdroj.getKapacita());
        return String.format("%1$2d",zdroj.getKapacita());
    }

    public String getHRlabel(Zdroj zdroj) {
        StringBuilder sb = new StringBuilder("");
        rezervaceList = new ArrayList<>();
        Cesta cesta;
        for (Rezervace rez : zdroj.getRezervaceList()) {
            cesta=rez.getIdcest();
            if (rez.getPlatiod().before(this.platiDo) && rez.getPlatido().after(this.platiOd) && rez.getIdcest()!= null) {
                sb.append(rez.getIdcest().getPopis().substring(0, Math.min(rez.getIdcest().getPopis().length(), 20)));
                sb.append("\n");
                rezervaceList.add(rez);
            }
        }
        if (sb.length() == 0) {
            sb.append("  ");
        }
        return sb.toString();
    }

    
    
    
        // Posluchaci jednoho vyberu osoby
    public void addHelperZdrojListener(HelperZdrojListener helperZdrojListener) {
        this.helperZdrojListeners.add(helperZdrojListener);
    }
    public void removeHelperZdrojListener(HelperZdrojListener helperZdrojListener) {
        this.helperZdrojListeners.remove(helperZdrojListener);
    }

    /**
     */
    public void fireHelperZdroj() {
        System.out.println("this.Zdroj =" + this.selectedZdr);
        for (HelperZdrojListener helperZdrojListener : helperZdrojListeners) {
            helperZdrojListener.actionHelperZdroj(this.selectedZdr);
        }

    }

    
    
    
}
