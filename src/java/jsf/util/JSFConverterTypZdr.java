/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.util;

import entity.Typzdroje;
import java.util.UUID;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import jsf.spravce.Zdroje;

/**
 *
 * @author Ivo
 */
@FacesConverter("JSFConverterTypZdr")
public class JSFConverterTypZdr implements Converter {

    @Inject
    Zdroje zdroje;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        UUID id = UUID.fromString(string);
        for (Typzdroje typZdroj : zdroje.getTypZdrList()) {
            if (typZdroj.getId().equals(id)) {
                return typZdroj;
            }
        }
        return null;
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
