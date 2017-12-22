/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.util;

import entity.Typaktivity;
import entity.Typschv;
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
@Named("JSFConverterTypAkt")
@Stateless
public class JSFConverterTypAkt implements Converter {

    @EJB
    private ejb.TypAktFacade ejbTypAktFacade;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        
        UUID id = UUID.fromString(string);
        Typaktivity typAktivity = this.ejbTypAktFacade.find(id);
        return typAktivity;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null) {
            return ((Typschv) o).getId().toString();
        } else {
            return null;
        }
    }
}
