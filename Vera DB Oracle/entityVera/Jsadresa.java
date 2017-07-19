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
    @NamedQuery(name = "Jsadresa.findAll", query = "SELECT j FROM Jsadresa j"),
    @NamedQuery(name = "Jsadresa.findByCastob", query = "SELECT j FROM Jsadresa j WHERE j.castob = :castob"),
    @NamedQuery(name = "Jsadresa.findByCisdom", query = "SELECT j FROM Jsadresa j WHERE j.cisdom = :cisdom"),
    @NamedQuery(name = "Jsadresa.findByCisor", query = "SELECT j FROM Jsadresa j WHERE j.cisor = :cisor"),
    @NamedQuery(name = "Jsadresa.findByDatin", query = "SELECT j FROM Jsadresa j WHERE j.datin = :datin"),
    @NamedQuery(name = "Jsadresa.findById", query = "SELECT j FROM Jsadresa j WHERE j.id = :id"),
    @NamedQuery(name = "Jsadresa.findByIdvoj", query = "SELECT j FROM Jsadresa j WHERE j.idvoj = :idvoj"),
    @NamedQuery(name = "Jsadresa.findByObec", query = "SELECT j FROM Jsadresa j WHERE j.obec = :obec"),
    @NamedQuery(name = "Jsadresa.findByOkres", query = "SELECT j FROM Jsadresa j WHERE j.okres = :okres"),
    @NamedQuery(name = "Jsadresa.findByPosta", query = "SELECT j FROM Jsadresa j WHERE j.posta = :posta"),
    @NamedQuery(name = "Jsadresa.findByPsc", query = "SELECT j FROM Jsadresa j WHERE j.psc = :psc"),
    @NamedQuery(name = "Jsadresa.findByTypcdom", query = "SELECT j FROM Jsadresa j WHERE j.typcdom = :typcdom"),
    @NamedQuery(name = "Jsadresa.findByUlice", query = "SELECT j FROM Jsadresa j WHERE j.ulice = :ulice"),
    @NamedQuery(name = "Jsadresa.findByUserin", query = "SELECT j FROM Jsadresa j WHERE j.userin = :userin"),
    @NamedQuery(name = "Jsadresa.findByVersion", query = "SELECT j FROM Jsadresa j WHERE j.version = :version"),
    @NamedQuery(name = "Jsadresa.findByZnakor", query = "SELECT j FROM Jsadresa j WHERE j.znakor = :znakor")})
public class Jsadresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 48)
    @Column(length = 48)
    private String castob;
    private Long cisdom;
    private Long cisor;
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
    @Size(max = 48)
    @Column(length = 48)
    private String obec;
    @Size(max = 48)
    @Column(length = 48)
    private String okres;
    @Size(max = 48)
    @Column(length = 48)
    private String posta;
    @Size(max = 5)
    @Column(length = 5)
    private String psc;
    @Size(max = 1)
    @Column(length = 1)
    private String typcdom;
    @Size(max = 48)
    @Column(length = 48)
    private String ulice;
    private Long userin;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private long version;
    @Size(max = 1)
    @Column(length = 1)
    private String znakor;
    @OneToMany(mappedBy = "adresa")
    private Collection<Jsutvar> jsutvarCollection;

    public Jsadresa() {
    }

    public Jsadresa(Long id) {
        this.id = id;
    }

    public Jsadresa(Long id, long idvoj, long version) {
        this.id = id;
        this.idvoj = idvoj;
        this.version = version;
    }

    public String getCastob() {
        return castob;
    }

    public void setCastob(String castob) {
        this.castob = castob;
    }

    public Long getCisdom() {
        return cisdom;
    }

    public void setCisdom(Long cisdom) {
        this.cisdom = cisdom;
    }

    public Long getCisor() {
        return cisor;
    }

    public void setCisor(Long cisor) {
        this.cisor = cisor;
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

    public String getObec() {
        return obec;
    }

    public void setObec(String obec) {
        this.obec = obec;
    }

    public String getOkres() {
        return okres;
    }

    public void setOkres(String okres) {
        this.okres = okres;
    }

    public String getPosta() {
        return posta;
    }

    public void setPosta(String posta) {
        this.posta = posta;
    }

    public String getPsc() {
        return psc;
    }

    public void setPsc(String psc) {
        this.psc = psc;
    }

    public String getTypcdom() {
        return typcdom;
    }

    public void setTypcdom(String typcdom) {
        this.typcdom = typcdom;
    }

    public String getUlice() {
        return ulice;
    }

    public void setUlice(String ulice) {
        this.ulice = ulice;
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

    public String getZnakor() {
        return znakor;
    }

    public void setZnakor(String znakor) {
        this.znakor = znakor;
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
        if (!(object instanceof Jsadresa)) {
            return false;
        }
        Jsadresa other = (Jsadresa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityVera.Jsadresa[ id=" + id + " ]";
    }
    
}
