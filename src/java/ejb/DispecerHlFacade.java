/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Cesta;
import entity.Dispecerhl;
import entity.Dispecerhl_;
import entity.Dispecerpol;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.Predicate;

/**
 *
 * @author Ivo
 */
@Stateless
public class DispecerHlFacade extends AbstractFacade<Dispecerhl> {

    // Hlavicka dispecera
    String insDispHl = "INSERT INTO aktivity.public.dispecerhl "
            + "(idoso,idtypschv,idtypzdr,iddisphl,popis, platiod, platido,id)"
            + "VALUES (?::uuid,?::uuid,?::uuid,?::uuid,?,?,?,?::uuid)";
    String updDispHl = "UPDATE aktivity.public.dispecerhl SET "
            + "idoso = ?::uuid,idtypschv = ?::uuid,idtypzdr = ?::uuid,iddisphl = ?::uuid,popis = ?, platiod = ?, platido = ?"
            + " WHERE id= ?::uuid";
    String delDispHl = "DELETE FROM aktivity.public.dispecerhl WHERE id=?::uuid";
    // Polozky - zdroje nebo podrizeni
    String insDispPol = "INSERT INTO aktivity.public.dispecerpol"
            + "(iddisphl, idoso, idzdr, popis, platiod, platido, id)"
            + "VALUES (?::uuid,?::uuid,?::uuid,?,?,?,?::uuid)";
    String updDispPol = "UPDATE aktivity.public.dispecerpol SET "
            + " iddisphl=?::uuid,idoso=?::uuid,idzdr=?::uuid,popis=?,platiod=?,platido=?"
            + " WHERE id=?::uuid";
    String delDispPol = "DELETE FROM aktivity.public.REZERVACE WHERE id=?::uuid";

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
     *
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

    /**
     * Metoda ulozi hlavicku dispecera a vsechny
     *
     * @param dispecerhl
     * @param dispHlDel
     * @param dispPolDel
     * @param zastupciDel
     * @return
     */
    public boolean saveDispecerHl(Dispecerhl dispecerhl, ArrayList<Dispecerhl> dispHlDel, ArrayList<Dispecerpol> dispPolDel, ArrayList<Dispecerhl> zastupciDel) {
        try {
            saveDispHl(dispecerhl);
            saveDispHlList(dispecerhl.getZastupciList());
            saveDispHlList(dispHlDel);
            saveDispHlList(zastupciDel);
            saveDispPolList(dispecerhl.getDispecerPolList());
            saveDispPolList(dispPolDel);
        } catch (Exception e) {
//              em.getTransaction().rollback();
            throw e;
        }
        return true;
    }

    /**
     *
     * @param dispecerhl
     * @return
     */
    public boolean saveDispHl(Dispecerhl dispecerhl) {
        Query q = em.createNativeQuery(dispecerhl.isNewEntity() ? insDispHl : updDispHl)
                .setParameter(1, dispecerhl.getIdoso().getId())
                .setParameter(2, dispecerhl.getIdtypschv().getId())
                .setParameter(3, dispecerhl.getIdtypzdr().getId())
                .setParameter(4, dispecerhl.getIddisphl() == null ? null : dispecerhl.getIddisphl().getId())
                .setParameter(5, dispecerhl.getPopis())
                .setParameter(6, dispecerhl.getPlatiod())
                .setParameter(7, dispecerhl.getPlatido())
                .setParameter(8, dispecerhl.getId());

        if (dispecerhl.isDelEntity()) {
            q = em.createNativeQuery(delDispHl)
                    .setParameter(1, dispecerhl.getId());
        }
//            em.getTransaction().begin();
        dispecerhl.setNewEntity(false);
        dispecerhl.setDelEntity(false);
        q.setFlushMode(FlushModeType.COMMIT);
        try {
            q.executeUpdate();
        } catch (Exception e) {
//              em.getTransaction().rollback();
            throw e;
        }
//            em.getTransaction().commit();

        return true;
    }

    /**
     *
     * @param dispHlList
     * @return
     */
    public boolean saveDispHlList(List<Dispecerhl> dispHlList) {
        for (Dispecerhl dhl : dispHlList) {
            if (dhl.getIdoso() != null) {
                saveDispHl(dhl);
            }
        }
        return true;
    }

    /**
     *
     * @param dispPolList
     * @return
     */
    public boolean saveDispPolList(List<Dispecerpol> dispPolList) {
        for (Dispecerpol dpol : dispPolList) {
            Query q = em.createNativeQuery(dpol.isNewEntity() ? insDispPol : updDispPol)
                    .setParameter(1, dpol.getIddisphl().getId())
                    .setParameter(2, dpol.getIdoso() == null ? null : dpol.getIdoso().getId())
                    .setParameter(3, dpol.getIdzdr() == null ? null : dpol.getIdzdr().getId())
                    .setParameter(4, dpol.getPopis())
                    .setParameter(5, dpol.getPlatiod())
                    .setParameter(6, dpol.getPlatido())
                    .setParameter(7, dpol.getId());

            if (dpol.isDelEntity()) {
                q = em.createNativeQuery(delDispHl)
                        .setParameter(1, dpol.getId());
            }
//            em.getTransaction().begin();
            dpol.setNewEntity(false);
            dpol.setDelEntity(false);
            q.setFlushMode(FlushModeType.COMMIT);
            try {
                q.executeUpdate();
            } catch (Exception e) {
//              em.getTransaction().rollback();
                throw e;
            }
//            em.getTransaction().commit();

        }
        return true;
    }
}
