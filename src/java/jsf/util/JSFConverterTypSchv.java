/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.util;

import entity.Typschv;
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
@Named("JSFConverterTypSchv")
@Stateless
public class JSFConverterTypSchv implements Converter {

    @EJB
    private ejb.TypSchvFacade ejbTypSchvFacade;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Integer id = Integer.valueOf(string);
        Typschv typSchv = this.ejbTypSchvFacade.find(id);
        return typSchv;
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
