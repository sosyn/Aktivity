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

    public List<Cesta> findCestyOsoba(Osoba osoba) {
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

    public boolean saveCesta(Cesta cesta, List<Ucastnik> ucastnikList, List<Rezervace> rezervaceList) {
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

    public boolean saveUcatnikList(List<Ucastnik> ucastnikList) {
        for (Ucastnik ucastnikLocal : ucastnikList) {
            saveUcatnik(ucastnikLocal);
        }
        return true;
    }

    public boolean saveRezervaceList(List<Rezervace> rezervaceList) {
        for (Rezervace rezervaceLocal : rezervaceList) {
            saveRezervace(rezervaceLocal);
        }
        return true;
    }

    public boolean saveCesta(Cesta cesta) {
        // Cesta
        String insCesta = "INSERT INTO aktivity.public.CESTA "
                + "(idtypzdr, idoso, komentar, zaloha, POPIS, PLATIOD, PLATIDO,id)"
                + "VALUES (?::uuid,?::uuid,?,?::numeric,?,?,?,cast(? AS uuid))";
        String updCesta = "UPDATE aktivity.public.CESTA SET "
                + "idtypzdr = ?::uuid, idoso = ?::uuid, komentar = ?, zaloha = ?::numeric, POPIS = ?, PLATIOD = ?, PLATIDO = ?"
                + " WHERE id= cast(? AS uuid)";
        String delCesta = "DELETE aktivity.public.CESTA WHERE id=?";

        Query q = em.createNativeQuery(cesta.isNewEntity() ? insCesta : updCesta)
        .setParameter(1, cesta.getIdtypzdr().getId())
        .setParameter(2, cesta.getIdoso().getId())
        .setParameter(3, cesta.getKomentar())
        .setParameter(4, cesta.getZaloha())
        .setParameter(5, cesta.getPopis())
        .setParameter(6, cesta.getPlatiod())
        .setParameter(7, cesta.getPlatido())
        .setParameter(8, cesta.getId());
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
                + "(idoso, idtypucast, idcest, POPIS, PLATIOD, PLATIDO,id)"
                + "VALUES (?::uuid,?::uuid,?::uuid, ?,?,?,?::uuid)";
        String updUcast = "UPDATE aktivity.public.UCASTNIK SET "
                + " idoso=?::uuid, idtypucast=?::uuid,idcest=?::uuid,POPIS=?,PLATIOD=?,PLATIDO=?"
                + " WHERE id=?::uuid";
        String delUcast = "DELETE aktivity.public.UCASTNIK WHERE id=CAST(? AS uuid)";

        Query q = em.createNativeQuery(ucastnik.isNewEntity() ? insUcast : updUcast)
        .setParameter(1, ucastnik.getIdoso().getId())
        .setParameter(2, ucastnik.getIdtypucast().getId())
        .setParameter(3, ucastnik.getIdcest().getId())
        .setParameter(4, ucastnik.getPopis())
        .setParameter(5, ucastnik.getPlatiod())
        .setParameter(6, ucastnik.getPlatido())
        .setParameter(7, ucastnik.getId());
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
                + "(idakt, idzdr, idcest, komentar, POPIS, PLATIOD, PLATIDO,id)"
                + "VALUES (?::uuid,?::uuid,?::uuid,?,?,?,?,?::uuid)";
        String updRez = "UPDATE aktivity.public.REZERVACE SET "
                + " idakt=?::uuid,idzdr=?::uuid,idcest=?::uuid,komentar=?,POPIS=?,PLATIOD=?,PLATIDO=?"
                + " WHERE id=?::uuid";
        String delRez = "DELETE aktivity.public.REZERVACE WHERE id=?::uuid";

        Query q = em.createNativeQuery(rezervace.isNewEntity() ? insRez : updRez);
        q.setParameter(1, rezervace.getIdakt().getId());
        q.setParameter(2, rezervace.getIdzdr().getId());
        q.setParameter(3, rezervace.getIdcest().getId());
        q.setParameter(4, rezervace.getKomentar());
        q.setParameter(5, rezervace.getPopis());
        q.setParameter(6, rezervace.getPlatiod());
        q.setParameter(7, rezervace.getPlatido());
        q.setParameter(8, rezervace.getId());
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
