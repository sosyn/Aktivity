/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.util;

import java.util.Calendar;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import jsf.cesty.CestaForm;

/**
 *
 * @author Ivo
 */
@ManagedBean
public class ValidatorCestaOd implements Validator {

    private Calendar cal = Calendar.getInstance(Locale.getDefault());

    @Inject
    CestaForm cestaForm;

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {

//        Map<String, Object> reqMap = fc.getExternalContext().getRequestMap();
//        for (String key : reqMap.keySet()) {
//            System.out.println("  Exter.. key=" + key + " |reqMap.get(key)=" + reqMap.get(key));
//        }
//        System.out.println(" cestaForm=" + cestaForm
//                + "| cestaForm.getPlatiOd()=" + cestaForm.getPlatiOd()
//                + "| cestaForm.getPlatiDo()=" + cestaForm.getPlatiDo());

        if (cestaForm.getCesta().getPlatiod().after(cestaForm.getCesta().getPlatido())) {
            this.cal.setTime(cestaForm.getCesta().getPlatiod());
            if (cal.get(Calendar.HOUR_OF_DAY) < 17) {
                cal.set(Calendar.HOUR_OF_DAY, 17);
            }
            cestaForm.getCesta().setPlatido(this.cal.getTime());
        }

//        System.out.println(" uic.getAttributes().get('cestaDo')=" + uic.getAttributes().get("cestaDo"));
//        Map<String, Object> uicAttrMap = uic.getAttributes();
//        for (String attrKey : uicAttrMap.keySet()) {
//            System.out.println(" UIC -- attrKey=" + attrKey + "| attrMap.get(attrKey)=" + uicAttrMap.get(attrKey));
//        }
//        List<UIComponent> uicChildren = uic.getChildren();
//        for (UIComponent uIComponent : uicChildren) {
//            System.out.println("uIComponent.getFamily()=" + uIComponent.getFamily()
//                    + "| uIComponent.getId()=" + uIComponent.getId()
//                    + "| uIComponent.getClientId()=" + uIComponent.getClientId()
//                    + "| uIComponent.getClientId(fc)=" + uIComponent.getClientId(fc)
//            );
//            Map<String, Object> attrMap = uIComponent.getAttributes();
//            for (String attrKey : attrMap.keySet()) {
//                System.out.println(" - ui attrKey=" + attrKey + "| attrMap.get(attrKey)=" + attrMap.get(attrKey));
//            }
//        }
//        List<UIComponent> vrChildren = fc.getViewRoot().getChildren();
//        for (UIComponent uIComponent : vrChildren) {
//            System.out.println("VR... uIComponent.getFamily()=" + uIComponent.getFamily()
//                    + "| uIComponent.getId()=" + uIComponent.getId()
//                    + "| uIComponent.getClientId()=" + uIComponent.getClientId()
//                    + "| uIComponent.getClientId(fc)=" + uIComponent.getClientId(fc)
//            );
//            Map<String, Object> attrMap = uIComponent.getAttributes();
//            for (String attrKey : attrMap.keySet()){
//                System.out.println(" VR - ui attrKey="+attrKey + "| attrMap.get(attrKey)="+attrMap.get(attrKey));               
//            }
//        }

    }
}
