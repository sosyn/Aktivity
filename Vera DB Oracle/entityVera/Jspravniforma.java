/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityVera;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sosyn
 */
@Entity
@Table(catalog = "", schema = "VERA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jspravniforma.findAll", query = "SELECT j FROM Jspravniforma j"),
    @NamedQuery(name = "Jspravniforma.findByDatin", query = "SELECT j FROM Jspravniforma j WHERE j.datin = :datin"),
    @NamedQuery(name = "Jspravniforma.findById", query = "SELECT j FROM Jspravniforma j WHERE j.id = :id"),
    @NamedQuery(name = "Jspravniforma.findByKod", query = "SELECT j FROM Jspravniforma j WHERE j.kod = :kod"),
    @NamedQuery(name = "Jspravniforma.findByNazev", query = "SELECT j FROM Jspravniforma j WHERE j.nazev = :nazev"),
    @NamedQuery(name = "Jspravniforma.findByUserin", query = "SELECT j FROM Jspravniforma j WHERE j.userin = :userin"),
    @NamedQuery(name = "Jspravniforma.findByVersion", query = "SELECT j FROM Jspravniforma j WHERE j.version = :version")})
public class Jspravniforma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datin;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Long id;
    @Size(max = 4)
    @Column(length = 4)
    private String kod;
    @Size(max = 100)
    @Column(length = 100)
    private String nazev;
    private Long userin;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private long version;
    @OneToMany(mappedBy = "pravniforma")
    private Collection<Jsutvar> jsutvarCollection;

    public Jspravniforma() {
    }

    public Jspravniforma(Long id) {
        this.id = id;
    }

    public Jspravniforma(Long id, long version) {
        this.id = id;
        this.version = version;
    }

    public Date getDatin() {
        return datin;
    }

    public void setDatin(Date datin) {
        this.datin = datin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public Long getUserin() {
        return userin;
    }

    public void setUserin(Long userin) {
        this.userin = userin;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @XmlTransient
    public Collection<Jsutvar> getJsutvarCollection() {
        return jsutvarCollection;
    }

    public void setJsutvarCollection(Collection<Jsutvar> jsutvarCollection) {
        this.jsutvarCollection = jsutvarCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jspravniforma)) {
            return false;
        }
        Jspravniforma other = (Jspravniforma) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityVera.Jspravniforma[ id=" + id + " ]";
    }
    
}
