/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Ivo
 */
@Entity
@Table(catalog = "aktivity", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Typzdroje.findAll", query = "SELECT t FROM Typzdroje t")})
public class Typzdroje extends entity.EntitySuperClass {

    private Integer typzdr;
    private Integer cesta;
    private Integer osoba;
    private Integer rezervace;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idtypzdr")
    private List<Zdroj> zdrojList;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idtypzdr")
    private List<Cesta> cestaList;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idtypzdr")
    private List<Dispecerhl> dispecerhlList;

    public Typzdroje() {
    }

    public Typzdroje(UUID id) {
        super(id);
    }

    public Typzdroje(UUID id, Date platiod, Date platido, Date timeinsert, Date timemodify) {
        super(id, platiod, platido, timeinsert, timemodify);
    }

    public Integer getTypzdr() {
        return typzdr;
    }

    public void setTypzdr(Integer typzdr) {
        this.typzdr = typzdr;
    }

    public Integer getCesta() {
        return cesta;
    }

    public void setCesta(Integer cesta) {
        this.cesta = cesta;
    }
    public Integer getOsoba() {
        return osoba;
    }

    public void setOsoba(Integer osoba) {
        this.osoba = osoba;
    }

    public Integer getRezervace() {
        return rezervace;
    }

    public void setRezervace(Integer rezervace) {
        this.rezervace = rezervace;
    }

    public List<Zdroj> getZdrojList() {
        return zdrojList;
    }

    public void setZdrojList(List<Zdroj> zdrojList) {
        this.zdrojList = zdrojList;
    }

    public List<Cesta> getCestaList() {
        return cestaList;
    }

    public void setCestaList(List<Cesta> cestaList) {
        this.cestaList = cestaList;
    }

    public List<Dispecerhl> getDispecerhlList() {
        return dispecerhlList;
    }

    public void setDispecerhlList(List<Dispecerhl> dispecerhlList) {
        this.dispecerhlList = dispecerhlList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Typzdroje)) {
            return false;
        }
        Typzdroje other = (Typzdroje) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Typzdroje[ id=" + this.getId() + " ]";
    }

}
