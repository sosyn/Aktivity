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
    @NamedQuery(name = "Typucast.findAll", query = "SELECT t FROM Typucast t")})
public class Typucast extends entity.EntitySuperClass {

    private Integer typucast;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idtypucast")
    private List<Ucastnik> ucastnikList;

    public Typucast() {
    }

    public Typucast(UUID id) {
        super();
    }

    public Typucast(UUID id, Date platiod, Date platido, Date timeinsert, Date timemodify) {
    super(id, platiod, platido, timeinsert, timemodify);
    }

    public Integer getTypucast() {
        return typucast;
    }

    public void setTypucast(Integer typucast) {
        this.typucast = typucast;
    }


    public List<Ucastnik> getUcastnikList() {
        return ucastnikList;
    }

    public void setUcastnikList(List<Ucastnik> ucastnikList) {
        this.ucastnikList = ucastnikList;
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
        if (!(object instanceof Typucast)) {
            return false;
        }
        Typucast other = (Typucast) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Typucast[ id=" + this.getId() + " ]";
    }
    
}
