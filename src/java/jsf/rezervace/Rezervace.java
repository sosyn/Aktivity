/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.rezervace;

import entity.Cesta;
import entity.TypZdrojeEnum;
import entity.Zdroj;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Ivo
 */
@Named("rezervace")
@SessionScoped
public class Rezervace implements Serializable {

    @EJB
    private ejb.CestaFacade ejbCestaFacade;
    @EJB
    private ejb.ZdrojeFacade ejbZdrojeFacade;
    @Inject
    private Kalendar kalendar;

    private java.util.Calendar cal = java.util.Calendar.getInstance(Locale.getDefault());
    private Date platiOd = new Date();
    private Date platiDo = new Date();

    private Cesta cesta = null;
    private ArrayList<Cesta> cesty = new ArrayList<>();
    private Rezervace rezervace = null;
    private ArrayList<Rezervace> rezervaceList = new ArrayList<>();
    private Zdroj zdroj = null;
    private ArrayList<Zdroj> zdrojList = new ArrayList<>();

    @PostConstruct
    void init() {
        this.platiOd = zaokrouhliDatum(this.platiOd, 15);
        cal.add(java.util.Calendar.DAY_OF_MONTH, Kalendar.COLUMNS_MAX);
        this.platiDo = cal.getTime();
        kalendar.initColumns(this.platiOd, this.platiDo);
        // System.out.println("Rezervace.init platiOd: " + this.getPlatiOd() + " platiDo: " + this.getPlatiDo());
    }

    public void onRefresh() {
        this.platiOd = zaokrouhliDatum(this.platiOd, 15);
        this.platiDo = zaokrouhliDatum(this.platiDo, 15);
        kalendar.initColumns(this.platiOd, this.platiDo);
        System.out.println("Rezervace.onRefresh platiOd: " + this.platiOd+ " platiDo: " + this.platiDo);
    }

    public void onColKalendarDown(int colIndex, int smer) {
        // smer=0 down
        // smer=1 up
        kalendar.modifiColumns(platiOd, platiDo, colIndex, smer);
        // System.out.println(" rezervace.colIndex=" + colIndex + " rezervace.smer=" + smer);

    }

    public boolean isColKalendarBtnRender(int colIndex, int smer) {
        int level = kalendar.getColumn(colIndex).getLevel();
        // Dolu
        if (smer == 0) {
            return (level < 2);
        }
        // Nahoru
        if (smer == 1) {
            return (level > 0);
        }
        return true;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void onPlatiOdSelect(SelectEvent event) {
        Calendar calendarPlatiOd = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("formRezervaceOdDo:rezPlatiOd");
        Date platiOd = (Date) event.getObject();
        this.platiOd = zaokrouhliDatum(this.platiOd, 15);
        Calendar calendarPlatiDo = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("formRezervaceOdDo:rezPlatiDo");
        Date platiDo = (Date) calendarPlatiDo.getValue();;
        this.platiDo = zaokrouhliDatum(this.platiDo, 15);
//        System.out.println("onPlatiOdSelect calendarPlatiOd.getValue()=" + calendarPlatiOd.getValue() + "  calendarPlatiDo.getValue()" + calendarPlatiDo.getValue());
//        if (platiOd.after(platiDo)) {
//            calendarPlatiDo.setValue(platiOd);
//        }
        System.out.println("onPlatiOdSelect calendarPlatiOd.getValue()=" + calendarPlatiOd.getValue() + "  calendarPlatiDo.getValue()" + calendarPlatiDo.getValue());

        if (this.getPlatiOd().after(this.getPlatiDo())) {
            this.setPlatiDo(this.getPlatiOd());
        }
        System.out.println("onPlatiOdSelect()= platiOd: " + this.getPlatiOd() + " platiDo: " + this.getPlatiDo());
    }

    public void onPlatiDoSelect(SelectEvent event) {
        Calendar calendarPlatiOd = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("formRezervaceOdDo:rezPlatiOd");
        Date platiOd = (Date) calendarPlatiOd.getValue();
        this.platiOd = zaokrouhliDatum(this.platiOd, 15);
        Calendar calendarPlatiDo = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("formRezervaceOdDo:rezPlatiDo");
        Date platiDo = (Date) event.getObject();
        this.platiDo = zaokrouhliDatum(this.platiDo, 15);
//        System.out.println("onPlatiDoSelect calendarPlatiOd.getValue()=" + calendarPlatiOd.getValue() + "  calendarPlatiDo.getValue()" + calendarPlatiDo.getValue());
//        if (platiOd.after(platiDo)) {
//            calendarPlatiOd.setValue(platiDo);
//            calendarPlatiDo.setValue(platiOd);
//        }
        System.out.println(">onPlatiDoSelect calendarPlatiOd.getValue()=" + calendarPlatiOd.getValue() + "  calendarPlatiDo.getValue()" + calendarPlatiDo.getValue());

        if (this.getPlatiOd().after(this.getPlatiDo())) {
            platiDo = this.getPlatiDo();
            this.setPlatiDo(this.getPlatiOd());
            this.setPlatiOd(platiDo);
        }
        System.out.println(">onPlatiDoSelect()= platiOd: " + this.getPlatiOd() + " platiDo: " + this.getPlatiDo());
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
        if (this.zdrojList.isEmpty()) {
            this.zdrojList = new ArrayList<>(ejbZdrojeFacade.findAllWhereTypZdroje(TypZdrojeEnum.VOZIDLO));
        }
        return zdrojList;
    }

    /**
     * @param zdrojList the zdrojList to set
     */
    public void setZdrojList(ArrayList<Zdroj> zdrojList) {
        this.zdrojList = zdrojList;
    }

    public String getHtmlText(Zdroj zdr, Integer colIndex) {
        StringBuilder html = new StringBuilder("");
        html.append("<div style=\"background-color: #00ffff; width:100%;  \" onClick=" + onClick(zdr, colIndex, null) + ">&nbsp;&nbsp;&nbsp;&nbsp;</div>");
        html.append("<div style=\"background-color: #00ffff; width:100%;  \" onClick=" + onClick(zdr, colIndex, null) + ">&nbsp;&nbsp;&nbsp;&nbsp;</div>");
        html.append("<div style=\"background-color: #00ffff; width:100%;  \" onClick=" + onClick(zdr, colIndex, null) + ">&nbsp;&nbsp;&nbsp;&nbsp;</div>");
        return html.toString();
    }

    private String onClick(Zdroj zdr, Integer colIndex, entity.Rezervace rez) {
        StringBuilder html = new StringBuilder("onClickCell('");
        html.append(zdr.getId().toString() + ":" + colIndex.toString());
        html.append(":" + (rez == null ? null : rez.getId()));
        html.append("')");
        return html.toString();
    }

    public void eventRezervaceCell() {
        String cell = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cell");
        System.out.println("Cell:" + cell);
    }

    /**
     * @return the cal
     */
    public java.util.Calendar getCal() {
        return cal;
    }

    /**
     * @param cal the cal to set
     */
    public void setCal(java.util.Calendar cal) {
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

    private Date zaokrouhliDatum(Date datum, int i) {
        java.util.Calendar calLocal = java.util.Calendar.getInstance(Locale.getDefault());
        calLocal.setTime(datum);
        int minuty = calLocal.get(java.util.Calendar.MINUTE);
        calLocal.add(java.util.Calendar.MINUTE, (minuty % i) == 0 ? 0 : i - (minuty % i));
        return calLocal.getTime();
    }
}
