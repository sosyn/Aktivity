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
    @NamedQuery(name = "Jsutvumi.findAll", query = "SELECT j FROM Jsutvumi j"),
    @NamedQuery(name = "Jsutvumi.findByDatin", query = "SELECT j FROM Jsutvumi j WHERE j.datin = :datin"),
    @NamedQuery(name = "Jsutvumi.findById", query = "SELECT j FROM Jsutvumi j WHERE j.id = :id"),
    @NamedQuery(name = "Jsutvumi.findByIdvoj", query = "SELECT j FROM Jsutvumi j WHERE j.idvoj = :idvoj"),
    @NamedQuery(name = "Jsutvumi.findByPlatnostvazbydo", query = "SELECT j FROM Jsutvumi j WHERE j.platnostvazbydo = :platnostvazbydo"),
    @NamedQuery(name = "Jsutvumi.findByPlatnostvazbyod", query = "SELECT j FROM Jsutvumi j WHERE j.platnostvazbyod = :platnostvazbyod"),
    @NamedQuery(name = "Jsutvumi.findByUserin", query = "SELECT j FROM Jsutvumi j WHERE j.userin = :userin"),
    @NamedQuery(name = "Jsutvumi.findByVersion", query = "SELECT j FROM Jsutvumi j WHERE j.version = :version")})
public class Jsutvumi implements Serializable {

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
    @JoinColumn(name = "MISTO", referencedColumnName = "ID")
    @ManyToOne
    private Jsmisto misto;
    @JoinColumn(name = "UTVAR", referencedColumnName = "ID")
    @ManyToOne
    private Jsutvar utvar;

    public Jsutvumi() {
    }

    public Jsutvumi(Long id) {
        this.id = id;
    }

    public Jsutvumi(Long id, long idvoj, long version) {
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

    public Jsmisto getMisto() {
        return misto;
    }

    public void setMisto(Jsmisto misto) {
        this.misto = misto;
    }

    public Jsutvar getUtvar() {
        return utvar;
    }

    public void setUtvar(Jsutvar utvar) {
        this.utvar = utvar;
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
        if (!(object instanceof Jsutvumi)) {
            return false;
        }
        Jsutvumi other = (Jsutvumi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityVera.Jsutvumi[ id=" + id + " ]";
    }
    
}
