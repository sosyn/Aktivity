/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.rezervace;

import entity.Zdroj;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Ivo
 */
@SessionScoped
public class Kalendar implements Serializable {

    static final int COLUMNS_MAX = 150;

    private Calendar cal = Calendar.getInstance(Locale.getDefault());
    private Date platiOd = new Date();
    private Date platiDo = new Date();

    ArrayList<Zdroj> zdroje = null;

    private KalendarColumn kalendarCol = null;
    private HashMap<Integer, KalendarColumn> columns = new HashMap<>();

    @PostConstruct
    void init() {
        cal.setTime(this.platiOd);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        this.platiOd = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, Kalendar.COLUMNS_MAX);
        this.platiDo = cal.getTime();
        getColumns();
        System.out.println("Kalendar.init Kalendar.platiOd="+this.platiOd+" Kalendar.platiDo="+this.platiDo);
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

    public Collection<KalendarColumn> getColumns() {
        if (!columns.isEmpty()) {
            return columns.values();
        }
        Calendar calOd = Calendar.getInstance(Locale.getDefault());
        Calendar calDo = Calendar.getInstance(Locale.getDefault());
        calOd.setTime(this.platiOd);
        calDo.setTime(this.platiOd);
        Integer key = 0;
        do {
            if (key > 0) {
                calOd.set(Calendar.HOUR_OF_DAY, 0);
                calOd.set(Calendar.MINUTE, 0);
                calOd.set(Calendar.SECOND, 0);
            }
            calDo.setTime(calOd.getTime());
            calDo.set(Calendar.HOUR_OF_DAY, 23);
            calDo.set(Calendar.MINUTE, 59);
            calDo.set(Calendar.SECOND, 59);
            if (calDo.getTime().after(this.platiDo)) {
                calDo.setTime(this.platiDo);
            }
            columns.put(key++, new KalendarColumn(calOd.getTime(), calDo.getTime(), Calendar.DAY_OF_WEEK));

            calOd.add(Calendar.HOUR_OF_DAY, 24);

        } while (calDo.getTime().before(this.platiDo));
        return columns.values();
    }

    public KalendarColumn getColumn(Integer key) {
        return columns.get(key);
    }

}
