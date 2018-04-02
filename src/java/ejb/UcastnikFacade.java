/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Cesta;
import entity.Osoba;
import entity.Ucastnik;
import entity.Ucastnik_;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

    public List<Ucastnik> findUcastnikyWhereCesta(Cesta cesta) {
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

    public ArrayList<Ucastnik> findUcastnikyWhereSchvalovatel(Properties prop) {

//        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
//        javax.persistence.criteria.Root<Ucastnik> rt = cq.from(Ucastnik.class);
//        cq.select(rt);
//
//        java.util.ArrayList<Order> orderList = new ArrayList<>();
//        orderList.add(cb.asc(rt.get(Ucastnik_.platiod)));
//        orderList.add(cb.asc(rt.get(Ucastnik_.platido)));
//        cq.orderBy(orderList);
//        javax.persistence.TypedQuery<Ucastnik> q = getEntityManager().createQuery(cq);
        StringBuilder selUc = new StringBuilder(
                "SELECT *"
                + "FROM ucastnik uc "
                + "     LEFT JOIN (dispecerpol dp "
                + "                 LEFT JOIN dispecerhl dh ON dp.iddisphl=dh.id) "
                + "     ON uc.idoso=dp.idoso "
                + "WHERE "
        );
        selUc.append("dh.idoso='");
        selUc.append(((Osoba)prop.get("osoba")).getId());
        selUc.append("'");
        Query q = em.createNativeQuery(selUc.toString());
        ArrayList<Ucastnik> rl = new ArrayList<>();
        List<Object[]> listUc = q.getResultList();
        Ucastnik uc;
        for (Object[] obj : listUc) {
            uc = em.find(Ucastnik.class, (UUID) obj[0]);
            rl.add(uc);
        }
        return rl;
    }

}
