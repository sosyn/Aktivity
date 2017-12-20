/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.util;

import entity.Zdroj;
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
@Named("JSFConverterZdroj")
@Stateless
public class JSFConverterZdroj implements Converter {

    @EJB
    private ejb.ZdrojeFacade ejbZdrojeFacade;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {

        Integer id = Integer.valueOf(string);
        Zdroj zdroj = this.ejbZdrojeFacade.find(id);
        return zdroj;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null) {
            return ((Zdroj) o).getId().toString();
        } else {
            return null;
        }
    }

}
