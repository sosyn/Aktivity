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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "Jsmisto.findAll", query = "SELECT j FROM Jsmisto j"),
    @NamedQuery(name = "Jsmisto.findByDatin", query = "SELECT j FROM Jsmisto j WHERE j.datin = :datin"),
    @NamedQuery(name = "Jsmisto.findByDatout", query = "SELECT j FROM Jsmisto j WHERE j.datout = :datout"),
    @NamedQuery(name = "Jsmisto.findByDplatdo", query = "SELECT j FROM Jsmisto j WHERE j.dplatdo = :dplatdo"),
    @NamedQuery(name = "Jsmisto.findByDplatod", query = "SELECT j FROM Jsmisto j WHERE j.dplatod = :dplatod"),
    @NamedQuery(name = "Jsmisto.findByExtid", query = "SELECT j FROM Jsmisto j WHERE j.extid = :extid"),
    @NamedQuery(name = "Jsmisto.findById", query = "SELECT j FROM Jsmisto j WHERE j.id = :id"),
    @NamedQuery(name = "Jsmisto.findByIdvoj", query = "SELECT j FROM Jsmisto j WHERE j.idvoj = :idvoj"),
    @NamedQuery(name = "Jsmisto.findByLokalita", query = "SELECT j FROM Jsmisto j WHERE j.lokalita = :lokalita"),
    @NamedQuery(name = "Jsmisto.findByNazev", query = "SELECT j FROM Jsmisto j WHERE j.nazev = :nazev"),
    @NamedQuery(name = "Jsmisto.findByPerid", query = "SELECT j FROM Jsmisto j WHERE j.perid = :perid"),
    @NamedQuery(name = "Jsmisto.findByUrn", query = "SELECT j FROM Jsmisto j WHERE j.urn = :urn"),
    @NamedQuery(name = "Jsmisto.findByUserin", query = "SELECT j FROM Jsmisto j WHERE j.userin = :userin"),
    @NamedQuery(name = "Jsmisto.findByVersion", query = "SELECT j FROM Jsmisto j WHERE j.version = :version"),
    @NamedQuery(name = "Jsmisto.findByZkratka", query = "SELECT j FROM Jsmisto j WHERE j.zkratka = :zkratka")})
public class Jsmisto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datin;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datout;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dplatdo;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dplatod;
    @Size(max = 250)
    @Column(length = 250)
    private String extid;
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
    private String lokalita;
    @Size(max = 250)
    @Column(length = 250)
    private String nazev;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private long perid;
    @Size(max = 50)
    @Column(length = 50)
    private String urn;
    private Long userin;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private long version;
    @Size(max = 10)
    @Column(length = 10)
    private String zkratka;
    @JoinTable(name = "JSMISTOH", joinColumns = {
        @JoinColumn(name = "PODRIZENEMISTO", referencedColumnName = "ID", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "NADRIZENEMISTO", referencedColumnName = "ID", nullable = false)})
    @ManyToMany
    private Collection<Jsmisto> jsmistoCollection;
    @ManyToMany(mappedBy = "jsmistoCollection")
    private Collection<Jsmisto> jsmistoCollection1;
    @OneToMany(mappedBy = "misto")
    private Collection<Jsutvumi> jsutvumiCollection;
    @JoinColumn(name = "CMISTO", referencedColumnName = "ID")
    @ManyToOne
    private Jscmisto cmisto;
    @OneToMany(mappedBy = "misto")
    private Collection<Jsfcemisto> jsfcemistoCollection;

    public Jsmisto() {
    }

    public Jsmisto(Long id) {
        this.id = id;
    }

    public Jsmisto(Long id, long idvoj, long perid, long version) {
        this.id = id;
        this.idvoj = idvoj;
        this.perid = perid;
        this.version = version;
    }

    public Date getDatin() {
        return datin;
    }

    public void setDatin(Date datin) {
        this.datin = datin;
    }

    public Date getDatout() {
        return datout;
    }

    public void setDatout(Date datout) {
        this.datout = datout;
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

    public String getExtid() {
        return extid;
    }

    public void setExtid(String extid) {
        this.extid = extid;
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

    public String getLokalita() {
        return lokalita;
    }

    public void setLokalita(String lokalita) {
        this.lokalita = lokalita;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public long getPerid() {
        return perid;
    }

    public void setPerid(long perid) {
        this.perid = perid;
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
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

    public String getZkratka() {
        return zkratka;
    }

    public void setZkratka(String zkratka) {
        this.zkratka = zkratka;
    }

    @XmlTransient
    public Collection<Jsmisto> getJsmistoCollection() {
        return jsmistoCollection;
    }

    public void setJsmistoCollection(Collection<Jsmisto> jsmistoCollection) {
        this.jsmistoCollection = jsmistoCollection;
    }

    @XmlTransient
    public Collection<Jsmisto> getJsmistoCollection1() {
        return jsmistoCollection1;
    }

    public void setJsmistoCollection1(Collection<Jsmisto> jsmistoCollection1) {
        this.jsmistoCollection1 = jsmistoCollection1;
    }

    @XmlTransient
    public Collection<Jsutvumi> getJsutvumiCollection() {
        return jsutvumiCollection;
    }

    public void setJsutvumiCollection(Collection<Jsutvumi> jsutvumiCollection) {
        this.jsutvumiCollection = jsutvumiCollection;
    }

    public Jscmisto getCmisto() {
        return cmisto;
    }

    public void setCmisto(Jscmisto cmisto) {
        this.cmisto = cmisto;
    }

    @XmlTransient
    public Collection<Jsfcemisto> getJsfcemistoCollection() {
        return jsfcemistoCollection;
    }

    public void setJsfcemistoCollection(Collection<Jsfcemisto> jsfcemistoCollection) {
        this.jsfcemistoCollection = jsfcemistoCollection;
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
        if (!(object instanceof Jsmisto)) {
            return false;
        }
        Jsmisto other = (Jsmisto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityVera.Jsmisto[ id=" + id + " ]";
    }
    
}
