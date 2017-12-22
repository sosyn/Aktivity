/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.rezervace;

import entity.Zdroj;
import java.util.Date;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Ivo
 */
class KalendarRow {

    private int id = 0;
    private Date cas = null;
    private Zdroj zdroj = null;
    private ArrayList<Zdroj> zdroje = null;

    KalendarRow(long time, ArrayList<Zdroj> zdroje,int id) {
        this.id = id;
        this.cas = new Date(time);
        this.zdroje = zdroje;
    }

    /**
     * @return the timeStamp
     */
    public Date getCas() {
        return cas;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setCas(Date time) {
        this.cas = time;
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
     * @return the zdroje
     */
    public ArrayList<Zdroj> getZdroje() {
        return zdroje;
    }

    /**
     * @param zdroje the zdroje to set
     */
    public void setZdroje(ArrayList<Zdroj> zdroje) {
        this.zdroje = zdroje;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

}
