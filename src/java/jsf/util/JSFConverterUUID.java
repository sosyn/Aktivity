/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.util;

import java.util.UUID;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Ivo
 */
@FacesConverter("JSFConverterUUID")
public class JSFConverterUUID implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return UUID.fromString(string);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return ((UUID) o).toString();
    }

}
