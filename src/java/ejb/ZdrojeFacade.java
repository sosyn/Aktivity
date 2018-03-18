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
     */
    public List<Zdroj> findAccesibleZdrojList(TypZdrojeEnum typZdrojeEnum, Osoba osoba, Date platiOd, Date platiDo, ArrayList<Zdroj> disableZdrojList) {
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

        // Vyloucit nevyzadane zdroje (uz obsazene)
        if (disableZdrojList != null && !disableZdrojList.isEmpty()) {
            Path<UUID> pathDisableZdr = zdrojRoot.get(Zdroj_.id);
            ArrayList<UUID> uuidDisableZdrojIdList = new ArrayList<>();
            for (Zdroj zdrojDisable : disableZdrojList) {
                uuidDisableZdrojIdList.add(zdrojDisable.getId());
            }
            prediWhere = cb.and(prediWhere, cb.not(pathDisableZdr.in(uuidDisableZdrojIdList)));
        }
        cq.where(prediWhere);
        cq.orderBy(cb.asc(zdrojRoot.get(Zdroj_.popis)));
        List<Zdroj> rl = getEntityManager().createQuery(cq).getResultList();
        return rl;
    }

}
