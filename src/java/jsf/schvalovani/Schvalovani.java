/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.schvalovani;

import ejb.LoginUser;
import entity.Cesta;
import entity.Osoba;
import entity.Rezervace;
import entity.Ucastnik;
import entity.Zdroj;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Ivo
 */
@Named("schvalovani")
@SessionScoped
public class Schvalovani implements Serializable {

    private Calendar cal = Calendar.getInstance(Locale.getDefault());
    private Date platiOd = new Date();
    private Date platiDo = new Date();
    private boolean vedouci = true;
    private boolean zastupce = false;
    private boolean schvalene = false;
    private boolean neschvalene = false;
    private boolean platiOdDo = false;

    @EJB
    private ejb.CestaFacade ejbCestaFacade;
    @Inject
    LoginUser loginUser;
    Osoba osoba = null;

    private Cesta cesta = null;
    private ArrayList<Cesta> cesty = new ArrayList<>();

    @PostConstruct
    void init() {
        loginUser.initLoginUser();
        this.osoba = loginUser.getOsoba();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    /**
     * @return the platiOd
     */
    public Date getPlatiOd() {
        return platiOd;
    }

    /**
     * @param platiOd the platiOd to set
     */
    public void setPlatiOd(Date platiOd) {
        this.platiOd = platiOd;
    }

    /**
     * @return the platiDo
     */
    public Date getPlatiDo() {
        return platiDo;
    }

    /**
     * @param platiDo the platiDo to set
     */
    public void setPlatiDo(Date platiDo) {
        this.platiDo = platiDo;
    }

    public void onPlatiOdSelect() {
        if (this.platiOd.after(this.platiDo)) {
            this.platiDo.setTime(platiOd.getTime());
        }
    }

    public void onPlatiDoSelect() {
    }

    /**
     * @return the ejbCestaFacade
     */
    public ejb.CestaFacade getEjbCestaFacade() {
        return ejbCestaFacade;
    }

    /**
     * @param ejbCestaFacade the ejbCestaFacade to set
     */
    public void setEjbCestaFacade(ejb.CestaFacade ejbCestaFacade) {
        this.ejbCestaFacade = ejbCestaFacade;
    }

    /**
     * @return the cesta
     */
    public Cesta getCesta() {
        return cesta;
    }

    /**
     * @param cesta the cesta to set
     */
    public void setCesta(Cesta cesta) {
        this.cesta = cesta;
    }

    /**
     * @return the cesty
     */
    public ArrayList<Cesta> getCesty() {
        this.cesty = new ArrayList<>(ejbCestaFacade.findOsoba(this.osoba));
//        if (this.cesty.isEmpty()) {
//        }
        return cesty;
    }

    /**
     * @param cesty the cesty to set
     */
    public void setCesty(ArrayList<Cesta> cesty) {
        this.cesty = cesty;
    }

    class Schv {
        Cesta cesta=null;
        ArrayList<Ucastnik> ucastnici= new ArrayList<Ucastnik>();
        ArrayList<Zdroj> zdroje = new ArrayList<Zdroj>();
        ArrayList<Rezervace> rezervace = new ArrayList<Rezervace>();
    }
}
