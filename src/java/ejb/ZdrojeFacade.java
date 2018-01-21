/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.TypZdrojeEnum;
import entity.Typzdroje_;
import entity.Zdroj;
import entity.Zdroj_;
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

    public List<Zdroj> findAllWhereTypZdroje(TypZdrojeEnum typZdrojeEnum) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery(Zdroj.class);
        javax.persistence.criteria.Root<Zdroj> zdrojRoot = cq.from(Zdroj.class);
        cq.select(zdrojRoot);
               
        Path<Integer> pathTypZdr = zdrojRoot.get(Zdroj_.idtypzdr).get(Typzdroje_.typzdr);
        Predicate prediTypZdrCar = cb.equal(pathTypZdr,typZdrojeEnum.getId());

        cq.where(prediTypZdrCar);
        cq.orderBy(cb.asc(zdrojRoot.get(Zdroj_.popis)));
        
        List<Zdroj> rl = getEntityManager().createQuery(cq).getResultList();
//        System.out.println("findAllWhereTypZdroje.size()="+rl.size());
        return rl;
    }

}
