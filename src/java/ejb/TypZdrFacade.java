/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Typzdroje;
import entity.Typzdroje_;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ivo
 */
@Stateless
public class TypZdrFacade extends AbstractFacade<Typzdroje> {

    @PersistenceContext(unitName = "AktivityPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypZdrFacade() {
        super(Typzdroje.class);
    }
     public Typzdroje findPopis(String podminka) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        javax.persistence.criteria.Root<Typzdroje> rt = cq.from(Typzdroje.class);
        cq.select(rt);
        cq.where(cb.like(rt.get(Typzdroje_.popis),podminka));
        javax.persistence.TypedQuery<Typzdroje> q = getEntityManager().createQuery(cq);
        return q.getSingleResult();
    }
   
}
