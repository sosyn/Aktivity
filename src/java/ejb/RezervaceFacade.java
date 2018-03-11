/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Cesta;
import entity.Rezervace;
import entity.Rezervace_;
import java.util.Date;
import java.util.List;
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

    public List<Rezervace> getRezeraceOdDo(Date platiOd, Date platiDo) {
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

}
