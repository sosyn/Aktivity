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
    @NamedQuery(name = "Jscmisto.findAll", query = "SELECT j FROM Jscmisto j"),
    @NamedQuery(name = "Jscmisto.findByDatin", query = "SELECT j FROM Jscmisto j WHERE j.datin = :datin"),
    @NamedQuery(name = "Jscmisto.findByDplatdo", query = "SELECT j FROM Jscmisto j WHERE j.dplatdo = :dplatdo"),
    @NamedQuery(name = "Jscmisto.findByDplatod", query = "SELECT j FROM Jscmisto j WHERE j.dplatod = :dplatod"),
    @NamedQuery(name = "Jscmisto.findById", query = "SELECT j FROM Jscmisto j WHERE j.id = :id"),
    @NamedQuery(name = "Jscmisto.findByIdvoj", query = "SELECT j FROM Jscmisto j WHERE j.idvoj = :idvoj"),
    @NamedQuery(name = "Jscmisto.findByMisto", query = "SELECT j FROM Jscmisto j WHERE j.misto = :misto"),
    @NamedQuery(name = "Jscmisto.findByTyp", query = "SELECT j FROM Jscmisto j WHERE j.typ = :typ"),
    @NamedQuery(name = "Jscmisto.findByUserin", query = "SELECT j FROM Jscmisto j WHERE j.userin = :userin"),
    @NamedQuery(name = "Jscmisto.findByVersion", query = "SELECT j FROM Jscmisto j WHERE j.version = :version")})
public class Jscmisto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datin;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dplatdo;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dplatod;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private long idvoj;
    @Size(max = 32)
    @Column(length = 32)
    private String misto;
    private Long typ;
    private Long userin;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private long version;
    @OneToMany(mappedBy = "cmisto")
    private Collection<Jsmisto> jsmistoCollection;

    public Jscmisto() {
    }

    public Jscmisto(Long id) {
        this.id = id;
    }

    public Jscmisto(Long id, long idvoj, long version) {
        this.id = id;
        this.idvoj = idvoj;
        this.version = version;
    }

    public Date getDatin() {
        return datin;
    }

    public void setDatin(Date datin) {
        this.datin = datin;
    }

    public Date getDplatdo() {
        return dplatdo;
    }

    public void setDplatdo(Date dplatdo) {
        this.dplatdo = dplatdo;
    }

    public Date getDplatod() {
        return dplatod;
    }

    public void setDplatod(Date dplatod) {
        this.dplatod = dplatod;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdvoj() {
        return idvoj;
    }

    public void setIdvoj(long idvoj) {
        this.idvoj = idvoj;
    }

    public String getMisto() {
        return misto;
    }

    public void setMisto(String misto) {
        this.misto = misto;
    }

    public Long getTyp() {
        return typ;
    }

    public void setTyp(Long typ) {
        this.typ = typ;
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
    public Collection<Jsmisto> getJsmistoCollection() {
        return jsmistoCollection;
    }

    public void setJsmistoCollection(Collection<Jsmisto> jsmistoCollection) {
        this.jsmistoCollection = jsmistoCollection;
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
        if (!(object instanceof Jscmisto)) {
            return false;
        }
        Jscmisto other = (Jscmisto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityVera.Jscmisto[ id=" + id + " ]";
    }
    
}
