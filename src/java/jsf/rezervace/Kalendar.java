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

    static final int COLUMNS_MAX = 5;

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
        calOd.setTime(platiOd);
        calDo.setTime(platiDo);
        this.platiOd = platiOd;
        this.platiDo = platiDo;
        this.columns = new HashMap<>();
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
            columns.put(key++, new KalendarColumn(calOd.getTime(), calDo.getTime(), Calendar.DAY_OF_MONTH));

            calOd.add(Calendar.HOUR_OF_DAY, 24);

            System.out.println(" calOd.getTime()=" + calOd.getTime() + " calDo.getTime()=" + calDo.getTime());
        } while (calDo.getTime().before(platiDo));

    }

    public void insColumns(Date platiOd, Date platiDo, int colIndex) {
        KalendarColumn kalCol = columns.get(colIndex);
        Calendar calKalCol = Calendar.getInstance(Locale.getDefault());
        calKalCol.setTime(kalCol.getPlatiOd());
        ArrayList<KalendarColumn> csIns = new ArrayList<>();
        HashMap<Integer, KalendarColumn> csNew = new HashMap<>();
        Date dOd = kalCol.getPlatiOd();
        Date dDo = kalCol.getPlatiDo();
        cal.setTime(dOd);
        int addTime = 0;
        if (kalCol.getStatus() == Calendar.DAY_OF_MONTH) {
            addTime = 59;
            cal.set(Calendar.SECOND, 0);
            do {
                dOd = cal.getTime();
                cal.add(Calendar.MINUTE, addTime);
                dDo = cal.getTime();
                csIns.add(new KalendarColumn(dOd, dDo, Calendar.HOUR_OF_DAY));
                cal.add(Calendar.MINUTE, 1);
            } while (cal.get(Calendar.DAY_OF_MONTH)==calKalCol.get(Calendar.DAY_OF_MONTH));

        }
        if (kalCol.getStatus() == Calendar.HOUR_OF_DAY) {
            addTime = 14;
            cal.set(Calendar.SECOND, 0);
            do {
                dOd = cal.getTime();
                cal.add(Calendar.MINUTE, addTime);
                dDo = cal.getTime();
                csIns.add(new KalendarColumn(dOd, dDo, Calendar.MINUTE));
                cal.add(Calendar.MINUTE, 1);
            } while (cal.get(Calendar.HOUR_OF_DAY)==calKalCol.get(Calendar.HOUR_OF_DAY));
        }

        int j = 0;
        for (Integer key : columns.keySet()) {
            if (key == colIndex) {
                for (KalendarColumn colIns : csIns) {
                    csNew.put(j++, colIns);
                }
            } else {
                csNew.put(j++, columns.get(key));
            }
        }
        this.columns = new HashMap<>(csNew);
    }

    void delColumns(Date platiOd, Date platiDo, int colIndex) {
        KalendarColumn kalCol = columns.get(colIndex);
        Calendar calKalCol = Calendar.getInstance(Locale.getDefault());
        calKalCol.setTime(kalCol.getPlatiOd());
        KalendarColumn col = null;
        KalendarColumn colNew = null;
        HashMap<Integer, KalendarColumn> csNew = new HashMap<>();
        int j = 0;
        for (Integer key : columns.keySet()) {
            col = columns.get(key);
            if (kalCol.getStatus() == Calendar.HOUR_OF_DAY) {
                cal.setTime(col.getPlatiOd());
                if (cal.get(Calendar.DAY_OF_MONTH) == calKalCol.get(Calendar.DAY_OF_MONTH)) {
                    if (colNew == null) {
                        colNew = new KalendarColumn(col.getPlatiOd(), col.getPlatiDo(), Calendar.DAY_OF_MONTH);
                    } else {
                        colNew.setPlatiDo(col.getPlatiDo());
                    }
                    continue;
                }
            }
            if (kalCol.getStatus() == Calendar.MINUTE) {
                cal.setTime(col.getPlatiOd());
                if (cal.get(Calendar.HOUR_OF_DAY) == calKalCol.get(Calendar.HOUR_OF_DAY)) {
                    if (colNew == null) {
                        colNew = new KalendarColumn(col.getPlatiOd(), col.getPlatiDo(), Calendar.DAY_OF_MONTH);
                    } else {
                        colNew.setPlatiDo(col.getPlatiDo());
                    }
                    continue;
                }
            }
            // Pridat jeste nepridany novy sloupec
            if (colNew != null) {
                csNew.put(j++, colNew);
                colNew = null;
            }
            csNew.put(j++, col);
        }
        // Pridat jeste nepridany novy sloupec
        if (colNew != null) {
            csNew.put(j++, colNew);
            colNew = null;
        }
        this.columns = new HashMap<>(csNew);
    }
}
