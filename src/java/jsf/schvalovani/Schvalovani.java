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
import java.util.Properties;
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
    private boolean neschvalene = true;
    private boolean zamitnute = false;
    private boolean platiOdDo = false;

    @EJB
    private ejb.UcastnikFacade ejbUcastnikFacade;
    @Inject
    LoginUser loginUser;
    private Osoba osoba = null;

    private Ucastnik ucastnik = null;
    private ArrayList<Ucastnik> ucastnici = new ArrayList<>();
    private ArrayList<Ucastnik> selUcastnici = new ArrayList<>();

    @PostConstruct
    void init() {
        loginUser.initLoginUser();
        this.setOsoba(loginUser.getOsoba());
        initUcastnici();

    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void initUcastnici() {
        setUcastnici(ejbUcastnikFacade.findUcastnikyWhereSchvalovatel(getProperties()));
        if (!ucastnici.isEmpty()) {
            this.setPlatiOd(getUcastnici().get(0).getPlatiod());
            this.setPlatiDo(getUcastnici().get(getUcastnici().size() - 1).getPlatido());
        }
    }

    private Properties getProperties() {
        Properties prop = new Properties();
        prop.put("osoba", this.osoba);
        prop.put("schvalene", this.schvalene);
        prop.put("neschvalene", this.neschvalene);
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
     * @return the ucastnici
     */
    public ArrayList<Ucastnik> getUcastnici() {
        return ucastnici;
    }

    /**
     * @param ucastnici the ucastnici to set
     */
    public void setUcastnici(ArrayList<Ucastnik> ucastnici) {
        this.ucastnici = ucastnici;
    }

    /**
     * @return the selUcastnici
     */
    public ArrayList<Ucastnik> getSelUcastnici() {
        return selUcastnici;
    }

    /**
     * @param selUcastnici the selUcastnici to set
     */
    public void setSelUcastnici(ArrayList<Ucastnik> selUcastnici) {
        this.selUcastnici = selUcastnici;
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
     * @return the neschvalene
     */
    public boolean isNeschvalene() {
        return neschvalene;
    }

    /**
     * @param neschvalene the neschvalene to set
     */
    public void setNeschvalene(boolean neschvalene) {
        this.neschvalene = neschvalene;
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
    public void refreshUcatnici() {
        System.out.println("onPlatiDoSelect()");
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
        System.out.println("schvalit()");
    }

    public void zamitnout() {
        System.out.println("zamitnout()");
    }

    public void showDetail(Ucastnik ucast) {
        this.setUcastnik(ucast);
    }

    public void onUcastnikSelect() {
        System.out.println("onUcastnikSelect()");
    }

    public void onUcastnikUnSelect() {
        System.out.println("onUcastnikUnSelect()");
    }

//    class Schv {
//        Cesta cesta = null;
//        ArrayList<Ucastnik> ucastnici = new ArrayList<Ucastnik>();
//        ArrayList<Zdroj> zdroje = new ArrayList<Zdroj>();
//        ArrayList<Rezervace> rezervace = new ArrayList<Rezervace>();
//    }

}
