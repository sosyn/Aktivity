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
    @NamedQuery(name = "Ucastnik.findAll", query = "SELECT u FROM Ucastnik u")})
public class Ucastnik extends entity.EntitySuperClass {

    @JoinColumn(name = "idoso", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Osoba idoso;

    @JoinColumn(name = "idcest", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Cesta idcest;

    @JoinColumn(name = "idtypucast", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Typucast idtypucast;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "ucastnik")
    private List<Log> logList;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "iducast")
    private List<Schvaleni> schvList;

    public Ucastnik() {
        super();
    }

    public Ucastnik(UUID id) {
        super(id);
    }

    public Ucastnik(UUID id, Date platiod, Date platido, Date timeinsert, Date timemodify) {
        super(id, platiod, platido, timeinsert, timemodify);
    }

    /**
     * @return the idcest
     */
    public Cesta getIdcest() {
        return idcest;
    }

    /**
     * @param idcest the idcest to set
     */
    public void setIdcest(Cesta idcest) {
        this.idcest = idcest;
    }

    public List<Log> getLogList() {
        return logList;
    }

    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }

    public Osoba getIdoso() {
        return idoso;
    }

    public void setIdoso(Osoba idoso) {
        this.idoso = idoso;
    }

    public Typucast getIdtypucast() {
        return idtypucast;
    }

    public void setIdtypucast(Typucast idtypucast) {
        this.idtypucast = idtypucast;
    }

    /**
     * @return the schvList
     */
    public List<Schvaleni> getSchvList() {
        return schvList;
    }

    /**
     * @param schvList the schvList to set
     */
    public void setSchvList(List<Schvaleni> schvList) {
        this.schvList = schvList;
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
        if (!(object instanceof Ucastnik)) {
            return false;
        }
        Ucastnik other = (Ucastnik) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Ucastnik[ id=" + this.getId() + " ]";
    }

}
