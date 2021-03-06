/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.helper;

import entity.Cesta;
import entity.Rezervace;
import entity.TypZdrojeEnum;
import entity.Typzdroje;
import entity.Zdroj;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Ivo
 *
 * Pripojeni na externi aplikaci IS VERA -------------------------------------
 * Pripojovaci retezec: jdbc:oracle:thin:@192.168.3.76:1521:vera Uzivatel: vera
 * Heslo: vera Schema: vera
 *
 */
@Named("helperZdroj")
@SessionScoped
public class HelperZdroj implements Serializable {

    private static final long serialVersionUID = 1L;
    private Calendar cal = Calendar.getInstance(Locale.getDefault());

//    @EJB
//    private ejb.RezervaceFacade rezFacade;
    @EJB
    private ejb.ZdrojeFacade zdrojeFacade;
    private Zdroj selectedZdr = null;
    private ArrayList<Zdroj> zdrojList = null;
    private ArrayList<Zdroj> selectedZdroje = new ArrayList<>();

    private Cesta cesta = null;
    private Rezervace selectedRez = null;
    private ArrayList<Rezervace> rezervaceList = new ArrayList<>();
    Date platiOd = new Date();
    Date platiDo = new Date();

    public void initHelperZdroj() {
        zdrojList = new ArrayList<>(zdrojeFacade.findAll());
        this.selectedZdr = null;
        selectedZdroje = new ArrayList<>();
    }

    public void initHelperZdroj(Cesta cesta) {
        this.cesta = cesta;
        zdrojList = new ArrayList<>(zdrojeFacade.findAccesibleZdrojList(cesta));
        this.platiOd = cesta.getPlatiod();
        this.platiDo = cesta.getPlatido();
        this.selectedZdr = null;
        this.selectedZdroje = new ArrayList<>();
    }

    public void initHelperZdroj(Date platiOd, Date platiDo, Typzdroje typZdroje) {
        TypZdrojeEnum typZdrojeEnum = TypZdrojeEnum.VOZIDLO;
        if (typZdroje != null) {
            for (TypZdrojeEnum tze : TypZdrojeEnum.values()) {
                if (tze.getId() == typZdroje.getTypzdr()) {
                    typZdrojeEnum = tze;
                    break;
                }
            }
        }
        zdrojList = new ArrayList<>(zdrojeFacade.findAllWhereTypZdroje(typZdrojeEnum, platiOd, platiDo));
        this.platiOd = platiOd;
        this.platiDo = platiDo;
        this.selectedZdr = null;
        this.selectedZdroje = new ArrayList<>();
    }

    /**
     * @return the cal
     */
    public Calendar getCal() {
        return cal;
    }

    /**
     * @param cal the cal to set
     */
    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    /**
     * @return the selectedZdr
     */
    public Zdroj getSelectedZdr() {
        return selectedZdr;
    }

    /**
     * @param selectedZdr the selectedZdr to set
     */
    public void setSelectedZdr(Zdroj selectedZdr) {
        this.selectedZdr = selectedZdr;
    }

    /**
     * @return the zdrojList
     */
    public ArrayList<Zdroj> getZdrojList() {
        if (zdrojList == null) {
            zdrojList = new ArrayList<>(zdrojeFacade.findAll());
        }
        return zdrojList;
    }

    /**
     * @param zdrojList the zdrojList to set
     */
    public void setZdrojList(ArrayList<Zdroj> zdrojList) {
        this.zdrojList = zdrojList;
    }

    /**
     * @return the selectedZdroje
     */
    public ArrayList<Zdroj> getSelectedZdroje() {
        return selectedZdroje;
    }

    /**
     * @param selectedZdroje the selectedZdroje to set
     */
    public void setSelectedZdroje(ArrayList<Zdroj> selectedZdroje) {
        this.selectedZdroje = selectedZdroje;
    }

    /**
     * @return the selectedRez
     */
    public Rezervace getSelectedRez() {
        return selectedRez;
    }

    /**
     * @param selectedRez the selectedRez to set
     */
    public void setSelectedRez(Rezervace selectedRez) {
        this.selectedRez = selectedRez;
    }

    /**
     * @return the rezervaceList
     */
    public ArrayList<Rezervace> getRezervaceList() {
        return rezervaceList;
    }

    /**
     * @param rezervaceList the rezervaceList to set
     */
    public void setRezervaceList(ArrayList<Rezervace> rezervaceList) {
        this.rezervaceList = rezervaceList;
    }

    public ArrayList<Rezervace> getRezervaceList(Zdroj zdroj) {
        this.rezervaceList = new ArrayList<>();
        if (zdroj.getRezervaceList() != null) {
            for (Rezervace rez : zdroj.getRezervaceList()) {
                if (rez.getPlatiod().before(this.platiDo) && rez.getPlatido().after(this.platiOd) && rez.getIdcest() != null) {
                    getRezervaceList().add(rez);
                }
            }
        }
        return getRezervaceList();
    }

    public String getRezervaceHtml(Zdroj zdroj) {
        StringBuffer sb = new StringBuffer();
        java.text.SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy HH:mm");
        if (zdroj.getRezervaceList() != null) {
            for (Rezervace rez : zdroj.getRezervaceList()) {
                if (rez.getPlatiod().before(this.platiDo) && rez.getPlatido().after(this.platiOd) && rez.getIdcest() != null) {
                    sb.append("<div>" );
                    sb.append(sdf.format(rez.getPlatiod())).append("-");
                    sb.append(sdf.format(rez.getPlatido())).append("; ");
                    sb.append(rez.getIdcest().getPopis()).append("; ");
                    sb.append(rez.getIdcest().getKomentar().substring(0, Math.min(10, rez.getIdcest().getKomentar().length() ) ));
                    sb.append("</div>" );                    
                }
            }
        }
        return sb.toString();
    }

    public void submitSelectedZdr() {
        RequestContext.getCurrentInstance().closeDialog(this.selectedZdr);
    }

    public void submitSelectedZdroje() {
        RequestContext.getCurrentInstance().closeDialog(this.selectedZdroje);
    }

    public void cancelSelectedZdr() {
        RequestContext.getCurrentInstance().closeDialog(null);
    }

    public void showDetailRez(Rezervace rez) {
        RequestContext.getCurrentInstance()
                .openDialog("/helper/helperZdrojDetailRez", null, null);
    }

}
