/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.schvalovani;

import ejb.LoginUser;
import entity.Osoba;
import entity.Rezervace;
import entity.Schvaleni;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jsf.helper.HelperZdroj;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Ivo
 */
@Named("schvalovaniRez")
@SessionScoped
public class SchvalovaniRez implements Serializable {

    private static final long serialVersionUID = 1L;
    private Calendar cal = Calendar.getInstance(Locale.getDefault());
    private Date platiOd = new Date();
    private Date platiDo = new Date();

    private boolean vedouci = true;
    private boolean zastupce = true;
    private boolean nezpracovane = true;
    private boolean schvalene = false;
    private boolean zamitnute = false;
    private boolean platiOdDo = false;

    @EJB
    private ejb.RezervaceFacade ejbRezervaceFacade;
    @Inject
    LoginUser loginUser;
    @Inject
    HelperZdroj helperZdroj;

    private Osoba osoba = null;

    private Rezervace rezervace = null;
    private ArrayList<Rezervace> rezervaceArr = new ArrayList<>();
    private ArrayList<Rezervace> selRezervace = new ArrayList<>();

    @PostConstruct
    void init() {
        loginUser.initLoginUser();
        this.setOsoba(loginUser.getOsoba());
        initRezervace();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void initRezervace() {
        this.rezervaceArr = ejbRezervaceFacade.findRezervaceWhere(getProperties());
        this.selRezervace = new ArrayList<>();
        if (!this.rezervaceArr.isEmpty()) {
            this.setPlatiOd(this.getRezervaceArr().get(0).getPlatiod());
            this.setPlatiDo(this.getRezervaceArr().get(this.getRezervaceArr().size() - 1).getPlatido());
        }
    }

    private Properties getProperties() {
        Properties prop = new Properties();
        prop.put("osoba", this.osoba);
        prop.put("nezpracovane", this.nezpracovane);
        prop.put("schvalene", this.schvalene);
        prop.put("zamitnute", this.zamitnute);
        prop.put("vedouci", this.vedouci);
        prop.put("zastupce", this.zastupce);
        prop.put("platiOdDo", this.platiOdDo);
        prop.put("platiOd", this.platiOd);
        prop.put("platiDo", this.platiDo);
        return prop;
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
     * @return the rezervaceArr
     */
    public ArrayList<Rezervace> getRezervaceArr() {
        return rezervaceArr;
    }

    /**
     * @param rezervaceArr the rezervaceArr to set
     */
    public void setRezervaceArr(ArrayList<Rezervace> rezervaceArr) {
        this.rezervaceArr = rezervaceArr;
    }

    /**
     * @return the selRezervace
     */
    public ArrayList<Rezervace> getSelRezervace() {
        return selRezervace;
    }

    /**
     * @param selRezervace the selRezervace to set
     */
    public void setSelRezervace(ArrayList<Rezervace> selRezervace) {
        this.selRezervace = selRezervace;
    }

    /**
     * @return the vedouci
     */
    public boolean isVedouci() {
        return vedouci;
    }

    /**
     * @param vedouci the vedouci to set
     */
    public void setVedouci(boolean vedouci) {
        this.vedouci = vedouci;
    }

    /**
     * @return the zastupce
     */
    public boolean isZastupce() {
        return zastupce;
    }

    /**
     * @param zastupce the zastupce to set
     */
    public void setZastupce(boolean zastupce) {
        this.zastupce = zastupce;
    }

    /**
     * @return the nezpracovane
     */
    public boolean isNezpracovane() {
        return nezpracovane;
    }

    /**
     * @param nezpracovane the nezpracovane to set
     */
    public void setNezpracovane(boolean nezpracovane) {
        this.nezpracovane = nezpracovane;
    }

    /**
     * @return the schvalene
     */
    public boolean isSchvalene() {
        return schvalene;
    }

    /**
     * @param schvalene the schvalene to set
     */
    public void setSchvalene(boolean schvalene) {
        this.schvalene = schvalene;
    }

    /**
     * @return the platiOdDo
     */
    public boolean isPlatiOdDo() {
        return platiOdDo;
    }

    /**
     * @param platiOdDo the platiOdDo to set
     */
    public void setPlatiOdDo(boolean platiOdDo) {
        this.platiOdDo = platiOdDo;
    }

    /**
     * @return the osoba
     */
    public Osoba getOsoba() {
        return osoba;
    }

    /**
     * @param osoba the osoba to set
     */
    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;
    }

    /**
     * @return the zamitnute
     */
    public boolean isZamitnute() {
        return zamitnute;
    }

    /**
     * @param zamitnute the zamitnute to set
     */
    public void setZamitnute(boolean zamitnute) {
        this.zamitnute = zamitnute;
    }

///////    
    public void refreshRezervace() {
        System.out.println("refreshRezervace()");
    }

    public void onPlatiOdSelect() {
        if (this.getPlatiOd().after(this.getPlatiDo())) {
            this.getPlatiDo().setTime(getPlatiOd().getTime());
        }
    }

    public void onPlatiDoSelect() {
        System.out.println("onPlatiDoSelect()");
    }

    public void schvalit() {
        for (Rezervace rez : this.getSelRezervace()) {
            if (mohuSchvalovat(rez)) {
                ejbRezervaceFacade.insSchvaleni(this.osoba, rez, 1, ejbRezervaceFacade.urovenOsobaRez(this.osoba, rez));
            }
        }
        initRezervace();
    }

    public void zamitnout() {
        for (Rezervace rez : this.getSelRezervace()) {
            if (mohuSchvalovat(rez)) {
                ejbRezervaceFacade.insSchvaleni(this.osoba, rez, 2, ejbRezervaceFacade.urovenOsobaRez(this.osoba, rez));
            }
        }
        initRezervace();
    }

    public void showDetail(Rezervace rez) {
        this.setRezervace(rez);
    }

    public void changeZdroj(Rezervace rez) {
        helperZdroj.initHelperZdroj(rez.getIdcest());
        RequestContext.getCurrentInstance()
                .openDialog("/helper/helperZdroj", null, null);
    }

    public void onRezervaceSelect() {
        System.out.println("onRezervaceSelect()");
    }

    public void onRezervaceUnSelect() {
        System.out.println("onRezervaceUnSelect()");
    }

    public String iconSchvalovani(Rezervace rez) {
        String iconFile = "/images/Pest.png";
        int[] stav = stavSchvaleni(rez);
        switch (stav[0]) {
            case 1:
                iconFile = "/images/Thumbs up.png";
                break;
            case 2:
                iconFile = "/images/Thumbs down.png";
                break;
        }
        return iconFile;
    }

    public String iconDispecer(Rezervace rez) {
        String iconFile = "/images/BlueLine.png";
        int uroven = ejbRezervaceFacade.urovenOsobaRez(this.osoba, rez);
        switch (uroven) {
            case 1:
                iconFile = "/images/Zastupce.png";
                break;
            case 2:
                iconFile = "/images/Boss.png";
                break;
        }
        return iconFile;
    }

    private int[] stavSchvaleni(Rezervace rez) {
        int[] stav = {0, 0};
        Date lastDate = null;
        for (Schvaleni schv : rez.getSchvList()) {
            if (lastDate == null || lastDate.before(schv.getPlatiod())) {
                lastDate = schv.getPlatiod();
                stav[0] = schv.getStav();
                stav[1] = schv.getUroven();
            }
        }
        return stav;
    }

    public boolean mohuSchvalovat(Rezervace rez) {
        boolean ret = true;
        cal.setTime(new Date());
        // Navysit cas rezervace o predstih 15 minut, aby se omezila moznost schvalovani na posledni chvili
        cal.add(Calendar.MINUTE, 15);
        int urovenOsoba = ejbRezervaceFacade.urovenOsobaRez(this.osoba, rez);
        // Pri pomalem zpracovani je mozne vyuzit i funkci  stavSchvaleni(Rezervace rez)
        int lastUrovenSchvRez = ejbRezervaceFacade.lastSchvaleniRez(rez);
        // Aktualne prihlasena osoba musi mit vyssi uroven prava nez posledni schvalovatel
        ret = (urovenOsoba >= lastUrovenSchvRez && rez.getPlatiod().after(cal.getTime()));
        return ret;
    }
}
