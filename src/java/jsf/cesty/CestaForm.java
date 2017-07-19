/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.cesty;

import ejb.LoginUser;
import entity.Cesta;
import entity.Osoba;
import entity.Rezervace;
import entity.Schvaleni;
import entity.Typucast;
import entity.Typzdroje;
import entity.Ucastnik;
import entity.Zdroj;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jsf.helper.HelperOsoba;
import jsf.helper.HelperOsobyListener;
import jsf.helper.HelperZdroj;
import jsf.helper.HelperZdrojListener;

/**
 *
 * @author Ivo
 */
@Named("cestaForm")
@SessionScoped
public class CestaForm implements Serializable, HelperOsobyListener, HelperZdrojListener {

    private static final int MODE_NEW = 0;
    private static final int MODE_EDIT = 1;
    private static final int MODE_DELETE = 2;

    @EJB
    private ejb.CestaFacade ejbCestaFacade;
    @EJB
    private ejb.TypZdrFacade ejbTypZdrFacade;
    @EJB
    private ejb.TypUcastFacade ejbTypUcastFacade;
    @EJB
    private ejb.RezervaceFacade ejbRezervaceFacade;
    @EJB
    private ejb.UcastnikFacade ejbUcastnikFacade;
    @EJB
    private ejb.AktivityFacade ejbAktivityFacade;
    @Inject
    private HelperOsoba helperOsoba;
    @Inject
    private HelperZdroj helperZdroj;
    @Inject
    LoginUser loginUser;

    private Calendar cal = Calendar.getInstance(Locale.getDefault());
    private Date platiOd = new Date();
    private Date platiDo = new Date();

    private int mode;
    private Cesta cesta = new Cesta();
    private ArrayList<Typzdroje> typZdrList = null;
    private ArrayList<Typucast> typUcastList = null;
    private ArrayList<Ucastnik> ucastnikList = null;
    private Ucastnik ucastnik = null;
    private ArrayList<Rezervace> rezervaceList = null;
    private Rezervace rezervace = null;

    @PostConstruct
    void init() {
        loginUser.initLoginUser();
        this.helperOsoba.addHelperOsobyListener(this);
        prepareHelperZdroj();
        helperZdroj.addHelperZdrojListener(this);
    }

    @PreDestroy
    void destroy() {
        this.helperOsoba.removeHelperOsobyListener(this);
        helperZdroj.removeHelperZdrojListener(this);
    }

