/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ivo
 */
@Entity
@Table(catalog = "aktivity", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Typschv.findAll", query = "SELECT t FROM Typschv t")})
public class Typschv extends entity.EntitySuperClass {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String typschv;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idtypschv")
    private List<Schvaleni> schvaleniList;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idtypschv")
    private List<Dispecerhl> dispecerhlList;

    public Typschv() {
    }

    public Typschv(UUID id) {
        super(id);
    }

    public Typschv(UUID id, String typschv, Date platiod, Date platido, Date timeinsert, Date timemodify) {
        super(id, platiod, platido, timeinsert, timemodify);
    }

    public String getTypschv() {
        return typschv;
    }

    public void setTypschv(String typschv) {
        this.typschv = typschv;
    }

    public List<Schvaleni> getSchvaleniList() {
        return schvaleniList;
    }

    public void setSchvaleniList(List<Schvaleni> schvaleniList) {
        this.schvaleniList = schvaleniList;
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
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Typschv)) {
            return false;
        }
        Typschv other = (Typschv) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Typschv[ id=" + getId() + " ]";
    }

}
