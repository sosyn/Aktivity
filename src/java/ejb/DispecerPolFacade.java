/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

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
public class DispecerPolFacade extends AbstractFacade<Dispecerpol> {

    @PersistenceContext(unitName = "AktivityPU")
    private EntityManager em;

    final private String dispecerPolInsert
            = "INSERT INTO aktivity.public.DISPECERPOL "
            + "(PLATIDO, PLATIOD, POPIS, iddisphl, idoso, idzdr, ID) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    final private String dispecerPolUpdate = "UPDATE aktivity.public.DISPECERPOL "
            + "SET PLATIDO=?, PLATIOD=?, POPIS=?, iddisphl=?, idoso=?, idzdr=? "
            + "WHERE ID=? ";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DispecerPolFacade() {
        super(Dispecerpol.class);
    }

    public void dispecerPolInsert(Dispecerpol dispecerPol) {
        Query q = em.createNativeQuery(dispecerPolInsert);
        if (dispecerPol.getIddisphl() != null) {
            q.setParameter(1, dispecerPol.getPlatido());
            q.setParameter(2, dispecerPol.getPlatiod());
            q.setParameter(3, dispecerPol.getPopis());
            q.setParameter(4, dispecerPol.getIddisphl().getId());
            q.setParameter(5, dispecerPol.getIdoso() != null ? dispecerPol.getIdoso().getId() : 0 );
            q.setParameter(6, dispecerPol.getIdzdr()!= null ? dispecerPol.getIdzdr().getId() : 0 );
            q.setParameter(7, dispecerPol.getId());
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
    public void dispecerPolUpdate(Dispecerpol dispecerPol) {
        Query q = em.createNativeQuery(dispecerPolUpdate);
        if (dispecerPol.getIddisphl() != null) {
            q.setParameter(1, dispecerPol.getPlatido());
            q.setParameter(2, dispecerPol.getPlatiod());
            q.setParameter(3, dispecerPol.getPopis());
            q.setParameter(4, dispecerPol.getIddisphl().getId());
            q.setParameter(5, dispecerPol.getIdoso() != null ? dispecerPol.getIdoso().getId() : 0 );
            q.setParameter(6, dispecerPol.getIdzdr()!= null ? dispecerPol.getIdzdr().getId() : 0 );
            q.setParameter(7, dispecerPol.getId());
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
