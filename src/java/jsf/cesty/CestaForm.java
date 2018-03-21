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
import entity.Typucast;
import entity.Typzdroje;
import entity.Ucastnik;
import entity.Zdroj;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jsf.helper.HelperOsoba;
import jsf.helper.HelperZdroj;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Ivo
 */
@Named("cestaForm")
@SessionScoped
public class CestaForm implements Serializable {

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
    HelperOsoba helperOsoba;
    @Inject
    HelperZdroj helperZdroj;
    @Inject
    LoginUser loginUser;

    private Calendar cal = Calendar.getInstance(Locale.getDefault());

    private int mode;

    private Cesta cesta = new Cesta();
    private ArrayList<Cesta> cesty;
    private ArrayList<Typzdroje> typZdrList = null;
    private ArrayList<Typucast> typUcastList = null;
    private Ucastnik ucastnik = null;
    private ArrayList<Ucastnik> ucastnikListDel = null;
    private Rezervace rezervace = null;
    private ArrayList<Rezervace> rezervaceListDel = null;

    @PostConstruct
    void init() {
        loginUser.initLoginUser();
    }

    public CestaForm() {
    }

// -----  Lokalni promenne ------------
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
     * @return the typUcastList
     */
    public ArrayList<Typucast> getTypUcastList() {
        if (this.typUcastList == null) {
            this.typUcastList = new ArrayList<>(ejbTypUcastFacade.findAll());
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

    public void platiOdListener() {
        if (this.getCesta().getPlatiod().after(this.getCesta().getPlatido())) {
            this.cal.setTime(this.getCesta().getPlatiod());
            if (cal.get(Calendar.HOUR_OF_DAY) < 17) {
                cal.set(Calendar.HOUR_OF_DAY, 17);
            }
            this.getCesta().setPlatido(this.cal.getTime());
        }
    }

    public void platiDoListener() {
    }

// ====
// Zalozeni nove cesty
//=====
    public boolean newCesta(ArrayList<Cesta> cestyParam) {
        this.cesty = cestyParam;
        this.cesta = new Cesta();
        this.cesta.setNewEntity(true);
        this.cesta.setIdoso(loginUser.getOsoba());
        this.cesta.setUcastnikList((List) new ArrayList<>());
        this.cesta.setRezervaceList((List) new ArrayList<>());
        this.cesta.setLogList((List) new ArrayList<>());
        this.cesta.setEmailList((List) new ArrayList<>());

        ucastnikListDel = new ArrayList<>();
        rezervaceListDel = new ArrayList<>();

        getCal().set(Calendar.HOUR_OF_DAY, 07);
        getCal().set(Calendar.MINUTE, 00);
        this.getCesta().setPlatiod(getCal().getTime());
        getCal().set(Calendar.HOUR_OF_DAY, 17);
        getCal().set(Calendar.MINUTE, 00);
        this.getCesta().setPlatido(getCal().getTime());
        this.getCesta().setIdtypzdr(this.ejbTypZdrFacade.findPopis("Vozidlo služební"));

        // Defaultni ucastnik je tvurce cesty
        this.ucastnik = new Ucastnik();
        this.ucastnik.setNewEntity(true);
        this.ucastnik.setIdcest(this.getCesta());
        this.ucastnik.setIdoso(loginUser.getOsoba());
        this.ucastnik.setIdtypucast(this.ejbTypUcastFacade.findPopis("Spolujezdec"));
        this.cesta.getUcastnikList().add(ucastnik);

        return true;
    }

    public boolean editCesta(Cesta cestaParam, ArrayList<Cesta> cestyParam) {
        this.cesta = cestaParam;
        this.cesty = cestyParam;
        ucastnikListDel = new ArrayList<>();
        rezervaceListDel = new ArrayList<>();
        this.getCesta().setUcastnikList(new ArrayList<>(ejbUcastnikFacade.findUcastnikyWhereCesta(this.getCesta())));
        this.getCesta().setRezervaceList(new ArrayList<>(ejbRezervaceFacade.findRezervaceWhereCesta(this.getCesta())));

        return true;
    }

    public String saveCesta() {
        boolean newCesta = this.cesta.isNewEntity();
        ejbCestaFacade.saveCesta(this.cesta, ucastnikListDel, rezervaceListDel);
        this.cesta = ejbCestaFacade.find(this.cesta.getId());
        if (newCesta) {
            this.cesty.add(this.cesta);
        } else {
            for (Cesta cestal : this.cesty) {
                if (this.cesta.equals(cestal)) {
                    cestal = this.cesta;
                    break;
                }
            }
        }

//        try {
//            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
//            transaction.begin();
//            transaction.commit();
//        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException | NotSupportedException | NamingException ex) {
//            Logger.getLogger(CestaForm.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return "/cesty/cesty";
    }

// ====
// Helper pro vyber ucastnika
//=====
    public void newUcastnici() {
        helperOsoba.initHelperOsoby(this.cesta);
        RequestContext.getCurrentInstance()
                .openDialog("/helper/helperOsoba", getDialogOptions(), null);
    }

    public void addUcastnici(SelectEvent selectEvent) {
        ArrayList<Osoba> osoby = (ArrayList<Osoba>) selectEvent.getObject();
        if (osoby == null) {
            return;
        }
        Ucastnik ucastnikLocal;
        //TODO: Dodelat defaultni typ ucastnika
        Typucast idtypucast = this.ejbTypUcastFacade.findPopis("Spolujezdec");
        for (Osoba osoba : osoby) {
            ucastnikLocal = new Ucastnik();
            ucastnikLocal.setIdcest(this.getCesta());
            ucastnikLocal.setIdoso(osoba);
            ucastnikLocal.setIdtypucast(idtypucast);
            ucastnikLocal.setPlatiod(this.getCesta().getPlatiod());
            ucastnikLocal.setPlatido(this.getCesta().getPlatido());
            ucastnikLocal.setNewEntity(true);
            this.getCesta().getUcastnikList().add(ucastnikLocal);
        }
    }

    public void ucastnikDelete() {
        if (!this.ucastnik.isNewEntity()) {
            this.ucastnik.setDelEntity(true);
            this.ucastnikListDel.add(this.ucastnik);
        }
        this.getCesta().getUcastnikList().remove(this.ucastnik);
    }

// ====
// Helper pro vyber zdroje k rezervaci
//=====
    public void newRezervace() {
        helperZdroj.initHelperZdroj(this.cesta);
        RequestContext.getCurrentInstance()
                .openDialog("/helper/helperZdroj", getDialogOptions(), null);
    }

    public void addRezervace(SelectEvent selectEvent) {
        Zdroj zdroj = (Zdroj) selectEvent.getObject();
        if (zdroj == null) {
            return;
        }
        this.rezervace = new Rezervace();
        this.rezervace.setIdzdr(zdroj);
        this.rezervace.setIdcest(this.getCesta());
        this.rezervace.setIdakt(ejbAktivityFacade.findAll().get(0));
        this.rezervace.setPlatiod(this.getCesta().getPlatiod());
        this.rezervace.setPlatido(this.getCesta().getPlatido());
        this.rezervace.setNewEntity(true);
        this.getCesta().getRezervaceList().add(rezervace);
    }

    public String iconDispecer(Rezervace itemRez) {
        String iconFile = null;
        if (itemRez != null && itemRez instanceof Rezervace) {
            iconFile = "/images/Otaznik.png";
        }
        return iconFile;
    }

    public void rezervaceDelete() {
        if (!this.rezervace.isNewEntity()) {
            this.rezervace.setDelEntity(true);
            this.rezervaceListDel.add(this.rezervace);
        }
        this.getCesta().getRezervaceList().remove(this.rezervace);
    }

    public Map<String, Object> getDialogOptions() {
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("resizable", true);
        options.put("maximizable", true);
        options.put("draggable", true);
        options.put("height", "650");
        options.put("contentHeight", "700");
        options.put("closeOnEscape", true);
        return options;
    }
}
