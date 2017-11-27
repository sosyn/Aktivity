/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.util;

import entity.Osoba;
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
@Named("JSFConverterOsoba")
@Stateless
public class JSFConverterOsoba implements Converter {

    @EJB
    private ejb.OsobaFacade ejbOsobaFacade;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {

        UUID uuid = UUID.fromString(string);
        Osoba osoba = this.ejbOsobaFacade.find(uuid);
        return osoba;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null) {
            return ((Osoba) o).getId().toString();
        } else {
            return null;
        }
    }

}
