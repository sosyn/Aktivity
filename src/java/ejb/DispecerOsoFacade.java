/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Dispeceroso;
import entity.Dispecerpol;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ivo
 */
@Stateless
public class DispecerOsoFacade extends AbstractFacade<Dispeceroso> {

    @PersistenceContext(unitName = "AktivityPU")
    private EntityManager em;

    final private String dispecerOsoInsert
            = "INSERT INTO aktivity.public.DISPECEROSO "
            + "(PLATIDO, PLATIOD, POPIS, iddisphl, idoso, ID) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
    final private String dispecerOsoUpdate = "UPDATE aktivity.public.DISPECEROSO "
            + "SET PLATIDO=?, PLATIOD=?, POPIS=?, iddisphl=?, idoso=? "
            + "WHERE ID=? ";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DispecerOsoFacade() {
        super(Dispeceroso.class);
    }

    public void dispecerOsoInsert(Dispeceroso dispecerOso) {
        Query q = em.createNativeQuery(dispecerOsoInsert);
        if (dispecerOso.getIddisphl() != null) {
            q.setParameter(1, dispecerOso.getPlatido());
            q.setParameter(2, dispecerOso.getPlatiod());
            q.setParameter(3, dispecerOso.getPopis());
            q.setParameter(4, dispecerOso.getIddisphl().getId());
            q.setParameter(5, dispecerOso.getIdoso().getId());
            q.setParameter(6, dispecerOso.getId());
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
    public void dispecerOsoUpdate(Dispeceroso dispecerOso) {
        Query q = em.createNativeQuery(dispecerOsoUpdate);
        if (dispecerOso.getIddisphl() != null) {
            q.setParameter(1, dispecerOso.getPlatido());
            q.setParameter(2, dispecerOso.getPlatiod());
            q.setParameter(3, dispecerOso.getPopis());
            q.setParameter(4, dispecerOso.getIddisphl().getId());
            q.setParameter(5, dispecerOso.getIdoso().getId() );
            q.setParameter(6, dispecerOso.getId());
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
