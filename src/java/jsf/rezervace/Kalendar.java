/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.rezervace;

import ejb.ZdrojeFacade;
import entity.Zdroj;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Ivo
 */
@SessionScoped
public class Kalendar implements Serializable {

    private Calendar cal = Calendar.getInstance(Locale.getDefault());
    private Date platiOd = new Date();
    private Date platiDo = new Date();

    @EJB
    ZdrojeFacade ejbZdrojeFacade;
    ArrayList<Zdroj> zdroje = null;

    private KalendarRow kalendarRow = null;
    private ArrayList<KalendarRow> kalendarRows = new ArrayList<>();

    @PostConstruct
    void init() {
        zdroje = new ArrayList<>(ejbZdrojeFacade.findAll());
        for (int i = 0; i < 30; i++) {
            kalendarRows.add(new KalendarRow(cal.getTimeInMillis(), this.zdroje, i));
            cal.add(Calendar.HOUR_OF_DAY, 1);
        }
    }

    public void insertBeforeFirstRow() {
        cal.setTimeInMillis(getKalendarRows().get(0).getCas().getTime());
        cal.add(Calendar.HOUR_OF_DAY, -1);
        int id = getKalendarRows().get(0).getId() - 1;
        getKalendarRows().add(0, new KalendarRow(cal.getTimeInMillis(), this.zdroje, id));
        getKalendarRows().remove(getKalendarRows().size() - 1);

    }

    public void insertAfterLastRow() {
        cal.setTimeInMillis(getKalendarRows().get(getKalendarRows().size() - 1).getCas().getTime());
        cal.add(Calendar.HOUR_OF_DAY, +1);
        int id = getKalendarRows().get(getKalendarRows().size() - 1).getId() + 1;
        getKalendarRows().add(new KalendarRow(cal.getTimeInMillis(), this.zdroje, id));
        getKalendarRows().remove(getKalendarRows().size() - 1);
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
     * @return the kalendarRows
     */
    public ArrayList<KalendarRow> getKalendarRows() {
        return kalendarRows;
    }

    /**
     * @param kalendarRows the kalendarRows to set
     */
    public void setKalendarRows(ArrayList<KalendarRow> kalendarRows) {
        this.kalendarRows = kalendarRows;
    }

    /**
     * @return the cas
     */
    public KalendarRow getKalendarRow() {
        return kalendarRow;
    }

    /**
     * @param kalendarRow the kalendarRow to set
     */
    public void setKalendarRow(KalendarRow kalendarRow) {
        this.kalendarRow = kalendarRow;
    }

    public Date getCas(KalendarRow item) {
        return item.getCas();
    }
}
