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
    private int status = Calendar.DAY_OF_MONTH;
    private String headerDate = "";
    private String headerOdDo = "";

    public KalendarColumn(Date platiOd, Date platiDo, int status) {
        this.platiOd = platiOd;
        this.platiDo = platiDo;
        this.status = status;
        this.headerDate = String.format("%1$td.%1$tm.%1$tY", platiOd);
        this.headerOdDo = String.format("%1$tR-%2$tR", platiOd, platiDo);
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

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the status
     */
    public int getLevel() {
        switch (this.status) {
            case Calendar.DAY_OF_MONTH:
                return 0;
            case Calendar.HOUR_OF_DAY:
                return 1;
            case Calendar.MINUTE:
                return 2;                
        }
        return 0;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += platiOd.hashCode();
        hash += platiDo.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KalendarColumn)) {
            return false;
        }
        KalendarColumn kc = (KalendarColumn) object;
        return (this.platiOd.equals(kc.platiOd) && this.platiDo.equals(kc.platiDo));
    }

    @Override
    public String toString() {
        return "jsf.rezervace.KalendarColumn[" +String.format("%1$td.%1$tm.%1$tY %1$tR-%2$td.%2$tm.%2$tY %2$tR", platiOd,platiDo)+ " ]";
    }

}
