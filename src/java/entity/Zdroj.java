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
    @NamedQuery(name = "Zdroj.findAll", query = "SELECT z FROM Zdroj z")})
public class Zdroj extends entity.EntitySuperClass {

    @Size(max = 10)
    @Column(length = 10)
    private String spz;
    @Size(max = 4096)
    @Column(length = 4096)
    private String komentar;
    private Integer kapacita;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idzdr")
    private List<Log> logList;
    
    @JoinColumn(name = "idoso", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Osoba idoso;
    
    @JoinColumn(name = "idtypzdr", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Typzdroje idtypzdr;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idzdr")
    private List<Rezervace> rezervaceList;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idzdr")
    private List<Dispecerpol> dispecerpolList;

    public Zdroj() {
        super();
    }

    public Zdroj(Integer id) {
        super(id);
    }

    public Zdroj(Integer id, Date platiod, Date platido, Date timeinsert, Date timemodify) {
        super(id, platiod, platido, timeinsert, timemodify);
    }

    public String getSpz() {
        return spz;
    }

    public void setSpz(String spz) {
        this.spz = spz;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public Integer getKapacita() {
        return kapacita;
    }

    public void setKapacita(Integer kapacita) {
        this.kapacita = kapacita;
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

    public Typzdroje getIdtypzdr() {
        return idtypzdr;
    }

    public void setIdtypzdr(Typzdroje idtypzdr) {
        this.idtypzdr = idtypzdr;
    }

    public List<Rezervace> getRezervaceList() {
        return rezervaceList;
    }

    public void setRezervaceList(List<Rezervace> rezervaceList) {
        this.rezervaceList = rezervaceList;
    }

    public List<Dispecerpol> getDispecerpolList() {
        return dispecerpolList;
    }

    public void setDispecerpolList(List<Dispecerpol> dispecerpolList) {
        this.dispecerpolList = dispecerpolList;
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
        if (!(object instanceof Zdroj)) {
            return false;
        }
        Zdroj other = (Zdroj) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Zdroj[ id=" + this.getId() + " ]";
    }

}
