/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.spravce;

import ejb.DAOdispecer;
import entity.Dispecerhl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    /**
     * @return the dispecerHl
     */
    public Dispecerhl getDispecerHl() {
        return this.daoDispecer.getDispecerHl();
    }

    /**
     * @param dispecerHl the dispecerHl to set
     */
    public void setDispecerHl(Dispecerhl dispecerHl) {
        this.daoDispecer.setDispecerHl(dispecerHl);
    }

    /**
     * @return the dispecerHlList
     */
    public List<Dispecerhl> getDispecerHlList() {
        return this.daoDispecer.getDispecerHlList();

    }

    /**
     * @param dispecerHlList the dispecerHlList to set
     */
    public void setDispecerHlList(ArrayList<Dispecerhl> dispecerHlList) {
        this.daoDispecer.setDispecerHlList(dispecerHlList);
    }

    public Dispecerhl getDispecerHl(Object id) {
        return this.daoDispecer.getEjbDispHlFacade().find(id);
    }

    public boolean isButtonEnabled(String param) {
        switch (param) {
            case "new":
                return true;
            case "edit":
                return this.daoDispecer.getDispecerHl() != null;
            case "delete":
                return this.daoDispecer.getDispecerHl() != null;
        }
        return true;
    }

    public void rowDispecerHlSelectded(Dispecerhl dispecerHl) {
    }

    public String newDispecerHl() {
        String detailDispecerHl = "/spravce/dispecerForm";
        daoDispecer.fullDispecerHl(0);
        return detailDispecerHl;
    }

    public String editDispecerHl() {
        String detailDispecerHl = "/spravce/dispecerForm";
        daoDispecer.fullDispecerHl(1);
        return detailDispecerHl;
    }

    public String delDispecerHl() {
        try {
            this.daoDispecer.delete();
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
