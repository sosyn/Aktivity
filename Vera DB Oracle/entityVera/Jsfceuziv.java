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
    @NamedQuery(name = "Jsfceuziv.findAll", query = "SELECT j FROM Jsfceuziv j"),
    @NamedQuery(name = "Jsfceuziv.findByDatin", query = "SELECT j FROM Jsfceuziv j WHERE j.datin = :datin"),
    @NamedQuery(name = "Jsfceuziv.findById", query = "SELECT j FROM Jsfceuziv j WHERE j.id = :id"),
    @NamedQuery(name = "Jsfceuziv.findByIdvoj", query = "SELECT j FROM Jsfceuziv j WHERE j.idvoj = :idvoj"),
    @NamedQuery(name = "Jsfceuziv.findByPlatnostvazbydo", query = "SELECT j FROM Jsfceuziv j WHERE j.platnostvazbydo = :platnostvazbydo"),
    @NamedQuery(name = "Jsfceuziv.findByPlatnostvazbyod", query = "SELECT j FROM Jsfceuziv j WHERE j.platnostvazbyod = :platnostvazbyod"),
    @NamedQuery(name = "Jsfceuziv.findByUserin", query = "SELECT j FROM Jsfceuziv j WHERE j.userin = :userin"),
    @NamedQuery(name = "Jsfceuziv.findByVersion", query = "SELECT j FROM Jsfceuziv j WHERE j.version = :version"),
    @NamedQuery(name = "Jsfceuziv.findByVychozi", query = "SELECT j FROM Jsfceuziv j WHERE j.vychozi = :vychozi")})
public class Jsfceuziv implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private short vychozi;
    @JoinColumn(name = "FUNKCNIZARAZENI", referencedColumnName = "ID")
    @ManyToOne
    private Jsfcezar funkcnizarazeni;
    @JoinColumn(name = "UZIVATEL", referencedColumnName = "ID")
    @ManyToOne
    private Jsuziv uzivatel;

    public Jsfceuziv() {
    }

    public Jsfceuziv(Long id) {
        this.id = id;
    }

    public Jsfceuziv(Long id, long idvoj, long version, short vychozi) {
        this.id = id;
        this.idvoj = idvoj;
        this.version = version;
        this.vychozi = vychozi;
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

    public short getVychozi() {
        return vychozi;
    }

    public void setVychozi(short vychozi) {
        this.vychozi = vychozi;
    }

    public Jsfcezar getFunkcnizarazeni() {
        return funkcnizarazeni;
    }

    public void setFunkcnizarazeni(Jsfcezar funkcnizarazeni) {
        this.funkcnizarazeni = funkcnizarazeni;
    }

    public Jsuziv getUzivatel() {
        return uzivatel;
    }

    public void setUzivatel(Jsuziv uzivatel) {
        this.uzivatel = uzivatel;
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
        if (!(object instanceof Jsfceuziv)) {
            return false;
        }
        Jsfceuziv other = (Jsfceuziv) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityVera.Jsfceuziv[ id=" + id + " ]";
    }
    
}
