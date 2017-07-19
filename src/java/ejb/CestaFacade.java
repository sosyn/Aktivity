/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Cesta;
import entity.Cesta_;
import entity.Osoba;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ivo
 */
@Stateless
public class CestaFacade extends AbstractFacade<Cesta> {

    @PersistenceContext(unitName = "AktivityPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CestaFacade() {
        super(Cesta.class);
    }

    public List<Cesta> findOsoba(Osoba osoba) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        javax.persistence.criteria.Root<Cesta> rt = cq.from(Cesta.class);
        cq.select(rt);
        cq.where(cb.equal(rt.get(Cesta_.idoso),osoba));
        javax.persistence.TypedQuery<Cesta> q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }

}
