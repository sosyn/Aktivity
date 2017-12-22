/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Dispecerzdr;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ivo
 */
@Stateless
public class DispecerZdrFacade extends AbstractFacade<Dispecerzdr> {

    @PersistenceContext(unitName = "AktivityPU")
    private EntityManager em;

    final private String dispecerZdrInsert
            = "INSERT INTO aktivity.public.DISPECERZDR "
            + "(PLATIDO, PLATIOD, POPIS, iddisphl, idzdr, ID) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
    final private String dispecerZdrUpdate = "UPDATE aktivity.public.DISPECERZDR "
            + "SET PLATIDO=?, PLATIOD=?, POPIS=?, iddisphl=?, idzdr=? "
            + "WHERE ID=? ";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DispecerZdrFacade() {
        super(Dispecerzdr.class);
    }

    public void dispecerZdrInsert(Dispecerzdr dispecerZdr) {
        Query q = em.createNativeQuery(dispecerZdrInsert);
        if (dispecerZdr.getIddisphl() != null) {
            q.setParameter(1, dispecerZdr.getPlatido());
            q.setParameter(2, dispecerZdr.getPlatiod());
            q.setParameter(3, dispecerZdr.getPopis());
            q.setParameter(4, dispecerZdr.getIddisphl().getId());
            q.setParameter(5, dispecerZdr.getIdzdr().getId());
            q.setParameter(6, dispecerZdr.getId());
//            em.getTransaction().begin();
            try {
                q.executeUpdate();
            } catch (Exception e) {
//              em.getTransaction().rollback();
                throw e;
            }
//            em.getTransaction().commit();
        }
    }
    public void dispecerZdrUpdate(Dispecerzdr dispecerZdr) {
        Query q = em.createNativeQuery(dispecerZdrUpdate);
        if (dispecerZdr.getIddisphl() != null) {
            q.setParameter(1, dispecerZdr.getPlatido());
            q.setParameter(2, dispecerZdr.getPlatiod());
            q.setParameter(3, dispecerZdr.getPopis());
            q.setParameter(4, dispecerZdr.getIddisphl().getId());
            q.setParameter(5, dispecerZdr.getIdzdr().getId() );
            q.setParameter(6, dispecerZdr.getId());
//          em.getTransaction().begin();
            try {
                q.executeUpdate();
            } catch (Exception e) {
//              em.getTransaction().rollback();
                throw e;
            }
//            em.getTransaction().commit();
        }
    }
}
