/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Ivo
 */
@Entity
@Table(catalog = "aktivity", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Aktivity.findAll", query = "SELECT a FROM Aktivity a")})
public class Aktivity extends entity.EntitySuperClass {

    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String komentar;

    @JoinColumn(name = "idoso", referencedColumnName = "id")
    @ManyToOne
    private Osoba idoso;
    
    @JoinColumn(name = "idtypakt", referencedColumnName = "id")
    @ManyToOne
    private Typaktivity idtypakt;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idakt")
    private List<Rezervace> rezervaceList;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idakt")
    private List<Log> logList;

    public Aktivity() {
        super();
    }

    public Aktivity(Integer id) {
        super(id);
    }

    public Aktivity(Integer id, Date platiod, Date platido, Date timeinsert, Date timemodify) {
        super(id, platiod, platido, timeinsert, timemodify);
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
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

    public Typaktivity getIdtypakt() {
        return idtypakt;
    }

    public void setIdtypakt(Typaktivity idtypakt) {
        this.idtypakt = idtypakt;
    }

    public List<Rezervace> getRezervaceList() {
        return rezervaceList;
    }

    public void setRezervaceList(List<Rezervace> rezervaceList) {
        this.rezervaceList = rezervaceList;
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
        if (!(object instanceof Aktivity)) {
            return false;
        }
        Aktivity other = (Aktivity) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Aktivity[ id=" + this.getId() + " ]";
    }

}
