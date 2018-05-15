/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Cesta;
import entity.Osoba;
import entity.Schvaleni;
import entity.Ucastnik;
import entity.Ucastnik_;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ivo
 */
@Stateless
public class UcastnikFacade extends AbstractFacade<Ucastnik> {

    @PersistenceContext(unitName = "AktivityPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UcastnikFacade() {
        super(Ucastnik.class);
    }

    public List<Ucastnik> findUcastnikyWhereCesta(Cesta cesta) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        javax.persistence.criteria.Root<Ucastnik> rt = cq.from(Ucastnik.class);
        cq.select(rt);
        if (cesta != null) {
            cq.where(cb.equal(rt.get(Ucastnik_.idcest), cesta));
        }
        javax.persistence.TypedQuery<Ucastnik> q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }

    public ArrayList<Ucastnik> findUcastnikyWhere(Properties prop) {

//        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
//        javax.persistence.criteria.Root<Ucastnik> rt = cq.from(Ucastnik.class);
//        cq.select(rt);
//
//        java.util.ArrayList<Order> orderList = new ArrayList<>();
//        orderList.add(cb.asc(rt.get(Ucastnik_.platiod)));
//        orderList.add(cb.asc(rt.get(Ucastnik_.platido)));
//        cq.orderBy(orderList);
//        javax.persistence.TypedQuery<Ucastnik> q = getEntityManager().createQuery(cq);
        StringBuilder selUc = new StringBuilder(
                "SELECT * "
                + "FROM ucastnik uc "
                + "     LEFT JOIN dispecerpol dp ON uc.idoso=dp.idoso AND uc.platiod<=dp.platido AND uc.platido>=dp.platiod "
                + "     LEFT JOIN dispecerhl dh ON dp.iddisphl=dh.id OR dp.iddisphl=dh.iddisphl "
                + "WHERE ");
        selUc.append("dh.idoso='")
                .append(((Osoba) prop.get("osoba")).getId())
                .append("' ");
        // Stav schvaleni
        if ((boolean) prop.get("nezpracovane") || (boolean) prop.get("schvalene") || (boolean) prop.get("zamitnute")) {
            selUc.append("AND (");
            if ((boolean) prop.get("nezpracovane")) {
                selUc.append("((SELECT count(*) FROM schvaleni sch WHERE sch.iducast=uc.id  AND sch.idcest=uc.idcest  )=0) ");
            }
            if ((boolean) prop.get("schvalene")) {
                if ((boolean) prop.get("nezpracovane")) {
                    selUc.append("OR ");
                }
                selUc.append("((SELECT sch1.stav FROM schvaleni sch1 WHERE sch1.iducast=uc.id AND sch1.idcest=uc.idcest  ORDER BY sch1.platiod DESC LIMIT 1)='1') ");
            }
            if ((boolean) prop.get("zamitnute")) {
                if ((boolean) prop.get("nezpracovane") || (boolean) prop.get("schvalene")) {
                    selUc.append("OR ");
                }
                selUc.append("((SELECT sch2.stav FROM schvaleni sch2 WHERE sch2.iducast=uc.id AND sch2.idcest=uc.idcest  ORDER BY sch2.platiod DESC LIMIT 1)='2') ");
            }
            selUc.append(") ");
        }

        // Vedouci nebo zastupce
        if ((boolean) prop.get("vedouci") || (boolean) prop.get("zastupce")) {
            selUc.append("AND (");
            if ((boolean) prop.get("vedouci")) {
                selUc.append(" (dh.iddisphl IS NULL OR dh.iddisphl=dh.id) ");
            }
            if ((boolean) prop.get("zastupce")) {
                if ((boolean) prop.get("vedouci")) {
                    selUc.append("OR ");
                }
                selUc.append("(dh.iddisphl IS NOT NULL AND dh.iddisphl<>dh.id) ");
            }
            selUc.append(") ");
        }
        // Plati OD-DO
        if ((boolean) prop.get("platiOdDo")) {
            selUc.append("AND( uc.platiod <= '")
                    .append(String.format("%1$td %1$tm,%1$tY %1$tR", prop.get("platiDo")))
                    .append("'  AND uc.platido >='")
                    .append(String.format("%1$td %1$tm,%1$tY %1$tR", prop.get("platiOd")))
                    .append("') ");
        }
        selUc.append("ORDER BY uc.platiod");
        System.out.println("selUc=" + selUc.toString());
        Query q = em.createNativeQuery(selUc.toString(), Ucastnik.class);
        List<Ucastnik> listUc = q.getResultList();
        return new ArrayList<>(listUc);

//        ArrayList<Ucastnik> rl = new ArrayList<>();
//        List<Object[]> listUc = q.getResultList();
//        Ucastnik uc;
//        for (Object[] obj : listUc) {
//            uc = em.find(Ucastnik.class, (UUID) obj[0]);
//            rl.add(uc);
//        }
//        return rl;
    }

    public void insSchvaleni(Osoba osoba, Ucastnik uc, int stav, int vedZast) {
        String insSchvaleni
                = "INSERT INTO aktivity.public.schvaleni "
                + "(id, idoso, idcest, iducast, idrez, stav, komentar, uroven, popis, platiod, platido) "
                + "VALUES (?::uuid, ?::uuid, ?::uuid, ?::uuid, ?::uuid, ?, ?, ?, ?, ?, ?)";
        Date datum = new Date();
        Schvaleni schv = new Schvaleni();
        schv.setNewEntity(true);
        schv.setIdoso(osoba);
        schv.setIdcest(uc.getIdcest());
        schv.setIducast(uc);
        // schv.setIdrez(null);
        schv.setStav(stav);
        schv.setKomentar("Schválení: " + osoba.getPopis());
        schv.setUroven(vedZast);
        schv.setPopis(" " + datum);
        schv.setPlatiod(datum);
        schv.setPlatido(datum);

//        em.persist(schv);
        Query q = em.createNativeQuery(insSchvaleni)
                .setParameter(1, schv.getId())
                .setParameter(2, schv.getIdoso() == null ? null : schv.getIdoso().getId())
                .setParameter(3, schv.getIdcest() == null ? null : schv.getIdcest().getId())
                .setParameter(4, schv.getIducast() == null ? null : schv.getIducast().getId())
                .setParameter(5, schv.getIdrez() == null ? null : schv.getIdrez().getId())
                .setParameter(6, schv.getStav())
                .setParameter(7, schv.getKomentar())
                .setParameter(8, schv.getUroven())
                .setParameter(9, schv.getPopis())
                .setParameter(10, schv.getPlatiod())
                .setParameter(11, schv.getPlatido());
        try {
            q.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    public int urovenOsobaUcastnik(Osoba osoba, Ucastnik uc) {
        int uroven = 0;
        String selUroven
                = "SELECT dh.id,dh.iddisphl "
                + "FROM dispecerhl dh LEFT JOIN dispecerpol dp ON dh.id=dp.iddisphl OR dh.iddisphl=dp.iddisphl "
                + "WHERE dh.idoso=?  AND dp.idoso= ?  AND dp.platido>=? AND dp.platiod<=? "
                + "ORDER BY dh.iddisphl ASC LIMIT 1";
        Query q = em.createNativeQuery(selUroven)
                .setParameter(1, osoba.getId())
                .setParameter(2, uc.getIdoso().getId())
                .setParameter(3, uc.getPlatiod())
                .setParameter(4, uc.getPlatido());
        try {
            Object[] result = (Object[]) q.getSingleResult();
            if (result instanceof Object[]) {
                if (result[1] == null) {
                    uroven = 2;
                } else {
                    uroven = 1;
                }
            }
        } catch (NoResultException e) {
            uroven = 0;
        } catch (Exception e) {
            throw e;
        }
        return uroven;
    }
}
