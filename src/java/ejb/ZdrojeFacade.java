/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Cesta;
import entity.EntitySuperClass_;
import entity.Osoba;
import entity.Rezervace;
import entity.TypZdrojeEnum;
import entity.Typzdroje_;
import entity.Zdroj;
import entity.Zdroj_;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    public List<Zdroj> findAllWhereTypZdroje(TypZdrojeEnum typZdrojeEnum, Date platiOd, Date platiDo) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery(Zdroj.class);
        javax.persistence.criteria.Root<Zdroj> zdrojRoot = cq.from(Zdroj.class);
        cq.select(zdrojRoot);

        Path<Integer> pathTypZdr = zdrojRoot.get(Zdroj_.idtypzdr).get(Typzdroje_.typzdr);
        Predicate prediTypZdrCar = cb.equal(pathTypZdr, typZdrojeEnum.getId());

        Path<Date> pathPlatiOd = zdrojRoot.get(EntitySuperClass_.platiod);
        Path<Date> pathPlatiDo = zdrojRoot.get(EntitySuperClass_.platido);
        Predicate prediPlatiOdDo = cb.and(
                cb.or(cb.isNull(pathPlatiOd), cb.not(cb.greaterThan(pathPlatiOd, platiDo))),
                cb.or(cb.isNull(pathPlatiDo), cb.greaterThan(pathPlatiDo, platiOd))
        );

        Predicate prediWhere = cb.and(prediTypZdrCar, prediPlatiOdDo);

        cq.where(prediWhere);
        cq.orderBy(cb.asc(zdrojRoot.get(Zdroj_.popis)));

        List<Zdroj> rl = getEntityManager().createQuery(cq).getResultList();
//        System.out.println("findAllWhereTypZdroje.size()="+rl.size());
        return rl;
    }

    /**
     * Metoda vybere všechny použitelné zdroje, kde jsou splněny podmínky: - je
     * to auto - zdroj je platný OD-DO - není ve vyloucenych-nedostupnych
     * zdrojich (napr.jiz jednou vybranych)
     * 
     * @param cesta - cesta, ktera definuje omeyujici podminky pro vyber
     * @return matice se zdroji, kter lze pouzit
     */
  
    public ArrayList<Zdroj> findAccesibleZdrojList(Cesta cesta) {
        StringBuilder selZdr = new StringBuilder(
                "SELECT zdr.id, zdr.idtypzdr, zdr.idoso, zdr.spz, zdr.komentar,zdr.kapacita, zdr.popis, zdr.platiod, zdr.platido, zdr.timeinsert, zdr.timemodify, zdr.usermodify,"
                + "( SELECT count(*) "
                + "         FROM ucastnik uc,rezervace re "
                + "         WHERE zdr.id=re.idzdr and re.idcest=uc.idcest and re.platiod<=? and re.platido>=?  "
                + ") AS obsazeno "
                + "FROM zdroj zdr INNER JOIN typzdroje tzdr ON zdr.idtypzdr=tzdr.id "
                + "WHERE tzdr.cesta='0' and zdr.platiod<=? and zdr.platido>=? and "
                + "( SELECT count(*)+? "
                + "         FROM ucastnik uc, rezervace re "
                + "         WHERE zdr.id=re.idzdr and re.idcest=uc.idcest and re.platiod<=? and re.platido>=? "
                + ") <= zdr.kapacita "
        );
        // Vyloucit nepripustne (jiz pouzite) zdroje
        if (!cesta.getRezervaceList().isEmpty()) {
            selZdr.append("and zdr.id NOT IN (");
            boolean comma = false;
            for (Rezervace rez : cesta.getRezervaceList()) {
                if (comma) {
                    selZdr.append(",");
                }
                selZdr.append("'");
                selZdr.append(rez.getIdzdr().getId());
                selZdr.append("'");
                comma = true;
            }
            selZdr.append(") ");
        }
        selZdr.append(" ORDER BY zdr.komentar;");
        // Doplnit parametry a spustit dotaz
        Query q = em.createNativeQuery(selZdr.toString())
                .setParameter(1, cesta.getPlatido())
                .setParameter(2, cesta.getPlatiod())
                .setParameter(3, cesta.getPlatiod())
                .setParameter(4, cesta.getPlatido())
                .setParameter(5, cesta.getUcastnikList().size())
                .setParameter(6, cesta.getPlatido())
                .setParameter(7, cesta.getPlatiod()) ;
        ArrayList<Zdroj> rl = new ArrayList<>();
        List<Object[]> zdrList = q.getResultList();
        Zdroj zdr;
        for (Object[] obj : zdrList) {
            zdr = em.find(Zdroj.class, (UUID) obj[0]);
            zdr.setObsazeno(((Long)obj[12]).intValue());
            rl.add(zdr);
        }
        return rl;
    }
}
