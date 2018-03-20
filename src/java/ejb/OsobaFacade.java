/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Cesta;
import entity.EntitySuperClass_;
import entity.Osoba;
import entity.Osoba_;
import entity.Ucastnik;
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
public class OsobaFacade extends AbstractFacade<Osoba> {

    @PersistenceContext(unitName = "AktivityPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OsobaFacade() {
        super(Osoba.class);
    }

    public Osoba findName(String podminka) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        javax.persistence.criteria.Root<Osoba> osobaRoot = cq.from(Osoba.class);
        cq.select(osobaRoot);
        cq.where(cb.like(osobaRoot.get(Osoba_.name), podminka));
        javax.persistence.TypedQuery<Osoba> q = getEntityManager().createQuery(cq);
        return q.getSingleResult();
    }

    public List<Osoba> findAlltByName() {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        javax.persistence.criteria.Root<Osoba> osobaRoot = cq.from(Osoba.class);
        cq.select(osobaRoot);
        cq.orderBy(cb.asc(osobaRoot.get(Osoba_.popis)));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<Osoba> findAccesibleOsobaList(Cesta cesta) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        javax.persistence.criteria.Root<Osoba> osobaRoot = cq.from(Osoba.class);
        cq.select(osobaRoot);
        // Vyloucit jiz ucastniky, kteri jsou jiz jednou v ceste pridani
        Path<UUID> pathNoOsoba = osobaRoot.get(Osoba_.id);
        Predicate prediNoOsoba = null;
        for (Ucastnik ucastnik : cesta.getUcastnikList()) {
            if (prediNoOsoba == null) {
                prediNoOsoba = cb.notEqual(pathNoOsoba, ucastnik.getIdoso().getId());
            } else {
                prediNoOsoba = cb.and(prediNoOsoba, cb.notEqual(pathNoOsoba, ucastnik.getIdoso().getId()));
            }
        }

        Path<Date> pathPlatiOd = osobaRoot.get(EntitySuperClass_.platiod);
        Path<Date> pathPlatiDo = osobaRoot.get(EntitySuperClass_.platido);
        Predicate prediPlatiOdDo = cb.and(
                cb.or(cb.isNull(pathPlatiOd), cb.not(cb.greaterThan(pathPlatiOd, cesta.getPlatido()))),
                cb.or(cb.isNull(pathPlatiDo), cb.greaterThanOrEqualTo(pathPlatiDo, cesta.getPlatiod()))
        );

        Predicate prediWhere = cb.and(prediNoOsoba, prediPlatiOdDo);
        cq.where(prediWhere);
        cq.orderBy(cb.asc(osobaRoot.get(Osoba_.popis)));

        return getEntityManager().createQuery(cq).getResultList();
    }
}
