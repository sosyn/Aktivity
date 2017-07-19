/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.util;

import entity.Typucast;
import java.util.ArrayList;
import java.util.UUID;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Ivo
 */
@FacesConverter("JSFConverterTypUcast")
public class JSFConverterTypUcast implements Converter {
    
    @EJB
    private ejb.TypUcastFacade ejbTypUcastFacade;
    
    private ArrayList<Typucast> typUcastList = null;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        System.out.println(" string=" + string);
        UUID uuid = UUID.fromString(string);
        for (Typucast typucast : getTypUcastList() ) {
            if (typucast.getId().equals(uuid)) {
                return typucast;
            }
        }
        return null;
    }
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        System.out.println(" o=" + o);
        if (o != null && o instanceof Typucast) {
            return ((Typucast) o).getId().toString();
        } else {
            return null;
        }
    }

    /**
     * @return the typUcastList
     */
    public ArrayList<Typucast> getTypUcastList() {
        if (this.typUcastList == null) {
            this.typUcastList = new ArrayList<>(ejbTypUcastFacade.findAll());
        }
        return typUcastList;
    }

    /**
     * @param typUcastList the typUcastList to set
     */
    public void setTypUcastList(ArrayList<Typucast> typUcastList) {
        this.typUcastList = typUcastList;
    }
    
}
