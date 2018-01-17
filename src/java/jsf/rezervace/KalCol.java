/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.rezervace;

import java.util.Date;
import java.util.Calendar;
import java.util.Locale;

/**
 *
 * @author Ivo
 */
public class KalCol {

//    private Calendar cal = Calendar.getInstance(Locale.getDefault());
    private Date platiOd = new Date();
    private Date platiDo = new Date();   
    int status=Calendar.DAY_OF_MONTH;
    private String header="";

    public KalCol(Date platiOd,Date platiDo,int status) {
        this.platiOd=platiOd;
        this.platiDo=platiDo;
        this.status=status;
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

    public String getHeader() {
        header=String.format("%1$td.%1$tm.%1$tY",platiOd);
        return header ;
    }

    /**
     * @param header the header to set
     */
    public void setHeader(String header) {
        this.header = header;
    }
    

    
}
