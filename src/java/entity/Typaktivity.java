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
    @NamedQuery(name = "Typaktivity.findAll", query = "SELECT t FROM Typaktivity t")})
public class Typaktivity extends entity.EntitySuperClass {

    private Integer typakt;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idtypakt")
    private List<Aktivity> aktivityList;

    public Typaktivity() {
    }

    public Typaktivity(UUID id) {
super(id);
    }

    public Typaktivity(UUID id, Date platiod, Date platido, Date timeinsert, Date timemodify) {
super(id, platiod, platido, timeinsert, timemodify);    }

    public Integer getTypakt() {
        return typakt;
    }

    public void setTypakt(Integer typakt) {
        this.typakt = typakt;
    }

    public List<Aktivity> getAktivityList() {
        return aktivityList;
    }

    public void setAktivityList(List<Aktivity> aktivityList) {
        this.aktivityList = aktivityList;
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
        if (!(object instanceof Typaktivity)) {
            return false;
        }
        Typaktivity other = (Typaktivity) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Typaktivity[ id=" + getId() + " ]";
    }
    
}
