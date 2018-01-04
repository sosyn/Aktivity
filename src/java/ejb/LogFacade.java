/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Aktivity;
import entity.Cesta;
import entity.Log;
import entity.Osoba;
import entity.Rezervace;
import entity.Ucastnik;
import entity.Zdroj;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ivo
 */
@Stateless
public class LogFacade extends AbstractFacade<Zdroj> {
    
    @PersistenceContext(unitName = "AktivityPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public LogFacade() {
        super(Zdroj.class);
    }
    
    public void log(UUID tblid, String tblname, String popis, Aktivity idakt, Cesta idcest, Osoba idoso, Rezervace idrez, Ucastnik iducast, Zdroj idzdr) {
        Log log = new Log();
        log.setTblid(UUID.fromString(tblid.toString()));
//       log.setTblid(null);
        log.setTblname(tblname);
        log.setPopis(popis);
        log.setIdakt(idakt);
        log.setIdcest(idcest);
        log.setIdoso(idoso);
        log.setIdrez(idrez);
        log.setIducast(iducast);
        log.setIdzdr(idzdr);
        em.persist(log);
    }
    public void log(UUID tblid, String tblname, String popis) {
        this.log(tblid, tblname, popis, null, null, null, null, null, null);
    }
}
