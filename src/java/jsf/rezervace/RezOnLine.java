/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.rezervace;

import entity.Cesta;
import entity.Rezervace;
import entity.TypZdrojeEnum;
import entity.Zdroj;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Ivo
 */
@Named("rezOnLine")
@SessionScoped
public class RezOnLine implements Serializable {

    @EJB
    private ejb.CestaFacade ejbCestaFacade;
    @EJB
    private ejb.ZdrojeFacade ejbZdrojeFacade;
    @EJB
    private ejb.RezervaceFacade ejbRezervaceFacade;
    @Inject
    private Kalendar kalendar;

    private java.util.Calendar cal = java.util.Calendar.getInstance(Locale.getDefault());
    private Date platiOd = new Date();
    private Date platiDo = new Date();

    private Cesta cesta = null;
    private ArrayList<Cesta> cesty = new ArrayList<>();
    private entity.Rezervace rezervace = null;
    private ArrayList<entity.Rezervace> rezervaceList = new ArrayList<>();
    private Zdroj zdroj = null;
    private ArrayList<Zdroj> zdrojList = new ArrayList<>();

    @PostConstruct
    void init() {
        this.platiOd = zaokrouhliDatum(this.platiOd, 15);
        cal.setTime(this.platiOd);
        cal.add(java.util.Calendar.DAY_OF_MONTH, Kalendar.COLUMNS_DEFAULT);
        this.platiDo = cal.getTime();
        getDataForRezOnLine();
    }

    public String getHtmlText(Zdroj zdr, Integer colIndex) {
        StringBuilder html = new StringBuilder("");
        KalendarColumn kalCol = this.kalendar.getColumn(colIndex);
        for (Rezervace rez : rezervaceList) {
            if (zdr.getId().compareTo(rez.getIdzdr().getId()) == 0
                    && kalCol.getPlatiOd().before(rez.getPlatido())
                    && kalCol.getPlatiDo().after(rez.getPlatiod())) {
                html.append("<div class=\"rezervace\" onClick=" + onClickHtml(zdr, colIndex, rez) + ">" + rez.getIdcest().getPopis() + "</div>");
            }
        }
        html.append("<div style=\" display:inline-block; line-height:300%; background-color: #e1e5ed; width:100%;\" onClick=" + onClickHtml(zdr, colIndex, null) + ">&nbsp;&nbsp;&nbsp;&nbsp;</div>");
        return html.toString();
    }

    private String onClickHtml(Zdroj zdr, Integer colIndex, entity.Rezervace rez) {
        StringBuilder html = new StringBuilder("onClickCell('");
        html.append(zdr.getId().toString() + ":" + colIndex.toString());
        html.append(":" + (rez == null ? null : rez.getId()));
        html.append("')");
        return html.toString();
    }

    public void eventRezInfo() {
        UUID uuidZdr = null;
        UUID uuidRez = null;
        Integer colIndex = null;
        String cell = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cell");
        System.out.println("Cell:" + cell);
        String[] cellParam;
        cellParam = cell.split(":");
        uuidZdr = UUID.fromString(cellParam[0]);
        colIndex = Integer.getInteger(cell);
        if (!cellParam[2].startsWith("null")) {
            uuidRez = UUID.fromString(cellParam[2]);
            this.rezervace = ejbRezervaceFacade.find(uuidRez);

            Map<String, Object> options = new HashMap<String, Object>();
            options.put("modal", true);
            options.put("resizable", true);
            options.put("draggable", true);
            options.put("width", 800);
            options.put("height", 500);
            options.put("contentWidth", "100%");
            options.put("contentHeight", "100%");
            options.put("closable", true);
//                    options.put("includeViewParams", true);
//                    options.put("headerElement", "customheader");
            options.put("maximizable", true);
            RequestContext.getCurrentInstance().openDialog("/rezervace/rezDetail", options, null);
        }

    }


    public void eventZdrInfo(Zdroj zdr) {
            this.zdroj = zdr;

            Map<String, Object> options = new HashMap<String, Object>();
            options.put("modal", true);
            options.put("resizable", true);
            options.put("draggable", true);
            options.put("width", 800);
            options.put("height", 500);
            options.put("contentWidth", "100%");
            options.put("contentHeight", "100%");
            options.put("closable", true);
//                    options.put("includeViewParams", true);
//                    options.put("headerElement", "customheader");
            options.put("maximizable", true);
            RequestContext.getCurrentInstance().openDialog("/rezervace/zdrDetail", options, null);

    }

    public void onReset() {
        platiOd = new Date();
        init();
    }

    public void onRefresh() {
        this.platiOd = zaokrouhliDatum(this.platiOd, 15);
        this.platiDo = zaokrouhliDatum(this.platiDo, 15);
        getDataForRezOnLine();
    }

    public void getDataForRezOnLine() {
        this.zdrojList = new ArrayList<>(ejbZdrojeFacade.findAllWhereTypZdroje(TypZdrojeEnum.VOZIDLO, this.platiOd, this.platiDo));
        this.rezervaceList = new ArrayList<>(ejbRezervaceFacade.getRezervaceOdDo(this.platiOd, this.platiDo));
        kalendar.initColumns(this.platiOd, this.platiDo);
        //System.out.println("RezOnLine.onRefresh platiOd: " + this.platiOd + " platiDo: " + this.platiDo);
    }

    public void onColBtn(int colIndex, int smer) {
        // Down
        if (smer == 0) {
            kalendar.insColumns(platiOd, platiDo, colIndex);
        }
        // Up
        if (smer == 1) {
            kalendar.delColumns(platiOd, platiDo, colIndex);
        }
        // System.out.println(" rezervace.colIndex=" + colIndex + " rezervace.smer=" + smer);

    }

    public boolean isColBtnRender(int colIndex, int smer) {
        int level = kalendar.getColumn(colIndex).getLevel();
        // Down
        if (smer == 0) {
            return (level < 2);
        }
        // Up
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
    public entity.Rezervace getRezervace() {
        return rezervace;
    }

    /**
     * @param rezervace the rezervace to set
     */
    public void setRezervace(entity.Rezervace rezervace) {
        this.rezervace = rezervace;
    }

    /**
     * @return the rezervaceList
     */
    public ArrayList<entity.Rezervace> getRezervaceList() {
        return rezervaceList;
    }

    /**
     * @param rezervaceList the rezervaceList to set
     */
    public void setRezervaceList(ArrayList<entity.Rezervace> rezervaceList) {
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
        return zdrojList;
    }

    /**
     * @param zdrojList the zdrojList to set
     */
    public void setZdrojList(ArrayList<Zdroj> zdrojList) {
        this.zdrojList = zdrojList;
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
        calLocal.set(java.util.Calendar.MILLISECOND, 0);
        calLocal.set(java.util.Calendar.SECOND, 0);
        calLocal.add(java.util.Calendar.MINUTE, (minuty % i) == 0 ? 0 : i - (minuty % i));
        return calLocal.getTime();
    }

}
