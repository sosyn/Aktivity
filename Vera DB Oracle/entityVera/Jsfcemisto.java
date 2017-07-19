/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityVera;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sosyn
 */
@Entity
@Table(catalog = "", schema = "VERA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jsfcemisto.findAll", query = "SELECT j FROM Jsfcemisto j"),
    @NamedQuery(name = "Jsfcemisto.findByDatin", query = "SELECT j FROM Jsfcemisto j WHERE j.datin = :datin"),
    @NamedQuery(name = "Jsfcemisto.findById", query = "SELECT j FROM Jsfcemisto j WHERE j.id = :id"),
    @NamedQuery(name = "Jsfcemisto.findByIdvoj", query = "SELECT j FROM Jsfcemisto j WHERE j.idvoj = :idvoj"),
    @NamedQuery(name = "Jsfcemisto.findByPlatnostvazbydo", query = "SELECT j FROM Jsfcemisto j WHERE j.platnostvazbydo = :platnostvazbydo"),
    @NamedQuery(name = "Jsfcemisto.findByPlatnostvazbyod", query = "SELECT j FROM Jsfcemisto j WHERE j.platnostvazbyod = :platnostvazbyod"),
    @NamedQuery(name = "Jsfcemisto.findByUserin", query = "SELECT j FROM Jsfcemisto j WHERE j.userin = :userin"),
    @NamedQuery(name = "Jsfcemisto.findByVersion", query = "SELECT j FROM Jsfcemisto j WHERE j.version = :version")})
public class Jsfcemisto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datin;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private long idvoj;
    @Temporal(TemporalType.TIMESTAMP)
    private Date platnostvazbydo;
    @Temporal(TemporalType.TIMESTAMP)
    private Date platnostvazbyod;
    private Long userin;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private long version;
    @JoinColumn(name = "FUNKCNIZARAZENI", referencedColumnName = "ID")
    @ManyToOne
    private Jsfcezar funkcnizarazeni;
    @JoinColumn(name = "MISTO", referencedColumnName = "ID")
    @ManyToOne
    private Jsmisto misto;

    public Jsfcemisto() {
    }

    public Jsfcemisto(Long id) {
        this.id = id;
    }

    public Jsfcemisto(Long id, long idvoj, long version) {
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

    public Date getPlatnostvazbydo() {
        return platnostvazbydo;
    }

    public void setPlatnostvazbydo(Date platnostvazbydo) {
        this.platnostvazbydo = platnostvazbydo;
    }

    public Date getPlatnostvazbyod() {
        return platnostvazbyod;
    }

    public void setPlatnostvazbyod(Date platnostvazbyod) {
        this.platnostvazbyod = platnostvazbyod;
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

    public Jsfcezar getFunkcnizarazeni() {
        return funkcnizarazeni;
    }

    public void setFunkcnizarazeni(Jsfcezar funkcnizarazeni) {
        this.funkcnizarazeni = funkcnizarazeni;
    }

    public Jsmisto getMisto() {
        return misto;
    }

    public void setMisto(Jsmisto misto) {
        this.misto = misto;
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
        if (!(object instanceof Jsfcemisto)) {
            return false;
        }
        Jsfcemisto other = (Jsfcemisto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityVera.Jsfcemisto[ id=" + id + " ]";
    }
    
}
