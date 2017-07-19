/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.util;

import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Ivo
 */
@FacesValidator("Aktivity.jsf.OdDoValidator")
public class OdDoValidator implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        Date platiOd = null;
        Date platiDo = null;
        if (uic.getId().equals("typAktOd")) {
            platiOd = (Date) o;
            UIComponent otherComponent = fc.getViewRoot().findComponent("typAktDo");
            if (otherComponent != null) {
                Object otherValue = ((UIInput) otherComponent).getValue();
            }
        }
        if (uic.getId().equals("typAktDo")) {
            platiDo = (Date) o;
            UIComponent otherComponent = fc.getViewRoot().findComponent("typAktDo");
            if (otherComponent != null) {
                Object otherValue = ((UIInput) otherComponent).getValue();
            }
        }
    }
}
