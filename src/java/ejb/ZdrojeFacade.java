/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Osoba;
import entity.TypZdrojeEnum;
import entity.Typzdroje_;
import entity.Zdroj;
import entity.Zdroj_;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

/**
 *
 * @author Ivo
 */
@Stateless
public class ZdrojeFacade extends AbstractFacade<Zdroj> {

    @PersistenceContext(unitName = "AktivityPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ZdrojeFacade() {
        super(Zdroj.class);
    }

    public List<Zdroj> findAllWhereTypZdroje(TypZdrojeEnum typZdrojeEnum, Date platiOd, Date platiDo) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery(Zdroj.class);
        javax.persistence.criteria.Root<Zdroj> zdrojRoot = cq.from(Zdroj.class);
        cq.select(zdrojRoot);

        Path<Integer> pathTypZdr = zdrojRoot.get(Zdroj_.idtypzdr).get(Typzdroje_.typzdr);
        Predicate prediTypZdrCar = cb.equal(pathTypZdr, typZdrojeEnum.getId());

        Path<Date> pathPlatiOd = zdrojRoot.get(Zdroj_.platiod);
        Path<Date> pathPlatiDo = zdrojRoot.get(Zdroj_.platido);
        Predicate prediPlatiOdDo = cb.and(
                cb.or(cb.isNull(pathPlatiOd), cb.not(cb.greaterThan(pathPlatiOd, platiDo))),
                cb.or(cb.isNull(pathPlatiDo), cb.greaterThan(pathPlatiDo, platiOd))
        );

        Predicate prediWhere = cb.and(prediTypZdrCar, prediPlatiOdDo);

        cq.where(prediWhere);
        cq.orderBy(cb.asc(zdrojRoot.get(Zdroj_.popis)));

