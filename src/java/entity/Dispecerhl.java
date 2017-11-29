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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sosyn
 */
@Entity
@Table(catalog = "aktivity", schema = "public")
public class Dispecerhl extends entity.EntitySuperClass {

    @JoinColumn(name = "idoso", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Osoba idoso;

    @JoinColumn(name = "idtypschv", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Typschv idtypschv;

    @JoinColumn(name = "idtypzdr", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Typzdroje idtypzdr;

    @JoinColumn(name = "iddisphl", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Dispecerhl iddisphl;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iddisphl", fetch = FetchType.LAZY)
    private List<Dispecerpol> dispecerpolList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iddisphl", fetch = FetchType.LAZY)
    private List<Dispecerhl> zastupci;

    
    
    public Dispecerhl() {
        super();
    }

    public Dispecerhl(UUID id) {
        super(id);
    }

    public Dispecerhl(UUID id, Date platiod, Date platido, Date timeinsert, Date timemodify) {
        super(id, platiod, platido, timeinsert, timemodify);
    }


    @XmlTransient
    public List<Dispecerpol> getDispecerpolList() {
        return dispecerpolList;
    }

    public void setDispecerpolList(List<Dispecerpol> dispecerpolList) {
        this.dispecerpolList = dispecerpolList;
    }

    public Osoba getIdoso() {
        return idoso;
    }

    public void setIdoso(Osoba idoso) {
        this.idoso = idoso;
    }

    public Typschv getIdtypschv() {
        return idtypschv;
    }

    public void setIdtypschv(Typschv idtypschv) {
        this.idtypschv = idtypschv;
    }

    public Typzdroje getIdtypzdr() {
        return idtypzdr;
    }

    public void setIdtypzdr(Typzdroje idtypzdr) {
        this.idtypzdr = idtypzdr;
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
        if (!(object instanceof Dispecerhl)) {
            return false;
        }
        Dispecerhl other = (Dispecerhl) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Dispecerhl[ id=" + getId() + " ]";
    }

    /**
     * @return the iddisphl
     */
    public Dispecerhl getIddisphl() {
        return iddisphl;
    }

    /**
     * @param iddisphl the iddisphl to set
     */
    public void setIddisphl(Dispecerhl iddisphl) {
        this.iddisphl = iddisphl;
    }

    /**
     * @return the zastupci
     */
    public List<Dispecerhl> getZastupci() {
        return zastupci;
    }

    /**
     * @param zastupci the zastupci to set
     */
    public void setZastupci(List<Dispecerhl> zastupci) {
        this.zastupci = zastupci;
    }
    
}
