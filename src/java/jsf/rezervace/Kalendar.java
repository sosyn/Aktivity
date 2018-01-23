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
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Ivo
 */
@SessionScoped
public class Kalendar implements Serializable {

    static final int COLUMNS_MAX = 60;

    private Calendar cal = Calendar.getInstance(Locale.getDefault());
    private Date platiOd = new Date();
    private Date platiDo = new Date();

    ArrayList<Zdroj> zdroje = null;

    private KalendarColumn kalendarCol = null;
    private HashMap<Integer, KalendarColumn> columns = new HashMap<>();

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
        return columns.values();
    }

    public KalendarColumn getColumn(Integer key) {
        return columns.get(key);
    }

    void initColumns(Date platiOd, Date platiDo) {
        Calendar calOd = Calendar.getInstance(Locale.getDefault());
        Calendar calDo = Calendar.getInstance(Locale.getDefault());
        calOd.setTime(this.platiOd);
        calDo.setTime(this.platiOd);
        columns = new HashMap<>();
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
            if (calDo.getTime().after(platiDo)) {
                calDo.setTime(platiDo);
            }
            columns.put(key++, new KalendarColumn(calOd.getTime(), calDo.getTime(), Calendar.DAY_OF_WEEK));

            calOd.add(Calendar.HOUR_OF_DAY, 24);

            System.out.println(" calOd.getTime()="+calOd.getTime()+" calDo.getTime()="+calDo.getTime());
        } while (calDo.getTime().before(platiDo));

    }

    public Collection<KalendarColumn> modifiColumns(Date platiOd, Date platiDo, int colIndex, int smer) {
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
            if (calDo.getTime().after(platiDo)) {
                calDo.setTime(platiDo);
            }
            columns.put(key++, new KalendarColumn(calOd.getTime(), calDo.getTime(), Calendar.DAY_OF_WEEK));

            calOd.add(Calendar.HOUR_OF_DAY, 24);

        } while (calDo.getTime().before(platiDo));

        return columns.values();
    }

}
