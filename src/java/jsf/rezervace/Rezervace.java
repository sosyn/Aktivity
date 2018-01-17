/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.rezervace;

import entity.Cesta;
import entity.Zdroj;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Ivo
 */
@Named("rezervace")
@SessionScoped
public class Rezervace implements Serializable {

    private final Calendar cal = Calendar.getInstance(Locale.getDefault());
    private Date platiOd = new Date();
    private Date platiDo = new Date();

    @EJB
    private ejb.CestaFacade ejbCestaFacade;
    @EJB
    private ejb.ZdrojeFacade ejbZdrojeFacade;
    @Inject
    private Kalendar kalendar;

    private Cesta cesta = null;
    private ArrayList<Cesta> cesty = new ArrayList<>();
    private Rezervace rezervace = null;
    private ArrayList<Rezervace> rezervaceList = new ArrayList<>();
    private Zdroj zdroj = null;
    private ArrayList<Zdroj> zdrojList = new ArrayList<>();

    @PostConstruct
    void init() {
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
        return cesty;
    }

    /**
     * @param cesty the cesty to set
     */
    public void setCesty(ArrayList<Cesta> cesty) {
        this.cesty = cesty;
    }

    public String newCesta() {
        return null;
    }

    public String editCesta() {
        return null;
    }

    /**
     * @return the kalendar
     */
    public Kalendar getKalendar() {
        return kalendar;
    }

    /**
     * @param kalendar the kalendar to set
     */
    public void setKalendar(Kalendar kalendar) {
        this.kalendar = kalendar;
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

    /**
     * @return the zdroj
     */
    public Zdroj getZdroj() {
        return zdroj;
    }

    /**
     * @param zdroj the zdroj to set
     */
    public void setZdroj(Zdroj zdroj) {
        this.zdroj = zdroj;
    }

    /**
     * @return the zdrojList
     */
    public ArrayList<Zdroj> getZdrojList() {
        this.zdrojList = new ArrayList<>(ejbZdrojeFacade.findAll());
        return zdrojList;
    }

    /**
     * @param zdrojList the zdrojList to set
     */
    public void setZdrojList(ArrayList<Zdroj> zdrojList) {
        this.zdrojList = zdrojList;
    }

    public String getHtmlText(Zdroj zdr, Integer colIndex) {
        StringBuilder html = new StringBuilder("<p>");
        html.append("<p onClick="+onClick(zdr,colIndex,null) +"> UUID zdroje=" + zdr.getId().toString() + "</p>");
        html.append("<p> Int sloupce=" + colIndex.toString() + "</p>");
        return html.toString();
    }

    private String onClick(Zdroj zdr, Integer colIndex, Rezervace rez) {
        StringBuilder html = new StringBuilder("onClickCell('");
        html.append(zdr.getId().toString() + ":" + colIndex.toString());
        html.append(":" + rez);
        html.append("')");
        return html.toString();
    }

    public void eventRezervaceCell() {
        String cell = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cell");
        System.out.println("Cell:" + cell);
    }
}
