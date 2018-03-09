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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import jsf.helper.HelperOsoba;
import jsf.helper.HelperOsobyListener;
import jsf.helper.HelperZdroj;
import jsf.helper.HelperZdrojListener;

/**
 *
 * @author Ivo
 */
@Named("cestaForm_Old")
@SessionScoped
public class CestaForm_Old implements Serializable, HelperOsobyListener, HelperZdrojListener {

    private static final int MODE_NEW = 0;
    private static final int MODE_EDIT = 1;
    private static final int MODE_DELETE = 2;

    @PersistenceContext(unitName = "AktivityPU")
    private EntityManager em;
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

    private int mode;

    private Cesta cesta = new Cesta();
    private CestaLocal cestaLocal = new CestaLocal();
    private ArrayList<Typzdroje> typZdrList = null;
    private ArrayList<Typucast> typUcastList = null;
    private Ucastnik ucastnik = null;
    private ArrayList<Ucastnik> ucastnikList = null;
    private ArrayList<Ucastnik> ucastnikListDel = null;
    private Rezervace rezervace = null;
    private ArrayList<Rezervace> rezervaceList = null;
    private ArrayList<Rezervace> rezervaceListDel = null;

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

    public CestaForm_Old() {
        this.mode = CestaForm_Old.MODE_NEW;
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
// -----  Lokalni promenne ------------

    /**
     * @return the platiOd
     */
    public Date getPlatiOd() {
        return cestaLocal.platiOd;
    }

    /**
     * @param platiOd the platiOd to set
     */
    public void setPlatiOd(Date platiOd) {
        this.cestaLocal.platiOd = platiOd;
    }

    /**
     * @return the platiDo
     */
    public Date getPlatiDo() {
        return cestaLocal.platiDo;
    }

    /**
     * @param platiDo the platiDo to set
     */
    public void setPlatiDo(Date platiDo) {
        this.cestaLocal.platiDo = platiDo;
    }

    public String getMisto() {
        return cestaLocal.misto;
    }

    public void setMisto(String misto) {
        this.cestaLocal.misto = misto;
    }

    public String getDuvod() {
        return cestaLocal.duvod;
    }

    public void setDuvod(String duvod) {
        this.cestaLocal.duvod = duvod;
    }

    public Typzdroje getTypZdroje() {
        return cestaLocal.typZdroje;
    }

    public void setTypZdroje(Typzdroje typZdroje) {
        this.cestaLocal.typZdroje = typZdroje;
    }

    public Double getZaloha() {
        return cestaLocal.zaloha;
    }

    public void setZaloha(Double zaloha) {
        this.cestaLocal.zaloha = zaloha;
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

    public void platiOdListener() {
        if (this.getPlatiOd().after(this.getPlatiDo())) {
            this.cal.setTime(this.getPlatiOd());
            if (cal.get(Calendar.HOUR_OF_DAY) < 17) {
                cal.set(Calendar.HOUR_OF_DAY, 17);
            }
            this.setPlatiDo(this.cal.getTime());
        }
    }

    public void platiDoListener() {
    }

// ====
// Zalozeni nove cesty
//=====
    public boolean newCesta() {
        this.mode = CestaForm_Old.MODE_NEW;

        this.cestaLocal = new CestaLocal();
        ucastnikList = new ArrayList<>();
        rezervaceList = new ArrayList<>();
        ucastnikListDel = new ArrayList<>();
        rezervaceListDel = new ArrayList<>();

        getCal().set(Calendar.HOUR_OF_DAY, 07);
        getCal().set(Calendar.MINUTE, 00);
        this.setPlatiOd(getCal().getTime());
        getCal().set(Calendar.HOUR_OF_DAY, 17);
        getCal().set(Calendar.MINUTE, 00);
        this.setPlatiDo(getCal().getTime());
        this.setTypZdroje(this.ejbTypZdrFacade.findPopis("Vozidlo služební"));

        // Defaultni ucastnik je tvurce cesty
        this.ucastnik = new Ucastnik();
        this.ucastnik.setNewEntity(true);
        this.ucastnik.setIdcest(this.cesta);
        this.ucastnik.setIdoso(this.cesta.getIdoso());
        this.ucastnik.setIdtypucast(this.ejbTypUcastFacade.findPopis("Spolujezdec"));
        this.ucastnikList.add(ucastnik);

        return true;
    }

    public boolean editCesta(Cesta cestaParam) {
        this.mode=CestaForm_Old.MODE_EDIT;
        this.cesta = cestaParam;
        this.cestaLocal = new CestaLocal(cestaParam);
//        ucastnikList = new ArrayList<>();
//        rezervaceList = new ArrayList<>();
        ucastnikListDel = new ArrayList<>();
        rezervaceListDel = new ArrayList<>();
        // Naplnit ucastniky
//        for (Ucastnik ucastnikLocal : this.cesta.getUcastnikList()) {
//            ucastnikList.add(ucastnikLocal);
//        }
        ucastnikList = new ArrayList<>(ejbUcastnikFacade.findUcastnikyWhereCesta(this.cesta));
        // Naplnit rezervace
//        for (Rezervace rezervaceLocal : this.cesta.getRezervaceList()) {
//            rezervaceList.add(rezervaceLocal);
//        }
        rezervaceList = new ArrayList<>(ejbRezervaceFacade.findRezervaceWhereCesta(this.cesta));
        return true;
    }

    public String saveCesta() {
        try {
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            if (this.mode == CestaForm_Old.MODE_NEW) {
                cesta = new Cesta();
            } else {
                this.cesta = ejbCestaFacade.find(this.cesta.getId());
            }
            this.cesta.setIdoso(loginUser.getOsoba());
            this.cesta.setPlatiod(this.getPlatiOd());
            this.cesta.setPlatido(this.getPlatiDo());
            this.cesta.setIdtypzdr(this.getTypZdroje());
            this.cesta.setPopis(this.getMisto());
            this.cesta.setKomentar(this.getDuvod());
            this.cesta.setZaloha(this.getZaloha());
            this.cesta.setUcastnikList(this.getUcastnikList());
            this.cesta.setRezervaceList(this.getRezervaceList());
            this.cesta.setSchvaleniList(new ArrayList<Schvaleni>());
            if (this.mode == CestaForm_Old.MODE_NEW) {
                this.ejbCestaFacade.create(this.cesta);
            } else {
                this.ejbCestaFacade.edit(this.cesta);
            }
            for (Ucastnik ucast : this.ucastnikList) {
                if (ucast.isNewEntity()) {
                    ucast.setNewEntity(false);
                    this.ejbUcastnikFacade.create(ucast);
                } else {
                    this.ejbUcastnikFacade.edit(ucast);
                }
            }
            for (Ucastnik ucast : this.ucastnikListDel) {
                if (!ucast.isNewEntity()) {
                    this.ejbUcastnikFacade.remove(ucast);
                }
            }
            for (Rezervace reze : this.rezervaceList) {
                if (reze.isNewEntity()) {
                    reze.setNewEntity(false);
                    this.ejbRezervaceFacade.create(reze);
                } else {
                    this.ejbRezervaceFacade.edit(reze);
                }
            }
            for (Rezervace reze : this.rezervaceListDel) {
                if (!reze.isNewEntity()) {
                    this.ejbRezervaceFacade.remove(reze);
                }
            }
            transaction.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException | NotSupportedException | NamingException ex) {
            Logger.getLogger(CestaForm_Old.class.getName()).log(Level.SEVERE, null, ex);
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
            ucastnikLocal.setPlatiod(this.cesta.getPlatiod());
            ucastnikLocal.setPlatido(this.cesta.getPlatido());
            ucastnikLocal.setNewEntity(true);
            this.ucastnikList.add(ucastnikLocal);
        }
//        this.helperOsoba.removeHelperOsobyListener(this);
    }

    public void ucastnikDelete() {
        if (!this.ucastnik.isNewEntity()) {
            this.ucastnikListDel.add(this.ucastnik);
        }
        this.ucastnikList.remove(this.ucastnik);
    }

// ====
// Helper pro vyber zdroje k rezervaci
//=====
    public void prepareHelperZdroj() {
        helperZdroj.initHelperZdroj(loginUser.getOsoba(), this.getPlatiOd(), this.getPlatiDo());
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
        this.rezervace.setPlatiod(this.cesta.getPlatiod());
        this.rezervace.setPlatido(this.cesta.getPlatido());
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
        if (!this.rezervace.isNewEntity()) {
            this.rezervaceListDel.add(this.rezervace);
        }
        this.rezervaceList.remove(this.rezervace);
    }

    /**
     * Vnitrni trida pro "stin" informaci o ceste
     */
    class CestaLocal {

        private Date platiOd = null;
        private Date platiDo = null;
        private String misto = null;
        private String duvod = null;
        private Typzdroje typZdroje = null;
        private Double zaloha = null;

        public CestaLocal() {
            platiOd = new Date();
            platiDo = new Date();
            misto = new String();
            duvod = new String();
            typZdroje = null;
            zaloha = 0.0;
        }

        public CestaLocal(Cesta cesta) {
            platiOd = cesta.getPlatiod();
            platiDo = cesta.getPlatido();
            misto = cesta.getPopis();
            duvod = cesta.getKomentar();
            typZdroje = cesta.getIdtypzdr();
            zaloha = cesta.getZaloha();
        }

    }
}
