/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Osoba;
import java.security.Principal;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Named;

/**
 *
 * @author Ivo
 */
@Named("loginuser")
@Stateless
public class LoginUser {

    @EJB
    private ejb.OsobaFacade ejbOsobaFacade;
    @Resource
    private SessionContext sctx;

    private String userName = "?";
    private Osoba osoba;

    public void initLoginUser() {
        if (this.sctx == null) {
            System.out.println("SessionContext==null");
        } else {
            Principal ctxPrincipal = sctx.getCallerPrincipal();
            userName = ctxPrincipal.getName();
        }
        System.out.println("userName=" + userName);
        setOsoba(ejbOsobaFacade.findName(userName));
        System.out.println("osoba=" + osoba);

//        try {
//            InitialContext ic = new InitialContext();
//            SessionContext sctxLookup
//                    = (SessionContext) ic.lookup("java:comp/env/sessionContext");
//            System.out.println("look up injected sctx: " + sctxLookup);
//
//        } catch (NamingException ex) {
//            Logger.getLogger(LoginUser.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    /**
     * @return the osoba
     */
    public Osoba getOsoba() {
        return osoba;
    }

    /**
     * @param osoba the osoba to set
     */
    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;
    }
}
