/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Cesta;
import entity.Ucastnik;
import entity.Ucastnik_;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    public List<Ucastnik> findUcastniky(Cesta cesta) {
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

}
