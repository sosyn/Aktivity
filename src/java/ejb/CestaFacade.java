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
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ivo
 */
@Stateless
public class CestaFacade extends AbstractFacade<Cesta> {
    // Cesta

    String insCesta = "INSERT INTO aktivity.public.CESTA "
            + "(idtypzdr, idoso, komentar, zaloha, POPIS, PLATIOD, PLATIDO,id)"
            + "VALUES (?::uuid,?::uuid,?,?::numeric,?,?,?,cast(? AS uuid))";
    String updCesta = "UPDATE aktivity.public.CESTA SET "
            + "idtypzdr = ?::uuid, idoso = ?::uuid, komentar = ?, zaloha = ?::numeric, POPIS = ?, PLATIOD = ?, PLATIDO = ?"
            + " WHERE id= cast(? AS uuid)";
    String delCesta = "DELETE FROM aktivity.public.CESTA WHERE id=?::uuid";
    // Ucastnik
    String insUcast = "INSERT INTO aktivity.public.UCASTNIK"
            + "(idoso, idtypucast, idcest, POPIS, PLATIOD, PLATIDO,id)"
            + "VALUES (?::uuid,?::uuid,?::uuid, ?,?,?,?::uuid)";
    String updUcast = "UPDATE aktivity.public.UCASTNIK SET "
            + " idoso=?::uuid, idtypucast=?::uuid,idcest=?::uuid,POPIS=?,PLATIOD=?,PLATIDO=?"
            + " WHERE id=?::uuid";
    String delUcast = "DELETE FROM aktivity.public.UCASTNIK WHERE id=?::uuid";
    // Rezervace
    String insRez = "INSERT INTO aktivity.public.REZERVACE"
            + "(idakt, idzdr, idcest, komentar, POPIS, PLATIOD, PLATIDO,id)"
            + "VALUES (?::uuid,?::uuid,?::uuid,?,?,?,?,?::uuid)";
    String updRez = "UPDATE aktivity.public.REZERVACE SET "
            + " idakt=?::uuid,idzdr=?::uuid,idcest=?::uuid,komentar=?,POPIS=?,PLATIOD=?,PLATIDO=?"
            + " WHERE id=?::uuid";
    String delRez = "DELETE FROM aktivity.public.REZERVACE WHERE id=?::uuid";

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

//        String selCesty = "SELECT * FROM  aktivity.public.CESTA WHERE aktivity.public.CESTA.idoso=?::uuid";
//        Query q = em.createNativeQuery(selCesty,Cesta.class).setParameter(1, osoba.getId());
        return q.getResultList();
    }

    public ArrayList<Cesta> findCesty(Properties prop) {
        StringBuilder sb = new StringBuilder("SELECT * FROM aktivity.public.cesta ce WHERE ");

        // Pokud neni definovana osoba, nebo neni definovan vztah k ceste [vlastnik|ucastnik] tak vrat prazdnou matici
        if (((Osoba) prop.get("osoba") == null) || (!(boolean) prop.get("vlastnik") && !(boolean) prop.get("ucastnik"))) {
            return new ArrayList<>();
        }
        // Pridat vyber vlastnika a|nebo ucastnika
        sb.append(" ( ");
        // Tvurce-vlastnik cesty
        if ((boolean) prop.get("vlastnik")) {
            sb.append("ce.idoso='").append(((Osoba) prop.get("osoba")).getId().toString()).append("' ");
        } else {
            sb.append(" false ");
        }
        sb.append(" OR ");
        // Ucastnik cesty
        if ((boolean) prop.get("ucastnik")) {
            sb.append("(SELECT count(*) FROM aktivity.public.ucastnik uc WHERE uc.idoso='").append(((Osoba) prop.get("osoba")).getId().toString()).append("' )>0 ");
        } else {
            sb.append(" false ");
        }
        sb.append(" ) ");
        if ((boolean) prop.get("platiOdDo")) {
            // Pridat vyber podle platiOdDo
            sb.append("AND ( ");
            // Cesta zacala Od
            if ((Date) prop.get("platiOd") != null) {
                sb.append(" ce.platido>='").append(String.format("%1$td %1$tm %1$tY %1$tR", (Date) prop.get("platiOd"))).append("' ");
            } else {
                sb.append(" true ");
            }
            sb.append(" AND ");
            // Cesta koncila Do
            if ((Date) prop.get("platiDo") != null) {
                sb.append(" ce.platiod<='").append(String.format("%1$td %1$tm %1$tY %1$tR", (Date) prop.get("platiDo"))).append("' ");
            } else {
                sb.append(" true ");
            }
            sb.append(" ) ");

        }
        // setridit podle data vzestupne
        sb.append("ORDER BY ce.platiod ASC ");
        System.out.println(" findCesty(Properties prop): " + sb.toString());
//            em.getTransaction().begin();
        Query q = em.createNativeQuery(sb.toString(),Cesta.class);
        q.setFlushMode(FlushModeType.COMMIT);
        List<Cesta> listRe = q.getResultList();
        ArrayList<Cesta> localCesty = new ArrayList<>();
        for (Cesta cesta : listRe) {
            localCesty.add(cesta);
        }
        return localCesty;
    }

    public boolean saveCestaList(ArrayList<Cesta> cestaList) {
        for (Cesta cestaLocal : cestaList) {
            saveCesta(cestaLocal);
        }
        return true;
    }

    public boolean saveCesta(Cesta cesta, List<Ucastnik> ucastnikListDel, List<Rezervace> rezervaceListDel) {
        try {
            saveCesta(cesta);
            saveUcatnikList(cesta.getUcastnikList());
            saveUcatnikList(ucastnikListDel);
            saveRezervaceList(cesta.getRezervaceList());
            saveRezervaceList(rezervaceListDel);
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

    /* Ukladani cesta, ucastniku a rezervaci je nutne provest v NativeQuery, protoze je v Query potreba udelat Cast( id<neco> AS uuid)  
    *  a ukladat kazde zvlast (cestu, rezervace a ucastniky)
     */
    public boolean saveCesta(Cesta cesta) {
        Query q = em.createNativeQuery(cesta.isNewEntity() ? insCesta : updCesta)
                .setParameter(1, cesta.getIdtypzdr().getId())
                .setParameter(2, cesta.getIdoso().getId())
                .setParameter(3, cesta.getKomentar())
                .setParameter(4, cesta.getZaloha())
                .setParameter(5, cesta.getPopis())
                .setParameter(6, cesta.getPlatiod())
                .setParameter(7, cesta.getPlatido())
                .setParameter(8, cesta.getId());

        if (cesta.isDelEntity()) {
            q = em.createNativeQuery(delCesta)
                    .setParameter(1, cesta.getId());
        }
//            em.getTransaction().begin();
        q.setFlushMode(FlushModeType.COMMIT);
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

        Query q = em.createNativeQuery(ucastnik.isNewEntity() ? insUcast : updUcast)
                .setParameter(1, ucastnik.getIdoso().getId())
                .setParameter(2, ucastnik.getIdtypucast().getId())
                .setParameter(3, ucastnik.getIdcest().getId())
                .setParameter(4, ucastnik.getPopis())
                .setParameter(5, ucastnik.getPlatiod())
                .setParameter(6, ucastnik.getPlatido())
                .setParameter(7, ucastnik.getId());

        if (ucastnik.isDelEntity()) {
            q = em.createNativeQuery(delUcast)
                    .setParameter(1, ucastnik.getId());
        }
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
        Query q = em.createNativeQuery(rezervace.isNewEntity() ? insRez : updRez);
        q.setParameter(1, rezervace.getIdakt().getId());
        q.setParameter(2, rezervace.getIdzdr().getId());
        q.setParameter(3, rezervace.getIdcest().getId());
        q.setParameter(4, rezervace.getKomentar());
        q.setParameter(5, rezervace.getPopis());
        q.setParameter(6, rezervace.getPlatiod());
        q.setParameter(7, rezervace.getPlatido());
        q.setParameter(8, rezervace.getId());
        if (rezervace.isDelEntity()) {
            q = em.createNativeQuery(delRez)
                    .setParameter(1, rezervace.getId());
        }
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
