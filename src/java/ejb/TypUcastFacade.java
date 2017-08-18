/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Typucast;
import entity.Typucast_;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ivo
 */
@Stateless
public class TypUcastFacade extends AbstractFacade<Typucast> {

    @PersistenceContext(unitName = "AktivityPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypUcastFacade() {
        super(Typucast.class);
    }
      public Typucast findPopis(String podminka) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        javax.persistence.criteria.Root<Typucast> rt = cq.from(Typucast.class);
        cq.select(rt);
        cq.where(cb.like(rt.get(Typucast_.popis),podminka));
        javax.persistence.TypedQuery<Typucast> q = getEntityManager().createQuery(cq);
        return q.getSingleResult();
    }

}
