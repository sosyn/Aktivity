/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author sosyn
 */
@Entity
@Table(catalog = "aktivity", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Dispecerzdr.selectAll", query = "SELECT d FROM Dispecerzdr d")
})
public class Dispecerzdr extends entity.EntitySuperClass {

    @JoinColumn(name = "iddisphl", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Dispecerhl iddisphl;

    @JoinColumn(name = "idzdr", referencedColumnName = "id", nullable = true)
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private Zdroj idzdr;

    public Dispecerzdr() {
        super();
    }

    public Dispecerzdr(UUID id) {
        super(id);
    }

    public Dispecerzdr(UUID id, Date platiod, Date platido, Date timeinsert, Date timemodify) {
        super(id, platiod, platido, timeinsert, timemodify);
    }

    public Dispecerhl getIddisphl() {
        return iddisphl;
    }

    public void setIddisphl(Dispecerhl iddisphl) {
        this.iddisphl = iddisphl;
    }

    public Zdroj getIdzdr() {
        return idzdr;
    }

    public void setIdzdr(Zdroj idzdr) {
        this.idzdr = idzdr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dispecerzdr)) {
            return false;
        }
        Dispecerzdr other = (Dispecerzdr) object;
        return (this.getId() != null && other.getId() != null && this.getId().equals(other.getId()));
    }

    @Override
    public String toString() {
        return "entity.Dispecerpol[ id=" + getId() + " ]";
    }

}
