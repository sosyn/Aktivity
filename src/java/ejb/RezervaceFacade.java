/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Cesta;
import entity.Rezervace;
import entity.Rezervace_;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    
}
