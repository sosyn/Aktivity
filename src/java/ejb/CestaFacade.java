/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Cesta;
import entity.Cesta_;
import entity.Osoba;
import entity.Rezervace;
import entity.Ucastnik;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ivo
 */
@Stateless
public class CestaFacade extends AbstractFacade<Cesta> {

    @PersistenceContext(unitName = "AktivityPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CestaFacade() {
        super(Cesta.class);
    }

    public List<Cesta> findOsoba(Osoba osoba) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        javax.persistence.criteria.Root<Cesta> rt = cq.from(Cesta.class);
        cq.select(rt);
        cq.where(cb.equal(rt.get(Cesta_.idoso), osoba));
        javax.persistence.TypedQuery<Cesta> q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }

    public boolean saveCestaList(ArrayList<Cesta> cestaList) {
        for (Cesta cestaLocal : cestaList) {
            saveCesta(cestaLocal);
        }
        return true;
    }

    public boolean saveCesta(Cesta cesta, ArrayList<Ucastnik> ucastnikList, ArrayList<Rezervace> rezervaceList) {
        try {
            saveCesta(cesta);
            saveUcatnikList(ucastnikList);
            saveRezervaceList(rezervaceList);
        } catch (Exception e) {
//              em.getTransaction().rollback();
            throw e;
        }
        return true;
    }

    public boolean saveUcatnikList(ArrayList<Ucastnik> ucastnikList) {
        for (Ucastnik ucastnikLocal : ucastnikList) {
            saveUcatnik(ucastnikLocal);
        }
        return true;
    }

    public boolean saveRezervaceList(ArrayList<Rezervace> rezervaceList) {
        for (Rezervace rezervaceLocal : rezervaceList) {
        }
        return true;
    }

    public boolean saveCesta(Cesta cesta) {
        // Cesta
        String insCesta = "INSERT INTO aktivity.public.CESTA "
                + "(id, idtypzdr, idoso, komentar, zaloha, POPIS, PLATIOD, PLATIDO)"
                + "VALUES (CAST(:id AS uuid), CAST(:idtypzdr AS uuid), CAST(:idoso AS uuid), :komentar, :zaloha, :POPIS, :PLATIOD, :PLATIDO)";
        String updCesta = "UPDATE aktivity.public.CESTA SET "
                + "idtypyzdr=CAST(:idtypzdr AS uuid), idoso=CAST(:idoso AS uuid), komentar=:komentar, zaloha=:zaloha, POPIS=:POPIS, PLATIOD=:PLATIOD, PLATIDO=:PLATIDO"
                + " WHERE id=CAST(:id AS uuid)";
        String delCesta = "DELETE aktivity.public.CESTA WHERE id=CAST(:id AS uuid)";

        Query q = em.createNativeQuery(cesta.isNewEntity() ? insCesta : updCesta);
        q.setParameter("id", cesta.getId());
        q.setParameter("idtypzdr", cesta.getIdtypzdr().getId());
        q.setParameter("idoso", cesta.getIdoso().getId());
        q.setParameter("komentar", cesta.getKomentar());
        q.setParameter("POPIS", cesta.getPopis());
        q.setParameter("PLATIOD", cesta.getPlatiod());
        q.setParameter("PLATIDO", cesta.getPlatido());
//            em.getTransaction().begin();
        try {
            q.executeUpdate();
        } catch (Exception e) {
//              em.getTransaction().rollback();
            throw e;
        }
//            em.getTransaction().commit();

        return true;
    }

    public boolean saveUcatnik(Ucastnik ucastnik) {
        // Ucastnik
        String insUcast = "INSERT INTO aktivity.public.UCASTNIK"
                + "(id,idoso, idtypucast, idcest, POPIS, PLATIOD, PLATIDO)"
                + "VALUES (CAST(:id AS uuid), CAST(:idoso AS uuid), CAST(:idtypucast AS uuid), CAST(:idcest AS uuid), :POPIS, :PLATIOD, :PLATIDO)";
        String updUcast = "UPDATE aktivity.public.UCASTNIK SET "
                + " idoso=CAST(:idoso AS uuid), idtypucast=CAST(:idtypucast AS uuid), idcest=CAST(:idcest AS uuid), POPIS=:POPIS, PLATIOD=:PLATIOD, PLATIDO=:PLATIDO"
                + " WHERE id=CAST(:id AS uuid)";
        String delUcast = "DELETE aktivity.public.UCASTNIK WHERE id=CAST(:id AS uuid)";

        Query q = em.createNativeQuery(ucastnik.isNewEntity() ? insUcast : updUcast);
        q.setParameter("id", ucastnik.getId());
        q.setParameter("idoso", ucastnik.getIdoso().getId());
        q.setParameter("idtypucast", ucastnik.getIdtypucast().getId());
        q.setParameter("idcest", ucastnik.getIdcest().getId());
        q.setParameter("POPIS", ucastnik.getPopis());
        q.setParameter("PLATIOD", ucastnik.getPlatiod());
        q.setParameter("PLATIDO", ucastnik.getPlatido());
//            em.getTransaction().begin();
        try {
            q.executeUpdate();
        } catch (Exception e) {
//              em.getTransaction().rollback();
            throw e;
        }
//            em.getTransaction().commit();

        return true;
    }

    public boolean saveRezervace(Rezervace rezervace) {
        // Rezervace
        String insRez = "INSERT INTO aktivity.public.REZERVACE"
                + "(id,idakt, idzdr, idcest, komentar, POPIS, PLATIOD, PLATIDO)"
                + "VALUES (CAST(:id AS uuid), CAST(:idakt AS uuid), CAST(:idzdr AS uuid), CAST(:idcest AS uuid), komentar, :POPIS, :PLATIOD, :PLATIDO)";
        String updRez = "UPDATE aktivity.public.REZERVACE SET "
                + " idakt=CAST(:idakt AS uuid), idtzdr=CAST(:idzdr AS uuid), idcest=CAST(:idcest AS uuid), komentar=:komentar, POPIS=:POPIS, PLATIOD=:PLATIOD, PLATIDO=:PLATIDO"
                + " WHERE id=CAST(:id AS uuid)";
        String delRez = "DELETE aktivity.public.REZERVACE WHERE id=CAST(:id AS uuid)";

        Query q = em.createNativeQuery(rezervace.isNewEntity() ? insRez : updRez);
        q.setParameter("id", rezervace.getId());
        q.setParameter("idakt", rezervace.getIdakt().getId());
        q.setParameter("idzdr", rezervace.getIdzdr().getId());
        q.setParameter("idcest", rezervace.getIdcest().getId());
        q.setParameter("komentar", rezervace.getKomentar());
        q.setParameter("POPIS", rezervace.getPopis());
        q.setParameter("PLATIOD", rezervace.getPlatiod());
        q.setParameter("PLATIDO", rezervace.getPlatido());
//            em.getTransaction().begin();
        try {
            q.executeUpdate();
        } catch (Exception e) {
//              em.getTransaction().rollback();
            throw e;
        }
//            em.getTransaction().commit();

        return true;
    }

}
