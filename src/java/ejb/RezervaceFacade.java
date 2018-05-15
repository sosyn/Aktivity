/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Cesta;
import entity.Osoba;
import entity.Rezervace;
import entity.Rezervace_;
import entity.Schvaleni;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

/**
 *
 * @author Ivo
 */
@Stateless
public class RezervaceFacade extends AbstractFacade<Rezervace> {

    @PersistenceContext(unitName = "AktivityPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RezervaceFacade() {
        super(Rezervace.class);
    }

    public List<Rezervace> findRezervaceWhereCesta(Cesta cesta) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        javax.persistence.criteria.Root<Rezervace> rt = cq.from(Rezervace.class);
        cq.select(rt);
        if (cesta != null) {
            cq.where(cb.equal(rt.get(Rezervace_.idcest), cesta));
        }
        javax.persistence.TypedQuery<Rezervace> q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }

    public List<Rezervace> getRezervaceOdDo(Date platiOd, Date platiDo) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery(Rezervace.class);
        javax.persistence.criteria.Root<Rezervace> rezRoot = cq.from(Rezervace.class);
        cq.select(rezRoot);

        Path<Date> pathPlatiOd = rezRoot.get(Rezervace_.platiod);
        Path<Date> pathPlatiDo = rezRoot.get(Rezervace_.platido);
        Predicate prediPlatiOdDo = cb.and(
                cb.or(cb.isNull(pathPlatiOd), cb.not(cb.greaterThan(pathPlatiOd, platiDo))),
                cb.or(cb.isNull(pathPlatiDo), cb.greaterThan(pathPlatiDo, platiOd))
        );

        Predicate prediWhere = cb.and(prediPlatiOdDo);

        cq.where(prediWhere);
        cq.orderBy(cb.asc(rezRoot.get(Rezervace_.platiod)));

        List<Rezervace> rl = getEntityManager().createQuery(cq).getResultList();
//        System.out.println("findAllWhereTypZdroje.size()="+rl.size());
        return rl;
    }

    public ArrayList<Rezervace> findRezervaceWhere(Properties prop) {

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
        StringBuilder selRe = new StringBuilder(
                "SELECT * "
                + "FROM rezervace re "
                + "     LEFT JOIN dispecerpol dp ON re.idzdr=dp.idzdr AND re.platiod<=dp.platido AND re.platido>=dp.platiod "
                + "     LEFT JOIN dispecerhl dh ON dp.iddisphl=dh.id OR dp.iddisphl=dh.iddisphl "
                + "WHERE ");
        selRe.append("dh.idoso='")
                .append(((Osoba) prop.get("osoba")).getId())
                .append("' ");
        // Stav schvaleni
        if ((boolean) prop.get("nezpracovane") || (boolean) prop.get("schvalene") || (boolean) prop.get("zamitnute")) {
            selRe.append("AND (");
            if ((boolean) prop.get("nezpracovane")) {
                selRe.append("((SELECT count(*) FROM schvaleni sch WHERE sch.idrez=re.id  AND sch.idcest=re.idcest  )=0) ");
            }
            if ((boolean) prop.get("schvalene")) {
                if ((boolean) prop.get("nezpracovane")) {
                    selRe.append("OR ");
                }
                selRe.append("((SELECT sch1.stav FROM schvaleni sch1 WHERE sch1.idrez=re.id AND sch1.idcest=re.idcest ORDER BY sch1.platiod DESC LIMIT 1)='1') ");
            }
            if ((boolean) prop.get("zamitnute")) {
                if ((boolean) prop.get("nezpracovane") || (boolean) prop.get("schvalene")) {
                    selRe.append("OR ");
                }
                selRe.append("((SELECT sch2.stav FROM schvaleni sch2 WHERE sch2.idrez=re.id AND sch2.idcest=re.idcest ORDER BY sch2.platiod DESC LIMIT 1)='2') ");
            }
            selRe.append(") ");
        }

        // Vedouci nebo zastupce
        if ((boolean) prop.get("vedouci") || (boolean) prop.get("zastupce")) {
            selRe.append("AND (");
            if ((boolean) prop.get("vedouci")) {
                selRe.append(" (dh.iddisphl IS NULL OR dh.iddisphl=dh.id) ");
            }
            if ((boolean) prop.get("zastupce")) {
                if ((boolean) prop.get("vedouci")) {
                    selRe.append("OR ");
                }
                selRe.append("(dh.iddisphl IS NOT NULL AND dh.iddisphl<>dh.id) ");
            }
            selRe.append(") ");
        }
        // Plati OD-DO
        if ((boolean) prop.get("platiOdDo")) {
            selRe.append("AND( re.platiod <= '")
                    .append(String.format("%1$td %1$tm,%1$tY %1$tR", prop.get("platiDo")))
                    .append("'  AND re.platido >='")
                    .append(String.format("%1$td %1$tm,%1$tY %1$tR", prop.get("platiOd")))
                    .append("') ");
        }
        selRe.append("ORDER BY re.platiod");
        System.out.println("selRe=" + selRe.toString());
        Query q = em.createNativeQuery(selRe.toString(), Rezervace.class);
        List<Rezervace> listRe = q.getResultList();
        return new ArrayList<>(listRe);

//        ArrayList<Ucastnik> rl = new ArrayList<>();
//        List<Object[]> listUc = q.getResultList();
//        Ucastnik uc;
//        for (Object[] obj : listUc) {
//            uc = em.find(Ucastnik.class, (UUID) obj[0]);
//            rl.add(uc);
//        }
//        return rl;
    }

    public void insSchvaleni(Osoba osoba, Rezervace rez, int stav, int vedZast) {
        String insSchvaleni
                = "INSERT INTO aktivity.public.schvaleni "
                + "(id, idoso, idcest, iducast, idrez, stav, komentar, uroven, popis, platiod, platido) "
                + "VALUES (?::uuid, ?::uuid, ?::uuid, ?::uuid, ?::uuid, ?, ?, ?, ?, ?, ?)";
        Date datum = new Date();
        Schvaleni schv = new Schvaleni();
        schv.setNewEntity(true);
        schv.setIdoso(osoba);
        schv.setIdcest(rez.getIdcest());
        schv.setIducast(null);
        schv.setIdrez(rez);
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

    public int urovenOsobaRez(Osoba osoba, Rezervace rez) {
        int uroven = 0;
        String selUroven
                = "SELECT dh.id,dh.iddisphl "
                + "FROM dispecerhl dh LEFT JOIN dispecerpol dp ON dh.id=dp.iddisphl OR dh.iddisphl=dp.iddisphl "
                + "WHERE dh.idoso=?  AND dp.idzdr= ?  AND dp.platido>=? AND dp.platiod<=? "
                + "ORDER BY dh.iddisphl ASC LIMIT 1";
        Query q = em.createNativeQuery(selUroven)
                .setParameter(1, osoba.getId())
                .setParameter(2, rez.getIdzdr().getId())
                .setParameter(3, rez.getPlatiod())
                .setParameter(4, rez.getPlatido());
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
