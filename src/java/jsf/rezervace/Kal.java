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
public class Kal implements Serializable {

    private Calendar cal = Calendar.getInstance(Locale.getDefault());
    private Date platiOd = new Date();
    private Date platiDo = new Date();

    ArrayList<Zdroj> zdroje = null;

    private KalCol kalendarRow = null;
    private HashMap<Integer, KalCol> columns = new HashMap<>();

    @PostConstruct
    void init() {
        cal.setTime(this.platiOd);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        this.platiOd = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, 14);
        this.platiDo = cal.getTime();
        getColumns();
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

    public Collection<KalCol> getColumns() {
        cal.setTime(this.platiOd);
        Integer key = 0;
        do {
            columns.put(key++, new KalCol(getPlatiOd(), getPlatiDo(), Calendar.DAY_OF_WEEK));
            cal.add(Calendar.DAY_OF_MONTH, 1);
        } while (cal.before(this.platiDo) || cal.equals(this.platiDo));
        return columns.values();
    }

    public KalCol getColumn(Integer key) {
        return columns.get(key);
    }

}
