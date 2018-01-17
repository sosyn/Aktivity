/*
 * To change this license headerDate, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.rezervace;

import java.util.Date;
import java.util.Calendar;

/**
 *
 * @author Ivo
 */
public class KalendarColumn {

//    private Calendar cal = Calendar.getInstance(Locale.getDefault());
    private Date platiOd = new Date();
    private Date platiDo = new Date();
    int status = Calendar.DAY_OF_MONTH;
    private String headerDate = "";
    private String headerOdDo = "";
    
    public KalendarColumn(Date platiOd, Date platiDo, int status) {
        this.platiOd = platiOd;
        this.platiDo = platiDo;
        this.status = status;
        this.headerDate = String.format("%1$td.%1$tm.%1$tY", platiOd);
        this.headerOdDo = String.format("%1$tR-%2$tR", platiOd,platiDo);
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

    public String getHeaderDate() {
        return headerDate;
    }

    /**
     * @param headerDate the headerDate to set
     */
    public void setHeaderDate(String headerDate) {
        this.headerDate = headerDate;
    }

    /**
     * @return the headerOdDo
     */
    public String getHeaderOdDo() {
        return headerOdDo;
    }

    /**
     * @param headerOdDo the headerOdDo to set
     */
    public void setHeaderOdDo(String headerOdDo) {
        this.headerOdDo = headerOdDo;
    }

}
