/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.cesty;

import ejb.LoginUser;
import entity.Cesta;
import entity.Osoba;
import jasper.Jasper;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ivo
 */
@Named("cesty")
@SessionScoped
public class Cesty implements Serializable {

    private static final long serialVersionUID = 1L;

    private Calendar cal = Calendar.getInstance(Locale.getDefault());
    private Date platiOd = new Date();
    private Date platiDo = new Date();

    @EJB
    private ejb.CestaFacade ejbCestaFacade;
    @EJB
    Jasper jasperRun;
    @Inject
    LoginUser loginUser;
    @Inject
    CestaForm cestaForm;

    Osoba osoba = null;
    private Cesta cesta = null;
    private ArrayList<Cesta> cesty = new ArrayList<>();

    @PostConstruct
    void init() {
        loginUser.initLoginUser();
        this.osoba = loginUser.getOsoba();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
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

    public void onPlatiOdSelect() {
        if (this.platiOd.after(this.platiDo)) {
            this.platiDo.setTime(platiOd.getTime());
        }
    }

    public void onPlatiDoSelect() {
    }

    /**
     * @return the ejbCestaFacade
     */
    public ejb.CestaFacade getEjbCestaFacade() {
        return ejbCestaFacade;
    }

    /**
     * @param ejbCestaFacade the ejbCestaFacade to set
     */
    public void setEjbCestaFacade(ejb.CestaFacade ejbCestaFacade) {
        this.ejbCestaFacade = ejbCestaFacade;
    }

    /**
     * @return the cesta
     */
    public Cesta getCesta() {
        return cesta;
    }

    /**
     * @param cesta the cesta to set
     */
    public void setCesta(Cesta cesta) {
        this.cesta = cesta;
    }

    /**
     * @return the cesty
     */
    public ArrayList<Cesta> getCesty() {
        this.cesty = new ArrayList<>(ejbCestaFacade.findCestyOsoba(this.osoba));
//        if (this.cesty.isEmpty()) {
//        }
        return cesty;
    }

    /**
     * @param cesty the cesty to set
     */
    public void setCesty(ArrayList<Cesta> cesty) {
        this.cesty = cesty;
    }

    public String printPruvodka() {
        byte[] jsonCesta;
        jsonCesta = json.JsonUtil.getJsonCesta(this.cesta);
//        try {
//            System.out.println("Cesta as JSON: " + new String(jsonCesta, "UTF-8"));
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(Cesty.class.getName()).log(Level.SEVERE, null, ex);
//        }
        byte[] html = jasperRun.makePdf(jsonCesta);
        if (html == null || html.length < 100) {
            return "cesty/cesty";
        }
//        BufferedInputStream fis = null;
        OutputStream out = null;
//        String filename = "c:\\temp\\IBM_Application_Release_and_Deployment_for_Dummies_0.pdf";
//        byte[] bytes = new byte[1000];
//        try {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
        response.reset();
        response.setContentType("text/html; charset=UTF-8");
        response.setDateHeader("Date", new Date().getTime());
        response.setContentLength(html.length);
        try {
            out = response.getOutputStream();
//            fis = new BufferedInputStream(new FileInputStream(filename));
//            response.setContentType("application/octet-stream");
//            response.setContentType("application/pdf");
//            response.addHeader("Content-Disposition", "attachment; filename=\"Pruvodka.pdf\"");
//            while (fis.read(bytes) != -1) {
//                out.write(bytes);
//            }
            out.write(html);
            //out.flush();

        } catch (IOException ex) {
            Logger.getLogger(Cesty.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(Cesty.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            if (out != null) {
                out.close();
            }
//            if (fis != null) {
//                fis.close();
//            }
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException e) {
            Logger.getLogger(Cesty.class.getName()).log(Level.SEVERE, null, e);
        }
        return "/cesty/cesty";
    }

    public String newCesta() {
        if (cestaForm.newCesta(this.cesty)) {
            return "/cesty/cestaForm?faces-redirect=true";
        }
        return null;
    }

    public String editCesta() {
        if (cestaForm.editCesta(this.cesta, this.cesty)) {
            return "/cesty/cestaForm?faces-redirect=true";
        }
        return null;
    }
}
