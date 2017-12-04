/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Osoba;
import entity.Osoba_;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
        cq.orderBy(cb.asc(osobaRoot.get(Osoba_.name)));
        return getEntityManager().createQuery(cq).getResultList();
    }
}
