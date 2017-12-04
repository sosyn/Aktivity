/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.spravce;

import ejb.DAOdispecer;
import entity.Dispecerhl;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jsf.util.JsfUtil;

/**
 *
 * @author Ivo
 */
@Named("dispeceri")
@SessionScoped
public class Dispeceri implements Serializable {

    @Inject
    DAOdispecer daoDispecer;

    
    @PostConstruct
    void initDispeceri() {
        System.out.println("initDispeceri()");
    }

    public boolean isButtonEnabled(String param) {
        switch (param) {
            case "new":
                return true;
            case "edit":
                // Dipecer je vybran a neni to zastupce
                return this.daoDispecer.getDispecerHl() != null && this.daoDispecer.getDispecerHl().getIddisphl()==null ;
            case "delete":
                return this.daoDispecer.getDispecerHl() != null;
        }
        return true;
    }

    public void rowDispecerHlSelectded(Dispecerhl dispecerHl) {
    }

    public String newDispecerHl() {
        String detailDispecerHl = "/spravce/dispecerForm";
        daoDispecer.fillDispecerHl(DAOdispecer.DISPECERHL_NEW);
        return detailDispecerHl;
    }

    public String editDispecerHl() {
        String detailDispecerHl = "/spravce/dispecerForm";
        daoDispecer.fillDispecerHl(DAOdispecer.DISPECERHL_EDIT);
        return detailDispecerHl;
    }

    public String delDispecerHl() {
        try {
            this.daoDispecer.dispHlDelete();
            this.daoDispecer.refreshDispecerHlList();
            JsfUtil.addSuccessMessage("Záznam byl úspěšně smazán.");
        } catch (EJBException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            String msg = "";
            Throwable cause = ex.getCause();
            if (cause != null) {
                msg = cause.getLocalizedMessage();
            }
            if (msg.length() > 0) {
                JsfUtil.addErrorMessage(msg);
            } else {
                JsfUtil.addErrorMessage(ex, "Chyba uložení dat");
            }
            JsfUtil.validationFailed();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex, "Chyba zpracování");
            JsfUtil.validationFailed();
        }
        return null;
    }
}
