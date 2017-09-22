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
import javax.ejb.Stateless;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 *
 * @author Ivo
 */
@Named("JSFConverterTypUcast")
@Stateless
public class JSFConverterTypUcast implements Converter {

    @EJB
    private ejb.TypUcastFacade ejbTypUcastFacade;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        // System.out.println(" string=" + string);
        UUID uuid = UUID.fromString(string);
        Typucast typucast = this.ejbTypUcastFacade.find(uuid);
        return typucast;
    }
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        // System.out.println(" o=" + o);
        if (o != null && o instanceof Typucast) {
            return ((Typucast) o).getId().toString();
        } else {
            return null;
        }
    }
}
