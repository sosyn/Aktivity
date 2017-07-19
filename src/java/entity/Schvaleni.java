/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Ivo
 */
@Entity
@Table(catalog = "aktivity", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Schvaleni.findAll", query = "SELECT s FROM Schvaleni s")})
public class Schvaleni extends entity.EntitySuperClass {

    // 0-nic,1-schvaleno,2-neschvaleno
    private Integer stav;

    @Size(max = 512)
    @Column(length = 512)
    private String komentar;

    @JoinColumn(name = "idtypschv", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Typschv idtypschv;
    
    @JoinColumn(name = "idoso", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Osoba idoso;

    @JoinColumn(name = "idcest", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Cesta idcest;

    @JoinColumn(name = "iducast", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Ucastnik iducast;

    @JoinColumn(name = "idrez", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Rezervace idrez;

    
    public Schvaleni() {
        super();
    }

    public Schvaleni(UUID id) {
        super(id);
    }

    public Schvaleni(UUID id, Date platiod, Date platido, Date timeinsert, Date timemodify) {
        super(id, platiod, platido, timeinsert, timemodify);
    }

    public Integer getStav() {
        return stav;
    }

    public void setStav(Integer stav) {
        this.stav = stav;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public Cesta getIdcest() {
        return idcest;
    }

    public void setIdcest(Cesta idcest) {
        this.idcest = idcest;
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

    /**
     * @return the iducast
     */
    public Ucastnik getIducast() {
        return iducast;
    }

    /**
     * @param iducast the iducast to set
     */
    public void setIducast(Ucastnik iducast) {
        this.iducast = iducast;
    }

    /**
     * @return the idrez
     */
    public Rezervace getIdrez() {
        return idrez;
    }

    /**
     * @param idrez the idrez to set
     */
    public void setIdrez(Rezervace idrez) {
        this.idrez = idrez;
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
        if (!(object instanceof Schvaleni)) {
            return false;
        }
        Schvaleni other = (Schvaleni) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Schvaleni[ id=" + this.getId() + " ]";
    }

}