    public CestaForm() {
        this.mode = CestaForm.MODE_NEW;
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
     * @return the ejbRezervaceFacade
     */
    public ejb.RezervaceFacade getEjbRezervaceFacade() {
        return ejbRezervaceFacade;
    }

    /**
     * @param ejbRezervaceFacade the ejbRezervaceFacade to set
     */
    public void setEjbRezervaceFacade(ejb.RezervaceFacade ejbRezervaceFacade) {
        this.ejbRezervaceFacade = ejbRezervaceFacade;
    }

    /**
     * @return the ejbUcastnikFacade
     */
    public ejb.UcastnikFacade getEjbUcastnikFacade() {
        return ejbUcastnikFacade;
    }

    /**
     * @param ejbUcastnikFacade the ejbUcastnikFacade to set
     */
    public void setEjbUcastnikFacade(ejb.UcastnikFacade ejbUcastnikFacade) {
        this.ejbUcastnikFacade = ejbUcastnikFacade;
    }

    /**
     * @return the ejbTypZdrFacade
     */
    public ejb.TypZdrFacade getEjbTypZdrFacade() {
        return ejbTypZdrFacade;
    }

    /**
     * @param ejbTypZdrFacade the ejbTypZdrFacade to set
     */
    public void setEjbTypZdrFacade(ejb.TypZdrFacade ejbTypZdrFacade) {
        this.ejbTypZdrFacade = ejbTypZdrFacade;
    }

    /**
     * @return the ejbTypUcastFacade
     */
    public ejb.TypUcastFacade getEjbTypUcastFacade() {
        return ejbTypUcastFacade;
    }

    /**
     * @param ejbTypUcastFacade the ejbTypUcastFacade to set
     */
    public void setEjbTypUcastFacade(ejb.TypUcastFacade ejbTypUcastFacade) {
        this.ejbTypUcastFacade = ejbTypUcastFacade;
    }

    /**
     * @return the ejbAktivityFacade
     */
    public ejb.AktivityFacade getEjbAktivityFacade() {
        return ejbAktivityFacade;
    }

    /**
     * @param ejbAktivityFacade the ejbAktivityFacade to set
     */
    public void setEjbAktivityFacade(ejb.AktivityFacade ejbAktivityFacade) {
        this.ejbAktivityFacade = ejbAktivityFacade;
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

    /**
     * @return the mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(int mode) {
        this.mode = mode;
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
     * @return the typZdrList
     */
    public ArrayList<Typzdroje> getTypZdrList() {
        if (this.typZdrList == null) {
            this.typZdrList = new ArrayList<>(getEjbTypZdrFacade().findAll());
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
     * @return the typUcastList
     */
    public ArrayList<Typucast> getTypUcastList() {
        if (this.typUcastList == null) {
            this.typUcastList = new ArrayList<>(getEjbTypUcastFacade().findAll());
        }
        return typUcastList;
    }

    /**
     * @param typUcastList the typUcastList to set
     */
    public void setTypUcastList(ArrayList<Typucast> typUcastList) {
        this.typUcastList = typUcastList;
    }

    /**
     * @return the ucastnik
     */
    public Ucastnik getUcastnik() {
        return ucastnik;
    }

    /**
     * @param ucastnik the ucastnik to set
     */
    public void setUcastnik(Ucastnik ucastnik) {
        this.ucastnik = ucastnik;
    }

    /**
     * @return the rezervace
     */
    public Rezervace getRezervace() {
        return rezervace;
    }

    /**
     * @param rezervace the rezervace to set
     */
    public void setRezervace(Rezervace rezervace) {
        this.rezervace = rezervace;
    }

    /**
     * @return the ucastnikList
     */
    public ArrayList<Ucastnik> getUcastnikList() {
        return ucastnikList;
    }

    /**
     * @param ucastnikList the ucastnikList to set
     */
    public void setUcastnikList(ArrayList<Ucastnik> ucastnikList) {
        this.ucastnikList = ucastnikList;
    }

    /**
     * @return the rezervaceList
     */
    public ArrayList<Rezervace> getRezervaceList() {
        return rezervaceList;
    }

    /**
     * @param rezervaceList the rezervaceList to set
     */
    public void setRezervaceList(ArrayList<Rezervace> rezervaceList) {
        this.rezervaceList = rezervaceList;
    }

// ====
// Zalozeni nove cesty
//=====
    public boolean newCesta() {
        this.mode = CestaForm.MODE_NEW;

        this.cesta = new Cesta();
        ucastnikList = new ArrayList<>();
        rezervaceList = new ArrayList<>();

        this.cesta.setNewEntity(true);
        this.cesta.setIdoso(loginUser.getOsoba());
        this.cesta.setUcastnikList(new ArrayList<Ucastnik>());
        this.cesta.setRezervaceList(new ArrayList<Rezervace>());
        this.cesta.setSchvaleniList(new ArrayList<Schvaleni>());
//        this.cesta.setZaloha(0.0);
        getCal().set(Calendar.HOUR_OF_DAY, 07);
        getCal().set(Calendar.MINUTE, 00);
        this.cesta.setPlatiod(getCal().getTime());
        getCal().set(Calendar.HOUR_OF_DAY, 17);
        getCal().set(Calendar.MINUTE, 00);
        this.cesta.setPlatido(getCal().getTime());
        this.cesta.setIdtypzdr(this.ejbTypZdrFacade.findPopis("Vozidlo služební"));

        // Defaultni ucastnik je tvurce cesty
        this.ucastnik = new Ucastnik();
        this.ucastnik.setNewEntity(true);
        this.ucastnik.setIdcest(this.cesta);
        this.ucastnik.setIdoso(this.cesta.getIdoso());
        this.ucastnik.setIdtypucast(this.ejbTypUcastFacade.findPopis("Spolujezdec"));
        this.ucastnikList.add(ucastnik);

        return true;
    }

    public boolean editCesta(Cesta cestaLocal) {
        this.setMode(CestaForm.MODE_EDIT);
        this.setCesta(ejbCestaFacade.find(cestaLocal.getId()));
        ucastnikList = new ArrayList<>();
        rezervaceList = new ArrayList<>();
        // Naplnit ucastniky
        for (Ucastnik ucastnikLocal : this.cesta.getUcastnikList()) {
            ucastnikList.add(ucastnikLocal);
        }
        // Naplnit rezervace
        for (Rezervace rezervaceLocal : this.cesta.getRezervaceList()) {
            rezervaceList.add(rezervaceLocal);
        }
        return true;
    }

    public String saveCesta() {
        if (this.cesta.isNewEntity()) {
            this.ejbCestaFacade.create(this.cesta);
        } else {
            this.ejbCestaFacade.edit(this.cesta);
        }
        for (Ucastnik ucast : this.ucastnikList) {
            if (ucast.isNewEntity()) {
                this.ejbUcastnikFacade.create(ucast);
            } else {
                this.ejbUcastnikFacade.edit(ucast);
            }
        }
        for (Rezervace reze : this.rezervaceList) {
            if (reze.isNewEntity()) {
                this.ejbRezervaceFacade.create(reze);
            } else {
                this.ejbRezervaceFacade.edit(reze);
            }
        }
        return "/cesty/cesty";
    }

// ====
// Helper pro vyber ucastnika
//=====
    public void prepareHelperOsoby() {
        System.out.println("prepareHelperOsoby()");
    }

    @Override
    public void actionHelperOsoby(ArrayList<Osoba> osoby) {
        Ucastnik ucastnikLocal;
        //TODO: Dodelat defaultni typ ucastnika
        Typucast idtypucast = this.ejbTypUcastFacade.findPopis("Spolujezdec");
        for (Osoba osoba : osoby) {
            ucastnikLocal = new Ucastnik();
            ucastnikLocal.setIdcest(this.cesta);
            ucastnikLocal.setIdoso(osoba);
            ucastnikLocal.setIdtypucast(idtypucast);
            ucastnikLocal.setPlatiod(this.platiOd);
            ucastnikLocal.setPlatido(this.platiDo);
            ucastnikLocal.setNewEntity(true);
            this.ucastnikList.add(ucastnikLocal);
        }
//        this.helperOsoba.removeHelperOsobyListener(this);
    }

    public void ucastnikDelete() {
        this.ucastnikList.remove(this.ucastnik);
    }

// ====
// Helper pro vyber zdroje k rezervaci
//=====
    public void prepareHelperZdroj() {
        helperZdroj.initHelperZdroj(loginUser.getOsoba(), this.platiOd, this.platiDo);
    }

    /**
     * @return the helperZdroj
     */
    public HelperZdroj getHelperZdroj() {
        return helperZdroj;
    }

    /**
     * @param helperZdroj the helperZdroj to set
     */
    public void setHelperZdroj(HelperZdroj helperZdroj) {
        this.helperZdroj = helperZdroj;
    }

    @Override
    public void actionHelperZdroj(Zdroj zdroj) {
        this.rezervace = new Rezervace();
        this.rezervace.setIdzdr(zdroj);
        this.rezervace.setIdcest(this.cesta);
        this.rezervace.setIdakt(ejbAktivityFacade.findAll().get(0));
        this.rezervace.setPlatiod(this.platiOd);
        this.rezervace.setPlatido(this.platiDo);
        this.rezervace.setNewEntity(true);
        this.rezervaceList.add(rezervace);
    }

    public String iconDispecer(Rezervace itemRez) {
        String iconFile = null;
        if (itemRez != null && itemRez instanceof Rezervace) {
            iconFile = "/images/Otaznik.png";
        }
        return iconFile;
    }

    public void rezervaceDelete() {
        this.rezervaceList.remove(this.rezervace);
    }

}
