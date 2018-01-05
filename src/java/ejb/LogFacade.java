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
import javax.persistence.Query;

/**
 *
 * @author Ivo
 */
@Stateless
public class LogFacade extends AbstractFacade<Zdroj> {

    @PersistenceContext(unitName = "AktivityPU")
    private EntityManager em;
    final private String logInsert
            = "INSERT INTO aktivity.public.LOG "
            + "(ID,USERMODIFY, PLATIOD, PLATIDO, POPIS, TBLID, TBLNAME,  idakt, idcest, idoso, idrez, iducast, idzdr) "
            + "VALUES (?::uuid, ?, ?, ?, ?, ?::uuid, ?, ?::uuid, ?::uuid, ?::uuid, ?::uuid, ?::uuid, ?::uuid)";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LogFacade() {
        super(Zdroj.class);
    }

    public void log(UUID tblid, String tblname, String popis, Aktivity idakt, Cesta idcest, Osoba idoso, Rezervace idrez, Ucastnik iducast, Zdroj idzdr) {
        Log log = new Log();
        log.setTblid(tblid);
//       log.setTblid(null);
        log.setTblname(tblname);
        log.setPopis(popis);
        log.setAktivity(idakt);
        log.setCesta(idcest);
        log.setOsoba(idoso);
        log.setRezervace(idrez);
        log.setUcastnik(iducast);
        log.setZdroj(idzdr);
//        em.persist(log);

        Query q = em.createNativeQuery(logInsert);
        q.setParameter(1, log.getId());
        q.setParameter(2, log.getUsermodify());
        q.setParameter(3, log.getPlatiod());
        q.setParameter(4, log.getPlatido());
        q.setParameter(5, log.getPopis());
        q.setParameter(6, log.getTblid());
        q.setParameter(7, log.getTblname());
        q.setParameter(8, log.getAktivity() == null ? null : log.getAktivity().getId());
        q.setParameter(9, log.getCesta() == null ? null : log.getCesta().getId());
        q.setParameter(10, log.getOsoba() == null ? null : log.getOsoba().getId());
        q.setParameter(11, log.getRezervace() == null ? null : log.getRezervace().getId());
        q.setParameter(12, log.getUcastnik() == null ? null : log.getUcastnik().getId());
        q.setParameter(13, log.getZdroj() == null ? null : log.getZdroj().getId());
        try {
            q.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    public void log(UUID tblid, String tblname, String popis) {
        this.log(tblid, tblname, popis, null, null, null, null, null, null);
    }
}
