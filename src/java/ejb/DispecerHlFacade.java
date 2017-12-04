/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Dispecerhl;
import entity.Dispecerhl_;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

/**
 *
 * @author Ivo
 */
@Stateless
public class DispecerHlFacade extends AbstractFacade<Dispecerhl> {

    @PersistenceContext(unitName = "AktivityPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DispecerHlFacade() {
        super(Dispecerhl.class);
    }

    /**
     * Metoda vrati matici dispeceru "s" nebo "bez" zastupcu
     * @param dispeceriAzastupci - se zastupci=true bez=false
     * @return
     */

    public List<Dispecerhl> findAllDispAZast(boolean dispeceriAzastupci) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        javax.persistence.criteria.Root<Dispecerhl> dispHlRoot = cq.from(Dispecerhl.class);
        cq.select(dispHlRoot);
        cq.orderBy(cb.asc(dispHlRoot.get(Dispecerhl_.idoso.getName())));
        // Bez zastupcu
        if (!dispeceriAzastupci) {
            Predicate predicateWhere = cb.isNull(dispHlRoot.get(Dispecerhl_.iddisphl));
            cq.where(predicateWhere);
        }
        return getEntityManager().createQuery(cq).getResultList();

    }
}