        List<Zdroj> rl = getEntityManager().createQuery(cq).getResultList();
//        System.out.println("findAllWhereTypZdroje.size()="+rl.size());
        return rl;
    }

    /**
     * Metoda vybere všechny použitelné zdroje, kde jsou splněny podmínky: - je
     * to auto - zdroj je platný OD-DO - není ve vyloucenych-nedostupnych
     * zdrojich (napr.jiz jednou vybranych)
     *
     * SELECT zdr.id, zdr.idtypzdr, zdr.idoso, zdr.spz, zdr.komentar,
     * zdr.kapacita, zdr.popis, zdr.platiod, zdr.platido, zdr.timeinsert,
     * zdr.timemodify, zdr.usermodify, ( SELECT count(*) FROM ucastnik uc,
     * rezervace re WHERE zdr.id=re.idzdr and re.platiod>'2018-02-14 11:40:00'
     * and re.platido<'2018-03-18 23:30:00' and uc.idcest=re.idcest ) AS
     * obsazeno FROM zdroj zdr INNER JOIN typzdroje tzdr ON zdr.idtypzdr=tzdr.id
     * WHERE tzdr.cesta='0' and
     * zdr.platiod<='2018-02-14 11:40:00' and zdr.platido>='2018-03-18 23:30:00'
     * and ( SELECT count(*) FROM ucastnik uc, rezervace re WHERE
     * zdr.id=re.idzdr and re.platiod>'2018-02-14 11:40:00' and
     * re.platido<'2018-03-18 23:30:00' and uc.idcest=re.idcest )<= zdr.kapacita
     * ORDER BY zdr.komentar;
     *
     *
     *
     */
    public ArrayList<Zdroj> findAccesibleZdrojList(TypZdrojeEnum typZdrojeEnum, Osoba osoba, Date platiOd, Date platiDo, ArrayList<Zdroj> disableZdrojList) {
//        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery(Zdroj.class);
//        javax.persistence.criteria.Root<Zdroj> zdrojRoot = cq.from(Zdroj.class);
//        cq.select(zdrojRoot);
//
//        Path<Integer> pathTypZdr = zdrojRoot.get(Zdroj_.idtypzdr).get(Typzdroje_.typzdr);
//        Predicate prediTypZdrCar = cb.equal(pathTypZdr, typZdrojeEnum.getId());
//
//        Path<Date> pathPlatiOd = zdrojRoot.get(Zdroj_.platiod);
//        Path<Date> pathPlatiDo = zdrojRoot.get(Zdroj_.platido);
//        Predicate prediPlatiOdDo = cb.and(
//                cb.or(cb.isNull(pathPlatiOd), cb.not(cb.greaterThan(pathPlatiOd, platiDo))),
//                cb.or(cb.isNull(pathPlatiDo), cb.greaterThan(pathPlatiDo, platiOd))
//        );
//
//        Predicate prediWhere = cb.and(prediTypZdrCar, prediPlatiOdDo);
//
//        // Vyloucit nevyzadane zdroje (uz obsazene)
//        if (disableZdrojList != null && !disableZdrojList.isEmpty()) {
//            Path<UUID> pathDisableZdr = zdrojRoot.get(Zdroj_.id);
//            ArrayList<UUID> uuidDisableZdrojIdList = new ArrayList<>();
//            for (Zdroj zdrojDisable : disableZdrojList) {
//                uuidDisableZdrojIdList.add(zdrojDisable.getId());
//            }
//            prediWhere = cb.and(prediWhere, cb.not(pathDisableZdr.in(uuidDisableZdrojIdList)));
//        }
//        cq.where(prediWhere);
//        cq.orderBy(cb.asc(zdrojRoot.get(Zdroj_.popis)));
//        List<Zdroj> rl = getEntityManager().createQuery(cq).getResultList();

        StringBuilder selZdr = new StringBuilder(
                "SELECT zdr.id, zdr.idtypzdr, zdr.idoso, zdr.spz, zdr.komentar,zdr.kapacita, zdr.popis, zdr.platiod, zdr.platido, zdr.timeinsert, zdr.timemodify, zdr.usermodify,"
                + "( SELECT count(*) "
                + "         FROM ucastnik uc,rezervace re "
                + "         WHERE zdr.id=re.idzdr and re.platiod<=? and re.platido>=? and uc.idcest=re.idcest "
                + ") AS obsazeno "
                + "FROM zdroj zdr INNER JOIN typzdroje tzdr ON zdr.idtypzdr=tzdr.id "
                + "WHERE tzdr.cesta='0' and zdr.platiod<=? and zdr.platido>=? and "
                + "( SELECT count(*) "
                + "         FROM ucastnik uc, rezervace re "
                + "         WHERE zdr.id=re.idzdr and re.platiod>=? and re.platido<=? and uc.idcest=re.idcest "
                + ")<= zdr.kapacita "
        );
        // Vyloucit nepripustne zdroje
        if (!disableZdrojList.isEmpty()) {
            selZdr.append("and zdr.id NOT IN (");
            boolean comma = false;
            for (Zdroj zdr : disableZdrojList) {
                if (comma) {
                    selZdr.append(",");
                }
                selZdr.append("'");
                selZdr.append(zdr.getId());
                selZdr.append("'");
                comma = true;
            }
            selZdr.append(") ");
        }
        selZdr.append(" ORDER BY zdr.komentar;");
        // Doplnit parametry a spustit dotaz
        Query q = em.createNativeQuery(selZdr.toString())
                .setParameter(1, platiDo)
                .setParameter(2, platiOd)
                .setParameter(3, platiOd)
                .setParameter(4, platiDo)
                .setParameter(5, platiDo)
                .setParameter(6, platiOd);
        ArrayList<Zdroj> rl = new ArrayList<>();
        List<Object[]> zdrList = q.getResultList();
        Zdroj zdr;
        for (Object[] obj : zdrList) {
            zdr = em.find(Zdroj.class, (UUID) obj[0]);
            zdr.setObsazeno(((Long)obj[12]).intValue());
            rl.add(zdr);
        }
        return rl;
    }

}
