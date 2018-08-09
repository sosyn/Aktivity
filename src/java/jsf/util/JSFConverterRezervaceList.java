/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.util;

import entity.Osoba;
import entity.Rezervace;
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
@Named("JSFConverterRezervaceList")
@Stateless
public class JSFConverterRezervaceList implements Converter {

    @EJB
    private ejb.OsobaFacade ejbRezervacFacade;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {

        UUID id = UUID.fromString(string);
        Osoba osoba = this.ejbRezervacFacade.find(id);
        return osoba;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null) {
            return ((Rezervace) o).getId().toString();
        } else {
            return null;
        }
    }

}
