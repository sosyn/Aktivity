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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author sosyn
 */
@Entity
@Table(catalog = "aktivity", schema = "public")
public class Dispecerpol extends entity.EntitySuperClass {

    @JoinColumn(name = "iddisphl", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Dispecerhl iddisphl;

    @JoinColumn(name = "idoso", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Osoba idoso;

    @JoinColumn(name = "idzdr", referencedColumnName = "id" )
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Zdroj idzdr;

    public Dispecerpol() {
        super();
    }

    public Dispecerpol(UUID id) {
        super(id);
    }

    public Dispecerpol(UUID id, Date platiod, Date platido, Date timeinsert, Date timemodify) {
        super(id, platiod, platido, timeinsert, timemodify);
    }

    public Dispecerhl getIddisphl() {
        return iddisphl;
    }

    public void setIddisphl(Dispecerhl iddisphl) {
        this.iddisphl = iddisphl;
    }

    public Osoba getIdoso() {
        return idoso;
    }

    public void setIdoso(Osoba idoso) {
        this.idoso = idoso;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dispecerpol)) {
            return false;
        }
        Dispecerpol other = (Dispecerpol) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Dispecerpol[ id=" + getId() + " ]";
    }
    
}
