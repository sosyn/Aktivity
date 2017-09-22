/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.util;

import entity.Typzdroje;
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
@Named("JSFConverterTypZdr")
@Stateless
public class JSFConverterTypZdr implements Converter {

    @EJB
    private ejb.TypZdrFacade ejbTypZdrFacade;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        UUID uuid = UUID.fromString(string);
        Typzdroje typZdroj = this.ejbTypZdrFacade.find(uuid);
        return typZdroj;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null) {
            return ((Typzdroje) o).getId().toString();
        } else {
            return null;
        }
    }

}
